package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.*;
import com.epam.tkach.carrent.entity.enums.*;
import com.epam.tkach.carrent.exceptions.NoSuchOrderException;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.exceptions.OrderProcessingException;
import com.epam.tkach.carrent.exceptions.UserExistsException;
import com.epam.tkach.carrent.repos.CarBrandRepository;
import com.epam.tkach.carrent.util.dto.*;
import com.epam.tkach.carrent.util.pagination.Paged;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
class OrderServiceTest {
    @Autowired
    CarBrandService carBrandService;
    @Autowired
    CarModelService carModelService;
    @Autowired
    CompleteSetsService completeSetsService;
    @Autowired
    CarService carService;
    @Autowired
    TariffService tariffService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;


    @Test
    void findById() {

        try {
            addToDB(20);
            Order order = orderService.findById(3);
            assertEquals(order.getId(),3);
            orderService.findById(3333);
        } catch (NoSuchUserException e) {
            assertEquals(NoSuchUserException.class, e.getClass());
        } catch (NoSuchOrderException e) {
            assertEquals(NoSuchOrderException.class, e.getClass());
        }

    }

    @Test
    void createNew() {
    }

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        try {
            addToDB(20);
            Paged<Order> pageActual = orderService.getPage(pageNumber, size,null,null,null, null);
            assertEquals(pageActual.getPage().getContent().size(), 5);
        } catch (NoSuchUserException e) {

        }
    }

    @Test
    void confirmOrder() {
        try {
            addToDB(20);
            orderService.confirmOrder(2);
            Order order = orderService.findById(2);
            assertEquals(OrderStatuses.APPROVED, order.getStatus());
            assertEquals(false,order.getCar().isAvailable());
        } catch (NoSuchUserException | NoSuchOrderException | OrderProcessingException e) {
            assertEquals(NoSuchOrderException.class, e.getClass());
        }
    }

    @Test
    void declineOrder() {
        try {
            addToDB(20);
            orderService.declineOrder(2, "test comment");
            Order order = orderService.findById(2);
            assertEquals(OrderStatuses.CANCELED, order.getStatus());
            assertEquals(true,order.getCar().isAvailable());
        } catch (NoSuchUserException | NoSuchOrderException e) {
            assertEquals(NoSuchOrderException.class, e.getClass());
        }
    }

    @Test
    void closeOrder() {
    }

    @Test
    void save() {
    }

    @Test
    void closeOrderWithDamage() {
    }

    private void addToDB(int count) throws NoSuchUserException {

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
        TariffDto tariffDto = new TariffDto(1, "TARIFF", 100, 200);
        tariffService.save(tariffDto);
        //Car
        Car car = new Car();
        car.setBrand(CarBrand.getFromDTO(carBrand));
        car.setModel(CarModel.getFromDTO(modelDto));
        car.setCompleteSet(CompleteSet.getFromDTO(csDto));
        car.setTariff(Tariff.getFromDTO(tariffDto));
        car.setVinCode("vin999");
        car.setGraduationYear(2015);
        car.setStateNumber("Fd1111DF");
        car.setAvailable(true);
        carService.save(car);
        //User
        try {
            userService.createNewUser(new UserDto(1,"admin@gmail.com","test","Test","9999","test","222","222",null,null));
            userService.createNewUser(new UserDto(2,"client@gmail.com","test","Test","9999","test","222","222",null,null));
        } catch (UserExistsException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < count+1; i++) {
            OrderDto orderDto = new OrderDto();
            orderDto.setCar(car);
            orderDto.setWithDriver(true);
            orderDto.setDocuments("Document");
            orderDto.setClient(userService.findUserByEmail("client@gmail.com"));
            orderDto.setWithDriver(true);
            orderDto.setDaysCount(2);
            orderService.createNew(orderDto);
        }

    }
}
