package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}