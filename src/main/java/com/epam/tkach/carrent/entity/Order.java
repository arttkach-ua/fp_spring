package com.epam.tkach.carrent.entity;


import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.util.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="client_id")
    @NotNull
    private User client;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private OrderStatuses status;

    @ManyToOne
    @JoinColumn(name="car_id")
    @NotNull
    private Car car;

    @Column(name="day_counts")
    @Positive
    private int daysCount;

    @Column(name="price")
    @Positive
    private double price;

    @Column(name="driver_price")
    @Positive
    private double driverPrice;

    @Column(name="docements")
    @NotNull
    private String documents;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private User manager;

    @Column(name="rent_sum")
    private double rentSum;

    @Column(name="manager_comment")
    private String managerComment;

    @Column(name="with_driver")
    private boolean withDriver;

    @Column(name="timestamp")
    private Date dateTime;

    public void calculateSum(){
        if (withDriver){
            rentSum = daysCount*(driverPrice + price);
        }else {
            rentSum = daysCount*price;
        }
    }

    public String getFormattedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy h:mm a");
        return sdf.format(dateTime);
    }

    public static Order getFromDto(OrderDto dto){
        Order order = new Order();
        order.setCar(dto.getCar());
        order.setClient(dto.getClient());
        order.setDaysCount(dto.getDaysCount());
        order.setDocuments(dto.getDocuments());
        order.setPrice(dto.getCar().getTariff().getRentPrice());
        order.setDriverPrice(dto.getCar().getTariff().getDriverPrice());
        order.setWithDriver(dto.isWithDriver());
        order.calculateSum();
        return order;
    }
}
