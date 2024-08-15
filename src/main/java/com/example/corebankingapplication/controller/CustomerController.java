package com.example.corebankingapplication.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger("CustomerController.class");

    private boolean isEdit = false;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/list")
    public String showCustomers(Model model) {
        List<Customer> customersList = customerRepository.findAll();
        model.addAttribute("customerList", customersList);
        model.addAttribute("activePage", "customers"); // Set the active page
        logger.info("Successfully fetched {} customers", customersList.size());
        return "showcustomers";
    }

    @RequestMapping("/add")
    public String addCustomer(Model model) {
        isEdit = false;
        model.addAttribute("isEdit", isEdit);
        // model.addAttribute("customer", customer);
        model.addAttribute("customer", new Customer());
        model.addAttribute("activePage", "customers"); // Set the active page
        logger.info("Displaying add customer form");
        return "addcust";
    }

    @RequestMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") long id, Model model) {
        isEdit = true;
        Customer customer = customerRepository.findById(id).orElseThrow();
        System.out.println(customer.getDob());
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("customer", customer);
        model.addAttribute("activePage", "customers"); // Set the active page
        logger.info("Editing customer with ID: {}, Date of Birth: {}", id);
        return "addcust";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        customerRepository.deleteById(id);
        logger.info("Deleted customer with ID: {}", id);
        return "redirect:/customers/list";
    }

    @RequestMapping("/save")
    public String saveCustomer(Customer customer) {
        System.out.println(customer);
        customerRepository.save(customer);
        logger.info("Saved customer: {}", customer);
        return "redirect:/customers/list";
    }

}
