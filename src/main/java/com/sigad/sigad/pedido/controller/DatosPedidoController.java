/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.ClienteDescuento;
import com.sigad.sigad.business.ClienteDireccion;
import com.sigad.sigad.business.ComboPromocion;
import com.sigad.sigad.business.Constantes;
import com.sigad.sigad.business.DetallePedido;
import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.LoteInsumo;
import com.sigad.sigad.business.Pedido;
import com.sigad.sigad.business.PedidoEstado;
import com.sigad.sigad.business.Producto;
import com.sigad.sigad.business.ProductoInsumo;
import com.sigad.sigad.business.ProductosCombos;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.business.helpers.ClienteDescuentoHelper;
import com.sigad.sigad.business.helpers.LoteInsumoHelper;
import com.sigad.sigad.business.helpers.PedidoHelper;
import com.sigad.sigad.deposito.helper.PedidoEstadoHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
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
    private JFXTextArea cmbDedicatoria;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnRegistrar;

    @FXML
    private JFXDatePicker dpFechaEntrega;

    @FXML
    private JFXComboBox<String> cmbInicio;

    @FXML
    private JFXComboBox<String> cmbDireccion;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    private JFXTextField txtDescuento;

    @FXML
    private JFXTextField txtTotalPago;

    @FXML
    private JFXTextField txtCliente;

    @FXML
    private JFXTextField txtEmpresa;

    @FXML
    private JFXTextField txtdoc;

    @FXML
    private JFXTextField txtcorreo;

    @FXML
    private JFXButton btnGenerar;

    @FXML
    private JFXTextField txtTipo;

    @FXML
    private JFXTextField txtDireccion;

    private final ObservableList<String> direcciones = FXCollections.observableArrayList();
    private HashMap<Insumo, Integer> insumos = new HashMap<>();
    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/pedido/view/datosPedido.fxml";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void llenarComboBox() {
        cmbDireccion.setItems(direcciones);
        direcciones.add(pedido.getDireccionDeEnvio());
    }

    public void initModel(Pedido pedido, StackPane stackPane) {
        this.pedido = pedido;
        this.stackPane = stackPane;
        txtTotal.setText(pedido.getTotal().toString());
        txtTotal.setDisable(true);
        llenarComboBox();
        obtenerDescuentos();

    }

    public void obtenerDescuentos() {
        try {
            ClienteDescuentoHelper helper = new ClienteDescuentoHelper();
            ArrayList<ClienteDescuento> descuentos = helper.getDescuentosVigentes();
            helper.close();
            Integer numPedidos = pedido.getCliente().getPedidoCliente().size();
            for (Iterator<ClienteDescuento> iterator = descuentos.iterator(); iterator.hasNext();) {
                ClienteDescuento t = iterator.next();
                if (t.getTipo() == Constantes.TIPO_DCTO_USUARIO_X_MONTO) {
                    if (pedido.getTotal() < t.getCondicion()) {
                        iterator.remove();
                    }
                } else if (t.getTipo() == Constantes.TIPO_DCTO_USUARIO_X_NPEDIDOS) {
                    if (numPedidos < t.getCondicion()) {
                        iterator.remove();
                    }
                }
            }

            ClienteDescuento descuentoCliente = descuentos.stream().max(Comparator.comparing(ClienteDescuento::getValue)).orElseThrow(NoSuchElementException::new);
            if (descuentoCliente != null) {
                pedido.setTotal(pedido.getTotal() * (1 - descuentoCliente.getValue()));
                pedido.setDescuentoCliente(descuentoCliente);
                txtTipo.setText("Por superar " + descuentoCliente.getCondicion().toString() + " en " + descuentoCliente.getTipo());
                txtDescuento.setText(String.valueOf(descuentoCliente.getValue() * 100) + " %");
                txtTotalPago.setText(pedido.getTotal().toString());
            } else {
                txtDescuento.setText("No aplica");
                txtTotalPago.setText(pedido.getTotal().toString());

            }

        } catch (Exception e) {

        }
    }

    public void isValid() {

    }

    public void construirPedido() {
        pedido.setMensajeDescripicion(cmbDedicatoria.getText());
        Date date = Date.from(dpFechaEntrega.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Timestamp timeStamp = new Timestamp(date.getTime());
        pedido.setFechaVenta(timeStamp);
        pedido.setVendedor(LoginController.user);
        PedidoEstadoHelper hp = new PedidoEstadoHelper();
        pedido.setTurno(cmbInicio.getValue());
        PedidoEstado estado = hp.getEstadoByName("pendiente");
        pedido.addEstado(estado);
        pedido.setEstado(estado);
        hp.close();

    }

    void calcularInsumos(Producto p, Integer cantidad) {
        ArrayList<ProductoInsumo> pxi = new ArrayList(p.getProductoxInsumos());
        for (ProductoInsumo productoInsumo : pxi) {
            if (insumos.get(productoInsumo.getInsumo()) != null) {
                insumos.put(productoInsumo.getInsumo(), productoInsumo.getCantidad().intValue() * cantidad + insumos.get(productoInsumo.getInsumo()));
            } else {
                insumos.put(productoInsumo.getInsumo(), productoInsumo.getCantidad().intValue() * cantidad);
            }
        }
    }

    @FXML
    void registrarPedido(MouseEvent event) {
        try {
            construirPedido();

            for (DetallePedido dp : pedido.getDetallePedido()) {
                if (dp.getProducto() != null) {
                    calcularInsumos(dp.getProducto(), dp.getCantidad() );
                } else if (dp.getCombo() != null) {
                    ComboPromocion p = dp.getCombo();
                    for (ProductosCombos productosCombos : p.getProductosxComboArray()) {
                        calcularInsumos(productosCombos.getProducto(), productosCombos.getCantidad() * dp.getCantidad());
                    }
                }
            }

            LoteInsumoHelper lihelper = new LoteInsumoHelper();
            Boolean ok = lihelper.descontarInsumos(insumos, pedido.getTienda());
            if (ok) {
                PedidoHelper helper = new PedidoHelper();
                helper.savePedido(pedido);
                helper.close();
            } else {
                ErrorController err = new ErrorController();
                err.loadDialog("Alerta", "No hay insumos", "ok", stackPane);
            }
//            gotoInicio();

        } catch (Exception ex) {
        }
    }

    void gotoInicio() {
        try {
            Node node;
            FXMLLoader loader = new FXMLLoader(DatosPedidoController.this.getClass().getResource(MantenimientoPedidosController.viewPath));
            node = (Node) loader.load();
            MantenimientoPedidosController desc = loader.getController();
            stackPane.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(DatosPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
