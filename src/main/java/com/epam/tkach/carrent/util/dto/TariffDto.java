package com.epam.tkach.carrent.util.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TariffDto {

    int id;

    @NotNull(message = "error.nullTariffName")
    @Size(min = 2,max = 150,message = "error.nullTariffName")
    private String name;

    @Positive(message = "error.nullRentPriceName")
    private double rentPrice;

    @Positive(message = "error.nullDriverPriceName")
    private double driverPrice;
}
