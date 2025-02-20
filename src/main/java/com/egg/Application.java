package com.egg;

import com.egg.entity.Autor;
import com.egg.persistence.dao.impl.AutorDAOImpl;
import com.egg.persistence.dao.impl.EditorialDAOImpl;
import com.egg.persistence.dao.impl.LibroDAOImpl;
import com.egg.persistence.dao.interfaces.AutorDAO;
import com.egg.persistence.dao.interfaces.EditorialDAO;
import com.egg.persistence.dao.interfaces.LibroDAO;
import com.egg.service.AutorService;
import com.egg.service.EditorialService;
import com.egg.service.LibroService;

public class Application {

    public static void main(String[] args) {
        AutorDAO autorDAO = new AutorDAOImpl();
        EditorialDAO editorialDAO = new EditorialDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();

        AutorService autorService = new AutorService(autorDAO);
        EditorialService editorialService = new EditorialService(editorialDAO);
        LibroService libroService = new LibroService(libroDAO, autorDAO, editorialDAO);

        Long isbn = 1234567890L;
        String nombre = "editorial";

        libroService.buscarPorNombreEditorial(nombre);

        //Integer id = 1;
        //autorService.buscarPorId(id);

        //editorialService.listarTodos();
        //libroService.listarTodos();

        //EditorialService editorialService = new EditorialService(editorialDAO);

        //String nombreEditorial = null;
        //editorialService.registrarEditorial(nombreEditorial);

    }
}
