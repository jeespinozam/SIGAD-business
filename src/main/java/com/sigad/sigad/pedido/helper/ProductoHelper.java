/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.helper;

import com.sigad.sigad.business.Producto;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Alexandra
 */
public class ProductoHelper {

    Configuration config;
    SessionFactory sessionFactory;
    Session session;

    public ProductoHelper() {

        config = new Configuration();
        config.configure("hibernate.cfg.xml");
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        // Add new Employee object
        Producto prod = new Producto("Rosas", "/images/rosa.jpg", 15, 12.0,Boolean.TRUE);
        Producto prod2 = new Producto("Chocolates", "/images/chocolate.jpg", 15,12.0, Boolean.TRUE);
        session.save(prod);
        session.save(prod2);

    }
    
    public ArrayList<Producto> getProducts(){
        Query query  = session.createQuery("from Producto");
        return new ArrayList<>( query.list());
    };

    public void close() {
        session.getTransaction().commit();
        sessionFactory.close();
    }

}
