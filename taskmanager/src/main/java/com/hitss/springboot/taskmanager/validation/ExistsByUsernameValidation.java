package com.hitss.springboot.taskmanager.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.hitss.springboot.taskmanager.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String>{

    @Autowired
    private UserService userService;

    // public ExistsByUsernameValidation(UserService userService) {
    //     this.userService = userService;
    // }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService == null) {
            return true;
        }
        return !userService.existsByUsername(value);
    }

}
