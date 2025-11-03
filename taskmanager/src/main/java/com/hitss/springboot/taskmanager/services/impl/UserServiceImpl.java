package com.hitss.springboot.taskmanager.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitss.springboot.taskmanager.models.Role;
import com.hitss.springboot.taskmanager.models.User;
import com.hitss.springboot.taskmanager.models.enums.Roles;
import com.hitss.springboot.taskmanager.repositories.RoleRepository;
import com.hitss.springboot.taskmanager.repositories.UserRepository;
import com.hitss.springboot.taskmanager.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        Optional<Role> optionalRole = roleRepository.findByName(Roles.ROLE_USER);
        List<Role> roles = new ArrayList<>();

        optionalRole.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optionalRoleUser = roleRepository.findByName(Roles.ROLE_ADMIN);
            optionalRoleUser.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


}
