package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.Tariff;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.exceptions.NoSuchTariffException;
import com.epam.tkach.carrent.service.TariffService;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.TariffDto;
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

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
public class TariffController {
    private static final Logger logger = LogManager.getLogger(TariffController.class);

    @Autowired
    TariffService tariffService;

    @GetMapping("/tariff/list")
    public String showTariffs(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                              Model model){
        model.addAttribute(PageParameters.ENTITY_LIST, tariffService.getPage(pageNumber, size));
        return Pages.TARIFFS;

    }
    @GetMapping("/tariff/add")
    public String openNewTariffPage(Model model){
        model.addAttribute(PageParameters.TARIFF_DTO, new TariffDto());
        return Pages.TARIFF_PAGE;
    }
    @GetMapping(value = "/tariff/edit/{id}")
    public String editCarModel(@PathVariable("id") int id, Model model) {
        try {
            Tariff tariff = tariffService.findById(id);
            TariffDto dto = Tariff.toDTO(tariff);
            System.out.println(dto.toString());
            logger.debug(dto.toString());
            model.addAttribute(PageParameters.TARIFF_DTO, dto);
            return Pages.TARIFF_PAGE;
        } catch (NoSuchTariffException e) {
            logger.error(e);
            model.addAttribute("Error", e.getMessage());
            return Pages.ERROR;
        }
    }

    @PostMapping("/tariff/save")
    public String saveTariff(@Valid TariffDto tariffDto,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()) {

            return Pages.TARIFF_PAGE;
        }
        tariffService.save(tariffDto);
        logger.debug("saving tariff");
        logger.debug(tariffDto.toString());
        return "redirect:/" + "tariff/list";
    }
}
