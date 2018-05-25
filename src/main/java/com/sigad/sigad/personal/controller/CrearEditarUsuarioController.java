/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.personal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import com.sigad.sigad.perfiles.helper.PerfilesHelper;
import static com.sigad.sigad.personal.controller.PersonalController.selectedUser;
import com.sigad.sigad.usuarios.helper.UsuariosHelper;
import com.sun.org.apache.xerces.internal.util.DOMUtil;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static com.sigad.sigad.personal.controller.PersonalController.isUserCreate;
import static com.sigad.sigad.personal.controller.PersonalController.userDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author jorgeespinoza
 */
public class CrearEditarUsuarioController implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/personal/view/crearEditarUsuario.fxml";
    @FXML
    private JFXTextField nameTxt,appTxt,apmTxt,dniTxt,telephoneTxt,cellphoneTxt;
    
    public static Usuario user = null;
    
//    public static ObservableList<Profile> data = FXCollections.observableArrayList();
    
    @FXML
    private StackPane hiddenSp;
    @FXML
    private AnchorPane containerPane;
    
//    public static Profile selectedProfile = null;
    public static boolean isProfileCreate;
    public static JFXDialog profileDialog;
    
    @FXML
    private JFXTextField emailTxt;
    @FXML
    private JFXTextField passwordTxt;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addDialogBtns();
        
        //validate edit or create option in order to set user
        if(!PersonalController.isUserCreate){
            System.out.println(PersonalController.selectedUser.nombres);
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
                    UsuariosHelper helper = new UsuariosHelper();
                    boolean success = helper.saveUser(CrearEditarUsuarioController.user);
                    if(success){
                        PersonalController.updateTable(CrearEditarUsuarioController.user);
                    }else{
                        ErrorController error = new ErrorController();
                        error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                    }
                    
                    PersonalController.userDialog.close();
                    
                    
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
            }
        });
        
        containerPane.getChildren().add(save);
        containerPane.getChildren().add(cancel);
    }
    
    private void loadFields() {
        
        UsuariosHelper helper = new UsuariosHelper();
        user = helper.getUser(PersonalController.selectedUser.correo.getValue());
        
        if(user != null){
            nameTxt.setText(user.getNombres());
            appTxt.setText(user.getApellidoPaterno());
            apmTxt.setText(user.getApellidoMaterno());
            dniTxt.setText(user.getDni());
            telephoneTxt.setText(user.getTelefono());
            cellphoneTxt.setText(user.getCelular());
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
        
        user.setPerfil(new Perfil("Usuario", "GG", true));
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
        
        JFXListView<Label> profilesList = new JFXListView<>();
        
        Label lbl = new Label("Buttons");
        profilesList.getItems().add(lbl);
        profilesList.getItems().add(lbl);
        profilesList.getItems().add(lbl);
        profilesList.getItems().add(lbl);
        
        profilesList.getStyleClass().add("mylistview");
        profilesList.setStyle("-fx-background-color:WHITE");
        GridPane.setConstraints(profilesList, 1, 3);
    }

    private void initStorePicker() {
        JFXListView<Label> storesList = new JFXListView<>();
        
        Label lbl = new Label("Buttons");
        storesList.getItems().add(lbl);
        storesList.getItems().add(lbl);
        storesList.getItems().add(lbl);
        storesList.getItems().add(lbl);
        
        storesList.getStyleClass().add("mylistview");
        storesList.setStyle("-fx-background-color:WHITE");
        GridPane.setConstraints(storesList, 3, 3);
    }

    
    
    
    
    
    //    @FXML
//    void handleAction(ActionEvent event) {
//        if(event.getSource() == addBtn){
//            data.add(new Profile(
//                    new SimpleStringProperty("Test"), 
//                    new SimpleStringProperty("Test"), 
//                    new SimpleBooleanProperty(false))
//            );
//            
//        }else if(event.getSource() == moreBtn){
//            int count = profilesTbl.getSelectionModel().getSelectedCells().size();
//            if( count > 1){
//                ErrorController error = new ErrorController();
//                error.loadDialog("Atención", "Debe seleccionar solo un registro de la tabla", "Ok", hiddenSp);
//            }else if(count<=0){
//                ErrorController error = new ErrorController();
//                error.loadDialog("Atención", "Debe seleccionar al menos un registro de la tabla", "Ok", hiddenSp);
//            }else{
//                int selected  = profilesTbl.getSelectionModel().getSelectedIndex();
//                selectedProfile = (CrearEditarUsuarioController.Profile) profilesTbl.getSelectionModel().getModelItem(selected).getValue();
//                initPopup();
//                popup.show(moreBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
//            }
//        }
//    }
    
//        private void initProfileTable() {
//        JFXTreeTableColumn<Profile, Boolean> select = new JFXTreeTableColumn<>("Seleccionar");
//        select.setPrefWidth(80);
//        select.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, Boolean> param) -> param.getValue().getValue().seleccion);
//        select.setCellFactory((TreeTableColumn<Profile, Boolean> p) -> {
//            CheckBoxTreeTableCell<Profile, Boolean> cell = new CheckBoxTreeTableCell<>();
//            cell.setAlignment(Pos.CENTER);
//            return cell;
//        });
//        
//        JFXTreeTableColumn<Profile, String> nombre = new JFXTreeTableColumn<>("Nombre");
//        nombre.setPrefWidth(120);
//        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, String> param) -> param.getValue().getValue().profileName);
//        
//        JFXTreeTableColumn<Profile, String> description = new JFXTreeTableColumn<>("Descripción");
//        description.setPrefWidth(120);
//        description.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, String> param) -> param.getValue().getValue().profileName);
//        
//        PerfilesHelper helper = new PerfilesHelper();
//        ArrayList<Perfil> list = helper.getProfiles();
//        list.forEach(p -> {
//            Perfil profile = (Perfil) p;
//            System.out.println(p.getNombre());
//            data.add(new Profile(
//                    new SimpleStringProperty(profile.getNombre()), 
//                    new SimpleStringProperty(profile.getDescripcion()), 
//                    new SimpleBooleanProperty(profile.isActivo())));
//        });
//        
//        final TreeItem<Profile> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
//        
//        profilesTbl.setEditable(true);
//        profilesTbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        profilesTbl.getColumns().setAll(select, nombre, description);
//        profilesTbl.setRoot(root);
//        profilesTbl.setShowRoot(false);        
//    }
    
//    private void initPopup(){
//        JFXButton edit = new JFXButton("Editar");
//        JFXButton delete = new JFXButton("Eliminar");
//        
//        edit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                popup.hide();
//                try {
//                    CreateEdditProfileDialog(false);
//                } catch (IOException ex) {
//                    Logger.getLogger(PersonalController.class.getName()).log(Level.SEVERE, "initPopup(): CreateEdditProfileDialog()", ex);
//                }
//            }
//        });
//        
//        delete.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                popup.hide();
//                deleteProfileDialog();
//            }
//        });
//        
//        edit.setPadding(new Insets(20));
//        delete.setPadding(new Insets(20));
//        
//        edit.setPrefHeight(40);
//        delete.setPrefHeight(40);
//        
//        edit.setPrefWidth(145);
//        delete.setPrefWidth(145);
//        
//        VBox vBox = new VBox(edit, delete);
//        
//        
//        popup = new JFXPopup();
//        popup.setPopupContent(vBox);
//    }
//    
//    public void CreateEdditProfileDialog(boolean iscreate) throws IOException {
//        isProfileCreate = iscreate;
//        
//        JFXDialogLayout content =  new JFXDialogLayout();
//        
//        if(isProfileCreate){
//            content.setHeading(new Text("Crear Perfil"));
//        }else{
//            content.setHeading(new Text("Editar Perfil"));
//            
//            PerfilesHelper helper = new PerfilesHelper();
//            if(!helper.existProfileName(CrearEditarUsuarioController.selectedProfile.profileName.getValue())){
//                ErrorController error = new ErrorController();
//                error.loadDialog("Error", "No se pudo obtener el perfil", "Ok", hiddenSp);
//                System.out.println("Error al obtener el perfil" + CrearEditarUsuarioController.selectedProfile.profileName.getValue());
//                return;
//            }
//        }
//        
//        Node node;
//        node = (Node) FXMLLoader.load(CrearEditarUsuarioController.this.getClass().getResource(CrearEditarPerfilController.viewPath));
//        content.setBody(node);
//        content.setPrefSize(400,400);
//                
//        profileDialog = new JFXDialog(hiddenSp, content, JFXDialog.DialogTransition.CENTER);
//        profileDialog.show();
//    }  
//    
//    private void deleteProfileDialog() {
//        JFXDialogLayout content =  new JFXDialogLayout();
//        content.setHeading(new Text("Error"));
//        content.setBody(new Text("Cuenta o contraseña incorrectas"));
//                
//        JFXDialog dialog = new JFXDialog(hiddenSp, content, JFXDialog.DialogTransition.CENTER);
//        JFXButton button = new JFXButton("Okay");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                dialog.close();
//            }
//        });
//        content.setActions(button);
//        dialog.show();
//    }
//    
//    public class Profile extends RecursiveTreeObject<Profile> {
//
//        StringProperty profileName;
//        StringProperty profileDesc;
//        BooleanProperty seleccion;
//
//        public Profile(StringProperty profileName, StringProperty profileDesc, BooleanProperty seleccion) {
//            this.profileName = profileName;
//            this.profileDesc = profileDesc;
//            this.seleccion = seleccion;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (o instanceof Profile) {
//                Profile u = (Profile) o;
//                return u.profileName.equals(profileName);
//            }
//            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
//        }
//
//    }
}
