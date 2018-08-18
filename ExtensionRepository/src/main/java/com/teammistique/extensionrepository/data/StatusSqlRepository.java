package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class StatusSqlRepository extends AbstractGenericRepository<Status> implements GenericRepository<Status> {
    private SessionFactory factory;

    @Autowired
    public  StatusSqlRepository (SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Status> listAll() {
        List<Status> statuses = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            statuses = session.createQuery("from Status").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return statuses;
    }

    @Override
    public Status findById(int id) {
      Status status = null;

      try (Session session = factory.openSession()) {
          session.beginTransaction();
          status = session.get(Status.class, id);
          session.getTransaction().commit();
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
        return status;
    }

    @Override
    public void update(int id, Status entity) {
       try (Session session = factory.openSession()) {
           session.beginTransaction();
           Status status = session.get(Status.class, id);
           status.setStatusName(entity.getStatusName());
           status.setExtensions(entity.getExtensions());
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Status status = session.get(Status.class, id);
            session.delete(status);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
