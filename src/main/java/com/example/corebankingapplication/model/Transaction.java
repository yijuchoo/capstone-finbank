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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String transType;
    private LocalDate transDate;
    private double transAmt;
    
    @ManyToOne
    private Account account;

    public Transaction(String transType, LocalDate transDate, double transAmt, Account account) {
        this.transType = transType;
        this.transDate = transDate;
        this.transAmt = transAmt;
        this.account = account;
    }
}
