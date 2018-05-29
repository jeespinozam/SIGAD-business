/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.insumos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.helpers.InsumosHelper;
import java.io.IOException;
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
public class CrearEditarInsumoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    public static String viewPath = "/com/sigad/sigad/insumos/view/crearEditarInsumo.fxml";
    
    @FXML
    private StackPane hiddenSp;

    @FXML
    private JFXTextField nombreTxt;

    @FXML
    private JFXTextField tiempoTxt;

    @FXML
    private JFXTextField volumenTxt;

    @FXML
    private JFXTextArea descripcionTxt;
    
    @FXML
    private JFXTextField precioTxt;


    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnCancelar;
    
    public static Insumo insumo = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        insumo = new Insumo();
    }
    
    
    @FXML
    private void handleAction(ActionEvent event) {
        if(event.getSource() == btnCancelar ) {
            ListaInsumoController.insumoDialog.close();
        }
        if(event.getSource() == btnGuardar) {
            //validar
            
            //crear nuevo objeto
            fillFields();
            
            InsumosHelper helper = new InsumosHelper();
            Long id = helper.saveInsumo(insumo);
            if( id!=null){
                ListaInsumoController.updateTable(insumo);
            }
            helper.close();
            ListaInsumoController.insumoDialog.close();
        }
    }
    
    private void fillFields(){
        insumo.setNombre(nombreTxt.getText());
        insumo.setTiempoVida(Integer.parseInt(tiempoTxt.getText()));
        insumo.setPrecio(Double.parseDouble(precioTxt.getText()));
        insumo.setActivo(true);
        insumo.setStockTotalFisico(0);
        insumo.setStockTotalLogico(0);
        insumo.setVolumen(Double.parseDouble(volumenTxt.getText()));
        insumo.setDescripcion(descripcionTxt.getText());
    }
    
    
    
}
