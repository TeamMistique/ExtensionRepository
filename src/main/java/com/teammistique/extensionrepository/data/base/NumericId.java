package com.teammistique.extensionrepository.data.base;

public interface NumericId<T> extends GenericRepository<T> {
    T findById(int id);

    void delete(int id);
}
