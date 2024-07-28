package com.example.corebankingapplication.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.corebankingapplication.model.Account;
import com.example.corebankingapplication.model.Customer;
import com.example.corebankingapplication.repo.AccountRepository;

@Controller
@RequestMapping("/accounts") // prefix to endpoints on methods /accts/list
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping("/list")
    public String showAccts(Model model) {
        List<Account> acctList = accountRepository.findAll();
        model.addAttribute("accounts", acctList);
        return "showaccts";
    }

    @RequestMapping("/search")
    public String searchAcct(@RequestParam("term") String keyword, Model model) {
        List<Account> searchList = accountRepository.search(keyword);
        model.addAttribute("accounts", searchList);
        return "showaccts";
    }

    @RequestMapping("/add")
    public String addAcct() {
        return "addacct";
    }

    /* End point for saving the account record */
    @RequestMapping("/save")
    public String saveRecord(
            @RequestParam("aid") Long aid,
            @RequestParam("atype") String atype,
            @RequestParam("abalance") double abalance,
            @RequestParam("aopendate") LocalDate aopendate,
            @RequestParam("acust") Customer customer) {
        Account newAccount = new Account(aid, atype, abalance, aopendate, customer);
        accountRepository.save(newAccount);
        return "redirect:/list";
    }

    /* End point for editing an account */
    @RequestMapping("/edit/{aid}")
    public String editAcct(@PathVariable("aid") Long aid, Model model) {
        Optional<Account> account = accountRepository.findById(aid);
        model.addAttribute("account", account.get());
        return "editaccount";
    }

    /* End point for deleting an account record */
    @RequestMapping("/delete/{aid}")
    public String deleteAcct(@PathVariable("aid") Long aid) {
        accountRepository.deleteById(aid);
        return "redirect:/list";
    }

}
