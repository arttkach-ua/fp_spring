package com.epam.tkach.carrent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_brands")
public class CarBrand{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "brand", nullable = false, unique = true)
    @NotNull(message = "error.nullCarBrandName")
    @Size(min = 2,max = 150,message = "error.nullCarBrandName")
    String carBrandName;

}