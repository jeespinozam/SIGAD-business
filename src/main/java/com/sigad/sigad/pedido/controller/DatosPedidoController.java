/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.ClienteDireccion;
import com.sigad.sigad.business.Pedido;
import com.sigad.sigad.business.PedidoEstado;
import com.sigad.sigad.business.helpers.PedidoHelper;
import com.sigad.sigad.deposito.helper.PedidoEstadoHelper;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
    private JFXTextArea cmbDedicatoria;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnRegistrar;

    @FXML
    private JFXDatePicker dpFechaEntrega;

    @FXML
    private JFXComboBox<String> cmbInicio;
    private final ObservableList<String> horasInicio = FXCollections.observableArrayList();
  
    @FXML
    private JFXComboBox<ClienteDireccion> cmbDireccion;
    private final ObservableList<ClienteDireccion> direcciones = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<String> cmbTarjeta;
    private final ObservableList<String> tarjetas = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/pedido/view/datosPedido.fxml";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void llenarComboBox() {
        cmbDireccion.setItems(direcciones);
        pedido.getCliente().getClienteDirecciones().forEach((t) -> {
            direcciones.add(t);
        });
        cmbTarjeta.getItems().addAll("Visa", "Mastercard");
        cmbInicio.getItems().addAll("M", "T", "N");

    }

    public void initModel(Pedido pedido, StackPane stackPane) {
        this.pedido = pedido;
        this.stackPane = stackPane;
        txtMonto.setText(pedido.getTotal().toString());
        txtMonto.setDisable(true);
        llenarComboBox();

    }
    public void isValid(){
        
    
    }

    @FXML
    void registrarPedido(MouseEvent event) {
        try {
            pedido.setDireccionDeEnvio(cmbDireccion.getValue().getDireccionCliente());
            pedido.setCooXDireccion(cmbDireccion.getValue().getCooXDireccion());
            pedido.setCooYDireccion(cmbDireccion.getValue().getCooXDireccion());
            pedido.setMensajeDescripicion(cmbDedicatoria.getText());
            Date date = Date.from(dpFechaEntrega.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Timestamp timeStamp = new Timestamp(date.getTime());
            pedido.setFechaVenta(timeStamp);
            pedido.setVendedor(LoginController.user);
            PedidoEstadoHelper hp = new PedidoEstadoHelper();
            PedidoEstado estado = hp.getEstadoByName("venta");
            pedido.addEstado(estado);
            pedido.setEstado(estado);
            hp.close();
            PedidoHelper helper = new PedidoHelper();
            helper.savePedido(pedido);
            helper.close();
            
        } catch (Exception ex) {
        }
    }

}
