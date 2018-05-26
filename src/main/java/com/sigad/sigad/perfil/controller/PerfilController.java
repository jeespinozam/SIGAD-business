/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.perfil.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.helpers.PerfilHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class PerfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/perfil/view/perfil.fxml";
    
    static ObservableList<PerfilController.Profile> dataPerfilTbl = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableView profilesTbl;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton moreBtn;
    @FXML
    private JFXPopup popup;
    @FXML
    private StackPane hiddenSp;
    @FXML
    public static JFXDialog profileDialog;
    
    public static boolean isProfileCreate;
    public static Profile selectedProfile = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initProfileTable();
        }
    public static void updateTable(Perfil p) {
        dataPerfilTbl.add(new Profile(
            new SimpleStringProperty(p.getNombre()), 
            new SimpleStringProperty(p.getDescripcion()), 
            new SimpleBooleanProperty(p.isActivo()))
        );
    }
    
    @FXML
    private void handleAction(ActionEvent event) {
        if(event.getSource() == addBtn){
            
        }else if(event.getSource() == moreBtn){
            int count = profilesTbl.getSelectionModel().getSelectedCells().size();
            if( count > 1){
                ErrorController error = new ErrorController();
                error.loadDialog("Atención", "Debe seleccionar solo un registro de la tabla", "Ok", hiddenSp);
            }else if(count<=0){
                ErrorController error = new ErrorController();
                error.loadDialog("Atención", "Debe seleccionar al menos un registro de la tabla", "Ok", hiddenSp);
            }else{
                int selected  = profilesTbl.getSelectionModel().getSelectedIndex();
                selectedProfile = (Profile) profilesTbl.getSelectionModel().getModelItem(selected).getValue();
                initPopup();
                popup.show(moreBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
            }
        }
    }
    
    private void initProfileTable() {
        JFXTreeTableColumn<Profile, String> nombre = new JFXTreeTableColumn<>("Nombre");
        nombre.setPrefWidth(120);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, String> param) -> param.getValue().getValue().profileName);
        
        JFXTreeTableColumn<Profile, String> description = new JFXTreeTableColumn<>("Descripción");
        description.setPrefWidth(120);
        description.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profile, String> param) -> param.getValue().getValue().profileName);
        
        PerfilHelper usuarioHelper = new PerfilHelper();
        ArrayList<Perfil> list = usuarioHelper.getProfiles();
        if(list != null){
            list.forEach(p -> {
                System.out.println(p.getNombre());
                dataPerfilTbl.add(new Profile(
                        new SimpleStringProperty(p.getNombre()), 
                        new SimpleStringProperty(p.getDescripcion()), 
                        new SimpleBooleanProperty(p.isActivo())));
            });
        }
        
        
        final TreeItem<Profile> root = new RecursiveTreeItem<>(dataPerfilTbl, RecursiveTreeObject::getChildren);
        
        profilesTbl.setEditable(true);
        profilesTbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        profilesTbl.getColumns().setAll(select, nombre, description);
        profilesTbl.setRoot(root);
        profilesTbl.setShowRoot(false);        
    }
    
    private void initPopup(){
        JFXButton edit = new JFXButton("Editar");
        JFXButton delete = new JFXButton("Eliminar");
        
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
                try {
                    CreateEdditProfileDialog(false);
                } catch (IOException ex) {
                    Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, "initPopup(): CreateEdditProfileDialog()", ex);
                }
            }
        });
        
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
                deleteProfileDialog();
            }
        });
        
        edit.setPadding(new Insets(20));
        delete.setPadding(new Insets(20));
        
        edit.setPrefHeight(40);
        delete.setPrefHeight(40);
        
        edit.setPrefWidth(145);
        delete.setPrefWidth(145);
        
        VBox vBox = new VBox(edit, delete);
        
        
        popup = new JFXPopup();
        popup.setPopupContent(vBox);
    }
    
    public void CreateEdditProfileDialog(boolean iscreate) throws IOException {
        isProfileCreate = iscreate;
        
        JFXDialogLayout content =  new JFXDialogLayout();
        
        if(isProfileCreate){
            content.setHeading(new Text("Crear Perfil"));
        }else{
            content.setHeading(new Text("Editar Perfil"));
            
            PerfilHelper helper = new PerfilHelper();
            Perfil p = helper.getProfile(selectedProfile.profileName.getValue());
            if(p == null){
                ErrorController error = new ErrorController();
                error.loadDialog("Error", "No se pudo obtener el perfil", "Ok", hiddenSp);
                System.out.println("Error al obtener el perfil" + selectedProfile.profileName.getValue());
                return;
            }
        }
        
        Node node = (Node) FXMLLoader.load(PerfilController.this.getClass().getResource(CrearEditarPerfilController.viewPath));
        content.setBody(node);
        content.setPrefSize(400,400);
                
        profileDialog = new JFXDialog(hiddenSp, content, JFXDialog.DialogTransition.CENTER);
        profileDialog.show();
    }  
    
    private void deleteProfileDialog() {
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
    
    public static class Profile extends RecursiveTreeObject<Profile> {

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
