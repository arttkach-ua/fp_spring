package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Page<Car> findAll(Pageable pageable);
}
