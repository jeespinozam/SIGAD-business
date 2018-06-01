/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.insumos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.Proveedor;
import com.sigad.sigad.business.helpers.InsumosHelper;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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
    private AnchorPane containerPane;

    @FXML
    private JFXTextField precioTxt;

    @FXML
    private JFXTextField nombreTxt;

    @FXML
    private JFXTextField volumenTxt;

    @FXML
    private JFXComboBox<Proveedor> cbxProv;

    @FXML
    private JFXTextField tiempoTxt;

    @FXML
    private JFXTextArea descripcionTxt;

    
    
    public static Insumo insumo = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addDialogBtns();
        if(!ListaInsumoController.isInsumoCreate) {
            System.out.println(ListaInsumoController.selectedInsumo);
            loadFields();
        }
        else {
            insumo = new Insumo();
        }
        
        initValidator();
    }
    
    
    private void addDialogBtns() {
        JFXButton save = new JFXButton("Guardar");
        save.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(save, -20.0);
        AnchorPane.setRightAnchor(save, 0.0);
        save.setOnAction((ActionEvent event) -> {
            if(validateFields()){
                System.out.println("VALIDADO ALL FIELDS");
                
                InsumosHelper helper = new InsumosHelper();
                //edicion
                if(!ListaInsumoController.isInsumoCreate){
                    System.out.println("editando");
                    System.out.println("waaa"+insumo.getNombre());
                    Long id = helper.updateInsumo(insumo);
                    if(id != null){
                        ListaInsumoController.insumosList.remove(ListaInsumoController.selectedInsumo);
                        ListaInsumoController.updateTable(insumo);
                        ListaInsumoController.insumoDialog.close();
                    }else{
                        ErrorController error = new ErrorController();
                        error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                    }
                }
                //creacion
                else{
                    Long id = helper.disableInsumo(insumo);
                    if(id != null){
                        ListaInsumoController.updateTable(insumo);
                        ListaInsumoController.insumoDialog.close();
                    }else {
                        ErrorController error = new ErrorController();
                        error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                    }
                }
            }
        });
        
        JFXButton cancel = new JFXButton("Cancelar");
        cancel.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(cancel, -20.0);
        AnchorPane.setRightAnchor(cancel, 85.0);
        cancel.setOnAction((ActionEvent event) -> {
            ListaInsumoController.insumoDialog.close();
            //PersonalController.getDataFromDB();
        });
        
        containerPane.getChildren().add(save);
        containerPane.getChildren().add(cancel);
    }
    
    private void loadFields(){
        InsumosHelper helper = new InsumosHelper();
        insumo = helper.getInsumo(ListaInsumoController.selectedInsumo.getId());
        
        if(insumo != null) {
            nombreTxt.setText(insumo.getNombre());
            nombreTxt.setDisable(true);
            tiempoTxt.setText(insumo.getTiempoVida() + "");
            descripcionTxt.setText(insumo.getDescripcion());
            precioTxt.setText(insumo.getPrecio()+ "");
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
    
    public boolean validateFields() {
        if(!nombreTxt.validate()){
            nombreTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            nombreTxt.requestFocus();
            return false;
        }else if(!tiempoTxt.validate()){
            tiempoTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            tiempoTxt.requestFocus();
            return false;
        }else if(!volumenTxt.validate()){
            volumenTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            volumenTxt.requestFocus();
            return false;
        }else if(!descripcionTxt.validate()){
            descripcionTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            descripcionTxt.requestFocus();
            return false;
        }else if(!precioTxt.validate()){
            precioTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            precioTxt.requestFocus();
            return false;
        }else return true;
    }
    
    private void initValidator() {
        RequiredFieldValidator r;
        NumberValidator n;
        DoubleValidator d;
                
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");  
        nombreTxt.getValidators().add(r);
        nombreTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!nombreTxt.validate()){
                    nombreTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else nombreTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        /*Tiempo de vida*/
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        tiempoTxt.getValidators().add(r);
        
        n = new NumberValidator();
        n.setMessage("Campo numérico");
        n.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        tiempoTxt.getValidators().add(n);
        
        tiempoTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!tiempoTxt.validate()){
                    tiempoTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else tiempoTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });

        /*Volumen*/
        n = new NumberValidator();
        n.setMessage("Campo numérico");
        n.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        volumenTxt.getValidators().add(n);
        
        volumenTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!volumenTxt.validate()){
                    volumenTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else volumenTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        /*Precio proveedor*/
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        precioTxt.getValidators().add(r);
        
        
        d = new DoubleValidator();
        d.setMessage("Campo numérico");
        d.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        precioTxt.getValidators().add(d);
        
        precioTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!precioTxt.validate()){
                    precioTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else precioTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        

    }
    
}
