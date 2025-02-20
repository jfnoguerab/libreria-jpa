package com.egg.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidISBNValidator implements ConstraintValidator<ValidISBN, Long> {

    @Override
    public boolean isValid(Long isbn, ConstraintValidatorContext context) {

        // Permitir null porque la anotación @Null ya lo maneja
        if (isbn == null) {
            return true;
        }

        // Convertir el número en String para validar la longitud
        String isbnStr = isbn.toString();

        // Validar longitud de ISBN (10 o 13 dígitos)
        return isbnStr.matches("\\d{10}") || isbnStr.matches("\\d{13}");
    }

}
