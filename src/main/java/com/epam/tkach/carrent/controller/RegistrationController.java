package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.service.UserService;
import com.epam.tkach.carrent.util.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegistrationController {
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @GetMapping("registration")
    public String registrationGet(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser.getRole()== Role.ADMIN){
                model.addAttribute(PageParameters.ROLES_LIST,Role.values());
            }
            return "register";
        } catch (NoSuchUserException e) {
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @PostMapping("registration")
    public String registrationPost(@Valid UserDto userDto, BindingResult bindingResult, Model model){
        System.out.println("post mapping1");
        if (bindingResult.hasErrors()){
//            bindingResult
//                    .getFieldErrors()
//                    .stream()
//                    .collect(Collectors.toMap(
//                            fieldError -> fieldError+"Error",
//                            FieldError::getDefaultMessage));
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error:errors) {
                System.out.println(error.getField() + error.getDefaultMessage());
            }

        }
        userService.createNewUser(userDto);
        System.out.println(userDto.toString());
        logger.debug(userDto.toString());
        return "login";
    }
}
