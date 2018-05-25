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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.insumos.controller.InsumoController;
import com.sigad.sigad.personal.controller.CrearEditarUsuarioController;
import com.sigad.sigad.personal.controller.PersonalController;


/**
 *
 * @author cfoch
 */

public class SIGADBusinessMain extends Application{
    
    @Override
	public void start(Stage stage) {
            try {
                    Parent root = FXMLLoader.load(getClass().getResource(PersonalController.viewPath));
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
        
}
