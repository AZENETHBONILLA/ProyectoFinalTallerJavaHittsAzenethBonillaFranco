package com.hitss.springboot.taskmanager.services;

import java.util.List;
import java.util.Optional;

import com.hitss.springboot.taskmanager.models.User;

public interface UserService {
    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);

    Optional<User> findById(Long id); // <-- agregar este método
}
