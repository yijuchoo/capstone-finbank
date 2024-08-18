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
            @RequestParam("tamt") String tamtStr,
            @RequestParam("aacct") Long accountId,
            Model model) {

        StringBuilder errorMessages = new StringBuilder();
        boolean hasErrors = false;

        // Validate Transaction Amount for non-numeric values
        if (!tamtStr.matches("\\d+(\\.\\d+)?")) {
            hasErrors = true;
            errorMessages.append("Transaction amount must be a numeric value.\n");
        }

        // Parse the amount to double
        double tamt = 0;
        if (hasErrors) {
            model.addAttribute("errorMessages", errorMessages.toString());
            logger.error("Validation errors occurred:\n{}", errorMessages.toString());
            return "addtransac";
        }

        tamt = Double.parseDouble(tamtStr);

        // Validate for zero or negative amounts
        if (tamt <= 0) {
            errorMessages.append("Transaction amount must be greater than zero.\n");
            hasErrors = true;
        }

        // Retrieve the account using the account ID
        Account account = accountRepository.findById(accountId).orElse(null);

        // If account is not found, return with an error message
        if (account == null) {
            errorMessages.append("Account not found. Please select a valid account.\n");
            hasErrors = true;
        }

        // Update the account balance based on transaction type
        if (ttype.equalsIgnoreCase("Withdrawal") && account != null && account.getBalance() < tamt) {
            errorMessages.append("Insufficient balance. Transaction cannot be processed.\n");
            hasErrors = true;
        }

        if (ttype.equalsIgnoreCase("Deposits") && account != null) {
            account.setBalance(account.getBalance() + tamt);
        } else if (ttype.equalsIgnoreCase("Withdrawal") && account != null) {
            account.setBalance(account.getBalance() - tamt);
        } else {
            errorMessages.append("Invalid transaction type.\n");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("errorMessages", errorMessages.toString());
            logger.error("Validation errors occurred:\n{}", errorMessages.toString());
            return "addtransac";
        }

        // Save the updated account balance
        accountRepository.save(account);

        // Set the transaction date to the current date and time
        LocalDateTime tdate = LocalDateTime.now();

        // Create and save the new transaction
        Transaction newTransaction = new Transaction(tid, ttype, tdate, tamt, account);
        transactionRepository.save(newTransaction);

        logger.info("Saved new transaction with ID: {}, Type: {}, Date: {}, Amount: {}, Account ID: {}",
                tid, ttype, tdate, tamt, account.getId());

        return "redirect:/transactions/list";
    }

}