package com.example.corebankingapplication.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // @RequestMapping("/save")
    // public String saveCustomer(Customer customer) {
    // System.out.println(customer);
    // customerRepository.save(customer);
    // logger.info("Saved customer: {}", customer);
    // return "redirect:/customers/list";
    // }

    @RequestMapping("/save")
    public String saveCustomer(Customer customer, Model model, RedirectAttributes redirectAttributes) {
        boolean hasErrors = false;
        StringBuilder errorMessages = new StringBuilder();

        // Validate First Name
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()
                || !customer.getFirstName().matches("^[A-Za-z]+$")) {
            hasErrors = true;
            errorMessages.append("First Name must not be null or numeric.<br>");
        }

        // Validate Last Name
        if (customer.getLastName() == null || customer.getLastName().isEmpty()
                || !customer.getLastName().matches("^[A-Za-z]+$")) {
            hasErrors = true;
            errorMessages.append("Last Name must not be null or numeric.<br>");
        }

        // Validate Date of Birth
        if (customer.getDob() == null || customer.getDob().isAfter(LocalDate.now())) {
            hasErrors = true;
            errorMessages.append("Date of Birth must be a past date.<br>");
        }

        // Validate Email
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (customer.getEmail() == null || !Pattern.matches(emailPattern, customer.getEmail())) {
            hasErrors = true;
            errorMessages.append("Email must be a valid email address.<br>");
        }

        // Add more checks as necessary

        if (hasErrors) {
            model.addAttribute("isEdit", isEdit);
            model.addAttribute("activePage", "customers");
            model.addAttribute("errorMessages", errorMessages.toString());
            logger.error("Validation errors occurred:\n{}", errorMessages.toString());
            return "addcust"; // Return to the form page with validation errors
        }

        customerRepository.save(customer);
        logger.info("Saved customer: {}", customer);
        return "redirect:/customers/list";
    }

}
