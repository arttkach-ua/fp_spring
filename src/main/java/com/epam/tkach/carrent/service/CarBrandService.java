package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.repos.CarBrandRepository;
import com.epam.tkach.carrent.util.dto.CarBrandDto;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import com.epam.tkach.carrent.util.pagination.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandService {
    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    public CarBrandService(CarBrandRepository carBrandRepository){
        this.carBrandRepository = carBrandRepository;
    }

    public Page<CarBrand> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        return carBrandRepository.findAll(pageable);
    }

    public Paged<CarBrand> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CarBrand> postPage = carBrandRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public List<CarBrand> getAll(){
        return carBrandRepository.findAll();
    }

    public void save(CarBrandDto dto){
        carBrandRepository.save(CarBrand.getFromDTO(dto));
    }

}
