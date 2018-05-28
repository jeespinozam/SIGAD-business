/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.repartos.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Vehiculo;
import com.sigad.sigad.utils.ui.UIFuncs.Dialogs;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.StackPane;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author cfoch
 */
public class VehiculoTipoListaController implements Initializable {
    public static final String VIEW_PATH =
            "/com/sigad/sigad/repartos/view/VehiculoTipoLista.fxml";

    @FXML
    private JFXTextField filtroTxt;
    @FXML
    private JFXTreeTableView treeView;
    @FXML
    private StackPane stackPane;

    ObservableList<VehiculoTipoInfo> infos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn<VehiculoTipoInfo, Number> colId;
        JFXTreeTableColumn<VehiculoTipoInfo, String> colNombre;
        JFXTreeTableColumn<VehiculoTipoInfo, Number> colCapacidad;
        JFXTreeTableColumn<VehiculoTipoInfo, String> colMarca;
        JFXTreeTableColumn<VehiculoTipoInfo, String> colModelo;

        colId = new JFXTreeTableColumn<>("Código");
        colId.setPrefWidth(150);
        colId.setCellValueFactory(
                (CellDataFeatures<VehiculoTipoInfo, Number> param) -> 
                        param.getValue().getValue().id);
        colNombre = new JFXTreeTableColumn<>("Nombre");
        colNombre.setPrefWidth(150);
        colNombre.setCellValueFactory(
                (CellDataFeatures<VehiculoTipoInfo, String> param) -> 
                        param.getValue().getValue().nombre);
        colCapacidad = new JFXTreeTableColumn<>("Capacidad");
        colCapacidad.setPrefWidth(150);
        colCapacidad.setCellValueFactory(
                (CellDataFeatures<VehiculoTipoInfo, Number> param) ->
                        param.getValue().getValue().capacidad);
        colMarca = new JFXTreeTableColumn<>("Marca");
        colMarca.setPrefWidth(150);
        colMarca.setCellValueFactory(
                (CellDataFeatures<VehiculoTipoInfo, String> param) ->
                        param.getValue().getValue().marca);
        colModelo = new JFXTreeTableColumn<>("Modelo");
        colModelo.setPrefWidth(150);
        colModelo.setCellValueFactory(
                (CellDataFeatures<VehiculoTipoInfo, String> param) ->
                        param.getValue().getValue().modelo);

        infos = FXCollections.observableArrayList();
        final TreeItem<VehiculoTipoInfo> root =
                new RecursiveTreeItem<VehiculoTipoInfo>(infos,
                        RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(colId, colNombre, colCapacidad, colMarca,
                colModelo);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        populate();
    }

    private void populate() {
        List<Vehiculo.Tipo> tipos = new ArrayList<>();

        try {
            tipos = getData();
        } catch (Exception ex) {
            Dialogs.showMsg(stackPane, Dialogs.HEADINGS.ERROR,
                    Dialogs.MESSAGES.DB_GENERIC_ERROR,
                    Dialogs.BUTTON.ACEPTAR);
        }
        tipos.forEach((tipo) -> {
            VehiculoTipoInfo info;
            info = new VehiculoTipoInfo(tipo);
            infos.add(info);
        });
    }

    private List<Vehiculo.Tipo> getData() throws Exception {
        Session session;
        Query query;
        ArrayList<Vehiculo.Tipo> vehiculoTipos;
        session = LoginController.serviceInit();
        try {
            query = session.createQuery("from Vehiculo$Tipo");
            vehiculoTipos = (ArrayList<Vehiculo.Tipo>) query.list();
        } catch (Exception ex) {
            if (session != null) {
                session.close();
            }
            throw ex;
        }
        return vehiculoTipos;
    }

    class VehiculoTipoInfo extends RecursiveTreeObject<VehiculoTipoInfo>{
        public IntegerProperty id;
        public StringProperty nombre;
        public DoubleProperty capacidad;
        public StringProperty marca;
        public StringProperty modelo;

        public VehiculoTipoInfo(final Vehiculo.Tipo vehiculoTipo) {
            id = new SimpleIntegerProperty(vehiculoTipo.getId().intValue());
            nombre = new SimpleStringProperty(vehiculoTipo.getNombre());
            capacidad = new SimpleDoubleProperty(vehiculoTipo.getCapacidad());
            marca = new SimpleStringProperty(vehiculoTipo.getMarca());
            modelo = new SimpleStringProperty(vehiculoTipo.getModelo());
        }
    }
}
