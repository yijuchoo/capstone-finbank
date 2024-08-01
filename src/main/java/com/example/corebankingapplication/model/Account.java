package com.example.corebankingapplication.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountType;
    private double balance;
    private LocalDate openDate;
    @ManyToOne
    private Customer customer;

    public Account(String accountType, double balance, LocalDate openDate, Customer customer) {
        this.accountType = accountType;
        this.balance = balance;
        this.openDate = openDate;
        this.customer = customer;
    }
}
