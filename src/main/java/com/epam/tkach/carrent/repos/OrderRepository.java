package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.CarBrand;
import com.epam.tkach.carrent.entity.Order;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where " +
            "(:statusFilter is null or o.status=:statusFilter) and" +
            "(:userFilter is null or o.client=:userFilter) and" +
            "(:brandFilter is null or o.car.brand=:brandFilter)")
    Page<Order> findOrdersCustom(Pageable pageable,
                                 @Param("statusFilter") OrderStatuses statusFilter,
                                 @Param("brandFilter") CarBrand brandFilter,
                                 @Param("userFilter") User userFilter);
}
