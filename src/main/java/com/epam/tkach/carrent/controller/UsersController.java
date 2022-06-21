package com.epam.tkach.carrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    @GetMapping("/users/list")
    public String showUsers(Model model){
        return Pages.
    }
}
