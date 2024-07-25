package com.example.corebankingapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.corebankingapplication.repo.AccountRepository;

@Controller
public class CustomerController {

    @Autowired
    AccountRepository accountRepository;
    
}
