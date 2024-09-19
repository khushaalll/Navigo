package com.example.travel.repository;

import com.example.travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
Optional<User> findByEmailAndPassword(String email, String password);
User findByEmail(String email);
}
