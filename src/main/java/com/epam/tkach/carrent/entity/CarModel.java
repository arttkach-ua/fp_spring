package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
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

    public static CarModel getFromDTO(CarModelDto dto){
        CarModel model = new CarModel();
        model.setId(dto.getId());
        model.setCarClass(dto.getCarClass());
        model.setBrand(dto.getCarBrand());
        model.setModelName(dto.getModelName());
        return model;
    }
    public static CarModelDto toDTO(CarModel carModel){
        CarModelDto dto = new CarModelDto();
        dto.setId(carModel.getId());
        dto.setCarBrand(carModel.getBrand());
        dto.setCarClass(carModel.getCarClass());
        dto.setModelName(carModel.getModelName());
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModel carModel = (CarModel) o;
        return id == carModel.id &&
                Objects.equals(brand, carModel.brand) &&
                carClass == carModel.carClass &&
                Objects.equals(modelName, carModel.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, carClass, modelName);
    }
}
