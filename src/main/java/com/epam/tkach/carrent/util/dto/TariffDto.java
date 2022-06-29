package com.epam.tkach.carrent.util.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TariffDto {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffDto tariffDto = (TariffDto) o;
        return id == tariffDto.id &&
                Double.compare(tariffDto.rentPrice, rentPrice) == 0 &&
                Double.compare(tariffDto.driverPrice, driverPrice) == 0 &&
                Objects.equals(name, tariffDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rentPrice, driverPrice);
    }

    int id;

    @NotNull(message = "error.nullTariffName")
    @Size(min = 2,max = 150,message = "error.nullTariffName")
    private String name;

    @Positive(message = "error.nullRentPriceName")
    private double rentPrice;

    @Positive(message = "error.nullDriverPriceName")
    private double driverPrice;
}
