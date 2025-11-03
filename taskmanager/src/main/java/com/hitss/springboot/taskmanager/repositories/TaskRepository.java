package com.hitss.springboot.taskmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.springboot.taskmanager.models.Task;
import com.hitss.springboot.taskmanager.models.enums.Status;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByStatus(Status status);
    List<Task> findByNameContainingIgnoreCase(String keyword);

}
