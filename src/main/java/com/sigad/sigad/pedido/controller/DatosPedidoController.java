/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.sigad.sigad.business.Pedido;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class DatosPedidoController implements Initializable {

    Pedido pedido;
    StackPane stackPane;
    
    
    
    @FXML
    private JFXRadioButton btnBoleta;

    @FXML
    private ToggleGroup genero;

    @FXML
    private JFXRadioButton btnFactura;

    @FXML
    private JFXRadioButton btnEfectivo;

    @FXML
    private ToggleGroup pago;

    @FXML
    private JFXTextField txtMonto;

    @FXML
    private JFXRadioButton btnPOS;

    @FXML
    private JFXComboBox<?> cmbTarjeta;

    @FXML
    private JFXTextArea cmbDedicatoria;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnRegistrar;

    @FXML
    private JFXDatePicker dpFechaEntrega;

    @FXML
    private JFXComboBox<?> cmbInicio;

    @FXML
    private JFXComboBox<?> cmbFin;

    @FXML
    private JFXComboBox<?> cmbDireccion;
    
    
    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/pedido/view/datosPedido.fxml";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initModel(Pedido pedido, StackPane stackPane) {
        this.pedido = pedido;
        this.stackPane = stackPane;

    }

}
