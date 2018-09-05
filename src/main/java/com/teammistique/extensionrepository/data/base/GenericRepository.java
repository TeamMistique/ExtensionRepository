package com.teammistique.extensionrepository.data.base;

import java.util.List;

public interface GenericRepository<T> {
    List<T> listAll();

    T create(T entity);

    T update(T entity);
}
