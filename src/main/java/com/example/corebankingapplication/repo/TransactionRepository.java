package com.example.corebankingapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corebankingapplication.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    
} 
