package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserSqlRepository extends AbstractGenericRepository<User> implements GenericRepository<User> {

    private SessionFactory factory;

    @Autowired
    public UserSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    public User findById(String id) {
        User user = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            user = session.get(User.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
