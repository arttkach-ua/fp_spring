package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.util.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getFromDTO() {
        UserDto dto = new UserDto(1,"test@gmail.com","test","Test","9999","test","222","222",null,null);
        User user = User.getFromDTO(dto);
        User expected = new User(1,"test@gmail.com","test","Test","9999","test","222",Role.CLIENT,false,null,false);
        assertEquals(expected, user);
    }

    @Test
    void toDTO() {
        User user = new User(1,"test@gmail.com","test","Test","9999","test","222",Role.CLIENT,false,null,false);
        UserDto dto= User.toDTO(user);
        UserDto expected = new UserDto(1,"test@gmail.com","test","Test","9999","test","222","222",null,"false");
        assertEquals(dto, expected);

    }

    @Test
    void getFullName() {
        User user = new User(1,"test@gmail.com","Name","SecondName","9999","test","222",Role.CLIENT,false,null,false);
        String result = user.getFullName();
        assertEquals("Name SecondName", result);
    }
}