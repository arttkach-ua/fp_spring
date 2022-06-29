package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.Tariff;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.exceptions.NoSuchTariffException;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.TariffDto;
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
class TariffServiceTest {
    @Autowired
    TariffService tariffService;

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        addToDB(20);

        Paged<Tariff> pageActual = tariffService.getPage(pageNumber, size);

        assertEquals(pageActual.getPage().getSize(), 5);
        assertEquals(pageActual.getPage().toList(), getExpectedList(5));
    }

    @Test
    void save() {
        int count = 5;
        addToDB(count);
        List<Tariff> list= tariffService.getAll();
        assertEquals(5, list.size());
        assertEquals(list,getExpectedList(count));
    }

    @Test
    void findById() {
        int count = 5;
        addToDB(count);
        try {
            assertEquals(tariffService.findById(2).getName(), "TEST2");
        } catch (NoSuchTariffException e) {
            e.printStackTrace();
        }
        try {
            tariffService.findById(999);
        } catch (NoSuchTariffException e) {
            assertEquals(NoSuchTariffException.class, e.getClass());
        }
    }

    @Test
    void getAll() {
        int count = 5;
        addToDB(count);
        List<Tariff> list= tariffService.getAll();
        assertEquals(5, list.size());
        assertEquals(list,getExpectedList(count));
    }

    List<Tariff> getExpectedList(int count){

        List<Tariff> list = new ArrayList<>();
        for (int i = 1; i < count+1; i++) {
            Tariff tariff = new Tariff();
            tariff.setId(i);
            tariff.setName("TEST" + i);
            tariff.setDriverPrice(i*33);
            tariff.setRentPrice(i*24);
            list.add(tariff);
        }
        return list;
    }

    private void addToDB(int count){
        for (int i = 1; i < count+1; i++) {
            TariffDto dto = new TariffDto();
            dto.setName("TEST" + i);
            dto.setDriverPrice(i*33);
            dto.setRentPrice(i*24);
            tariffService.save(dto);
        }
    }
}