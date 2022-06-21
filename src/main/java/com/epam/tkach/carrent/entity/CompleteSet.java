package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.util.dto.CompleteSetDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "complete_sets", schema = "car_rent_spring")
public class CompleteSet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    @NotNull(message = "error.nullCompleteSetName")
    @Size(min=2)
    private String name;

    @ManyToOne
    @JoinColumn(name = "model_id")
    @NotNull(message = "error.nullCarModel")
    private CarModel carModel;

    @Column(name = "body_style_id")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "error.nullBodyStyle")
    private BodyStyles bodyStyle;

    @Column(name = "transmission_id")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "error.nullTransmissionType")
    private TransmissionTypes transmission;

    @Column(name = "fuel_type_id")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "error.nullFuelType")
    private FuelTypes fuelType;

    @Column(name="engine")
    @Positive(message = "error.nullRentPriceName")
    double engine;


    public void generateName(){
        StringJoiner joiner = new StringJoiner("/");
        joiner.add(this.carModel.getModelName());
        joiner.add(this.getBodyStyle().name());
        joiner.add(this.getTransmission().name());
        joiner.add(this.getFuelType().name());
        joiner.add(Double.toString(this.getEngine()));
        this.name = joiner.toString();
    }

    public static CompleteSet getFromDTO(CompleteSetDto dto){
        CompleteSet completeSet = new CompleteSet();
        completeSet.setId(dto.getId());
        completeSet.setCarModel(dto.getCarModel());
        completeSet.setBodyStyle(dto.getBodyStyle());
        completeSet.setTransmission(dto.getTransmission());
        completeSet.setFuelType(dto.getFuelType());
        completeSet.setEngine(dto.getEngine());
        completeSet.generateName();

        return completeSet;
    }

    public static CompleteSetDto toDTO(CompleteSet completeSet){
        CompleteSetDto dto = new CompleteSetDto();
        dto.setId(completeSet.getId());
        dto.setName(completeSet.getName());
        dto.setBodyStyle(completeSet.getBodyStyle());
        dto.setCarBrand(completeSet.getCarModel().getBrand());
        dto.setCarModel(completeSet.getCarModel());
        dto.setTransmission(completeSet.getTransmission());
        dto.setFuelType(completeSet.getFuelType());
        dto.setEngine(completeSet.getEngine());
        return dto;
    }
}
