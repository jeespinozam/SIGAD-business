/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.cuentas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import static javafx.scene.input.KeyCode.S;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class CuentasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/cuentas/view/cuentas.fxml";
    public static String windowName = "Cuentas";
    
    @FXML
    private JFXTreeTableView userTbl;

    ObservableList<User> data = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton addBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn name = new JFXTreeTableColumn("Nombre");
        name.setPrefWidth(50);
        name.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<CuentasController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CuentasController.User, String> p) {
                return p.getValue().getValue().nombres;
            }
        });
        
        JFXTreeTableColumn dni = new JFXTreeTableColumn("DNI");
        dni.setPrefWidth(50);
        dni.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<CuentasController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CuentasController.User, String> p) {
                return p.getValue().getValue().dni;
            }
        });
        
        JFXTreeTableColumn telephone = new JFXTreeTableColumn("Teléfono");
        telephone.setPrefWidth(50);
        telephone.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<CuentasController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CuentasController.User, String> p) {
                return p.getValue().getValue().telefono;
            }
        });
        
        JFXTreeTableColumn cellphone = new JFXTreeTableColumn("Celular");
        cellphone.setPrefWidth(50);
        cellphone.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<CuentasController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CuentasController.User, String> p) {
                return p.getValue().getValue().celular;
            }
        });
        
        JFXTreeTableColumn profile = new JFXTreeTableColumn("Perfil");
        profile.setPrefWidth(50);
        profile.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<CuentasController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CuentasController.User, String> p) {
                return p.getValue().getValue().profileName;
            }
        });
        
        JFXTreeTableColumn profileDesc = new JFXTreeTableColumn("Descripción");
        profileDesc.setPrefWidth(50);
        profileDesc.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<CuentasController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CuentasController.User, String> p) {
                return p.getValue().getValue().profileDesc;
            }
        });
        
        userTbl.getColumns().add(name);
        userTbl.getColumns().add(dni);
        userTbl.getColumns().add(telephone);
        userTbl.getColumns().add(cellphone);
        userTbl.getColumns().add(profile);
        userTbl.getColumns().add(profileDesc);
        
        data.add(
                new User(
                        new SimpleStringProperty("Jorge"), 
                        new SimpleStringProperty("71067346"),
                        new SimpleStringProperty("943820931"),
                        new SimpleStringProperty("943820931"),
                        new SimpleStringProperty("User"),
                        new SimpleStringProperty("User"),
                        false
                ));
        data.add(       
                new User(
                        new SimpleStringProperty("Jorge"), 
                        new SimpleStringProperty("71067346"),
                        new SimpleStringProperty("943820931"),
                        new SimpleStringProperty("943820931"),
                        new SimpleStringProperty("User"),
                        new SimpleStringProperty("User"),
                        false
                ));
        data.add(
                new User(
                        new SimpleStringProperty("Jorge"), 
                        new SimpleStringProperty("71067346"),
                        new SimpleStringProperty("943820931"),
                        new SimpleStringProperty("943820931"),
                        new SimpleStringProperty("User"),
                        new SimpleStringProperty("User"),
                        false
                )
        );
        
        final TreeItem<CuentasController.User> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);

        
        userTbl.setEditable(true);
        userTbl.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        userTbl.getColumns().setAll(name, dni, telephone, cellphone, profile, profileDesc);
        userTbl.setRoot(root);
        userTbl.setShowRoot(false);
        
        //Btn Add
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.add(
                    new User(
                            new SimpleStringProperty("Jorge"), 
                            new SimpleStringProperty("71067346"),
                            new SimpleStringProperty("943820931"),
                            new SimpleStringProperty("943820931"),
                            new SimpleStringProperty("User"),
                            new SimpleStringProperty("User"),
                            false
                    )
                );
            }
        });
        
    }    
    class User extends RecursiveTreeObject<User> {

        StringProperty nombres;
        StringProperty dni;
        StringProperty telefono;
        StringProperty celular;
        StringProperty profileName;
        StringProperty profileDesc;
        boolean activo;

        public User(StringProperty nombres, StringProperty dni,
                StringProperty telefono, StringProperty celular,
                StringProperty nombre, StringProperty descripcion,
                boolean activo) {
            this.nombres = nombres;
            this.dni = dni;
            this.telefono = telefono;
            this.celular = celular;
            this.profileName = nombre;
            this.profileDesc = descripcion;
            this.activo = activo;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof User) {
                User u = (User) o;
                return u.dni.equals(dni);
            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + Objects.hashCode(this.dni);
            return hash;
        }

    }
}
