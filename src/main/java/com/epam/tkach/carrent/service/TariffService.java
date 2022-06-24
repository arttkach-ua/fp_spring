package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.Tariff;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.exceptions.NoSuchTariffException;
import com.epam.tkach.carrent.repos.TariffRepository;
import com.epam.tkach.carrent.util.dto.CarModelDto;
import com.epam.tkach.carrent.util.dto.TariffDto;
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
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    public Paged<Tariff> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Tariff> postPage = tariffRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public void save(TariffDto dto){
        tariffRepository.save(Tariff.getFromDTO(dto));
    }

    public Tariff findById(int id) throws NoSuchTariffException {
        Optional<Tariff> opt = tariffRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchTariffException("error.TariffNotFound");
        return opt.get();
    }

    public List<Tariff> getAll(){
        return tariffRepository.findAll();
    }
}
