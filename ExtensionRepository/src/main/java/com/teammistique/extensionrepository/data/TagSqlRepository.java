package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.data.base.TagRepository;
import com.teammistique.extensionrepository.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TagSqlRepository extends AbstractGenericRepository<Tag> implements TagRepository {

    private SessionFactory factory;

    @Autowired
    public TagSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public List<Tag> listAll() {
        List<Tag> tags = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            tags = session.createQuery("from Tag").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public Tag findById(int id) {
        Tag tag = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            tag = session.get(Tag.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tag;
    }

    @Override
    public Tag saveOrUpdate(Tag tag) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tag;
    }
}