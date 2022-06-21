package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Integer> {
    Page<Tariff> findAll(Pageable pageable);
}
