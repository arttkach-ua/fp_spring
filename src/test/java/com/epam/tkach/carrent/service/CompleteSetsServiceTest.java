package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.CompleteSet;
import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.exceptions.NoSuchCompleteSetException;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.CompleteSetDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CompleteSetsServiceTest {
    @Autowired
    CompleteSetsService completeSetsService;
    @Autowired
    CarBrandService carBrandService;
    @Autowired
    CarModelService carModelService;

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        addToDB(20);

        Paged<CompleteSet> pageActual = completeSetsService.getPage(pageNumber, size);

        assertEquals(pageActual.getPage().getSize(), 5);
        assertEquals(pageActual.getPage().toList(), getExpectedList(5));
    }

    @Test
    void findById() {
        addToDB(5);
        try {
            assertEquals("RS6/COMBI/AUTOMATIC/BENZINE/5.0", completeSetsService.findById(1).getName());
        } catch (NoSuchCompleteSetException e) {}

        try {
            completeSetsService.findById(999);
        } catch (NoSuchCompleteSetException e) {
            assertEquals(NoSuchCompleteSetException.class, e.getClass());
        }
    }

    @Test
    void findByModelId() {
        addToDB(5);
        assertEquals(5,completeSetsService.findByModelId(1).size());
        assertEquals(0,completeSetsService.findByModelId(222).size());
    }

    List<CompleteSet> getExpectedList(int count){
        List<CompleteSet> list = new ArrayList<>();
        CarBrandDto carBrand = new CarBrandDto(1, "Audi");
        CarModelDto modelDto = new CarModelDto(1,CarBrand.getFromDTO(carBrand), CarClass.PREMIUM,"RS6");
        for (int i = 1; i < count+1; i++) {
            CompleteSet cs = new CompleteSet(i,"RS6/COMBI/AUTOMATIC/BENZINE/5.0",CarModel.getFromDTO(modelDto),BodyStyles.COMBI,TransmissionTypes.AUTOMATIC, FuelTypes.BENZINE,5);
            list.add(cs);
        }
        return list;
    }

    private void addToDB(int count){
        for (int i = 1; i < count+1; i++) {
            CarBrandDto carBrand = new CarBrandDto(1, "Audi");
            carBrandService.save(carBrand);
            CarModelDto modelDto = new CarModelDto(1,CarBrand.getFromDTO(carBrand), CarClass.PREMIUM,"RS6");
            carModelService.save(modelDto);
            CompleteSetDto dto = new CompleteSetDto();
            dto.setCarBrand(CarBrand.getFromDTO(carBrand));
            dto.setCarModel(CarModel.getFromDTO(modelDto));
            dto.setFuelType(FuelTypes.BENZINE);
            dto.setTransmission(TransmissionTypes.AUTOMATIC);
            dto.setBodyStyle(BodyStyles.COMBI);
            dto.setEngine(5);
            //i, CarBrand.getFromDTO(carBrand),"test" + i, CarModel.getFromDTO(modelDto), BodyStyles.COMBI, TransmissionTypes.AUTOMATIC, FuelTypes.BENZINE,5);
            completeSetsService.save(dto);
        }
    }
}