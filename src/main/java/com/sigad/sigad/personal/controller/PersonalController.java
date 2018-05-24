/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.personal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import com.sigad.sigad.usuarios.helper.UsuariosHelper;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class PersonalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/personal/view/personal.fxml";
    public static String windowName = "Cuentas";
    
    @FXML
    private JFXTreeTableView userTbl;

    ObservableList<User> data = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton moreBtn;
    @FXML
    private JFXPopup popup;
    @FXML
    private StackPane hiddenSp;
    
    public static int selectedIndex;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn name = new JFXTreeTableColumn("Nombre");
        name.setPrefWidth(50);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().nombres;
            }
        });
        
        JFXTreeTableColumn apellidoMaterno = new JFXTreeTableColumn("Appelido Materno");
        apellidoMaterno.setPrefWidth(50);
        apellidoMaterno.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().apellidoMaterno;
            }
        });
        
        JFXTreeTableColumn apellidoPaterno = new JFXTreeTableColumn("Appelido Paterno");
        apellidoPaterno.setPrefWidth(50);
        apellidoPaterno.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().apellidoPaterno;
            }
        });
        
        JFXTreeTableColumn dni = new JFXTreeTableColumn("DNI");
        dni.setPrefWidth(50);
        dni.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().dni;
            }
        });
        
        JFXTreeTableColumn telephone = new JFXTreeTableColumn("Teléfono");
        telephone.setPrefWidth(50);
        telephone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().telefono;
            }
        });
        
        JFXTreeTableColumn cellphone = new JFXTreeTableColumn("Celular");
        cellphone.setPrefWidth(50);
        cellphone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().celular;
            }
        });
        
        JFXTreeTableColumn profile = new JFXTreeTableColumn("Perfil");
        profile.setPrefWidth(50);
        profile.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().profileName;
            }
        });
        
        JFXTreeTableColumn profileDesc = new JFXTreeTableColumn("Descripción");
        profileDesc.setPrefWidth(50);
        profileDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalController.User, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalController.User, String> p) {
                return p.getValue().getValue().profileDesc;
            }
        });
        
        userTbl.getColumns().add(name);
        userTbl.getColumns().add(apellidoMaterno);
        userTbl.getColumns().add(apellidoPaterno);
        userTbl.getColumns().add(dni);
        userTbl.getColumns().add(telephone);
        userTbl.getColumns().add(cellphone);
        userTbl.getColumns().add(profile);
        userTbl.getColumns().add(profileDesc);
        
        //DB
        getDataFromDB();
        
        final TreeItem<PersonalController.User> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        
        userTbl.setEditable(true);
        userTbl.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);        
        userTbl.getColumns().setAll(name,apellidoMaterno,apellidoPaterno, dni, telephone, cellphone, profile, profileDesc);
        userTbl.setRoot(root);
        userTbl.setShowRoot(false);
        
    }    

    private void getDataFromDB() {
        UsuariosHelper userHelper = new UsuariosHelper();
        
        ArrayList<?> lista = userHelper.getUsers(-1);
        lista.forEach((p) -> {
            Usuario u = (Usuario) p;
            //System.out.println(u.getNombres() + u.getApellidoMaterno() + u.getApellidoPaterno());
            data.add(
                new User(
                        new SimpleStringProperty(u.getNombres()),
                        new SimpleStringProperty(u.getApellidoMaterno()),
                        new SimpleStringProperty(u.getApellidoPaterno()),
                        new SimpleStringProperty(u.getDni()),
                        new SimpleStringProperty(u.getTelefono()),
                        new SimpleStringProperty(u.getCelular()),
                        new SimpleStringProperty(u.getPerfil().getNombre()),
                        new SimpleStringProperty(u.getPerfil().getDescripcion()),
                        true
                ));
        });
        userHelper.close();
    }

    @FXML
    private void handleAction(ActionEvent event) {
        if(event.getSource() == addBtn){
            try {
                CreateEdditUserDialog();
            } catch (IOException ex) {
                Logger.getLogger(PersonalController.class.getName()).log(Level.SEVERE, "handleAction()", ex);
            }
        }else if(event.getSource() == moreBtn){
            selectedIndex = userTbl.getSelectionModel().getSelectedIndex();
            if(selectedIndex<0){
                JFXDialogLayout content =  new JFXDialogLayout();
                content.setHeading(new Text("Atención"));
                content.setBody(new Text("Debe seleccionar un registro de la tabla"));
                ErrorController error = new ErrorController();
                error.loadDialog(content, "Ok", hiddenSp);
            }else{
                initPopup();
                popup.show(moreBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
            }
            
        }
    }
    
    private void initPopup(){
        JFXButton bl = new JFXButton("Editar");
        JFXButton bl1 = new JFXButton("Eliminar");
        
        bl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CreateEdditUserDialog();
                } catch (IOException ex) {
                    Logger.getLogger(PersonalController.class.getName()).log(Level.SEVERE, "initPopup()", ex);
                }
            }
        });
        
        bl1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteUserDialog();
            }
        });
        
        bl.setPadding(new Insets(20));
        bl1.setPadding(new Insets(20));
        
        bl.setPrefHeight(40);
        bl1.setPrefHeight(40);
        
        bl.setPrefWidth(145);
        bl1.setPrefWidth(145);
        
        VBox vBox = new VBox(bl, bl1);
        
        
        popup = new JFXPopup();
        popup.setPopupContent(vBox);
    }
    
    public void CreateEdditUserDialog() throws IOException {
        JFXDialogLayout content =  new JFXDialogLayout();
        content.setHeading(new Text("Editar Usuario"));
        
        Node node;
        node = (Node) FXMLLoader.load(PersonalController.this.getClass().getResource(EditarForm.viewPath));
        
//        Parent root;
//        root = FXMLLoader.load(getClass().getResource(EditarForm.viewPath));
        
        content.setBody(node);
        content.setPrefSize(400,400);
                
        JFXDialog dialog = new JFXDialog(hiddenSp, content, JFXDialog.DialogTransition.CENTER);
        
        JFXButton save = new JFXButton("Guardar");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                //save to bd
                
            }
        });
        JFXButton cancel = new JFXButton("Cancelar");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(save);
        content.setActions(cancel);
        dialog.show();
    }  
    
    private void deleteUserDialog() {
        JFXDialogLayout content =  new JFXDialogLayout();
        content.setHeading(new Text("Error"));
        content.setBody(new Text("Cuenta o contraseña incorrectas"));
                
        JFXDialog dialog = new JFXDialog(hiddenSp, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
    
    /*Class for table*/
    class User extends RecursiveTreeObject<User> {

        StringProperty nombres;
        StringProperty apellidoMaterno;
        StringProperty apellidoPaterno;
        StringProperty dni;
        StringProperty telefono;
        StringProperty celular;
        StringProperty profileName;
        StringProperty profileDesc;
        boolean activo;

        public User(StringProperty nombres, StringProperty apellidoMaterno, StringProperty apellidoPaterno, StringProperty dni, StringProperty telefono, StringProperty celular, StringProperty profileName, StringProperty profileDesc, boolean activo) {
            this.nombres = nombres;
            this.apellidoMaterno = apellidoMaterno;
            this.apellidoPaterno = apellidoPaterno;
            this.dni = dni;
            this.telefono = telefono;
            this.celular = celular;
            this.profileName = profileName;
            this.profileDesc = profileDesc;
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
