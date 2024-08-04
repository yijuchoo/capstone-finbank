package com.example.corebankingapplication.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        model.addAttribute("customerList", customersList);
        return "showcustomers";
    }

    @RequestMapping("/add")
    public String addCustomer(Model model) {
        isEdit = false;
        model.addAttribute("isEdit", isEdit);
        // model.addAttribute("customer", customer);
        model.addAttribute("customer", new Customer());
        return "addcust";
    }

    @RequestMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") long id, Model model) {
        isEdit = true;
        Customer customer = customerRepository.findById(id).orElseThrow();
        System.out.println(customer.getDob());
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDate = sdf.format(customer.getDob());
        LocalDate ld = LocalDate.parse(formatDate, sdf);
        System.out.println(ld);
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("customer", customer);
        return "addcust";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        customerRepository.deleteById(id);
        return "redirect:/customers/";
    }

    @RequestMapping("/save")
    public String saveCustomer(Customer customer) {
        System.out.println(customer);
        customerRepository.save(customer);
        return "redirect:/customers/";
    }

}
