package com.example.corebankingapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.corebankingapplication.model.Customer;
import com.example.corebankingapplication.repo.CustomerRepository;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private boolean isEdit = false;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String showCustomers(Model model) {
        List<Customer> customersList = customerRepository.findAll();
        model.addAttribute("customersList", customersList);
        return "showcustomers";
    }

    @RequestMapping("/new")
    public String addCustomer(Model model) {
        isEdit = false;
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("customer", new Customer());
        return "addcust";
    }

    @RequestMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Long id, Model model) {
        isEdit = true;
        Customer customer = customerRepository.findById(id).orElseThrow();
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("customer", customer);
        return "addcust";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id, Model model) {
        customerRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/save")
    public String saveCustomer(Customer customer) {
        System.out.println(customer);
        customerRepository.save(customer);
        return "redirect:/customers/";
    }

}
