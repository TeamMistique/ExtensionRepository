package com.teammistique.extensionrepository.data.base;

import java.util.List;
import java.util.stream.Stream;

public interface GenericRepository <T> {
    List<T> listAll();

    Stream<T> modelStream();

    T findById(int id);

    void create(T entity);

    void update(int id, T entity);

    void delete(int id);
}
