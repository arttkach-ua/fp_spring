package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.service.CarBrandService;
import com.epam.tkach.carrent.service.CarModelService;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class CarModelController {
    private static final Logger logger = LogManager.getLogger(CarModelController.class);

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarModelService carModelService;

    @GetMapping(value = Pages.CAR_MODELS)
    public String showCarBrands(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {

        model.addAttribute(PageParameters.CAR_MODELS, carModelService.getPage(pageNumber, size));
        return Pages.CAR_MODELS;
    }

    @PostMapping(value = "/saveCarModel")
    public String addNewCarModel(@Valid CarModelDto carModelDto,
                                 BindingResult bindingResult,
                                 Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute(PageParameters.CAR_MODEL_DTO, carModelDto);
            model.addAttribute(PageParameters.CAR_CLASSES, CarClass.values());
            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            return Pages.CAR_MODEL_PAGE;
        }
        carModelService.save(carModelDto);
        System.out.println(carModelDto.toString());
        logger.debug("adding new Car model");
        logger.debug(carModelDto.toString());
        return "redirect:/" + Pages.CAR_MODELS;
    }

    @GetMapping(value = "newCarModel")
    public String openNewCarModelPage(Model model){
        model.addAttribute(PageParameters.CAR_MODEL_DTO, new CarModelDto());
        model.addAttribute(PageParameters.CAR_CLASSES, CarClass.values());
        model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
        return Pages.CAR_MODEL_PAGE;
    }
    @GetMapping(value = "carModel/edit/{id}")
    public String editCarModel(@PathVariable("id") int id, Model model){
        try {
            CarModel carModel = carModelService.findById(id);
            CarModelDto dto = CarModel.toDTO(carModel);

            model.addAttribute(PageParameters.CAR_CLASSES, CarClass.values());
            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            model.addAttribute(PageParameters.CAR_MODEL_DTO, dto);
            return Pages.CAR_MODEL_PAGE;
        } catch (NoSuchCarModelException e) {
            logger.error(e);
            model.addAttribute("Error", e.getMessage());
            return Pages.ERROR;
        }
    }
}
