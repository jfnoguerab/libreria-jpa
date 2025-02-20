package com.egg.service;

import java.util.List;

import com.egg.entity.Autor;
import com.egg.entity.Editorial;
import com.egg.entity.Libro;
import com.egg.persistence.dao.interfaces.AutorDAO;
import com.egg.persistence.dao.interfaces.EditorialDAO;
import com.egg.persistence.dao.interfaces.LibroDAO;
import com.egg.utils.ConsoleUtility;
import com.egg.utils.StrUtility;
import com.egg.utils.ValidationUtility;

public class LibroService {
    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;
    private final EditorialDAO editorialDAO;

    public LibroService(LibroDAO libroDAO, AutorDAO autorDAO, EditorialDAO editorialDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
        this.editorialDAO = editorialDAO;
    }

    public void registrar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer idAutor, Integer idEditorial) {
        try {
            // Limpiamos la cadena de texto
            titulo = StrUtility.cleanStr(titulo);

            // Validamos el ISBN
            ValidationUtility.validarIsbn(isbn);

            // Validamos que el Libro no haya sido registrado antes con el mismo ISBN
            // Obtenemos la instancia
            Libro libro = libroDAO.findById(isbn);
            if (libro != null) {
                System.out.println("Lo sentimos, ya existe registrado un libro con el ISBN: " + isbn);
                return;
            }

            // Validamos el ID del autor
            ValidationUtility.validarId(idAutor, "El ID del autor");
            
            // Validamos que Exista el Autor en la BD
            Autor validAutor = autorDAO.findById(idAutor);

            if (validAutor == null) {
                System.out.println("Lo sentimos, no se encontró ningún autor registrado con el ID: " + idAutor);
                return;
            }

            // Validamos el ID de la editorial
            ValidationUtility.validarId(idEditorial, "El ID de la editorial");
            
            // Validamos que Exista el Autor en la BD
            Editorial validEditorial = editorialDAO.findById(idEditorial);

            if (validEditorial == null) {
                System.out.println("Lo sentimos, no se encontró ninguna editorial registrada con el ID: " + idEditorial);
                return;
            }

            // Creamos la nueva instancia
            Libro nuevoLibro = new Libro(isbn, titulo, anio, ejemplares, validAutor, validEditorial);

            // Validamos antes de guardar
            ValidationUtility.validarEntidad(nuevoLibro);

            // Registramos la instancia
            libroDAO.create(nuevoLibro);

            System.out.println("El libro se registró satisfactoriamente con el ISBN: " + nuevoLibro.getIsbn());
        } catch (IllegalArgumentException e) { // Si la validación encuentra un error
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer idAutor, Integer idEditorial) {
        try {
            // Validamos el ISBN
            ValidationUtility.validarIsbn(isbn);

            // Obtenemos la instancia
            Libro libro = libroDAO.findById(isbn);

            if (libro != null) {

                // Limpiamos la cadena de texto
                titulo = StrUtility.cleanStr(titulo);

                // Validamos el ID del autor
                ValidationUtility.validarId(idAutor, "El ID del autor");
                
                // Validamos que Exista el Autor en la BD
                Autor validAutor = autorDAO.findById(idAutor);

                if (validAutor == null) {
                    System.out.println("Lo sentimos, no se encontró ningún autor registrado con el ID: " + idAutor);
                    return;
                }

                // Validamos el ID de la editorial
                ValidationUtility.validarId(idEditorial, "El ID de la editorial");
                
                // Validamos que Exista el Autor en la BD
                Editorial validEditorial = editorialDAO.findById(idEditorial);

                if (validEditorial == null) {
                    System.out.println("Lo sentimos, no se encontró ninguna editorial registrada con el ID: " + idEditorial);
                    return;
                }

                libro.setTitulo(titulo);
                libro.setAnio(anio);
                libro.setEjemplares(ejemplares);
                libro.setAutor(validAutor);
                libro.setEditorial(validEditorial);
    
                // Validamos antes de actualizar
                ValidationUtility.validarEntidad(libro);
    
                // Actualizamos el libro
                libroDAO.update(libro);

                System.out.println("El libro se atualizó satisfactoriamente con ISBN: " + isbn);
            } else {
                System.out.println("Lo sentimos, no se encontró ningún libro registrado con el ISBN: " + isbn);
            }

        } catch (IllegalArgumentException e) { // Si la validación encuentra un error
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminar(Long isbn) {
        try {
            // Validamos el ISBN
            ValidationUtility.validarIsbn(isbn);

            // Obtenemos la instancia
            Libro libro = libroDAO.findById(isbn);

            if (libro != null) {
                // "Eliminamos" la libro
                libro = libroDAO.delete(libro);
    
                System.out.println("El libro se eliminó satisfactoriamente");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún libro registrado con el ISBN: " + isbn);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void buscarPorIsbn(Long isbn) {
        try {
            // Validamos el ISBN
            ValidationUtility.validarIsbn(isbn);

            // Obtenemos la instancia
            Libro libro = libroDAO.findById(isbn);

            if (libro != null) {
                // Imprimimos la instancia
                ConsoleUtility.imprimirEntidad(libro, "isbn", "titulo", "anio", "ejemplares", "autor.nombre", "editorial.nombre");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún libro registrado con el ISBN: " + isbn);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void buscarPorNombreAutor(String nombreAutor) {
        try {
            // Validamos el nombre
            ValidationUtility.validarNombre(nombreAutor, "El nombre del autor");

            // Obtenemos la lista de instancias
            List<Libro> libros = libroDAO.findByAuthor(nombreAutor);

            if (libros.size() > 0) {
                // Imprimimos la instancia
                ConsoleUtility.imprimirListado(libros, "isbn", "titulo", "anio", "ejemplares", "autor.nombre", "editorial.nombre");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún libro registrado con el nombre del autor: " + nombreAutor);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void buscarPorNombreEditorial(String nombreEditorial) {
        try {
            // Validamos el nombre
            ValidationUtility.validarNombre(nombreEditorial, "El nombre de la editorial");

            // Obtenemos la lista de instancias
            List<Libro> libros = libroDAO.findByEditorial(nombreEditorial);

            if (libros.size() > 0) {
                // Imprimimos la instancia
                ConsoleUtility.imprimirListado(libros, "isbn", "titulo", "anio", "ejemplares", "autor.nombre", "editorial.nombre");
            } else {
                System.out.println("Lo sentimos, no se encontró ningún libro registrado con el nombre de la editorial: " + nombreEditorial);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarTodos() {
        try {
           List<Libro> libros = libroDAO.findAll();
           ConsoleUtility.imprimirListado(libros, "isbn", "titulo", "anio", "ejemplares", "autor.nombre", "editorial.nombre");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
