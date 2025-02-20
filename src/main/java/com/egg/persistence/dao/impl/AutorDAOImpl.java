package com.egg.persistence.dao.impl;

import java.util.List;

import com.egg.config.EntityManagerFactorySingleton;
import com.egg.entity.Autor;
import com.egg.persistence.dao.interfaces.AutorDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class AutorDAOImpl implements AutorDAO {

    @Override
    public void create(Autor autor) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(autor);
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
    public void update(Autor autor) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(autor);
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
    public Autor delete(Autor autor) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            autor.setAlta(false);
            em.merge(autor);
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
    public List<Autor> findAll() {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT a 
                            FROM Autor a 
                            WHERE a.alta = true
                        """;
            return em.createQuery(jpql, Autor.class)
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Autor findById(Integer id) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT a 
                            FROM Autor a 
                            WHERE a.id = :id AND a.alta = true
                        """;
            return em.createQuery(jpql, Autor.class)
                     .setParameter("id", id)
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
    public List<Autor> findByName(String name) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT a 
                            FROM Autor a 
                            WHERE a.nombre LIKE :name AND a.alta = true
                        """;
            return em.createQuery(jpql, Autor.class)
                     .setParameter("name", "%" + name + "%")
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

}
