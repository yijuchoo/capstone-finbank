package com.example.corebankingapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corebankingapplication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
