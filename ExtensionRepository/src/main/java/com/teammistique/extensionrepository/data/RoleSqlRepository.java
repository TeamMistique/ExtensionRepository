package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.RoleRepository;
import com.teammistique.extensionrepository.models.security.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleSqlRepository extends AbstractGenericRepository<Role> implements RoleRepository {
    private SessionFactory factory;

    @Autowired
    public RoleSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Role getByName(String role) {
        List roles = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            roles = session.createQuery("FROM Role WHERE role = '"+role+"'").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (Role) roles.get(0);
    }

    @Override
    public List<Role> listAll() {
        List roles = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            roles = session.createQuery("FROM Role").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return roles;
    }

    @Override
    public Role findById(int id) {
        Role role = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            role = session.get(Role.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return role;
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Role role = session.get(Role.class, id);
            session.delete(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
