/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.personal.controller;

import com.jfoenix.controls.JFXButton;
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
import static com.sigad.sigad.personal.controller.PersonalController.dialog;
import com.sigad.sigad.usuarios.helper.UsuariosHelper;
import com.sun.org.apache.xerces.internal.util.DOMUtil;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    private JFXTreeTableView<Profile> rolesTbl;
    private final ObservableList<Profile> roles = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton moreBtn,addBtn;

    @FXML
    private JFXTextField nameTxt,appTxt,apmTxt,dniTxt,telephoneTxt,cellphoneTxt;
    
    public static Usuario user = null;
    
    ObservableList<Profile> data = FXCollections.observableArrayList();
    
    @FXML
    private StackPane hiddenSp;
    @FXML
    private AnchorPane containerPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXButton save = new JFXButton("Guardar");
        save.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(save, -20.0);
        AnchorPane.setRightAnchor(save, 0.0);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(validateFields()){
                    
                    updateFields();
                    UsuariosHelper helper = new UsuariosHelper();
                    helper.saveUser(EditarForm.user);
                    
                    dialog.close();
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
                dialog.close();
            }
        });
        
        containerPane.getChildren().add(save);
        containerPane.getChildren().add(cancel);
        
        //validate edit or create option in order to set user
        if(!PersonalController.isCreate){
            System.out.println(PersonalController.selectedUser.nombres);
            loadFields();
        }else{
            user = new Usuario();
        }
        
        initValidator();
        initTable();
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
    
    @FXML
    void handleAction(ActionEvent event) {
        if(event.getSource() == addBtn){
            
        }else if(event.getSource() == moreBtn){
            
        }
    }

    
    public boolean validateFields() {
        if(!nameTxt.validate()){
            nameTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            nameTxt.requestFocus();
            return false;
        }else nameTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
        
        if(!appTxt.validate()){
            appTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            return false;
        }else appTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
        
        if(!apmTxt.validate()){
            apmTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            return false;
        }else apmTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
        
        if(!dniTxt.validate()){
            dniTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            return false;
        }else dniTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
        
        if(!cellphoneTxt.validate()){
            cellphoneTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            return false;
        }else cellphoneTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
        
        return true;
    }

    public void updateFields() {
        user.setNombres(nameTxt.getText());
        user.setApellidoPaterno(appTxt.getText());
        user.setApellidoMaterno(apmTxt.getText());
        user.setDni(dniTxt.getText());
        user.setTelefono(telephoneTxt.getText());
        user.setCelular(cellphoneTxt.getText());
    }

    private void initValidator() {
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        requiredFieldValidator.setMessage("Campo obligatorio");
        
        nameTxt.getValidators().add(requiredFieldValidator);
        nameTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!nameTxt.validate()){
                    nameTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else nameTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        appTxt.getValidators().add(requiredFieldValidator);
        appTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!appTxt.validate()){
                    appTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else appTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        apmTxt.getValidators().add(requiredFieldValidator);
        apmTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!apmTxt.validate()){
                    apmTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else apmTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        dniTxt.getValidators().add(requiredFieldValidator);
        dniTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!dniTxt.validate()){
                    dniTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else dniTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        cellphoneTxt.getValidators().add(requiredFieldValidator);
        cellphoneTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!cellphoneTxt.validate()){
                    cellphoneTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else cellphoneTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
    }

    private void initTable() {
        JFXTreeTableColumn<Profile, Boolean> select = new JFXTreeTableColumn<>("Seleccionar");
        select.setPrefWidth(80);
        select.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, Boolean> param) -> param.getValue().getValue().seleccion);
        select.setCellFactory((TreeTableColumn<Profile, Boolean> p) -> {
            CheckBoxTreeTableCell<Profile, Boolean> cell = new CheckBoxTreeTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        
        JFXTreeTableColumn<Profile, String> nombre = new JFXTreeTableColumn<>("Nombre");
        nombre.setPrefWidth(120);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, String> param) -> param.getValue().getValue().profileName);
        
        JFXTreeTableColumn<Profile, String> description = new JFXTreeTableColumn<>("Descripción");
        description.setPrefWidth(120);
        description.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, String> param) -> param.getValue().getValue().profileName);
        
        PerfilesHelper helper = new PerfilesHelper();
        ArrayList<Perfil> list = helper.getProfiles();
        list.forEach(p -> {
            Perfil profile = (Perfil) p;
            System.out.println(p.getNombre());
            data.add(new Profile(
                    new SimpleStringProperty(profile.getNombre()), 
                    new SimpleStringProperty(profile.getDescripcion()), 
                    new SimpleBooleanProperty(false)));
        });
        
        final TreeItem<Profile> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        
        rolesTbl.setEditable(true);
        rolesTbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rolesTbl.getColumns().setAll(select, nombre, description);
        rolesTbl.setRoot(root);
        rolesTbl.setShowRoot(false);
        
    }
    
    public class Profile extends RecursiveTreeObject<Profile> {

        StringProperty profileName;
        StringProperty profileDesc;
        BooleanProperty seleccion;

        public Profile(StringProperty profileName, StringProperty profileDesc, BooleanProperty seleccion) {
            this.profileName = profileName;
            this.profileDesc = profileDesc;
            this.seleccion = seleccion;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Profile) {
                Profile u = (Profile) o;
                return u.profileName.equals(profileName);
            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

    }
    
}
