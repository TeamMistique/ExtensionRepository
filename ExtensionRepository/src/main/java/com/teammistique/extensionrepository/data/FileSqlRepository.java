package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.models.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FileSqlRepository extends AbstractGenericRepository<File> {
    private SessionFactory factory;

    @Autowired
    public FileSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<File> listAll() {
        List<File> files = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            files = session.createQuery("from File").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return files;
    }

    @Override
    public File findById(int id) {
        File file = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            file = session.get(File.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return file;
    }
}
