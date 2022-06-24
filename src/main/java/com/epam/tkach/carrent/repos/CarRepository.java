package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.Car;
import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.TransmissionTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CarRepository extends JpaRepository<Car, Integer> {
    Page<Car> findAll(Pageable pageable);

    @Query("select c from Car c where c.available=:isAvailable and " +
            "(:classFilter is null or c.model.carClass=:classFilter) and " +
            "(:tmFilter is null or c.completeSet.transmission=:tmFilter) and" +
            "(:brandFilter is null or c.brand=:brandFilter)")
    Page<Car> findCarMy(Pageable pageable,
                        @Param("isAvailable")boolean isAvailable,
                        @Param("classFilter")CarClass classFilter,
                        @Param("brandFilter")CarBrand brandFilter,
                        @Param("tmFilter") TransmissionTypes tmFilter);

}
