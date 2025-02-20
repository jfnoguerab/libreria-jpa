package com.egg.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public final class ValidationUtility {
    private ValidationUtility(){
        // Private constructor to prevent instantiation
    }

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validarEntidad(T entidad) {
        Set<ConstraintViolation<T>> validaciones = validator.validate(entidad);
        if (!validaciones.isEmpty()) {

            // Obtener el orden de los atributos en la entidad
            List<String> ordenAtributos = new ArrayList<>();
            for (Field field : entidad.getClass().getDeclaredFields()) {
                ordenAtributos.add(field.getName());
            }

            // Ordenar las validaciones en base a la posición del atributo en la clase
            List<ConstraintViolation<T>> validacionesOrdenadas = new ArrayList<>(validaciones);
            validacionesOrdenadas.sort(Comparator.comparingInt(e -> ordenAtributos.indexOf(e.getPropertyPath().toString())));

            StringBuilder errores = new StringBuilder("Ocurrieron los siguientes errores:\n");
            for (ConstraintViolation<T> validacion : validacionesOrdenadas) {
                errores.append("  - ")
                       .append(validacion.getPropertyPath()) // Nombre del atributo
                       .append(": ")
                       .append(validacion.getMessage()) // Mensaje de la validación
                       .append("\n");
            }
            throw new IllegalArgumentException(errores.toString());
        }
    }

    public static void validarId(Integer id) {
        StringBuilder errores = new StringBuilder("Ocurrieron los siguientes errores:\n");
        if (id == null) {
            errores.append("  - El ID no puede ser nulo");
            throw new IllegalArgumentException(errores.toString());
        }

        if (id < 1) {
            errores.append("  - El ID no puede ser menor a 1");
            throw new IllegalArgumentException(errores.toString());
        }
    }
    
    public static void validarId(Integer id, String label) {
        StringBuilder errores = new StringBuilder("Ocurrieron los siguientes errores:\n");
        if (id == null) {
            errores.append("  - " + label + " no puede ser nulo");
            throw new IllegalArgumentException(errores.toString());
        }

        if (id < 1) {
            errores.append("  - " + label + " no puede ser menor a 1");
            throw new IllegalArgumentException(errores.toString());
        }
    }
    
    public static void validarIsbn(Long isbn) {
        StringBuilder errores = new StringBuilder("Ocurrieron los siguientes errores:\n");
        if (isbn == null) {
            errores.append("  - El Isbn no puede ser nulo");
            throw new IllegalArgumentException(errores.toString());
        }

        // Validamos el tamaño del isbn de solo 10 o 13 dígitos
        int length = String.valueOf(isbn).length();
        if (length != 10 && length != 13) {
            errores.append("  - El ISBN debe tener exactamente 10 o 13 dígitos");
            throw new IllegalArgumentException(errores.toString());
        }
    }
    
    public static void validarNombre(String nombre) {
        StringBuilder errores = new StringBuilder("Ocurrieron los siguientes errores:\n");
        if (nombre == null) {
            errores.append("  - El nombre no puede ser nulo");
            throw new IllegalArgumentException(errores.toString());
        }

        // Validamos que el nombre no este en blanco
        if (StrUtility.isEmptyStr(StrUtility.cleanStr(nombre))) {
            errores.append("  - El nombre no puede estar en blanco.");
            throw new IllegalArgumentException(errores.toString());
        }
    }
    
    public static void validarNombre(String nombre, String label) {
        StringBuilder errores = new StringBuilder("Ocurrieron los siguientes errores:\n");
        if (nombre == null) {
            errores.append("  - " + label + " no puede ser nulo");
            throw new IllegalArgumentException(errores.toString());
        }

        // Validamos que el nombre no este en blanco
        if (StrUtility.isEmptyStr(StrUtility.cleanStr(nombre))) {
            errores.append("  - " + label + " no puede estar en blanco.");
            throw new IllegalArgumentException(errores.toString());
        }
    }
}
