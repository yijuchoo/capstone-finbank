package com.example.corebankingapplication.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.corebankingapplication.model.Role;
import com.example.corebankingapplication.model.User;
import com.example.corebankingapplication.repo.UserRepository;


@Controller
public class GeneralController {

    Logger logger = LoggerFactory.getLogger("GeneralController.class");

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String landingPage(Model model, Principal principal) {
        model.addAttribute("activePage", "home");

        // Retrieve the current authenticated user's username
        String userName = principal != null ? principal.getName() : "anonymous";

        /* For User Role */
        // Fetch the user from the repository
        User user = userRepository.findByUserName(userName);

        String roles = user.getUserRoles().stream()
                .map(Role::getName)  // Extract role names
                .collect(Collectors.joining(", "));  // Join role names with commas
        

        logger.info("Homepage loaded successfully by user: {} with role: {}", userName, roles);
        // logger.info("Homepage loaded successfully by user: {}", userName);
        return "index";
    }
}
