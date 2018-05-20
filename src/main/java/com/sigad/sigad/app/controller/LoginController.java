/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.controller;

import com.jfoenix.controls.JFXDialog;
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
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.app.controller.ErrorController;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField usertxt;
    @FXML
    private JFXPasswordField passwordtxt;

    /**
     * Initializes the controller class.
     */
    public static String viewPath = "/com/sigad/sigad/app/view/login.fxml";
    @FXML
    private StackPane hiddensp;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginbtn(MouseEvent event) throws IOException {
        System.out.println(usertxt.getText());
        System.out.println(passwordtxt.getText());
        
        if(validate()){
            this.loadWindow(HomeController.viewPath, "Home");
        }else{
            JFXDialogLayout content =  new JFXDialogLayout();
            content.setHeading(new Text("Error"));
            content.setBody(new Text("Cuenta o contraseña incorrectas"));

            ErrorController error = new ErrorController();
            error.loadDialog(content, hiddensp);
            
        }
    }
    
    private boolean validate() {
       return usertxt.getText().equals("admin") && passwordtxt.getText().equals("admin");
    }
    
    private void loadWindow(String viewPath, String windowTitle) throws IOException {
       usertxt.getScene().getWindow().hide();
       Parent newRoot = FXMLLoader.load(getClass().getResource(viewPath));
       Stage stage = new Stage();
       stage.setTitle(windowTitle);
       stage.setScene(new Scene(newRoot));
       stage.show();
    }

    @FXML
    private void recoverPassword(MouseEvent event) {
    }

    @FXML
    private void signupbtn(MouseEvent event) {
    }
}
