package com.egg.service;

import java.util.List;

import com.egg.entity.Editorial;
import com.egg.persistence.dao.interfaces.EditorialDAO;
import com.egg.utils.ConsoleUtility;
import com.egg.utils.StrUtility;
import com.egg.utils.ValidationUtility;


public class EditorialService {
    private final EditorialDAO editorialDAO;

    public EditorialService(EditorialDAO editorialDAO) {
        this.editorialDAO = editorialDAO;
    }

    public void registrar(String nombre) {

        try {
            // Limpiamos la cadena de texto
            nombre = StrUtility.cleanStr(nombre);

            // Creamos la nueva instancia
            Editorial nuevoEditorial = new Editorial(nombre);

            // Validamos antes de guardar
            ValidationUtility.validarEntidad(nuevoEditorial);

            // Registramos la instancia
            editorialDAO.create(nuevoEditorial);

            System.out.println("La editorial se registró satisfactoriamente con ID: " + nuevoEditorial.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizar(Integer id, String nombre) {
        try {

             // Validamos el ID
             ValidationUtility.validarId(id);

            // Limpiamos la cadena de texto
            nombre = StrUtility.cleanStr(nombre);

            // Obtenemos la instancia
            Editorial editorial = editorialDAO.findById(id);

            if (editorial != null) {

                editorial.setNombre(nombre);
    
                // Validamos antes de actualizar
                ValidationUtility.validarEntidad(editorial);
    
                // Actualizamos la editorial
                editorialDAO.update(editorial);

                System.out.println("La editorial se atualizó satisfactoriamente con ID: " + id);
            } else {
                System.out.println("Lo sentimos, no se encontró ninguna editorial registrada con el ID: " + id);
            }

        } catch (IllegalArgumentException e) { // Si la validación encuentra un error
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminar(Integer id) {
        try {
            // Validamos el ID
            ValidationUtility.validarId(id);

            // Obtenemos la instancia
            Editorial editorial = editorialDAO.findById(id);

            if (editorial != null) {
                // "Eliminamos" la editorial
                editorial = editorialDAO.delete(editorial);
    
                System.out.println("La editorial se eliminó satisfactoriamente");
            } else {
                System.out.println("Lo sentimos, no se encontró ninguna editorial registrada con el ID: " + id);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void buscarPorId(Integer id) {
        try {
            // Validamos el ID
            ValidationUtility.validarId(id);

            // Obtenemos la instancia
            Editorial editorial = editorialDAO.findById(id);

            if (editorial != null) {
                // Imprimimos la instancia
                System.out.println(editorial);
            } else {
                System.out.println("Lo sentimos, no se encontró ninguna editorial registrada con el ID: " + id);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarTodos() {
        try {
            List<Editorial> editoriales = editorialDAO.findAll();
            ConsoleUtility.imprimirListado(editoriales, "id", "nombre");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
