package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.CompleteSet;
import com.epam.tkach.carrent.entity.Tariff;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarDto {

    int id;

    @NotNull(message = "error.nullCarBrand")
    private CarBrand brand;

    @NotNull(message = "error.nullCarModel")
    private CarModel model;

    @NotNull(message = "error.nullGraduationYear")
    private int year;

    @NotNull(message = "error.nullCompleteSet")
    private CompleteSet completeSet;

    @NotNull(message = "error.nullStateNumber")
    @Size(min = 2, message = "error.nullStateNumber")
    private String stateNumber;

    @NotNull(message = "error.nullVinNumber")
    @Size(min = 2, message = "error.nullVinNumber")
    private String vinCode;

    @NotNull(message = "error.nullTariff")
    private Tariff tariff;

    private boolean available;
}
