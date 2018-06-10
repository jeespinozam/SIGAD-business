/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.descuentos.controller;

import com.jfoenix.controls.*;
import com.sigad.sigad.business.ProductoCategoria;
import com.sigad.sigad.business.ProductoCategoriaDescuento;
import com.sigad.sigad.business.ProductoCategoriaDescuentoHelper;
import com.sigad.sigad.business.helpers.ProductoCategoriaHelper;
import com.sigad.sigad.business.helpers.ProductoDescuentoHelper;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class RegistrarDescuentoCategoriaProductoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/descuentos/view/registrarDescuentoCategoriaProducto.fxml";

    @FXML
    private JFXDatePicker txtFechaInicio;

    @FXML
    private JFXDatePicker txtFechaFin;

    @FXML
    private JFXTextField txtValuePct;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXComboBox<ProductoCategoria> cmbCategorias;

    Boolean isEdit;

    ProductoCategoriaDescuento pc;
    ObservableList<ProductoCategoria> categoriasproductos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarCategorias() {
        ProductoCategoriaHelper helper = new ProductoCategoriaHelper();
        ArrayList<ProductoCategoria> categorias = helper.getActiveProductCategories();
        categoriasproductos = FXCollections.observableArrayList(categorias);
        cmbCategorias.setItems(categoriasproductos);
        cmbCategorias.setPromptText("Categorias");
//        cmbCategorias.setOnAction((event) -> {
//            pc.setCategoria(cmbCategorias.getSelectionModel().getSelectedItem());
//        });

    }

    public void initModel(Boolean isedit, ProductoCategoriaDescuento pc, StackPane st) {
        this.isEdit = isedit;
        this.pc = pc;
        cargarCategorias();
        if (isedit) {
//            cargarDatos();
        } else {

            this.pc = new ProductoCategoriaDescuento();
        }

    }

    @FXML
    void close(MouseEvent event) {

    }

    public void construirDescuento() {
        pc.setCategoria(cmbCategorias.getValue());
        Date dateIni = Date.valueOf(txtFechaInicio.getValue());
        Date dateFin = Date.valueOf(txtFechaFin.getValue());
        pc.setFechaInicio(dateIni);
        pc.setFechaFin(dateFin);
        pc.setValue(Double.valueOf(txtValuePct.getText()) /100);

    }

    @FXML
    void guardarDescuento(ActionEvent event) {
        ProductoCategoriaDescuentoHelper helper = new ProductoCategoriaDescuentoHelper();
        if (/*validarCampos() && cant == 1 &&*/!isEdit) {
            construirDescuento();

            helper.saveDescuento(pc);
        } else if (/*validarCampos() && cant == 1 &&*/isEdit) {
            construirDescuento();
            helper.updateDescuento(pc);
        }
        //reloadTable();
        MantenimientoDescuentosController.descDialog.close();
    }

}
