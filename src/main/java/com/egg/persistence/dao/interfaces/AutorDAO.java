package com.egg.persistence.dao.interfaces;

import java.util.List;

import com.egg.entity.Autor;

public interface AutorDAO extends DAO<Autor, Integer> {
    List<Autor> findByName(String name);
}
