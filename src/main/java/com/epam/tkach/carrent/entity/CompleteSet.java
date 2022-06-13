package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Override
    public String toString() {
        return "CompleteSet{" +
                "name='" + name + '\'' +
                ", carModel=" + carModel +
                ", bodyStyle=" + bodyStyle +
                ", transmission=" + transmission +
                ", fuelType=" + fuelType +
                ", engine=" + engine +
                ", ID=" + id +
                '}';
    }
}
