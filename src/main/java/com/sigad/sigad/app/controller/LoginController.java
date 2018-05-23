/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.controller;

import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/app/view/login.fxml";
    public static String windowName = "Login";
    private static Configuration config = null;
    private static SessionFactory sessionFactory = null;
    @FXML
    private JFXTextField userTxt;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private StackPane hiddenSp;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginClicked(MouseEvent event) throws IOException {
        System.out.println(userTxt.getText());
        System.out.println(passwordTxt.getText());
        
        if(validate()){
            serviceInit();
            this.loadWindow(HomeController.viewPath, HomeController.windowName);
        }else{
            JFXDialogLayout content =  new JFXDialogLayout();
            content.setHeading(new Text("Error"));
            content.setBody(new Text("Cuenta o contraseña incorrectas"));

            ErrorController error = new ErrorController();
            error.loadDialog(content, hiddenSp);
            
        }
    }
    
    public static Session serviceInit(){
        Session session = null;
        
        if(config==null || sessionFactory==null) {
            try {
                config = new Configuration();
                config.configure("hibernate.cfg.xml");
                sessionFactory = config.buildSessionFactory();
                session = sessionFactory.openSession();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        } else {
            session = sessionFactory.openSession();
        }
        
        return session;
    }
    
    public static boolean serviceEnd(){
        if(sessionFactory!=null){
            try {
                sessionFactory.close();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        
        return true;
    } 
    
    private boolean validate() {
       return true;
       //return userTxt.getText().equals("admin") && passwordTxt.getText().equals("admin");
    }
    
    private void loadWindow(String viewPath, String windowTitle) throws IOException {
       userTxt.getScene().getWindow().hide();
       Parent newRoot = FXMLLoader.load(getClass().getResource(viewPath));
       Stage stage = new Stage();
       stage.setTitle(windowTitle);
       stage.setScene(new Scene(newRoot));
       stage.show();
    }

    @FXML
    private void signupClicked(MouseEvent event) {
    }

    @FXML
    private void recoverPasswordClicked(MouseEvent event) {
    }
}
