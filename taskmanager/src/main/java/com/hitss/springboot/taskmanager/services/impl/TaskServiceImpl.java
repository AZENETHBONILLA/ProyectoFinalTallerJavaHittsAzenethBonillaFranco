package com.hitss.springboot.taskmanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitss.springboot.taskmanager.models.Task;
import com.hitss.springboot.taskmanager.repositories.TaskRepository;
import com.hitss.springboot.taskmanager.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public Optional<Task> update(Long id, Consumer<Task> updater) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        taskOptional.ifPresent(task -> {
            updater.accept(task);        // aplica los cambios
            taskRepository.save(task);   // guarda los cambios
        });
        return taskOptional;
    }


    @Transactional
    @Override
    public Optional<Task> delete(Long id) {
        Optional<Task> optional = taskRepository.findById(id);
        optional.ifPresent(p -> taskRepository.delete(p));
        return optional;
    }
    
}
