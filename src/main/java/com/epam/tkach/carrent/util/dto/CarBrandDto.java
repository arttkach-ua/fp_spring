package com.epam.tkach.carrent.util.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarBrandDto {

    private int Id;

    @NotNull(message = "error.nullCarBrandName")
    @Size(min = 2,max = 150,message = "error.nullCarBrandName")
    String carBrandName;
}
