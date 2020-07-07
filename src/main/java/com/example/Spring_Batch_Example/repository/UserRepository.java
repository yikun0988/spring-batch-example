package com.example.Spring_Batch_Example.repository;

import com.example.Spring_Batch_Example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
