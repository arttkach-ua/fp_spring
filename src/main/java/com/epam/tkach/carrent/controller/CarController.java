package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.Car;
import com.epam.tkach.carrent.exceptions.NoSuchCarException;
import com.epam.tkach.carrent.service.*;
import com.epam.tkach.carrent.util.dto.CarDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

@Controller
public class CarController {
    private static final Logger logger = LogManager.getLogger(CarController.class);
    @Autowired
    private CarService carService;

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private CompleteSetsService completeSetsService;

    @GetMapping("/cars/list")
    public String showCarsList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                Model model) {

        model.addAttribute(PageParameters.ENTITY_LIST, carService.getPage(pageNumber, size));
        return Pages.CARS;
    }

    @GetMapping("/cars/add")
    public String openCarPage(Model model){
        CarDto carDto = new CarDto();
        carDto.setYear(Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute(PageParameters.CAR_DTO, carDto);
        model.addAttribute(PageParameters.TARIFF_LIST,tariffService.getAll());
        model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
        return Pages.CAR_PAGE;
    }

    @GetMapping("/cars/edit/{id}")
    public String openEditPage(@PathVariable("id") int id, Model model) {
        try {
            Car car = carService.findById(id);
            model.addAttribute(PageParameters.CAR_DTO, Car.toDto(car));
            model.addAttribute(PageParameters.TARIFF_LIST,tariffService.getAll());
            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            model.addAttribute(PageParameters.CAR_MODELS, carModelService.findByBrandId(car.getBrand().getId()));
            model.addAttribute(PageParameters.COMPLETE_SETS, completeSetsService.findByModelId(car.getModel().getId()));
            return Pages.CAR_PAGE;
        } catch (NoSuchCarException e) {
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @PostMapping("/cars/save")
    public String saveCar(@ModelAttribute @Valid CarDto carDto,
                          BindingResult bindingResult,
                          Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute(PageParameters.CAR_DTO, carDto);
            model.addAttribute(PageParameters.TARIFF_LIST,tariffService.getAll());
            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            return Pages.CAR_PAGE;
        }
        carService.save(carDto);
        System.out.println("-----");
        return "redirect:/cars/list";
    }
}
