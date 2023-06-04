package com.example.computershop.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LaptopSizeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LaptopSizeConstraint {

    String message() default "must match {sizes}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] sizes();
}
