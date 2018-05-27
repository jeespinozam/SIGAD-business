/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.productos.controller;

import com.jfoenix.controls.JFXButton;
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jorgito-stark
 */
public class ProductosSubmenuController implements Initializable {
    public static  String viewPath = "/com/sigad/sigad/productos/view/ProductosSubmenu.fxml";
    
    @FXML
    private JFXButton ingresosBtn;
    @FXML
    private JFXButton salidasBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ingresosBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try {
                    loadWindow(ProductosNuevoController.viewPath, "Ingreso de productos");
                }catch (IOException ex) {
                    Logger.getLogger(ProductosSubmenuController.class.getName()).log(Level.SEVERE, "sidebarBtns.get(1).setOnAction", ex);
                }                
            }
        });

        salidasBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try {
                    loadWindow(SeleccionarProductosController.viewPath, "Selección de productos");
                }catch (IOException ex) {
                    Logger.getLogger(ProductosSubmenuController.class.getName()).log(Level.SEVERE, "sidebarBtns.get(1).setOnAction", ex);
                }                
            }
        });
    }  
    
    private void loadWindow(String viewPath, String windowTitle) throws IOException {
       ingresosBtn.getScene().getWindow().hide();
       Parent newRoot = FXMLLoader.load(getClass().getResource(viewPath));
       Stage stage = new Stage();
       stage.setTitle(windowTitle);
       stage.setScene(new Scene(newRoot));
       stage.show();
    }
    
}
