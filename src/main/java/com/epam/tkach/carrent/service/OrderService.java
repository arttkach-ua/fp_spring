package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.entity.*;
import com.epam.tkach.carrent.entity.enums.InvoiceTypes;
import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.exceptions.NoSuchCarException;
import com.epam.tkach.carrent.exceptions.NoSuchInvoiceException;
import com.epam.tkach.carrent.exceptions.NoSuchOrderException;
import com.epam.tkach.carrent.exceptions.OrderProcessingException;
import com.epam.tkach.carrent.repos.OrderRepository;
import com.epam.tkach.carrent.util.dto.OrderDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import com.epam.tkach.carrent.util.pagination.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CarService carService;

    @Autowired
    InvoiceService invoiceService;

    public Order findById(int id) throws NoSuchOrderException {
        Optional<Order> opt = orderRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchOrderException(Messages.ORDER_NOT_FOUND);
        return opt.get();
    }

    @Transactional
    public void createNew(OrderDto dto) throws OrderProcessingException, NoSuchCarException {
        //1. creating new order
        Order order = Order.getFromDto(dto);
        order.setStatus(OrderStatuses.NEW);
        order.setDateTime(new Date());
        orderRepository.save(order);
        //2. set
        if (carService.findById(order.getCar().getId()).isAvailable()==false) throw new OrderProcessingException(Messages.CAR_IS_NOT_AVALIBLE);
        Car car = order.getCar();
        car.setAvailable(false);
        carService.save(car);
    }

    public Paged<Order> getPage(int pageNumber, int size, CarBrand brandFilter, OrderStatuses statusFilter, User userFilter, String sortField) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(getSortDirection(sortField), getColumnNameToSort(sortField)));
        Page<Order> postPage = orderRepository.findOrdersCustom(request,statusFilter,brandFilter, userFilter);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    @Transactional
    public void confirmOrder(int id) throws NoSuchOrderException, OrderProcessingException {
        Optional<Order> opt = orderRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchOrderException();
        Order order = opt.get();
        if (order.getStatus()!=OrderStatuses.NEW) throw new OrderProcessingException(Messages.ORDER_PROCESSING_ERROR);
        order.setStatus(OrderStatuses.APPROVED);
        orderRepository.save(order);
        invoiceService.save(invoiceService.createNew(order, InvoiceTypes.RENT,order.getRentSum(),null));
    }

    @Transactional
    public void declineOrder(int id, String comment) throws NoSuchOrderException {
        Order order = findById(id);
        order.setStatus(OrderStatuses.CANCELED);
        order.getCar().setAvailable(true);
        carService.save(order.getCar());
        save(order);
    }

    @Transactional
    public void closeOrder(int id) throws NoSuchOrderException {
        //1.Setting order status
        Order order = findById(id);
        order.setStatus(OrderStatuses.COMPLETE);
        save(order);
        //2. Setting car available to true
        Car car = order.getCar();
        car.setAvailable(true);
        carService.save(car);
    }

    public void save(Order order){
        orderRepository.save(order);
    }
    /**
     * Function that returns field that can be used to sort order list
     * @return
     */
    public List<String> getSortList() {
        List<String> sortList = new ArrayList<>();
        sortList.add("sum.down");
        sortList.add("sum.up");
        sortList.add("date.up");
        sortList.add("date.down");
        return sortList;
    }

    /**
     * Gets sort column from string(used in front)
     * @param sortName
     * @return column name for query.
     */
    public String getColumnNameToSort(String sortName){
        if (sortName==null) return "id";
        switch (sortName){
            case "sum.down":
            case "sum.up":
                return "rentSum";
            case "date.up":
            case "date.down":
                return "dateTime";
            default: return "id";
        }
    }

    /**
     * Gets sort direction from string(used in front)
     * @param sortName
     * @return Sort direction.
     */

    public Sort.Direction getSortDirection(String sortName){
        if (sortName==null) return Sort.DEFAULT_DIRECTION;
        switch (sortName){
            case "sum.down":
            case "date.down":
                return Sort.Direction.DESC;
            case "sum.up":
            case "date.up":
                return Sort.Direction.ASC;
            default: return Sort.DEFAULT_DIRECTION;
        }
    }

    @Transactional
    public void closeOrderWithDamage(OrderDto orderDto) throws NoSuchOrderException {
        //1.Setting order status
        Order order = findById(orderDto.getId());
        order.setStatus(OrderStatuses.HASDAMAGE);
        order.setManagerComment(orderDto.getComment());
        save(order);
        //2. Setting car available to true
        Car car = order.getCar();
        car.setAvailable(true);
        carService.save(car);
        //3. Create invoice
        Invoice invoice = invoiceService.createNew(order, InvoiceTypes.DAMAGE, orderDto.getDamageAmount(), orderDto.getComment());
        invoiceService.save(invoice);
    }
}
