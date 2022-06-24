package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.Car;
import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.Tariff;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.exceptions.NoSuchCarException;
import com.epam.tkach.carrent.exceptions.NoSuchTariffException;
import com.epam.tkach.carrent.repos.CarRepository;
import com.epam.tkach.carrent.util.dto.CarDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import com.epam.tkach.carrent.util.pagination.Paging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private static final Logger logger = LogManager.getLogger(CarService.class);
    @Autowired
    CarRepository carRepository;

    public Paged<Car> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Car> postPage = carRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    /**
     * Creating car from car dto and saving it in db
     * @param dto
     */
    public void save(CarDto dto){
        logger.debug("Saving car to db");
        logger.debug(dto.toString());
        carRepository.save(Car.getFromDTO(dto));
    }

    /**
     * Saves instance of car in db
     * @param car
     */
    public void save(Car car){
        carRepository.save(car);
    }

    public Car findById(int id) throws NoSuchCarException {
        Optional<Car> opt = carRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchCarException("error.CarNotFound");
        return opt.get();
    }

    public Paged<Car> getCarsForNewOrder(int pageNumber, int size, CarClass classFilter,
                                         CarBrand brandFilter, TransmissionTypes tmFilter, String sortField){


        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(getSortDirection(sortField), getColumnNameToSort(sortField)));
        Page<Car> postPage = carRepository.findCarMy(request, true, classFilter,brandFilter,tmFilter);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public List<String> getSortingList(){
        List<String> sortList = new ArrayList<>();
        sortList.add("price.down");
        sortList.add("price.up");
        sortList.add("brand.up");
        sortList.add("brand.down");
        return sortList;
    }

    /**
     * Gets sort column from string(used in front)
     * @param sortName
     * @return column name for query.
     */
    public String getColumnNameToSort(String sortName){
        if (sortName==null) return "id";
        switch (sortName){
            case "price.down": return "tariff.rentPrice";
            case "price.up": return "tariff.rentPrice";
            case "brand.up": return "brand.carBrandName";
            case "brand.down": return "brand.carBrandName";
            default: return "id";
        }
    }

    /**
     * Gets sort direction from string(used in front)
     * @param sortName
     * @return Sort direction.
     */

    public Sort.Direction getSortDirection(String sortName){
        if (sortName==null) return Sort.DEFAULT_DIRECTION;
        switch (sortName){
            case "price.down": return Sort.Direction.DESC;
            case "price.up": return Sort.Direction.ASC;
            case "brand.up": return Sort.Direction.ASC;
            case "brand.down": return Sort.Direction.DESC;
            default: return Sort.DEFAULT_DIRECTION;
        }
    }
}
