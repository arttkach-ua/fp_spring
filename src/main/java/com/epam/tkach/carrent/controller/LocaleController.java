package com.epam.tkach.carrent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LocaleController {
    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/international")
    public String changeLangGet(@RequestParam(name = "lang") String lang, HttpSession session, HttpServletRequest request) {
//        session.setAttribute("local", lang);
//
//        String referer = request.getHeader("Referer");
//        return "redirect:" + referer;
        return changeLang(lang, session, request);
    }

    @PostMapping(value = "/international")
    public String changeLangPost(@RequestParam(name = "lang") String lang, HttpSession session, HttpServletRequest request) {
        return changeLang(lang, session, request);
    }

    private String changeLang(String lang, HttpSession session, HttpServletRequest request){
        session.setAttribute("local", lang);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}