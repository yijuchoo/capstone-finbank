package com.example.corebankingapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
// base path for all AccountControllers
@RequestMapping("/accounts")
public class AccountController<AccountResponse> {

    @PostMapping
    public ResponseEntity<AccountResponse> createNewAccount() {
        // call method to create new account and store in accountResponse
        AccountResponse accountResponse = accountService.createNewAccount();
        return ResponseEntity
        // initialize ResponseEntity with accountResponse as body
                .status(HttpStatus.CREATED)
                .body(accountResponse);
    }
}
