package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.service.UserService;
import com.epam.tkach.carrent.util.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UsersController {
    private static final Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    UserService userService;

    @GetMapping("/users/list")
    public String showUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            Model model){
        model.addAttribute(PageParameters.ENTITY_LIST, userService.getPage(pageNumber, size));
        return Pages.USERS;
    }

    @GetMapping("/users/setBlock")
    public String setBlockToUser(@RequestParam(value = "id", required = true, defaultValue = "-1") int id,
                                 @RequestParam(value = "newStatus", required = true, defaultValue = "false") boolean newStatus,
                                 Model model){
        try {
            userService.setBlockToUser(id, newStatus);
            return "redirect:/" + "users/list";
        } catch (NoSuchUserException e) {
            logger.error(e);
            return "redirect:/users/list";
        }
    }

    @GetMapping("/users/add")
    public String addUser(Model model){
        return "redirect:/" + "users/list";
    }

    @GetMapping("/users/myProfile")
    public String openMyProfile(Authentication authentication, Model model){
        try {
            User user = userService.findUserByEmail(authentication.getName());
            model.addAttribute(PageParameters.USER_DTO, User.toDTO(user));
            return Pages.MY_PROFILE;
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @PostMapping("/users/save")
    public String saveUser(@Valid UserDto userDto,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors()) {
            return Pages.MY_PROFILE;
        }

        try {
            userService.updateUser(userDto);
            return "redirect:/users/list";
        } catch (NoSuchUserException e) {
            logger.error(e);
            return Pages.ERROR;
        }
    }

}
