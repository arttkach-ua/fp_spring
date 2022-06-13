package com.epam.tkach.carrent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="brand_id")
    @NotNull(message = "error.nullCarBrand")
    private CarBrand brand;

    @ManyToOne
    @JoinColumn(name="model_id")
    @NotNull(message = "error.nullCarModel")
    private CarModel model;

//    @JoinColumn(name = "")
//    @NotNull(message = "error.nullCarClass")
//    private CarClass carClass;

    @Column(name="graduation_year")
    @NotNull(message = "error.nullGraduationYear")
    private int graduationYear;

    @ManyToOne
    @JoinColumn(name = "complete_set_id")
    @NotNull
    private CompleteSet completeSet;

    @Column(name = "state_number")
    @NotNull(message = "error.nullStateNumber")
    @Size(min = 2, message = "error.nullStateNumber")
    private String stateNumber;

    @Column(name="vin_code")
    @NotNull(message = "error.nullVinNumber")
    @Size(min = 2, message = "error.nullVinNumber")
    private String vinCode;

    @ManyToOne
    @JoinColumn(name="tariff_id")
    @NotNull(message = "error.nullTariff")
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

}
