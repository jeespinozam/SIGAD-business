/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sigad.sigad.deposito.controller.FXMLAlmacenIngresoListaOrdenCompraController;
import com.sigad.sigad.deposito.controller.FXMLAlmacenIngresoDetalleOrdenCompraController;

/**
 *
 * @author chrs
 */
public class Main extends Application{
    private String viewPath=FXMLAlmacenIngresoDetalleOrdenCompraController.viewPath;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(viewPath));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
