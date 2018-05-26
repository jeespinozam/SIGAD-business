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
import com.sigad.sigad.helpers.cargaMasiva.CargaMasivaConstantes;
import com.sigad.sigad.helpers.cargaMasiva.CargaMasivaHelper;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.insumos.controller.InsumoController;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author cfoch
 */
public class SIGADBusinessMain {
    
    public static void main(String[] args) {
//        Configuration config;
//        SessionFactory sessionFactory;
//        Session session;
//
//        config = new Configuration();
//        config.configure("hibernate.cfg.xml");
//        sessionFactory = config.buildSessionFactory();
//        session = sessionFactory.openSession();
//
//        session.beginTransaction();
//
//        
//        Perfil perfil1 = new Perfil();
//        perfil1.setNombre("cliente");
//        perfil1.setDescripcion("Perfil de cliente");
//        perfil1.setActivo(true);
//
//        session.save(perfil1);
//
//        session.getTransaction().commit();
//        
//        session.close();
//        sessionFactory.close();

    List<String> lista = new ArrayList<>();
    lista.add(CargaMasivaConstantes.TABLA_PERMISOS);
    lista.add(CargaMasivaConstantes.TABLA_PRODUCTOCATEGORIA);
    lista.add(CargaMasivaConstantes.TABLA_TIENDAS);
    CargaMasivaHelper.generarCargaMasivaTemplate(lista, "/home/paul/pruebaTemplate.xls");

    }
    
    /*
    @Override
	public void start(Stage stage) {
            try {
                    Parent root = FXMLLoader.load(getClass().getResource(LoginController.viewPath));
                    Scene scene = new Scene(root);
                    //scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
            } catch(Exception e) {
                    e.printStackTrace();
            }
	}
	
	public static void main(String[] args) {
            launch(args);
	}
*/
}
