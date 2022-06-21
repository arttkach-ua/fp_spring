package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    Page<CarModel> findAll(Pageable pageable);

    List<CarModel> findAllByBrand_Id(int brand_id);
}

