package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CarBrandServiceTest {
    @Autowired
    private CarBrandService carBrandService;

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        addBrandsToDB(20);

        Paged<CarBrand> pageActual = carBrandService.getPage(pageNumber, size);

        assertEquals(pageActual.getPage().getSize(), 5);
        assertEquals(pageActual.getPage().toList(), getExpectedList(5));
    }

    @Test
    void getAll() {
        List<CarBrand> list= carBrandService.getAll();
        assertEquals(0, list.size());
        addBrandsToDB(1);
        list= carBrandService.getAll();
        assertEquals(1, list.size());
    }

    @Test
    void save() {
        int count = 5;
        addBrandsToDB(count);
        List<CarBrand> list= carBrandService.getAll();
        assertEquals(5, list.size());
        assertEquals(list,getExpectedList(count));
    }

    List<CarBrand> getExpectedList(int count){
        List<CarBrand> list = new ArrayList<>();
        for (int i = 1; i < count+1; i++) {
            CarBrand brand = new CarBrand();
            brand.setId(i);
            brand.setCarBrandName("AUDI" + i);
            list.add(brand);
        }
        return list;
    }

    private void addBrandsToDB(int count){
        for (int i = 1; i < count+1; i++) {
            CarBrandDto brand = new CarBrandDto();
            brand.setCarBrandName("AUDI" + i);
            carBrandService.save(brand);
        }
    }
}