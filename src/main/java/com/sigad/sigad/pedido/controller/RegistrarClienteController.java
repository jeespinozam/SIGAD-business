/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.*;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sigad.sigad.business.ClienteDireccion;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.business.helpers.PerfilHelper;
import com.sigad.sigad.business.helpers.UsuarioHelper;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class RegistrarClienteController implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/pedido/view/registrarCliente.fxml";

    @FXML
    private JFXTreeTableView<DireccionesLista> tablaDirecciones;
    private final ObservableList<DireccionesLista> direcciones = FXCollections.observableArrayList();
    @FXML
    JFXTreeTableColumn<DireccionesLista, String> direccion = new JFXTreeTableColumn<>("Direccion");
    @FXML
    JFXTreeTableColumn<DireccionesLista, String> nombre = new JFXTreeTableColumn<>("aNombre");

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApp;

    @FXML
    private JFXTextField txtApm;

    @FXML
    private JFXTextField txtdni;

    @FXML
    private JFXTextField txttel;

    @FXML
    private JFXTextField txtcel;

    @FXML
    private JFXTextField txtcorreo;
    @FXML
    private JFXTextField txtnuevaDir;

    @FXML
    private JFXTextField txtnuevoNom;

    @FXML
    private JFXButton btnRegistrar;

    @FXML
    private JFXButton btnAgregarDireccion;
    @FXML
    private Usuario cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        agregarColumnasDireccion();
        agregarColumnasTablasClientes();
        initValidator();
    }

    private void initValidator() {
        RequiredFieldValidator r;
        NumberValidator n;

        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtNombre.getValidators().add(r);
        txtNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txtNombre.validate()) {
                    txtNombre.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtNombre.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });

        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtApp.getValidators().add(r);
        txtApp.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txtApp.validate()) {
                    txtApp.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtApp.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });

        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtApm.getValidators().add(r);
        txtApm.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txtApm.validate()) {
                    txtApm.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtApm.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });

        /*DNI*/
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtdni.getValidators().add(r);

        n = new NumberValidator();
        n.setMessage("Campo numérico");
        n.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        txtdni.getValidators().add(n);

        txtdni.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txtdni.validate()) {
                    txtdni.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtdni.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });

        /**/
 /*TELEPHONE*/
        n = new NumberValidator();
        n.setMessage("Campo numérico");
        n.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        txttel.getValidators().add(n);

        txttel.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txttel.validate()) {
                    txttel.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txttel.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });

        /**/
 /*CELLPHONE*/
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtcel.getValidators().add(r);

        n = new NumberValidator();
        n.setMessage("Campo numérico");
        n.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        txtcel.getValidators().add(n);

        txtcel.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txtcel.validate()) {
                    txtcel.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtcel.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });

        /**/
 /*EMAIL*/
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtcorreo.getValidators().add(r);
        txtcorreo.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!txtcorreo.validate()) {
                    txtcorreo.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtcorreo.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });
        /**/

    }

    public void agregarColumnasDireccion() {
        nombre.setPrefWidth(90);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<DireccionesLista, String> param) -> param.getValue().getValue().direccion);
        direccion.setPrefWidth(150);
        direccion.setCellValueFactory((TreeTableColumn.CellDataFeatures<DireccionesLista, String> param) -> param.getValue().getValue().nombre);
    }

    public void crearUsuario() {
        PerfilHelper perfilHeler = new PerfilHelper();
        Perfil perfil = perfilHeler.getProfile("Cliente");
        perfilHeler.close();
        if (perfil != null) {
            cliente = new Usuario(txtNombre.getText(), txtApp.getText(), txtApm.getText(),
                    perfil, txttel.getText(), txtdni.getText(), txtcel.getText(), true, txtcorreo.getText(), txtdni.getText(), "");
            direcciones.forEach((t) -> {
                cliente.getClienteDirecciones().add(new ClienteDireccion(t.direccion.getValue(), t.nombre.getValue(), Boolean.FALSE, cliente));

            });
        }

    }

    public void agregarColumnasTablasClientes() {
        final TreeItem<DireccionesLista> rootPedido = new RecursiveTreeItem<>(direcciones, RecursiveTreeObject::getChildren);
        tablaDirecciones.setEditable(true);
        tablaDirecciones.getColumns().setAll(direccion);
        tablaDirecciones.setRoot(rootPedido);
        tablaDirecciones.setShowRoot(false);
    }

    @FXML
    void guardarUsuario(ActionEvent event) {

        if (SeleccionarClienteController.isClientCreate) {
            crearUsuario();
            UsuarioHelper usuariohelper = new UsuarioHelper();
            usuariohelper.saveUser(cliente);
            usuariohelper.close();
        }

    }

    @FXML
    void agregarDireccion(ActionEvent event) {
        direcciones.add(new DireccionesLista(txtnuevaDir.getText(), txtnuevoNom.getText(), null));
        txtnuevaDir.clear();
        txtnuevoNom.clear();
    }

    @FXML
    void close(ActionEvent event) {
        SeleccionarClienteController.userDialog.close();
    }

    class DireccionesLista extends RecursiveTreeObject<DireccionesLista> {

        StringProperty direccion;
        StringProperty nombre;
        Integer codigo;

        public DireccionesLista(String direccion, String nombre, Integer codigo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.direccion = new SimpleStringProperty(direccion);

            this.codigo = codigo;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof DireccionesLista) {
                DireccionesLista cl = (DireccionesLista) o;
                return cl.codigo.equals(this.codigo);
            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 71 * hash + Objects.hashCode(this.codigo);
            return hash;
        }

    }

}
