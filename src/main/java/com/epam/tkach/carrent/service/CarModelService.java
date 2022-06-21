package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.repos.CarModelRepository;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import com.epam.tkach.carrent.util.pagination.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarModelService {
    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    public CarModelService(CarModelRepository carModelRepository){
        this.carModelRepository = carModelRepository;
    }

    public Paged<CarModel> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CarModel> postPage = carModelRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public void save(CarModelDto dto){
        carModelRepository.save(CarModel.getFromDTO(dto));
    }

    public List<CarModel> findAll(){
        return carModelRepository.findAll();
    }

    public List<CarModel> findByBrandId(int brandId){
        return carModelRepository.findAllByBrand_Id(brandId);
    }

    public CarModel findById(int id) throws NoSuchCarModelException {
        Optional<CarModel> opt = carModelRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchCarModelException("error.CarModelNotFound");
        return opt.get();
    }
}
