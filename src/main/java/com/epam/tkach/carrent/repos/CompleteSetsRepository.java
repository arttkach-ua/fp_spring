package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.CompleteSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteSetsRepository extends JpaRepository<CompleteSet, Integer> {
    Page<CompleteSet> findAll(Pageable pageable);
}
