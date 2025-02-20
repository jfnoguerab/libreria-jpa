package com.egg.persistence.dao.impl;

import java.util.List;

import com.egg.config.EntityManagerFactorySingleton;
import com.egg.entity.Libro;
import com.egg.persistence.dao.interfaces.LibroDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public void create(Libro libro) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(libro);
            et.commit();
        } catch (Exception e) {
            if(et.isActive()) {
                et.rollback();
            }
            throw e;
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void update(Libro libro) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(libro);
            et.commit();
        } catch (Exception e) {
            if(et.isActive()) {
                et.rollback();
            }
            throw e;
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Libro delete(Libro libro) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            libro.setAlta(false);
            em.merge(libro);
            et.commit();
            return null; // "Eliminamos" la instancia.
        } catch (Exception e) {
            if(et.isActive()) {
                et.rollback();
            }
            throw e;
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Libro> findAll() {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT l 
                            FROM Libro l 
                            WHERE l.alta = true
                        """;
            return em.createQuery(jpql, Libro.class)
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Libro findById(Long isbn) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT l 
                            FROM Libro l 
                            JOIN FETCH l.autor a 
                            JOIN FETCH l.editorial e 
                            WHERE l.isbn = :isbn AND l.alta = true
                        """;
            return em.createQuery(jpql, Libro.class)
                     .setParameter("isbn", isbn)
                     .getSingleResult();
        } catch(NoResultException e) {
            return null;
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Libro> findByTitle(String title) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT l 
                            FROM Libro l 
                            JOIN FETCH l.autor a 
                            JOIN FETCH l.editorial e 
                            WHERE l.titulo LIKE :title AND l.alta = true
                        """;
            return em.createQuery(jpql, Libro.class)
                     .setParameter("title", "%" + title + "%")
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Libro> findByAuthor(String authorName) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT l 
                            FROM Libro l 
                            JOIN FETCH l.autor a 
                            JOIN FETCH l.editorial e 
                            WHERE a.nombre LIKE :authorName AND l.alta = true
                        """;
            return em.createQuery(jpql, Libro.class)
                     .setParameter("authorName", "%" + authorName + "%")
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Libro> findByEditorial(String editorialName) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT l 
                            FROM Libro l 
                            JOIN FETCH l.autor a 
                            JOIN FETCH l.editorial e 
                            WHERE e.nombre LIKE :editorialName AND l.alta = true
                        """;
            return em.createQuery(jpql, Libro.class)
                     .setParameter("editorialName", "%" + editorialName + "%")
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

}
