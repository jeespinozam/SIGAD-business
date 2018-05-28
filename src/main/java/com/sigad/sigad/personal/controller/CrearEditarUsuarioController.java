/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.personal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Tienda;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.business.helpers.PerfilHelper;
import com.sigad.sigad.business.helpers.TiendaHelper;
import com.sigad.sigad.business.helpers.UsuarioHelper;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author jorgeespinoza
 */
public class CrearEditarUsuarioController implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/personal/view/crearEditarUsuario.fxml";
    @FXML
    private JFXTextField nameTxt,appTxt,apmTxt,dniTxt,telephoneTxt,cellphoneTxt;
    public static Usuario user = null;
    @FXML
    private StackPane hiddenSp;
    @FXML
    private AnchorPane containerPane;
    @FXML
    private JFXTextField emailTxt;
    @FXML
    private JFXTextField passwordTxt;
    @FXML
    private JFXToggleButton isActiveBtn;
    @FXML
    private JFXScrollPane profilePane, storePane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add the buttons of the static dialog
        addDialogBtns();
        
        //validate edit or create option in order to set user
        if(!PersonalController.isStoreCreate){
            System.out.println(PersonalController.selectedStore.nombres);
            loadFields();
        }else{
            user = new Usuario();
        }
        
        //Inmediate validations
        initValidator();
        
        //initProfileTable();
        initProfilePicker();
        
        //initStoresTable()
        initStorePicker();
        
    }

    private void addDialogBtns() {
        JFXButton save = new JFXButton("Guardar");
        save.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(save, -20.0);
        AnchorPane.setRightAnchor(save, 0.0);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(validateFields()){
                    System.out.println("VALIDADO ALL FIELDS");
                    updateFields();
                    
                    UsuarioHelper helper = new UsuarioHelper();
                    if(!PersonalController.isStoreCreate){
                        boolean ok = helper.updateUser(CrearEditarUsuarioController.user);
                        if(ok){
                            PersonalController.data.remove(PersonalController.selectedStore);
                            PersonalController.updateTable(CrearEditarUsuarioController.user);
                            PersonalController.userDialog.close();
                        }else{
                            ErrorController error = new ErrorController();
                            error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                        }
                    }else{
                        
                        Long id = helper.saveUser(CrearEditarUsuarioController.user);
                        if(id != null){
                            PersonalController.updateTable(CrearEditarUsuarioController.user);
                            PersonalController.userDialog.close();
                        }else{
                            ErrorController error = new ErrorController();
                            error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                        }

                    }
                }
            }
        });
        
        JFXButton cancel = new JFXButton("Cancelar");
        cancel.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(cancel, -20.0);
        AnchorPane.setRightAnchor(cancel, 85.0);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PersonalController.userDialog.close();
                //PersonalController.getDataFromDB();
            }
        });
        
        containerPane.getChildren().add(save);
        containerPane.getChildren().add(cancel);
    }
    
    private void loadFields() {
        
        UsuarioHelper usuarioHelper = new UsuarioHelper();
        user = usuarioHelper.getUser(PersonalController.selectedStore.correo.getValue());
        
        if(user != null){
            nameTxt.setText(user.getNombres());
            appTxt.setText(user.getApellidoPaterno());
            apmTxt.setText(user.getApellidoMaterno());
            dniTxt.setText(user.getDni());
            telephoneTxt.setText(user.getTelefono());
            cellphoneTxt.setText(user.getCelular());
            emailTxt.setText(user.getCorreo());
            passwordTxt.setText(user.getPassword());
            isActiveBtn.setSelected(user.isActivo());
            
            //styles
            passwordTxt.setEditable(false);
            passwordTxt.setOpacity(0.5);
        }
    }
    
    public boolean validateFields() {
        if(!nameTxt.validate()){
            nameTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            nameTxt.requestFocus();
            return false;
        }else if(!appTxt.validate()){
            appTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            appTxt.requestFocus();
            return false;
        }else if(!apmTxt.validate()){
            apmTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            apmTxt.requestFocus();
            return false;
        }else if(!dniTxt.validate()){
            dniTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            dniTxt.requestFocus();
            return false;
        }else if(!cellphoneTxt.validate()){
            cellphoneTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            cellphoneTxt.requestFocus();
            return false;
        }else if(!emailTxt.validate()){
            emailTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            emailTxt.requestFocus();
            return false;
        }else if(!passwordTxt.validate()){
            passwordTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            passwordTxt.requestFocus();
            return false;
        }else return true;
    }

    public void updateFields() {
        user.setNombres(nameTxt.getText());
        user.setApellidoPaterno(appTxt.getText());
        user.setApellidoMaterno(apmTxt.getText());
        user.setDni(dniTxt.getText());
        user.setTelefono(telephoneTxt.getText());
        user.setCelular(cellphoneTxt.getText());
        user.setCorreo(emailTxt.getText());
        user.setPassword(passwordTxt.getText());
        user.setActivo(isActiveBtn.isSelected());
        
        PerfilHelper helper = new PerfilHelper();
        Perfil p = helper.getProfile("Usuario");
        if(p!= null){
            user.setPerfil(p);
        }

    }

    private void initValidator() {
        RequiredFieldValidator r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");  
        nameTxt.getValidators().add(r);
        nameTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!nameTxt.validate()){
                    nameTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else nameTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");  
        appTxt.getValidators().add(r);
        appTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!appTxt.validate()){
                    appTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else appTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        apmTxt.getValidators().add(r);
        apmTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!apmTxt.validate()){
                    apmTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else apmTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        dniTxt.getValidators().add(r);
        dniTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!dniTxt.validate()){
                    dniTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else dniTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        cellphoneTxt.getValidators().add(r);
        cellphoneTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!cellphoneTxt.validate()){
                    cellphoneTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else cellphoneTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        emailTxt.getValidators().add(r);
        emailTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!emailTxt.validate()){
                    emailTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else emailTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        passwordTxt.getValidators().add(r);
        passwordTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!passwordTxt.validate()){
                    passwordTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else passwordTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });

    }

    private void initProfilePicker() {
        JFXListView<Label> profilesListView = new JFXListView<>();
        
        //load profiles
        PerfilHelper helper = new PerfilHelper();
        ArrayList<Perfil> perfilList = helper.getProfiles();
        
        if(perfilList != null){
            for (int i = 0; i < perfilList.size(); i++) {
                Label lbl = new Label(perfilList.get(i).getNombre());
                lbl.setPrefSize(200, 30);
                profilesListView.getItems().add(lbl);
            }
        }
        
        profilesListView.getStyleClass().add("mylistview");
        profilesListView.setStyle("-fx-background-color:WHITE");
        
        profilePane.getChildren().add(profilesListView);
    }

    private void initStorePicker() {
        JFXListView<Label> storesListView = new JFXListView<>();
        
        //load stores
        TiendaHelper helper = new TiendaHelper();
        ArrayList<Tienda> tiendaList = helper.getStores();
        
        if(tiendaList != null){
            for (int i = 0; i < tiendaList.size(); i++) {
                Label lbl = new Label(tiendaList.get(i).getDireccion());
                lbl.setPrefSize(200, 30);
                storesListView.getItems().add(lbl);
            }
        }
        
        storesListView.getStyleClass().add("mylistview");
        storesListView.setStyle("-fx-background-color:WHITE");
        
        storePane.getChildren().add(storesListView);
    }

}
