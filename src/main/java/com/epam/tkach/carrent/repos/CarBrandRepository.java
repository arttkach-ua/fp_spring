package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.CarBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandRepository extends JpaRepository<CarBrand, Integer>{

    Page<CarBrand> findAll(Pageable pageable);
}
