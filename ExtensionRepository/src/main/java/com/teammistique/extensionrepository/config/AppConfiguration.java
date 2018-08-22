package com.teammistique.extensionrepository.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.teammistique.extensionrepository.models.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public SessionFactory createSessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Extension.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Image.class)
                .addAnnotatedClass(File.class)
                .buildSessionFactory();
    }
}
