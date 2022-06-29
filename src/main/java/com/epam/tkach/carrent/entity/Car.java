package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.util.dto.CarDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "cars")
public class Car{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="brand_id")
    private CarBrand brand;

    @ManyToOne
    @JoinColumn(name="model_id")
    private CarModel model;

//    @JoinColumn(name = "")
//    @NotNull(message = "error.nullCarClass")
//    private CarClass carClass;

    @Column(name="graduation_year")
    private int graduationYear;

    @ManyToOne
    @JoinColumn(name = "complete_set_id")
    private CompleteSet completeSet;

    @Column(name = "state_number")
    private String stateNumber;

    @Column(name="vin_code")
    private String vinCode;

    @ManyToOne
    @JoinColumn(name="tariff_id")
    private Tariff tariff;

    @Column(name="available")
    private boolean available;

    public String getDescription(){
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(brand.getCarBrandName());
        joiner.add(completeSet.getName());
        joiner.add(stateNumber);
        return joiner.toString();
    }

    public static Car getFromDTO(CarDto dto){
        Car car = new Car();
        car.setId(dto.getId());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setCompleteSet(dto.getCompleteSet());
        car.setGraduationYear(dto.getYear());
        car.setStateNumber(dto.getStateNumber());
        car.setVinCode(dto.getVinCode());
        car.setTariff(dto.getTariff());
        car.setAvailable(true);
        return car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                graduationYear == car.graduationYear &&
                available == car.available &&
                brand.equals(car.brand) &&
                model.equals(car.model) &&
                completeSet.equals(car.completeSet) &&
                stateNumber.equals(car.stateNumber) &&
                vinCode.equals(car.vinCode) &&
                tariff.equals(car.tariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, graduationYear, completeSet, stateNumber, vinCode, tariff, available);
    }

    public static CarDto toDto(Car car){
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setYear(car.getGraduationYear());
        dto.setCompleteSet(car.getCompleteSet());
        dto.setTariff(car.getTariff());
        dto.setStateNumber(car.getStateNumber());
        dto.setVinCode(car.getVinCode());
        return dto;
    }


}
