package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import lombok.*;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompleteSetDto {
    int id;

    @NotNull(message = Messages.ERROR_NULL_CAR_BRAND)
    CarBrand carBrand;

//    @NotNull(message = "error.nullCompleteSetName")
//    @Size(min=2)
    private String name;

    @NotNull(message = "error.nullCarModel")
    private CarModel carModel;

    @NotNull(message = "error.nullBodyStyle")
    private BodyStyles bodyStyle;

    @NotNull(message = "error.nullTransmissionType")
    private TransmissionTypes transmission;

    @NotNull(message = "error.nullFuelType")
    private FuelTypes fuelType;

    @Positive(message = "error.nullRentPriceName")
    double engine;

    public int getCarBrandId(){
        if (carModel!=null) {
            return carModel.getBrand().getId();
        } else{
            return -1;
        }
    }
    public int getCarModelId(){
        if (carModel==null){
            return -1;
        }else{
            return carModel.getId();
        }
    }
}
