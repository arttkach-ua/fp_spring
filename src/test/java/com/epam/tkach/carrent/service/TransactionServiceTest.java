package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.Transaction;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.exceptions.NoSuchCarModelException;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.util.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TransactionServiceTest {
    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

//    @Test
//    void getUserBalance() {
//    }
//
//    @Test
//    void testGetUserBalance() {
//    }

    @Test
    void topUp() {
        User user = new User(1,"test@gmail.com","test","test","99999","sssss","password", Role.CLIENT,false,null,false);
        userService.save(user);
        TransactionDto dto = new TransactionDto(1,user,"Test Top up",1000, null);
        transactionService.topUp(dto);
        assertEquals(1000, transactionService.getUserBalance(1));

    }

    @Test
    void createPayment() {
        User user = new User(1,"test@gmail.com","test","test","99999","sssss","password", Role.CLIENT,false,null,false);
        userService.save(user);
        Transaction transaction = new Transaction(1,user,"Test payment",-9, null);
        transactionService.createPayment(user,1,10);
        try {
            assertEquals(-10, transactionService.getUserBalance("test@gmail.com"));

        } catch (NoSuchUserException e) {
        }

        try {
           transactionService.getUserBalance("test111@gmail.com");
        } catch (NoSuchUserException e) {
            assertEquals(NoSuchUserException.class, e.getClass());
        }
    }


}