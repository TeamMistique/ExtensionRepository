package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Extension;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtensionSqlRepository extends AbstractGenericRepository<Extension> implements GenericRepository<Extension> {

    private SessionFactory factory;

    @Autowired
    public ExtensionSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Extension> listAll() {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            extensions = session.createQuery("from Extension").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public Extension findById(int id) {
        Extension extension = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            extension = session.get(Extension.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extension;
    }

    @Override
    public void update(int id, Extension extension) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            Extension e = session.get(Extension.class, id);
            e.setStatus(extension.getStatus());
            e.setName(extension.getName());
            e.setDescription(extension.getDescription());
            e.setOwner(extension.getOwner());
            e.setDownloadsCounter(extension.getDownloadsCounter());
            e.setFile(extension.getFile());
            e.setLink(extension.getLink());
            e.setTags(extension.getTags());
            e.setIssuesCounter(extension.getIssuesCounter());
            e.setPullRequestsCounter(extension.getPullRequestsCounter());
            e.setLastCommitDate(extension.getLastCommitDate());
        }
    }

    @Override
    public void delete(int id) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            Extension extension= session.get(Extension.class, id);
            session.delete(extension);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
