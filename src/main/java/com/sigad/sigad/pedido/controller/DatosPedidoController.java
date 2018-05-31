/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.sigad.sigad.business.Pedido;
import com.sigad.sigad.business.helpers.PedidoHelper;
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
    private JFXComboBox<String> cmbFin;
    private final ObservableList<String> horaFin = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox<String> cmbDireccion;
    private final ObservableList<String> direcciones = FXCollections.observableArrayList();

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
            direcciones.add(t.getDireccionCliente());
        });
        cmbTarjeta.getItems().addAll("Visa", "Mastercard");
        cmbInicio.getItems().addAll("06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00");
        cmbFin.getItems().addAll("06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00");

    }

    public void initModel(Pedido pedido, StackPane stackPane) {
        this.pedido = pedido;
        this.stackPane = stackPane;
        llenarComboBox();

    }

    @FXML
    void registrarPedido(MouseEvent event) {
        try {
            pedido.setDireccionDeEnvio(cmbDireccion.getValue());
            pedido.setCooXDireccion(0);
            pedido.setCooYDireccion(0);
            pedido.setMensajeDescripicion(cmbDedicatoria.getText());
            Date date = Date.from(dpFechaEntrega.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Timestamp timeStamp = new Timestamp(date.getTime());
            pedido.setFechaVenta(timeStamp);
            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            long ms1 = sdf.parse(cmbInicio.getValue()).getTime();
            Time ti = new Time(ms1);
            pedido.setHoraIniEntrega(ti);
            
            long ms2 = sdf.parse(cmbFin.getValue()).getTime();
            Time tf = new Time(ms2);
            pedido.setHoraIniEntrega(tf);
            
            PedidoHelper helper = new PedidoHelper();
            helper.savePedido(pedido);
            helper.close();
            
        } catch (ParseException ex) {
            Logger.getLogger(DatosPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
