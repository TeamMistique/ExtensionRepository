package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.ExtensionRepository;
import com.teammistique.extensionrepository.models.Extension;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtensionSqlRepository extends AbstractGenericRepository<Extension> implements ExtensionRepository {

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
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Extension extension = session.get(Extension.class, id);
            session.delete(extension);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Extension> listPublishedExtensions(boolean published) {
        List extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = null;
            if (published) {
                query = session.createQuery("FROM Extension extension WHERE extension.publishedDate IS NOT NULL ORDER BY extension.publishedDate DESC");
            } else {
                query = session.createQuery("FROM Extension extension WHERE extension.publishedDate IS NULL ORDER BY extension.publishedDate DESC");
            }
            extensions = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> listFeaturedExtensions(boolean featured) {
        List extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = null;
            if (featured) {
                query = session.createQuery("FROM Extension extension WHERE extension.featuredDate IS NOT NULL ORDER BY extension.featuredDate DESC");
            } else {
                query = session.createQuery("FROM Extension extension WHERE extension.featuredDate IS NULL ORDER BY extension.publishedDate DESC");
            }
            extensions = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> filterPublishedByName(String name) {
        List extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Extension extension WHERE LOWER(extension.name) LIKE '%" + name + "%' AND extension.publishedDate IS NOT NULL");
            extensions = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> filterAllByName(String name) {
        List extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Extension extension WHERE LOWER(extension.name) LIKE '%" + name + "%'");
            extensions = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> listPopularExtensions(int count) {
        List extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Extension extension WHERE extension.publishedDate IS NOT NULL " + "ORDER BY extension.downloadsCounter DESC");
            query.setMaxResults(count);
            extensions = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> listNewExtensions(int count) {
        List extensions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Extension extension WHERE extension.publishedDate IS NOT NULL " +
                    "ORDER BY extension.publishedDate DESC");
            query.setMaxResults(count);
            extensions = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public Extension getExtensionByFile(String fileName) {
        Object extension = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            extension = session.createQuery("FROM Extension extension WHERE extension.file LIKE '%"+ fileName +"%'").stream().findFirst().orElse(null);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (Extension) extension;
    }
}
