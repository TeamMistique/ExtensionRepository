package com.teammistique.extensionrepository.data.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    @Autowired
    SessionFactory factory;

    @Override
    public Stream<T> modelStream() {
        return listAll().stream();
    }


    @Override
    public void create(T entity) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
