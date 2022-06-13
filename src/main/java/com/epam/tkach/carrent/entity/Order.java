package com.epam.tkach.carrent.entity;


import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

    public void calculateSum(){
        if (withDriver){
            rentSum = daysCount*(driverPrice + price);
        }else {
            rentSum = daysCount*price;
        }
    }



}
