package com.hitss.springboot.taskmanager.models.dto;

import com.hitss.springboot.taskmanager.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Long userId;     // solo el id del usuario
    private String username; // opcional
}

