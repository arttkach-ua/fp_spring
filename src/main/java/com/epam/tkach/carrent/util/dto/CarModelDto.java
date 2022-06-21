package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.enums.CarClass;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarModelDto {
    int id;
    @NotNull(message = "error.nullCarBrand")
    CarBrand carBrand;
    @NotNull(message = "error.nullCarClass")
    CarClass carClass;
    @Size(min = 1,max = 150,message = "error.nullCarModelName")
    String modelName;
}
