package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.CompleteSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteSetsRepository extends JpaRepository<CompleteSet, Integer> {
    Page<CompleteSet> findAll(Pageable pageable);

    List<CompleteSet> findAllByCarModel_Id(int model_id);
}
