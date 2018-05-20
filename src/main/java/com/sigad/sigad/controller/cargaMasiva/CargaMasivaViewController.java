/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.controller.cargaMasiva;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import com.sigad.sigad.business.*;
import com.sigad.sigad.helpers.cargaMasiva.*;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author paul
 */
public class CargaMasivaViewController implements Initializable {
    private Desktop desktop = Desktop.getDesktop();
    private File loadedFile;
    private String entity;
    
    @FXML
    private JFXButton plantillaBtn;
    @FXML
    private JFXButton cargaMasivaBtn;
    @FXML
    private JFXComboBox<String> comboEntidad;
    @FXML
    private JFXTextField archivoNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = CargaMasivaConstantes.getList();
        comboEntidad.getItems().addAll(options);
        
        cargaMasivaBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                File file = loadFile(event);
                if(file != null){
                    openFile(file);
                    String fileName = file.getAbsolutePath();
                    archivoNombre.setText(fileName);
                    loadedFile = file;
                    initializeGrid(file);
               }
            }
        });
    }

    public File loadFile(ActionEvent event){
        Node source = (Node) event.getSource();
        Window currentStage = source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo de carga");
        File file = fileChooser.showOpenDialog(currentStage);
        return file;
    }
    
    
    private void openFile(File file) {
        if(Desktop.isDesktopSupported()){
            new Thread(() -> {
                 try {
                    desktop.open(file);
                } catch (IOException ex) {
                    Logger.getLogger(CargaMasivaViewController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }       
            }).start();
        }
    }

    private void initializeGrid(File file){
        
    }
}
