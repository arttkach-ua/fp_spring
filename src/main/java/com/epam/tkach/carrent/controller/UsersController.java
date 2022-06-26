package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.PageParameters;
import com.epam.tkach.carrent.Pages;
import com.epam.tkach.carrent.entity.Transaction;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.entity.enums.CarClass;
import com.epam.tkach.carrent.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.exceptions.UserExistsException;
import com.epam.tkach.carrent.service.TransactionService;
import com.epam.tkach.carrent.service.UserService;
import com.epam.tkach.carrent.util.dto.TransactionDto;
import com.epam.tkach.carrent.util.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersController {
    private static final Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/users/list")
    public String showUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            Model model) {
        model.addAttribute(PageParameters.ENTITY_LIST, userService.getPage(pageNumber, size));
        return Pages.USERS;
    }

    @GetMapping("/users/setBlock")
    public String setBlockToUser(@RequestParam(value = "id", required = true, defaultValue = "-1") int id,
                                 @RequestParam(value = "newStatus", required = true, defaultValue = "false") boolean newStatus,
                                 Model model) {
        try {
            userService.setBlockToUser(id, newStatus);
            return "redirect:/" + "users/list";
        } catch (NoSuchUserException e) {
            logger.error(e);
            return "redirect:/users/list";
        }
    }

    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute(PageParameters.USER_DTO, new UserDto());
        model.addAttribute(PageParameters.ROLES_LIST, Role.values());
        return "admin/newUser";
    }

    @PostMapping("/users/add")
    public String addUserPost(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            bindingResult.rejectValue("password", "error.password", Messages.PASS_NOT_MATCH);
        }
        if (bindingResult.hasErrors()){
            model.addAttribute(PageParameters.ROLES_LIST, Role.values());
            model.addAttribute(PageParameters.USER_DTO, userDto);
            return "admin/newUser";
        }
        try {
            userService.createNewUser(userDto);
            return "redirect:/users/list";
        } catch (UserExistsException e) {
            bindingResult.rejectValue("email", "error.user", Messages.USER_EXISTS);
            model.addAttribute(PageParameters.USER_DTO, userDto);
            model.addAttribute(PageParameters.ROLES_LIST, Role.values());
            return "admin/newUser";
        }
    }

    @GetMapping("/users/myProfile")
    public String openMyProfile(Authentication authentication, Model model) {
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
                           Model model) {
        if (bindingResult.hasErrors()) {
            return Pages.MY_PROFILE;
        }

        try {
            userService.updateUser(userDto);
            return "redirect:/";
        } catch (NoSuchUserException e) {
            logger.error(e);
            return Pages.ERROR;
        }
    }

    @GetMapping("/users/topUp")
    public String openTopUpPage(Model model) {
        model.addAttribute("transactionDto", new TransactionDto());
        return Pages.TOP_UP;
    }

    @PostMapping("/users/topUp")
    public String topUp(HttpSession session,
                        @Valid TransactionDto transactionDto,
                        BindingResult bindingResult,
                        Model model) {
        //Validation
        if (bindingResult.hasErrors()) {
            model.addAttribute(PageParameters.TRANSACTION_DTO, transactionDto);
            return Pages.TOP_UP;
        }
        //Creating transaction

        try {
            transactionDto.setUser(userService.getCurrentUser());
            transactionService.topUp(transactionDto);
            //Setting new balance to session
            session.setAttribute("balance", transactionService.getUserBalance(transactionDto.getUser().getEmail()));
            return "redirect:/";
        } catch (NoSuchUserException e) {
            logger.error(e);
            return "redirect:/error";
        }
    }


}
