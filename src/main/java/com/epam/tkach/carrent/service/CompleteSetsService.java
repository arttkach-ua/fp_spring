package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.CompleteSet;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.exceptions.NoSuchCompleteSetException;
import com.epam.tkach.carrent.repos.CompleteSetsRepository;
import com.epam.tkach.carrent.util.dto.CompleteSetDto;
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
public class CompleteSetsService {
    @Autowired
    private CompleteSetsRepository completeSetsRepository;

    @Autowired
    public CompleteSetsService(CompleteSetsRepository completeSetsRepository){
        this.completeSetsRepository = completeSetsRepository;
    }

    public Paged<CompleteSet> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CompleteSet> postPage = completeSetsRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public void save(CompleteSetDto dto){
        completeSetsRepository.save(CompleteSet.getFromDTO(dto));
    }

    public CompleteSet findById(int id) throws NoSuchCompleteSetException {
        Optional<CompleteSet> opt = completeSetsRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchCompleteSetException("error.CompleteSetNotFound");
        return opt.get();

    }

    public List<CompleteSet> findByModelId(int modelId){
        return completeSetsRepository.findAllByCarModel_Id(modelId);
    }
}
