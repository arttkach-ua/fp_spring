package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.Transaction;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.repos.TransactionRepository;
import com.epam.tkach.carrent.util.dto.TransactionDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    public double getUserBalance(int userId){
        return transactionRepository.getUserBalance(userId);
    }

    public double getUserBalance(String email) throws NoSuchUserException {
        User user = userService.findUserByEmail(email);
        Double balance = transactionRepository.getUserBalance(user.getId());
        if (balance==null){
            return 0;
        }else{
            return balance.doubleValue();
        }
    }

    public void topUp(TransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setSum(dto.getSum());
        transaction.setUser(dto.getUser());
        transaction.setDescription("Balance top up");
        transactionRepository.save(transaction);
    }

    public void createPayment(User user, int invoiceId, double amount){
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Payment by invoice#" + invoiceId);
        transaction.setSum(amount*(-1));
        transactionRepository.save(transaction);
    }
}
