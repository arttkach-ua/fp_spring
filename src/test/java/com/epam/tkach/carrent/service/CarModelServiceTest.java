package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
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
class CarModelServiceTest {
    @Autowired
    CarModelService carModelService;
    @Autowired
    CarBrandService carBrandService;

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        addModelsToDB(20);

        Paged<CarModel> pageActual = carModelService.getPage(pageNumber, size);

        assertEquals(pageActual.getPage().getSize(), 5);
        assertEquals(pageActual.getPage().toList(), getExpectedList(5));
    }

    @Test
    void save() {
        int count = 5;
        addModelsToDB(count);
        List<CarModel> list= carModelService.findAll();
        assertEquals(5, list.size());
        assertEquals(list,getExpectedList(count));
    }

    @Test
    void findAll() {
        int count = 5;
        addModelsToDB(count);
        List<CarModel> list= carModelService.findAll();
        assertEquals(5, list.size());
        assertEquals(list,getExpectedList(count));
    }

    @Test
    void findByBrandId() {
        int count = 5;
        addModelsToDB(count);
        assertEquals(carModelService.findByBrandId(1), getExpectedList(5));
        assertEquals(carModelService.findByBrandId(22), new ArrayList<CarModel>());
    }

    @Test
    void findById() {
        int count = 5;
        addModelsToDB(count);
        try {
            assertEquals(carModelService.findById(2).getModelName(), "TEST2");
        } catch (NoSuchCarModelException e) {
            e.printStackTrace();
        }
        try {
            carModelService.findById(999);
        } catch (NoSuchCarModelException e) {
            assertEquals(NoSuchCarModelException.class, e.getClass());
        }
    }

    List<CarModel> getExpectedList(int count){
        CarBrand carBrand = new CarBrand(1, "AUDI");

        List<CarModel> list = new ArrayList<>();
        for (int i = 1; i < count+1; i++) {
            CarModel model = new CarModel();
            model.setId(i);
            model.setCarClass(CarClass.BUSINESS);
            model.setBrand(carBrand);
            model.setModelName("TEST" + i);

            list.add(model);
        }
        return list;
    }

    private void addModelsToDB(int count){
        CarBrandDto brand = new CarBrandDto();
        brand.setCarBrandName("AUDI");
        brand.setId(1);

        carBrandService.save(brand);
        CarBrand carBrand = new CarBrand(1, "AUDI");

        for (int i = 1; i < count+1; i++) {
            CarModelDto modelDto = new CarModelDto();
            modelDto.setCarClass(CarClass.BUSINESS);
            modelDto.setModelName("TEST" + i);
            modelDto.setCarBrand(carBrand);
            carModelService.save(modelDto);
        }
    }
}