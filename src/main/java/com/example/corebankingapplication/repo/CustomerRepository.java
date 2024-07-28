package com.example.corebankingapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corebankingapplication.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    
} 
