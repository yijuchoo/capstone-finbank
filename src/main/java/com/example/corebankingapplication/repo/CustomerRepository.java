package com.example.corebankingapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corebankingapplication.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    // create a custom query method to search by multiple attributes
    List<Customer> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
} 
