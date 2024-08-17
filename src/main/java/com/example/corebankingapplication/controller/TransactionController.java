package com.example.corebankingapplication.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.corebankingapplication.model.Account;
import com.example.corebankingapplication.model.Transaction;
import com.example.corebankingapplication.repo.AccountRepository;
import com.example.corebankingapplication.repo.TransactionRepository;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    Logger logger = LoggerFactory.getLogger("TransactionController.class");

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping("/list")
    public String showTrans(Model model) {
        List<Transaction> tranList = transactionRepository.findAll();
        model.addAttribute("transactions", tranList);
        model.addAttribute("activePage", "transactions"); // Set the active page
        logger.info("Successfully fetched {} transactions", tranList.size()); // log messages
        return "showtransac";
    }

    @RequestMapping("/search")
    public String searchAcct(@RequestParam("term") String keyword, Model model) {
        List<Transaction> searchList = transactionRepository.search(keyword);
        model.addAttribute("transactions", searchList);
        model.addAttribute("activePage", "transactions"); // Set the active page
        logger.info("Search completed, found {} transactions with keyword: {}", searchList.size(), keyword);
        // logger.info("Search completed with keyword '{}', found {} transactions",
        // keyword, searchList.size());
        return "showtransac";
    }

    @RequestMapping("/add")
    public String addTrans(Model model) {
        List<Account> accounts = accountRepository.findAll();
        List<String> transactionTypes = List.of("Deposits", "Withdrawal");
        model.addAttribute("accounts", accounts);
        model.addAttribute("transactionTypes", transactionTypes);
        model.addAttribute("activePage", "transactions"); // Set the active page
        logger.info("Preparing to add new transaction. Accounts and transaction types loaded.");
        return "addtransac";
    }


    // @RequestMapping("/save")
    // public String saveRecord(
    // @RequestParam("tid") long tid,
    // @RequestParam("ttype") String ttype,
    // @RequestParam("tdate") LocalDate tdate,
    // @RequestParam("tamt") double tamt,
    // @RequestParam("aacct") Account account) {
    // Transaction newTransaction = new Transaction(tid, ttype, tdate, tamt,
    // account);
    // transactionRepository.save(newTransaction);
    // logger.info("Saved new transaction with ID: {}, Type: {}, Date: {}, Amount:
    // {}, Account ID: {}", tid, ttype,
    // tdate, tamt, account.getId());
    // return "redirect:/transactions/list";
    // }

    @RequestMapping("/save")
    public String saveRecord(
            @RequestParam("tid") long tid,
            @RequestParam("ttype") String ttype,
            // @RequestParam("tdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            // LocalDate tdate,
            @RequestParam("tamt") String tamtStr,
            @RequestParam("aacct") Long accountId,
            Model model) {

        // Validation for alphabetic and non-numeric characters
        if (!tamtStr.matches("\\d+(\\.\\d+)?")) {
            model.addAttribute("errorMessage", "Transaction amount must be a numeric value.");
            return "addtransac";
        }

        // Parse the amount to double
        double tamt = Double.parseDouble(tamtStr);

        // Validation for zero or negative amounts
        if (tamt <= 0) {
            model.addAttribute("errorMessage", "Transaction amount must be greater than zero.");
            return "addtransac";
        }

        // Retrieve the account using the account ID
        Account account = accountRepository.findById(accountId)
                .orElse(null);

        // If account is not found, return with an error message
        if (account == null) {
            model.addAttribute("errorMessage", "Account not found. Please select a valid account.");
            return "addtransac";
        }

        // Update the account balance based on transaction type
        if (ttype.equalsIgnoreCase("Withdrawal")) {
            // Check if the account has sufficient balance for withdrawal
            if (account.getBalance() < tamt) {
                model.addAttribute("errorMessage", "Insufficient balance. Transaction cannot be processed.");
                return "addtransac";
            }
            account.setBalance(account.getBalance() - tamt);
        } else if (ttype.equalsIgnoreCase("Deposits")) {
            account.setBalance(account.getBalance() + tamt);
        } else {
            model.addAttribute("errorMessage", "Invalid transaction type.");
            return "addtransac";
        }

        // Save the updated account balance
        accountRepository.save(account);

        // Set the transaction date to the current date and time
        LocalDateTime tdate = LocalDateTime.now();

        // Create and save the new transaction
        Transaction newTransaction = new Transaction(tid, ttype, tdate, tamt, account);
        // Transaction newTransaction = new Transaction(tid, ttype, tdate, tamt,
        // account);
        transactionRepository.save(newTransaction);
        logger.info("Saved new transaction with ID: {}, Type: {}, Date: {}, Amount: {}, Account ID: {}",
                tid, ttype, tdate, tamt, account.getId());

        return "redirect:/transactions/list";
    }

}