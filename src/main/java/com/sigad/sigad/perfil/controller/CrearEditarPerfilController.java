/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.perfil.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.helpers.PerfilHelper;
import com.sigad.sigad.business.helpers.UsuarioHelper;
import com.sigad.sigad.personal.controller.CrearEditarUsuarioController;
import static com.sigad.sigad.personal.controller.CrearEditarUsuarioController.user;
import com.sigad.sigad.personal.controller.PersonalController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class CrearEditarPerfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/perfil/view/crearEditarPerfil.fxml";
    public static Perfil perfil = null;
    @FXML
    private JFXTextField nameTxt;
    @FXML
    private JFXTextArea descriptionTXt;
    @FXML
    private AnchorPane containerPane;
    @FXML
    private StackPane hiddenSp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Add the buttons of the static dialog
        addDialogBtns();
        
        //validate edit or create option in order to set user
        if(!PerfilController.isProfileCreate){
            System.out.println(PerfilController.selectedProfile.name);
            //loadFields();
        }else{
            perfil = new Perfil();
        }
        
        //Inmediate validations
        //initValidator();
        
        //initProfileTable();
        //initProfilePicker();
    }

    private void addDialogBtns() {
        JFXButton save = new JFXButton("Guardar");
        save.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(save, -30.0);
        AnchorPane.setRightAnchor(save, 0.0);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(validateFields()){
                    System.out.println("VALIDADO ALL FIELDS");
                    updateFields();
                    
                    PerfilHelper helper = new PerfilHelper();
                    Long id = helper.saveProfile(CrearEditarPerfilController.perfil);
                    if(id != null){
                        PerfilController.updateProfileData(CrearEditarPerfilController.perfil);
                        PerfilController.profileDialog.close();
                    }else{
                        ErrorController error = new ErrorController();
                        error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                    }
                    
                }
            }
        });
        
        JFXButton cancel = new JFXButton("Cancelar");
        cancel.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(cancel, -30.0);
        AnchorPane.setRightAnchor(cancel, 85.0);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PerfilController.profileDialog.close();
            }
        });
        
        containerPane.getChildren().add(save);
        containerPane.getChildren().add(cancel);
    }
    
    public boolean validateFields() {
        if(!nameTxt.validate()){
            nameTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            nameTxt.requestFocus();
            return false;
        }else return true;
    }
    
    public void updateFields() {
        perfil.setNombre(nameTxt.getText());
        perfil.setDescripcion(descriptionTXt.getText());
        perfil.setActivo(true);
    }
}
