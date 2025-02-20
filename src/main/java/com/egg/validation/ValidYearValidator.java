package com.egg.validation;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidYearValidator implements ConstraintValidator<ValidYear, Integer> {

    private static final int MIN_YEAR = 1967;

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) {
            return true; // Permitir valores nulos si no hay @NotNull
        }
        int currentYear = Year.now().getValue();
        return year >= MIN_YEAR && year <= currentYear;
    }

}
