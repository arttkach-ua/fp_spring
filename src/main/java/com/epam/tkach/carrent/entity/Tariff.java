package com.epam.tkach.carrent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tariffs")
public class Tariff{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="name")
    @NotNull(message = "error.nullTariffName")
    @Size(min = 2,max = 150,message = "error.nullTariffName")
    private String name;

    @Column(name = "rent_price")
    @Positive(message = "error.nullRentPriceName")
    private double rentPrice;

    @Column(name = "driver_price")
    @Positive(message = "error.nullDriverPriceName")
    private double driverPrice;


    @Override
    public String toString() {
        return "Tariff{" +
                "name='" + name + '\'' +
                ", rentPrice=" + rentPrice +
                ", driverPrice=" + driverPrice +
                ", ID=" + id +
                '}';
    }
}
