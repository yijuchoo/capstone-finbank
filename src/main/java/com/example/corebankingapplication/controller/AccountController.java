package com.example.corebankingapplication.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.corebankingapplication.model.Account;
import com.example.corebankingapplication.model.Customer;
import com.example.corebankingapplication.repo.AccountRepository;
import com.example.corebankingapplication.repo.CustomerRepository;

@Controller
@RequestMapping("/accounts") // prefix to endpoints on methods /accts/list
public class AccountController {

    Logger logger = LoggerFactory.getLogger("AccountController.class");

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/list")
    public String showAccts(Model model) {
        List<Account> acctList = accountRepository.findAll();
        model.addAttribute("accounts", acctList);
        model.addAttribute("activePage", "accounts"); // Set the active page
        logger.info("Successfully fetched {} accounts", acctList.size()); // Log the number of accounts fetched
        return "showaccts";
    }

    @RequestMapping("/search")
    public String searchAcct(@RequestParam("term") String keyword, Model model) {
        logger.info("Searching for accounts with keyword: {}", keyword);
        List<Account> searchList = accountRepository.search(keyword);
        model.addAttribute("accounts", searchList);
        model.addAttribute("activePage", "accounts"); // Set the active page
        logger.info("Search completed, found {} accounts with keyword: {}", searchList.size(), keyword);
        return "showaccts";
    }

    @RequestMapping("/add")
    public String addAcct(Model model) {
        List<Customer> customers = customerRepository.findAll(); // Fetch all customers
        List<String> accountTypes = List.of("Savings", "Checking", "Investment"); // Define account types
        
        model.addAttribute("customers", customers);
        model.addAttribute("accountTypes", accountTypes);
        model.addAttribute("activePage", "accounts"); // Set the active page
        logger.info("Preparing to add new account. Customers and account types loaded.");
        return "addacct";
    }

    /* End point for saving the account record */
    @RequestMapping("/save")
    public String saveRecord(
            @RequestParam("aid") long aid,
            @RequestParam("atype") String atype,
            @RequestParam("abalance") double abalance,
            @RequestParam("aopendate") LocalDate aopendate,
            @RequestParam("acust") Customer customer) {
        Account newAccount = new Account(aid, atype, abalance, aopendate, customer);
        accountRepository.save(newAccount);
        logger.info("Saved new account with ID: {}, Type: {}, Balance: {}, Open Date: {}, Customer ID: {}",
                aid, atype, abalance, aopendate, customer.getId());
        return "redirect:/accounts/list";
    }

    /* End point for editing an account */
    @RequestMapping("/edit/{aid}")
    public String editAcct(@PathVariable("aid") long aid, Model model) {
        Optional<Account> account = accountRepository.findById(aid);
        List<Customer> customers = customerRepository.findAll();
        List<String> accountTypes = List.of("Savings", "Checking", "Investment"); // Define account types

        model.addAttribute("account", account.get());
        model.addAttribute("customers", customers);
        model.addAttribute("accountTypes", accountTypes);
        model.addAttribute("activePage", "accounts"); // Set the active page
        logger.info("Editing account with ID: {}", aid);
        return "editaccount";
    }

    /* End point for deleting an account record */
    @RequestMapping("/delete/{aid}")
    public String deleteAcct(@PathVariable("aid") long aid) {
        accountRepository.deleteById(aid);
        logger.info("Deleted account with ID: {}", aid);
        return "redirect:/accounts/list";
    }

}
