/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    public static String viewPath = "/com/sigad/sigad/app/view/home.fxml";
    public static String windowName = "Home";
    @FXML
    private JFXButton profileBtn, productoBtn;
    @FXML
    private AnchorPane secondPane, firstPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    private void handleButtonAction(MouseEvent event){
        if(event.getSource() == profileBtn){
            firstPane.toFront();
        }else if(event.getSource() == productoBtn){
            secondPane.toFront();
        }
    }

    @FXML
    private void profileBtnClicked(MouseEvent event) {
        firstPane.toFront();
    }

    @FXML
    private void productoBtnClicked(MouseEvent event) {
        secondPane.toFront();
    }
    
}
