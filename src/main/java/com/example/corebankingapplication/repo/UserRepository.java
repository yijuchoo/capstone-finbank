package com.example.corebankingapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corebankingapplication.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);

}
