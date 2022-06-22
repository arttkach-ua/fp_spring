package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {
    private static final Logger logger = LogManager.getLogger(CarController.class);
    @Autowired
    private CarService carService;

    @GetMapping("cars/list")
    public String showCarsList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                Model model) {

        model.addAttribute(PageParameters.ENTITY_LIST, carService.getPage(pageNumber, size));
        return Pages.CAR_BRANDS;
    }
}
