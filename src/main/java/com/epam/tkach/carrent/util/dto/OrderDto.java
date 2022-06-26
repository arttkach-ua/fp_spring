package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.entity.Car;
import com.epam.tkach.carrent.entity.User;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private int id;
    private User client;
    private Car car;
    private int daysCount;
    private String documents;
    private boolean withDriver;
    private double amount;
    private double damageAmount;
    private String comment;
}
