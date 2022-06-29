package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.util.dto.TariffDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TariffTest {

    @Test
    void getFromDTO() {
        Tariff expected = new Tariff();
        expected.setId(1);
        expected.setName("Test");
        expected.setDriverPrice(99);
        expected.setRentPrice(999);

        TariffDto dto = new TariffDto(1,"Test",999,99);
        assertEquals(expected, Tariff.getFromDTO(dto));
    }

    @Test
    void toDTO() {
        TariffDto expected = new TariffDto(1,"Test",999,99);
        Tariff tariff = new Tariff(1,"Test",999,99);
        assertEquals(expected, Tariff.toDTO(tariff));
    }
}