package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.entity.*;
import com.epam.tkach.carrent.entity.enums.InvoiceTypes;
import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.exceptions.InvoiceProcessingException;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.exceptions.NoSuchInvoiceException;
import com.epam.tkach.carrent.repos.InvoiceRepository;
import com.epam.tkach.carrent.util.pagination.Paged;
import com.epam.tkach.carrent.util.pagination.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    TransactionService transactionService;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    OrderService orderService;

    public List<String> getSortingList() {
        List<String> sortList = new ArrayList<>();
        sortList.add("amount.up");
        sortList.add("amount.down");
        sortList.add("date.up");
        sortList.add("date.down");
        sortList.add("paid.up");
        sortList.add("paid.down");
        return sortList;
    }

    /**
     * Gets sort column from string(used in front)
     *
     * @param sortName
     * @return column name for query.
     */
    public static String getColumnNameToSort(String sortName) {
        if (sortName == null) return "id";
        switch (sortName) {
            case "amount.down":
            case "amount.up":
                return "amount";
            case "date.up":
            case "date.down":
                return "dateTime";
            case "paid.up":
            case "paid.down":
                return "paid";
            default:
                return "id";
        }
    }

    /**
     * Gets sort direction from string(used in front)
     *
     * @param sortName
     * @return Sort direction.
     */

    public Sort.Direction getSortDirection(String sortName) {
        if (sortName == null) return Sort.DEFAULT_DIRECTION;
        switch (sortName) {
            case "amount.down":
            case "paid.down":
            case "date.down":
                return Sort.Direction.DESC;
            case "amount.up":
            case "paid.up":
            case "date.up":
                return Sort.Direction.ASC;
            default:
                return Sort.DEFAULT_DIRECTION;
        }
    }

    public Invoice createNew(Order order, InvoiceTypes invoiceType, double amount, String damage) {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setType(invoiceType);
        invoice.setClient(order.getClient());
        if (invoiceType == InvoiceTypes.RENT) {
            invoice.setDescription("For car rent by order №" + order.getId());
            invoice.setAmount(order.getRentSum());
        } else {
            invoice.setDescription("Order №" + order.getId() + damage);
            invoice.setAmount(amount);
        }
        invoice.setPaid(false);
        return invoice;
    }

    @Transactional
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public Paged<Invoice> getPage(int pageNumber, int size, Boolean paidFilter, User userFilter, String sortField) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(getSortDirection(sortField), getColumnNameToSort(sortField)));
        Page<Invoice> postPage = invoiceRepository.findInvoicesCustom(request, paidFilter, userFilter);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public Invoice findById(int id) throws NoSuchInvoiceException {
        Optional<Invoice> opt = invoiceRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchInvoiceException("error.CarModelNotFound");
        return opt.get();
    }

    public boolean checkPayment(int userId, double amount) {
        double balance = transactionService.getUserBalance(userId);
        return balance > amount;
    }

    @Transactional
    public void payInvoice(int id) throws NoSuchInvoiceException, InvoiceProcessingException {

        Invoice invoice = findById(id);
        if (invoice.isPaid()) throw new InvoiceProcessingException("error.invoiceProcessing");
        if (checkPayment(invoice.getClient().getId(),invoice.getAmount())==false) throw new InvoiceProcessingException(Messages.ERROR_LOW_BALANCE);
        //do payment
        //1. Adding transaction
        transactionService.createPayment(invoice.getClient(), id, invoice.getAmount());
        //2. Setting order status
        Order order = invoice.getOrder();
        order.setStatus(OrderStatuses.INPROGRESS);
        orderService.save(order);
        //3. Setting paid to invoice
        invoice.setPaid(true);
        save(invoice);
    }


}
