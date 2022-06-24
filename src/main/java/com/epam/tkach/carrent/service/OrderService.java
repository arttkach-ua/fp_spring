package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.Car;
import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.Order;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.OrderStatuses;
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

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CarService carService;

    @Transactional
    public void createNew(OrderDto dto){
        //1. creating new order
        Order order = Order.getFromDto(dto);
        order.setStatus(OrderStatuses.NEW);
        order.setDateTime(new Date());
        orderRepository.save(order);
        //2. set
        Car car = order.getCar();
        car.setAvailable(false);
        carService.save(car);
    }

    public Paged<Order> getPage(int pageNumber, int size, CarBrand brandFilter, OrderStatuses statusFilter, User userFilter, String sortField) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(getSortDirection(sortField), getColumnNameToSort(sortField)));
        Page<Order> postPage = orderRepository.findOrdersCustom(request,statusFilter,brandFilter, userFilter);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
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
}
