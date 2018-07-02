/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.business.Constantes;
import com.sigad.sigad.business.NotaCredito;
import com.sigad.sigad.business.Pedido;
import com.sigad.sigad.business.PedidoEstado;
import com.sigad.sigad.business.helpers.GeneralHelper;
import com.sigad.sigad.business.helpers.NotaCreditoHelper;
import com.sigad.sigad.business.helpers.PedidoEstadoHelper;
import com.sigad.sigad.business.helpers.PedidoHelper;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class PagoPedidoController implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/pedido/view/PagoPedido.fxml";

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField txtnotaCredito;

    @FXML
    private JFXTextField txtDepositoBanco;

    @FXML
    private JFXTextField txtmontonota;

    @FXML
    private JFXTextField txtmontoBanco;

    @FXML
    private JFXButton btnVerificar;

    @FXML
    private JFXTextField txtMontoTotal;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXTextField txtMontoPagado;

    Pedido pedido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupValidations();
    }

    public void initModel(Pedido pedido) {
        this.pedido = pedido;
        txtMontoTotal.setText(pedido.getTotal().toString());
    }

    @FXML
    void verificarTransaccion(MouseEvent event) {

        if (validateFields()) {
            if (txtnotaCredito.getText().length() != 0) {
                NotaCreditoHelper helpernota = new NotaCreditoHelper();
                NotaCredito nota = helpernota.getNota(txtnotaCredito.getText());
                if (nota == null) {
                    ErrorController err = new ErrorController();
                    err.loadDialog("Alerta", "La nota de credito no existe", "Ok", stackPane);
                    return;
                } else if (nota.getPedidoPago() != null) {
                    ErrorController err = new ErrorController();
                    err.loadDialog("Alerta", "La nota de credito ya fue usada", "Ok", stackPane);
                    return;
                } else {
                    nota.setPedidoPago(pedido);
                    txtmontonota.setText(GeneralHelper.roundTwoDecimals(nota.getMonto()).toString());
                    helpernota.saveNotaCredito(nota);
                }
            }

            PedidoEstadoHelper estadohelper = new PedidoEstadoHelper();
            PedidoEstado pedidoestado = estadohelper.getEstadoByName(Constantes.ESTADO_VENTA);
            estadohelper.close();
            PedidoHelper pedidohelper = new PedidoHelper();
            Pedido ped = pedidohelper.getPedido(pedido.getId());
            Double montoBanco = Double.valueOf(txtMontoTotal.getText()) - Double.valueOf(txtmontonota.getText());
            if (ped.getCodigoBanco() == null && montoBanco >= 0.0 && txtDepositoBanco.getText().length() != 0) {
                txtmontoBanco.setText(montoBanco.toString());
                ped.setCodigoBanco(txtDepositoBanco.getText());
                ped.addEstado(pedidoestado);
                ped.setEstado(pedidoestado);
                pedidohelper.savePedido(ped);
            } else if (ped.getCodigoBanco() != null) {
                ErrorController err = new ErrorController();
                err.loadDialog("Alerta", "Ya fue ingresado el pago", "Ok", stackPane);
                txtDepositoBanco.setText(ped.getCodigoBanco());
                return;
            } else if (txtDepositoBanco.getText().length() == 0 && montoBanco >= 0.0) {
                ErrorController err = new ErrorController();
                err.loadDialog("Alerta", "Falta el codigo del banco", "Ok", stackPane);
                txtDepositoBanco.setText(ped.getCodigoBanco());
                return;
            } else {
                ped.addEstado(pedidoestado);
                ped.setEstado(pedidoestado);
                pedidohelper.savePedido(ped);
            }
            txtMontoPagado.setText(txtMontoTotal.getText());
            MantenimientoPedidosController.reloadTable();
            ErrorController err = new ErrorController();
            err.loadDialog("Alerta", "El pago fue exitoso", "Ok", stackPane);
            btnVerificar.setDisable(true);

        }

    }

    @FXML
    public void close(MouseEvent event) {

        MantenimientoPedidosController.payDialog.close();

    }

    public void setupValidations() {
        RequiredFieldValidator r;

        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        txtDepositoBanco.getValidators().add(r);
        txtDepositoBanco.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {

                if (txtDepositoBanco.getText().length() == 0 || txtDepositoBanco.getText().length() == 8) {
                    txtDepositoBanco.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                } else {
                    txtDepositoBanco.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                }
            }
        });
        txtnotaCredito.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtnotaCredito.getText().length() == 0 || txtnotaCredito.getText().length() == 11) {
                    txtnotaCredito.setFocusColor(new Color(0.30, 0.47, 0.23, 1));
                } else {
                    txtnotaCredito.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }

            }
        });
    }

    public boolean validateFields() {
        if (txtDepositoBanco.getText().length() > 0 && txtDepositoBanco.getText().length() != 8) {
            ErrorController r = new ErrorController();
            r.loadDialog("Error", "El numero de deposito debe tener 8 caracteres", "Ok", stackPane);
            txtDepositoBanco.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            txtDepositoBanco.requestFocus();
            return false;
        } else if (txtnotaCredito.getText().length() > 0 && txtnotaCredito.getText().length() != 11) {
            ErrorController r = new ErrorController();
            r.loadDialog("Error", "La nota de credito debe tener 8 caracteres", "Ok", stackPane);
            txtDepositoBanco.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            txtDepositoBanco.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
