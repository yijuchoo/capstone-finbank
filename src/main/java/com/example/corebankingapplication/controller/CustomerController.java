package com.example.corebankingapplication.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.corebankingapplication.model.Customer;
import com.example.corebankingapplication.repo.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/list")
    public String showCust(Model model, Principal principal) {
        List<Customer> custList = customerRepository.findAll();
        model.addAttribute("customers", custList);
        return "viewcustomers";
    }
    
}
