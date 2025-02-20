package com.egg.persistence.dao.interfaces;

import java.util.List;

import com.egg.entity.Libro;

public interface LibroDAO extends DAO<Libro, Long> {
    List<Libro> findByTitle(String title);
    List<Libro> findByAuthor(String authorName);
    List<Libro> findByEditorial(String editorialName);
}
