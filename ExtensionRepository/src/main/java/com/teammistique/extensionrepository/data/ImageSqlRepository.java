package com.teammistique.extensionrepository.data;

import com.teammistique.extensionrepository.data.base.AbstractGenericRepository;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Image;
import com.teammistique.extensionrepository.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageSqlRepository extends AbstractGenericRepository<Image> implements GenericRepository<Image> {

    private SessionFactory factory;

    @Autowired
    public ImageSqlRepository(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public List<Image> listAll() {
        List<Image> images = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            images = session.createQuery("from Image").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return images;
    }

    @Override
    public Image findById(int id) {
        Image image = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            image = session.get(Image.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

}
