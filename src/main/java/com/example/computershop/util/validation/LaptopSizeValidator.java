package com.example.computershop.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;

public class LaptopSizeValidator implements ConstraintValidator<LaptopSizeConstraint, Integer> {

    private int[] sizes;

    @Override
    public void initialize(LaptopSizeConstraint constraintAnnotation) {
        this.sizes = constraintAnnotation.sizes();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || Arrays.stream(sizes).anyMatch(elem -> value == elem);
    }
}
