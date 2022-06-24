package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.CompleteSet;
import com.epam.tkach.carrent.entity.enums.BodyStyles;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.FuelTypes;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.exceptions.NoSuchCompleteSetException;
import com.epam.tkach.carrent.service.CarBrandService;
import com.epam.tkach.carrent.service.CarModelService;
import com.epam.tkach.carrent.service.CompleteSetsService;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.CompleteSetDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import javax.validation.Valid;

@Controller
public class CompleteSetsController {
    private static final Logger logger = LogManager.getLogger(CompleteSetsController.class);
    @Autowired
    CompleteSetsService completeSetsService;

    @Autowired
    CarBrandService carBrandService;

    @Autowired
    CarModelService carModelService;

    @GetMapping("completeSets/list")
    public String showCompleteSets(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                   Model model){
        model.addAttribute(PageParameters.ENTITY_LIST, completeSetsService.getPage(pageNumber, size));
        return Pages.COMPLETE_SETS;
    }
    @GetMapping("completeSets/newCompleteSet")
    public String addNewCompleteSet(Model model){
        model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
        model.addAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
        model.addAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
        model.addAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
        model.addAttribute(PageParameters.COMPLETE_SET_DTO, new CompleteSetDto());
        return Pages.COMPLETESET_PAGE;
    }

    @PostMapping("completeSets/saveCompleteSet")
    public String saveCompleteSet(@ModelAttribute @Valid CompleteSetDto completeSetDto,
                                  BindingResult bindingResult,
                                  Model model){
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                logger.debug("Complete set validation failed");
                logger.debug(fieldError.getField());
                logger.debug(fieldError.getDefaultMessage());
            });
            model.addAttribute(PageParameters.COMPLETE_SET_DTO, completeSetDto);
            model.addAttribute(PageParameters.CAR_MODELS, carModelService.findByBrandId(completeSetDto.getCarBrandId()));
            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            model.addAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
            model.addAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            model.addAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
            return Pages.COMPLETESET_PAGE;
        }
        completeSetsService.save(completeSetDto);
        return "redirect:/completeSets/list";
    }

    @GetMapping(value = "completeSet/edit/{id}")
    public String editCarModel(@PathVariable("id") int id, Model model){
        try {
            CompleteSet completeSet = completeSetsService.findById(id);
            CompleteSetDto dto = CompleteSet.toDTO(completeSet);

            model.addAttribute(PageParameters.COMPLETE_SET_DTO, dto);
            model.addAttribute(PageParameters.CAR_MODELS, carModelService.findByBrandId(dto.getCarBrandId()));
            model.addAttribute(PageParameters.CAR_BRANDS, carBrandService.getAll());
            model.addAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
            model.addAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            model.addAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
            return Pages.COMPLETESET_PAGE;
        } catch (NoSuchCompleteSetException e) {
            logger.error(e);
            model.addAttribute("Error", e.getMessage());
            return Pages.ERROR;
        }
    }
}
