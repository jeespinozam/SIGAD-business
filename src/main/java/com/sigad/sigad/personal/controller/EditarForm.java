/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.personal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

/**
 *
 * @author jorgeespinoza
 */
public class EditarForm implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/personal/view/editarForm.fxml";
    public static String windowName = "Editar Usuario";
    RequiredFieldValidator requiredFieldValidator ;
    
    @FXML
    private JFXTreeTableView<?> rolesTbl;

    @FXML
    private JFXButton moreBtn,addBtn;

    @FXML
    private JFXTextField nameTxt,appTxt,apmTxt,dniTxt,telephoneTxt,cellphoneTxt;

    @FXML
    void handleAction(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //try the index user
        System.out.println(PersonalController.selectedIndex);
        
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        requiredFieldValidator.setMessage("Campo obligatorio");
        
        nameTxt.getValidators().add(requiredFieldValidator);
        nameTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!nameTxt.validate()) nameTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                else nameTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        //add more validators
    }
    
}
