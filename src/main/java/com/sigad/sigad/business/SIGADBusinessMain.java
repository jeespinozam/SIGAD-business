/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author cfoch
 */
public class SIGADBusinessMain {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Configuration config;
        SessionFactory sessionFactory;
        Session session;

        config = new Configuration();
        config.configure("hibernate.cfg.xml");
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();

        session.beginTransaction();

        
        Usuario u= new Usuario();
        MessageDigest digest=MessageDigest.getInstance("MD5");
        String pass="test";
        digest.update(pass.getBytes());
        String hash=digest.digest().toString();
        u.setPassword(hash);
        session.save(u);

        session.getTransaction().commit();
        
        session.close();
        sessionFactory.close();
    }
}
