/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.sigad.sigad.controller.*;
import com.sigad.sigad.controller.cargaMasiva.CargaMasivaViewController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cfoch
 */
public class SIGADBusinessMain extends Application {
    public static final String viewPath = "/fxml/cargaMasivaView.fxml";
    public static void main(String[] args) {
        /*
        Configuration config;
        SessionFactory sessionFactory;
        Session session;

        config = new Configuration();
        config.configure("hibernate.cfg.xml");
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();

        session.beginTransaction();

        
        Perfil perfil1 = new Perfil();
        perfil1.setNombre("cliente");
        perfil1.setDescripcion("Perfil de cliente");
        perfil1.setActivo(true);

        session.save(perfil1);

        session.getTransaction().commit();
        
        session.close();
        sessionFactory.close();
        */
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        Parent root = FXMLLoader.load(getClass().getResource(viewPath));
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
