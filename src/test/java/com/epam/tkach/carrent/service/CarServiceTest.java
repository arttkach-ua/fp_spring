package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.*;
import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.exceptions.NoSuchCarException;
import com.epam.tkach.carrent.exceptions.NoSuchCompleteSetException;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.CompleteSetDto;
import com.epam.tkach.carrent.util.dto.TariffDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CarServiceTest {
    @Autowired CarBrandService carBrandService;
    @Autowired CarModelService carModelService;
    @Autowired CompleteSetsService completeSetsService;
    @Autowired CarService carService;
    @Autowired TariffService tariffService;

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        addToDB(20);

        Paged<Car> pageActual = carService.getPage(pageNumber, size);

        assertEquals(pageActual.getPage().getSize(), 5);
        assertEquals(pageActual.getPage().toList(), getExpectedList(5));
    }


    @Test
    void findById() {
        addToDB(20);
        try {
            Car result = carService.findById(1);
            assertEquals(result, getExpectedList(1).get(0));

            carService.findById(99);
        } catch (NoSuchCarException e) {
            assertEquals(NoSuchCarException.class, e.getClass());
        }
    }

    @Test
    void getCarsForNewOrder() {
        addToDB(20);
        Paged<Car> result = carService.getCarsForNewOrder(1,5, null, null,null,null);
        assertEquals(result.getPage().toList(), getExpectedList(5));
        result = carService.getCarsForNewOrder(1,5, CarClass.ECONOMY, null,null,null);
        assertEquals(result.getPage().toList(), new ArrayList<Car>());

    }

    private void addToDB(int count){
        //Brand
        CarBrandDto carBrand = new CarBrandDto(1, "Audi");
        carBrandService.save(carBrand);
        //model
        CarModelDto modelDto = new CarModelDto(1, CarBrand.getFromDTO(carBrand), CarClass.PREMIUM, "RS6");
        carModelService.save(modelDto);
        //COmpleteSet
        CompleteSetDto csDto = new CompleteSetDto();
        csDto.setId(1);
        csDto.setCarBrand(CarBrand.getFromDTO(carBrand));
        csDto.setCarModel(CarModel.getFromDTO(modelDto));
        csDto.setFuelType(FuelTypes.BENZINE);
        csDto.setTransmission(TransmissionTypes.AUTOMATIC);
        csDto.setBodyStyle(BodyStyles.COMBI);
        csDto.setEngine(5);
        completeSetsService.save(csDto);
        //Tariff
        TariffDto tariffDto = new TariffDto(1,"TARIFF",100,200);
        tariffService.save(tariffDto);
        for (int i = 1; i < count+1; i++) {
            Car car = new Car();
            car.setBrand(CarBrand.getFromDTO(carBrand));
            car.setModel(CarModel.getFromDTO(modelDto));
            car.setCompleteSet(CompleteSet.getFromDTO(csDto));
            car.setTariff(Tariff.getFromDTO(tariffDto));
            car.setVinCode("vin" + i*999);
            car.setGraduationYear(2015);
            car.setStateNumber("Fd" + i + "DF");
            car.setAvailable(true);
            carService.save(car);
        }
    }

    private List<Car> getExpectedList (int count){
        List<Car> list = new ArrayList<>();
        CarBrand carBrand = new CarBrand(1, "Audi");
        CarModel model = new CarModel(1, carBrand, CarClass.PREMIUM, "RS6");
        Tariff tariff = new Tariff(1,"TARIFF",100,200);
        CompleteSet cs = new CompleteSet();
        cs.setId(1);
        cs.setCarModel(model);
        cs.setFuelType(FuelTypes.BENZINE);
        cs.setTransmission(TransmissionTypes.AUTOMATIC);
        cs.setBodyStyle(BodyStyles.COMBI);
        cs.setEngine(5);
        cs.generateName();
        for (int i = 1; i < count+1; i++) {
            Car car = new Car(i,carBrand,model,2015,cs,"Fd" + i + "DF","vin" + i*999,tariff, true);
            list.add(car);
        }
        return list;
    }


}