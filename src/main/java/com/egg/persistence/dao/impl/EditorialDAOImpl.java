package com.egg.persistence.dao.impl;

import java.util.List;

import com.egg.config.EntityManagerFactorySingleton;
import com.egg.entity.Editorial;
import com.egg.persistence.dao.interfaces.EditorialDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class EditorialDAOImpl implements EditorialDAO {

    @Override
    public void create(Editorial editorial) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(editorial);
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
    public void update(Editorial editorial) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(editorial);
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
    public Editorial delete(Editorial editorial) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            editorial.setAlta(false);
            em.merge(editorial);
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
    public List<Editorial> findAll() {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT e 
                            FROM Editorial e 
                            WHERE e.alta = true
                        """;
            return em.createQuery(jpql, Editorial.class)
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Editorial findById(Integer id) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT e 
                            FROM Editorial e 
                            WHERE e.id = :id AND e.alta = true
                        """;
            return em.createQuery(jpql, Editorial.class)
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
    public List<Editorial> findByName(String name) {
        EntityManager em = EntityManagerFactorySingleton.getEntityManager();
        try {
            String jpql = """
                            SELECT e 
                            FROM Editorial e 
                            WHERE e.nombre LIKE :name AND e.alta = true
                        """;
            return em.createQuery(jpql, Editorial.class)
                     .setParameter("name", "%" + name + "%")
                     .getResultList();
        } finally {
            if(em.isOpen()) {
                em.close();
            }
        }
    }

}
