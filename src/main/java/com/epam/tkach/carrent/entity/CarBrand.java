package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "car_brands")
public class CarBrand{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "brand", nullable = false, unique = true)
    @NotNull(message = "error.nullCarBrandName")
    @Size(min = 2,max = 150,message = "error.nullCarBrandName")
    String carBrandName;

    public static CarBrand getFromDTO(CarBrandDto dto){
        CarBrand carBrand = new CarBrand();
        carBrand.setId(dto.getId());
        carBrand.setCarBrandName(dto.getCarBrandName());

        return carBrand;
    }
}