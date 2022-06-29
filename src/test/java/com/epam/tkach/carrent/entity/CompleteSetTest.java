package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.CompleteSetDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompleteSetTest {

    @Test
    void toDTO() {
        CarBrandDto carBrand = new CarBrandDto(1, "Audi");
        CarModelDto modelDto = new CarModelDto(1,CarBrand.getFromDTO(carBrand), CarClass.PREMIUM,"RS6");
        CompleteSet cs = new CompleteSet(1,"RS6/COMBI/AUTOMATIC/BENZINE/5.0",CarModel.getFromDTO(modelDto), BodyStyles.COMBI, TransmissionTypes.AUTOMATIC, FuelTypes.BENZINE,5);
        CompleteSetDto dto = new CompleteSetDto();
        dto.setId(1);
        dto.setCarBrand(CarBrand.getFromDTO(carBrand));
        dto.setCarModel(CarModel.getFromDTO(modelDto));
        dto.setFuelType(FuelTypes.BENZINE);
        dto.setTransmission(TransmissionTypes.AUTOMATIC);
        dto.setBodyStyle(BodyStyles.COMBI);
        dto.setEngine(5);
        dto.setName("RS6/COMBI/AUTOMATIC/BENZINE/5.0");
        CompleteSetDto result = CompleteSet.toDTO(cs);
        assertTrue(dto.equals(result));
    }
}