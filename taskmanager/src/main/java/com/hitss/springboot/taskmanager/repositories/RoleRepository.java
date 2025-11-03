package com.hitss.springboot.taskmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.springboot.taskmanager.models.Role;
import com.hitss.springboot.taskmanager.models.enums.Roles;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(Roles name);

}
