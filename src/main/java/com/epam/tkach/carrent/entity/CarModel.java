package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.CarClass;
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
@Table(name = "car_models")
public class CarModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "car_brands_id")
    @NotNull(message = "error.nullCarBrand")
    CarBrand brand;

    @Column(name = "car_classes_id")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "error.nullCarClass")
    CarClass carClass;

    @Column(name = "car_model")
    @Size(min = 1,max = 150,message = "error.nullCarModelName")
    String modelName;

}
