package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.CarModel;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.exceptions.UserExistsException;
import com.epam.tkach.carrent.util.dto.UserDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void createNewUser() {
        UserDto dto = new UserDto(1,"Test@gmail.com","test","Test","9999","test","222","222",null,null);
        try {
            userService.createNewUser(dto);
        } catch (UserExistsException e) {}

        try {
            userService.createNewUser(dto);
        } catch (UserExistsException e) {
            assertEquals(UserExistsException.class, e.getClass());
        }
    }

    @Test
    void findByEmail() {
        UserDto dto = new UserDto(1,"Test@gmail.com","test","Test","9999","test","222","222",null,null);
        try {
            userService.createNewUser(dto);
            Optional<User> user = userService.findByEmail("Test@gmail.com");
            assertTrue(user.isPresent());
        } catch (UserExistsException e) {}
    }

    @Test
    void getPage() {
        int pageNumber = 1;
        int size= 5;
        addToDB(20);

        Paged<User> pageActual = userService.getPage(pageNumber, size);

        assertEquals(pageActual.getPage().getSize(), 5);
        assertEquals(pageActual.getPage().toList(), getExpectedList(5));

    }

    @Test
    void setBlockToUser() {
        try {
            UserDto dto = new UserDto(1,"test@gmail.com","test","Test","9999","test","222","222",null,null);
            userService.createNewUser(dto);
            userService.setBlockToUser(1, true);
            User user = userService.findUserByEmail("test@gmail.com");
            assertTrue(user.isBlocked());

        } catch (NoSuchUserException | UserExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateUser() {
        try {
            UserDto dto = new UserDto(1,"test@gmail.com","test","Test","9999","test","222","222",null,null);
            userService.createNewUser(dto);
            dto.setFirstName("new test");
            userService.updateUser(dto);
            User user = userService.findUserByEmail(dto.getEmail());
            assertEquals("new test", user.getFirstName());
            assertEquals("Test", user.getSecondName());
        } catch (UserExistsException | NoSuchUserException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getCurrentUser() {
        //сделать разобравшить с мокито
    }

    private void addToDB(int count){
        try {
            for (int i = 1; i < count+1; i++) {
                UserDto dto = new UserDto(i,"test" + i + "@gmail.com","test"+i,"Test"+i,"9999","test","222","222",null,null);
                userService.createNewUser(dto);
            }

        } catch (UserExistsException e) {
            e.printStackTrace();
        }
    }

    private List<User> getExpectedList(int count){
        ArrayList<User> list = new ArrayList<>();
        for (int i = 1; i < count+1; i++) {
            list.add(new User(i,"test" + i + "@gmail.com","test"+i,"Test"+i,"9999","test","222",i==1? Role.ADMIN:Role.CLIENT,false,null,false));
        }
        return list;
    }
}