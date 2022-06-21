package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.util.dto.TariffDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
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

    public static Tariff getFromDTO(TariffDto dto){
        Tariff tariff = new Tariff();
        tariff.setId(dto.getId());
        tariff.setName(dto.getName());
        tariff.setRentPrice(dto.getRentPrice());
        tariff.setDriverPrice(dto.getDriverPrice());
        return tariff;
    }

    public static TariffDto toDTO(Tariff tariff){
        TariffDto dto = new TariffDto();
        dto.setId(tariff.getId());
        dto.setName(tariff.getName());
        dto.setRentPrice(tariff.getRentPrice());
        dto.setDriverPrice(tariff.getDriverPrice());
        return dto;
    }
}
