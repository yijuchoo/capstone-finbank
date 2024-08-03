package com.example.corebankingapplication.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.corebankingapplication.model.Account;
// import com.example.corebankingapplication.model.Customer;
import com.example.corebankingapplication.model.Transaction;
import com.example.corebankingapplication.repo.AccountRepository;
import com.example.corebankingapplication.repo.TransactionRepository;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping("/list")
    public String showTrans(Model model) {
        List<Transaction> tranList = transactionRepository.findAll();
        model.addAttribute("transactions", tranList);
        return "showtrans";
    }

    @RequestMapping("/search")
    public String searchTran(@RequestParam("term") String keyword, Model model) {
        List<Transaction> searchList = transactionRepository.search(keyword);
        model.addAttribute("transactions", searchList);
        return "showtrans";
    }

    @RequestMapping("/add")
    public String addTran(Model model) {
        List<Account> accounts = accountRepository.findAll(); // Fetch all accounts
        List<String> transTypes = List.of("Deposits", "Withdrawal"); // Define transaction types

        model.addAttribute("transTypes", transTypes);
        model.addAttribute("accounts", accounts);
        return "addtran";
    }

    /* End point for saving the transaction record */
    @RequestMapping("/save")
    public String saveRecord(
            // @RequestParam("aid") Long aid,
            @RequestParam("ttype") String ttype,
            @RequestParam("ttransdate") LocalDate ttransdate,
            @RequestParam("ttransamt") double ttransamt,
            @RequestParam("tacct") Account account) {
        // Transaction newTransaction = new Transaction(ttype, ttransdate, ttransamt, account);
        // transactionRepository.save(newTransaction);
        return "redirect:/transactions/list";
    }
}
