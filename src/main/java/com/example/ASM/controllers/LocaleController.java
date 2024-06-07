package com.example.ASM.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocaleController {

    @GetMapping("/locale")
    public String changeLocale(@RequestParam("lang") String lang, HttpServletResponse response) {
        Cookie cookie = new Cookie("locale", lang);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request) {
        String lang = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("locale")) {
                    lang = cookie.getValue();
                    break;
                }
            }
        }
        if (lang == null) {
            lang = request.getLocale().getLanguage();
        }
        request.setAttribute("locale", lang);
        return "index";
    }
}