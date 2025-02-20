package com.egg.persistence.dao.interfaces;

import java.util.List;

public interface DAO<T,K> {
    void create(T obj);

    void update(T obj);

    T delete(T obj);

    List<T> findAll();

    T findById(K id);
}

