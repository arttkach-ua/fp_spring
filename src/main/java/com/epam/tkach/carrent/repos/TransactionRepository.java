package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select sum(t.sum) from Transaction t where t.user.id=:userId group by t.user")
    public Double getUserBalance(int userId);
}
