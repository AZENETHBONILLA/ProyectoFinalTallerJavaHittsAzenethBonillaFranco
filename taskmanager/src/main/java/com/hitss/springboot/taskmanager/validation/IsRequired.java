package com.hitss.springboot.taskmanager.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = RequiredValidation.class)
@Target({ ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRequired {
    String message() default "es requerido utilizando anotaciones";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
