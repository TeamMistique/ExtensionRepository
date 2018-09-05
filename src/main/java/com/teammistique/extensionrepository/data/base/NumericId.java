package com.teammistique.extensionrepository.data.base;

import java.util.List;
import java.util.stream.Stream;

public interface NumericId<T> extends GenericRepository<T> {
    T findById(int id);

    void delete(int id);
}
