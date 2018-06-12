/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.perfil.controller;

import com.google.maps.errors.ApiException;
import com.jfoenix.controls.*;
import com.sigad.sigad.business.Tienda;
import com.sigad.sigad.business.helpers.GMapsHelper;
import com.sigad.sigad.business.helpers.GeneralHelper;
import com.sigad.sigad.business.helpers.TiendaHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.tuple.Pair;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class SolicitarDireccionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/pedido/view/solicitarDireccion.fxml";
    private Tienda tienda;
    private ArrayList<Tienda> tiendas;
    private Double distancia;
    @FXML
    private JFXButton btnBuscarTienda;

    @FXML
    private JFXButton btnContinuar;

    @FXML
    private JFXTextField txtdireccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TiendaHelper helper = new TiendaHelper();
        tiendas =  helper.getStores();
        
    }

    public void initModel(Boolean isEdit, Tienda tienda) {
        if (isEdit) {

        } else {

        }

        this.tienda = tienda;

    }

    @FXML
    void encontrarTiendaCercana(MouseEvent event) {
        try {
            GMapsHelper helper = GMapsHelper.getInstance();
            Pair<Double, Double> pair = helper.geocodeAddress(txtdireccion.getText());
            tiendas.forEach((t) -> {
                distancia = GeneralHelper.distanceBetweenTwoPoints(pair.getLeft(), Double.NaN, Double.NaN, Double.NaN);
            });
            
            
        } catch (ApiException | InterruptedException | IOException ex) {
            Logger.getLogger(SolicitarDireccionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
