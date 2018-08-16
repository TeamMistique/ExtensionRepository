//package com.teammistique.extensionrepository.config;
//
//import com.teammistique.extensionrepository.models.Extension;
//import com.teammistique.extensionrepository.models.Status;
//import com.teammistique.extensionrepository.models.Tag;
//import org.hibernate.SessionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ServicesConfig {
//    private static SessionFactory sessionFactory;
//
//    static {
//        sessionFactory = new org.hibernate.cfg.Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(Tag.class)
//                .addAnnotatedClass(Extension.class)
//                .addAnnotatedClass(Status.class)
//                .buildSessionFactory();
//    }
//
//    @Bean
//    public SessionFactory sessionFactory() {
//        return sessionFactory;
//    }
//}
