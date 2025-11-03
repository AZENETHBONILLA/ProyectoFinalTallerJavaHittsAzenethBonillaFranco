package com.hitss.springboot.taskmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.springboot.taskmanager.models.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
}
