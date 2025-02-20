package com.egg.service;

import java.util.List;

import com.egg.entity.Autor;
import com.egg.persistence.dao.interfaces.AutorDAO;
import com.egg.utils.ConsoleUtility;
import com.egg.utils.StrUtility;
import com.egg.utils.ValidationUtility;


public class AutorService {
    private final AutorDAO autorDAO;

    public AutorService(AutorDAO autorDAO) {
        this.autorDAO = autorDAO;
    }

    public void registrar(String nombre) {

        try {
            // Limpiamos la cadena de texto
            nombre = StrUtility.cleanStr(nombre);

            // Creamos la nueva instancia
            Autor nuevoAutor = new Autor(nombre);

            // Validamos antes de guardar
            ValidationUtility.validarEntidad(nuevoAutor);

            // Registramos la instancia
            autorDAO.create(nuevoAutor);

            System.out.println("El autor se registró satisfactoriamente con ID: " + nuevoAutor.getId());
        } catch (IllegalArgumentException e) { // Si la validación encuentra un error
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
            Autor autor = autorDAO.findById(id);

            if (autor != null) {

                autor.setNombre(nombre);
    
                // Validamos antes de actualizar
                ValidationUtility.validarEntidad(autor);
    
                // Actualizamos el autor
                autorDAO.update(autor);

                System.out.println("El autor se atualizó satisfactoriamente con ID: " + id);
            } else {
                System.out.println("Lo sentimos, no se encontró ningún autor registrado con el ID: " + id);
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
            Autor autor = autorDAO.findById(id);

            if (autor != null) {
                // "Eliminamos" el autor
                autor = autorDAO.delete(autor);
    
                System.out.println("El autor se eliminó satisfactoriamente");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún autor registrado con el ID: " + id);
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
            Autor autor = autorDAO.findById(id);

            if (autor != null) {
                // Imprimimos la instancia
                ConsoleUtility.imprimirEntidad(autor, "id", "nombre");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún autor registrado con el ID: " + id);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void buscarPorNombre(String nombre) {
        try {

            // Validamos el nombre
            ValidationUtility.validarNombre(nombre);

            // Obtenemos las instancias
            List<Autor> autores = autorDAO.findByName(nombre);

            if (autores.size() > 0) {
                // Imprimimos la instancia
                ConsoleUtility.imprimirListado(autores, "id", "nombre");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún autor registrado con el nombre: " + nombre);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarTodos() {
        try {
           List<Autor> autores = autorDAO.findAll();
           ConsoleUtility.imprimirListado(autores, "id", "nombre");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
