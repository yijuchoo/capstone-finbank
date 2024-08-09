package com.example.corebankingapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {
    @RequestMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("activePage", "home");
        return "index";
    }
}
