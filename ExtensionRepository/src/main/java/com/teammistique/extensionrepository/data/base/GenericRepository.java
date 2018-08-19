package com.teammistique.extensionrepository.data.base;

import java.util.List;
import java.util.stream.Stream;

public interface GenericRepository <T> {
    List<T> listAll();

    T findById(int id);

    T create(T entity);

    T update(T entity);

    void delete(T entity);
}
