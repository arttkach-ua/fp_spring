package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.service.CarBrandService;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
public class CarBrandController {
    private static final Logger logger = LogManager.getLogger(CarBrandController.class);

    @Autowired
    private CarBrandService carBrandService;

    @GetMapping(value = Pages.CAR_BRANDS)
    public String showCarBrands(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                Model model) {

        model.addAttribute("carBrands", carBrandService.getPage(pageNumber, size));
        return Pages.CAR_BRANDS;
    }

    @PostMapping(value = Pages.NEW_CAR_BRAND)
    public String addCarBrand(@ModelAttribute @Valid CarBrandDto carBrandDto,
                              BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("carBrandDto", carBrandDto);
            return "newCarBrand";//Pages.NEW_CAR_BRAND;
        }
        logger.debug("adding new car brand");
        logger.debug(carBrandDto.toString());
        carBrandService.save(carBrandDto);

        return "redirect:/" + Pages.CAR_BRANDS;
    }

    @GetMapping(value = "addNewBrand")
    public String addNewBrand(Model model){
        model.addAttribute("carBrandDto", new CarBrandDto());
        return Pages.NEW_CAR_BRAND;
    }
}
