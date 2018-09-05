package com.teammistique.extensionrepository.data.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    @Autowired
    SessionFactory factory;

    @Override
    public T create(T entity) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public T update(T entity){
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }
}
