package com.example.corebankingapplication.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.corebankingapplication.model.Role;
import com.example.corebankingapplication.model.User;
import com.example.corebankingapplication.repo.RoleRepository;
import com.example.corebankingapplication.repo.UserRepository;


@Controller
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger("UserController.class");

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private boolean isEdit = false;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/list")
    public String showUsers(Model model, Principal principal) {
        List<User> usersList = userRepository.findAll();
        model.addAttribute("userList", usersList);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("principal", principal);
        model.addAttribute("activePage", "users"); // Set the active page
        logger.info("Successfully fetched {} users", usersList.size());
        return "showusers";
    }

    @RequestMapping("/add")
    public String addUser(User user, Model model) {
        isEdit = false;
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("user", user);
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("activePage", "users"); // Set the active page
        logger.info("Displaying add user form");
        return "adduser";
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        isEdit = true;
        model.addAttribute("isEdit", isEdit);
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("activePage", "users"); // Set the active page
        logger.info("Editing user with ID: {}", id);
        return "adduser";
    }

    @RequestMapping("/delete/{id}")
    public String delUser(@PathVariable("id") Long id, Model model) {
        userRepository.deleteById(id);
        logger.info("Deleted user with ID: {}", id);
        return "redirect:/users/list";
    }

    @RequestMapping("/newrole")
    public String addRole(Model model) {
        model.addAttribute("activePage", "users"); // Set the active page
        logger.info("Displaying add role form");
        return "addroles";
    }

    @RequestMapping("/saverole")
    public String saveRole(@RequestParam("name") String roleName) {
        Role newRole = new Role();
        newRole.setName(roleName);
        roleRepository.save(newRole);
        logger.info("Saved new role with name: {}", roleName);
        return "redirect:/users/list";
    }

    @RequestMapping("/save")
    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("Saved user with ID: {}", user.getId());
        return "redirect:/users/list";
    }

}
