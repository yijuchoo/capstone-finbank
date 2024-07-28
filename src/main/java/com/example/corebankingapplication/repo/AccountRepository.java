package com.example.corebankingapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corebankingapplication.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

    
} 
