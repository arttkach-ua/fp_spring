package com.epam.tkach.carrent.controller.rest;

import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.CompleteSet;
import com.epam.tkach.carrent.service.CarModelService;
import com.epam.tkach.carrent.service.CompleteSetsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class RestFunctions {
    private static final Logger logger = LogManager.getLogger(RestFunctions.class);

    @Autowired
    CarModelService carModelService;

    @Autowired
    CompleteSetsService completeSetsService;

    /**
     * Function searches car models for car brand.
     * @param id - Car brand id
     * @return JSON with list of car brands
     */
    @GetMapping(value = "/RestGetCarModels")
    public String getCarModels(int id) {
        logger.debug("Rest get car models in action. brand id::" + id);
        List<CarModel> modelList = carModelService.findByBrandId(id);
        Gson json = new Gson();
        return json.toJson(modelList);
    }

    @GetMapping(value = "/RestGetCompleteSetsByCarModel")
    public String getCompleteSets(int id){
        logger.debug("Rest get complete sets in action. model id::" + id);
        List<CompleteSet> modelList = completeSetsService.findByModelId(id);
        Gson json = new Gson();
        return json.toJson(modelList);
    }

}
