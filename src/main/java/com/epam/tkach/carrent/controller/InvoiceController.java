package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.exceptions.InvoiceProcessingException;
import com.epam.tkach.carrent.exceptions.NoSuchInvoiceException;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.service.InvoiceService;
import com.epam.tkach.carrent.service.TransactionService;
import com.epam.tkach.carrent.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class InvoiceController {
    private static final Logger logger = LogManager.getLogger(InvoiceController.class);

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;

    @GetMapping("/invoices/list")
    public String showInvoices(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                               @RequestParam(value = "paid_filter", required = false, defaultValue = "") Boolean paidFilter,
                               @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField,
                               Model model){
        ArrayList<Boolean> paidList = new ArrayList<>();
        paidList.add(true);
        paidList.add(false);

        User userFilter = null;

        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser.getRole().equals(Role.CLIENT)) userFilter = currentUser;
        } catch (NoSuchUserException e) {
            logger.error(e);
        }

        model.addAttribute(PageParameters.ENTITY_LIST, invoiceService.getPage(pageNumber,size,paidFilter,userFilter,sortField));
        model.addAttribute(PageParameters.PAID_LIST, paidList);
        model.addAttribute(PageParameters.SORT_LIST, invoiceService.getSortingList());
        //Setting filter fields
        model.addAttribute("paid_filter", paidFilter!=null ? paidFilter : "");
        model.addAttribute("sort_field", sortField!=null ? sortField : "");
        return Pages.INVOICES;
    }

    @GetMapping("/invoices/pay/{id}")
    public String paidInvoice(HttpSession session,
                              @PathVariable("id") int id, Model model){
        try {
            invoiceService.payInvoice(id);
            //Setting new balance to session

            session.setAttribute("balance", transactionService.getUserBalance( userService.getCurrentUser().getEmail()));
            return "redirect:/invoices/list";
        } catch (NoSuchInvoiceException | InvoiceProcessingException | NoSuchUserException e) {
            logger.error(e);
            model.addAttribute(PageParameters.ERROR_MESSAGE, e.getMessage());
            return Pages.ERROR;
        }
    }




}
