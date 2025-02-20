package com.egg.persistence.dao.interfaces;

import java.util.List;

import com.egg.entity.Editorial;

public interface EditorialDAO extends DAO<Editorial, Integer> {
    List<Editorial> findByName(String name);
}
