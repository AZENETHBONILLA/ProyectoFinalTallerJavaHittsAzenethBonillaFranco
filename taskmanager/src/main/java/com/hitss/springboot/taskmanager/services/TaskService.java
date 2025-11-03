package com.hitss.springboot.taskmanager.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.hitss.springboot.taskmanager.models.Task;

public interface TaskService {
    //Métodos crud -> listar, buscar por id, crear, actualizar y eliminar
    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    Optional<Task> update(Long id, Consumer<Task> updater);
    
    Optional<Task> delete(Long id);
}
