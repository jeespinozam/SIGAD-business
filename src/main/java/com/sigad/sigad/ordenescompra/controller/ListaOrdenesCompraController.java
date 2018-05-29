/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.ordenescompra.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author chrs
 */
public class ListaOrdenesCompraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane hiddenSp;

    @FXML
    private JFXTreeTableView<?> tblOrdenesCompra;

    @FXML
    private JFXButton moreBtn;

    @FXML
    private JFXButton btnAdd;

    @FXML
    void handleAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
