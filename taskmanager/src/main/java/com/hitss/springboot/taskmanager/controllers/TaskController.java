package com.hitss.springboot.taskmanager.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitss.springboot.taskmanager.models.Task;
import com.hitss.springboot.taskmanager.models.User;
import com.hitss.springboot.taskmanager.models.dto.TaskDTO;
import com.hitss.springboot.taskmanager.services.TaskService;
import com.hitss.springboot.taskmanager.services.UserService;
import com.hitss.springboot.taskmanager.utils.UtilCrud;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tasks")
@Tag(
    name = "Tasks",
    description = "Endpoints ..."
)
public class TaskController {
    private TaskService taskService;
    private UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    //Listar las tareas
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all tasks", description = "Retrieves all tasks")
    public List<TaskDTO> list() {
        return taskService.findAll().stream().map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setUserId(task.getUser().getId());
            dto.setUsername(task.getUser().getUsername()); // opcional
            return dto;
        }).collect(Collectors.toList());
    }

    //Obtener una tarea especifica 
    @GetMapping("/{id}")
    @Operation(summary = "Get a specific task", description = "Retrieve a task by id")
    public ResponseEntity<?> getTaskById(@PathVariable Long id){
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            // Convertir a DTO
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setUserId(task.getUser().getId());
            dto.setUsername(task.getUser().getUsername());

            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }


    //Crear una nueva tarea
    @Operation(summary = "Post a new task", description = "Retrieves all registered ...")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Task task, BindingResult result){
        if (result.hasFieldErrors()) {
            return UtilCrud.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
    }

    //Modificar una tarea
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a task", description = "Update an existing task")
    public ResponseEntity<?> update(@PathVariable Long id, 
                                    @Valid @RequestBody TaskDTO taskDTO,
                                    BindingResult result) {

        if (result.hasFieldErrors()) {
            return UtilCrud.validation(result);
        }

        // Verificar que el usuario exista
        Optional<User> optionalUser = userService.findById(taskDTO.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with id " + taskDTO.getUserId() + " not found");
        }

        Optional<Task> updated = taskService.update(id, task -> {
            task.setName(taskDTO.getName());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());
            task.setUser(optionalUser.get());
        });

        return updated.map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setUserId(task.getUser().getId());
            dto.setUsername(task.getUser().getUsername());
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Eliminar una tarea
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a task", description = "Deletes a task by ID")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Task> optional = taskService.delete(id);

        return optional.map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setUserId(task.getUser().getId());
            dto.setUsername(task.getUser().getUsername());
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
