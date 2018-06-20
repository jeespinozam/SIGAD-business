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
import com.sigad.sigad.app.repartos.controller.VehiculoController.Modo;
import com.sigad.sigad.business.Vehiculo;
import com.sigad.sigad.utils.ui.UIFuncs.Dialogs;
import com.sigad.sigad.utils.ui.UIFuncs.Dialogs.SimplePopupMenuFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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
public class VehiculoListaController implements Initializable {
    public static final String VIEW_PATH =
            "/com/sigad/sigad/repartos/view/VehiculoLista.fxml";

    @FXML
    private JFXTextField filtroTxt;
    @FXML
    private JFXTreeTableView treeView;
    @FXML
    private StackPane stackPane;

    ObservableList<VehiculoInfo> infos;
    private VehiculoController mainController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn<VehiculoInfo, String> colPlaca;
        JFXTreeTableColumn<VehiculoInfo, String> colTipo;
        JFXTreeTableColumn<VehiculoInfo, String> colNombre;

        colPlaca = new JFXTreeTableColumn<>("Nombre");
        colPlaca.setPrefWidth(150);
        colPlaca.setCellValueFactory(
                (CellDataFeatures<VehiculoInfo, String> param) ->
                        param.getValue().getValue().placa);
        colTipo = new JFXTreeTableColumn<>("Tipo");
        colTipo.setPrefWidth(150);
        colTipo.setCellValueFactory(
                (CellDataFeatures<VehiculoInfo, String> param) ->
                        param.getValue().getValue().tipo);
        colNombre = new JFXTreeTableColumn<>("Nombre");
        colNombre.setPrefWidth(150);
        colNombre.setCellValueFactory(
                (CellDataFeatures<VehiculoInfo, String> param) ->
                        param.getValue().getValue().tipo);

        infos = FXCollections.observableArrayList();
        final TreeItem<VehiculoInfo> root =
                new RecursiveTreeItem<VehiculoInfo>(infos,
                        RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(colPlaca, colTipo, colNombre);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        //populate();
        //handleEvents();
    }

    private void handleEvents() {
//        ReadOnlyObjectProperty prop =
//                treeView.getSelectionModel().selectedItemProperty();
//
//        prop.addListener((obs, oldSelection, newSelection) -> {
//            SimplePopupMenuFactory<Modo> menuFactory;
//            if (mainController == null) {
//                return;
//            }
//            menuFactory = mainController.getMenuFactory();
//            if (newSelection == null) {
//                menuFactory.getButton(Modo.BORRAR).setDisable(true);
//                menuFactory.getButton(Modo.EDITAR).setDisable(true);
//            } else {
//                menuFactory.getButton(Modo.BORRAR).setDisable(false);
//                menuFactory.getButton(Modo.EDITAR).setDisable(false);
//            }
//            if (newSelection != null && mainController != null) {
//                menuFactory.getButton(Modo.BORRAR).setOnAction((evt) -> {
//                    int i = treeView.getSelectionModel().getFocusedIndex();
//                    try {
//                        deleteData(infos.get(i));
//                        infos.remove(i);
//                        Dialogs.showMsg(stackPane, Dialogs.HEADINGS.EXITO,
//                                Dialogs.MESSAGES.CRUD_DELETE_SUCCESS,
//                                Dialogs.BUTTON.ACEPTAR);
//                    } catch (Exception ex) {
//                        Logger.getLogger(VehiculoListaController.class.getName())
//                                .log(Level.SEVERE, null, ex);
//                    }
//                });
//            }
//        });
    }

    private void populate() {
        List<Vehiculo> objs = new ArrayList<>();

        try {
            objs = getData();
        } catch (Exception ex) {
            Dialogs.showMsg(stackPane, Dialogs.HEADINGS.ERROR,
                    Dialogs.MESSAGES.DB_GENERIC_ERROR,
                    Dialogs.BUTTON.ACEPTAR);
        }
        objs.forEach((obj) -> {
            VehiculoInfo info;
            info = new VehiculoInfo(obj);
            infos.add(info);
        });
    }

    private void deleteData(VehiculoInfo info) throws Exception {
        Session session;
        Query query;
        session = LoginController.serviceInit();
        try {
            Long id = new Long(info.id.intValue());
            String hql = "delete from Vehiculo where id = :id";
            session.beginTransaction();
            session.createQuery(hql).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Dialogs.showMsg(stackPane, Dialogs.HEADINGS.ERROR,
                    Dialogs.MESSAGES.CRUD_DELETE_ERROR, Dialogs.BUTTON.ACEPTAR);
            throw ex;
        } finally {
            session.close();
        }
    }

    public Long getSelectedId() {
        int i;
        VehiculoInfo info;
        i = treeView.getSelectionModel().getSelectedIndex();
        info = infos.get(i);
        if (info == null) {
            return null;
        }
        return new Long(info.id.getValue());
    }

    private List<Vehiculo> getData() throws Exception {
        Session session;
        Query query;
        ArrayList<Vehiculo> objs;
        session = LoginController.serviceInit();
        try {
            query = session.createQuery("from Vehiculo$Tipo");
            objs = (ArrayList<Vehiculo>) query.list();
        } catch (Exception ex) {
            if (session != null) {
                session.close();
            }
            throw ex;
        }
        return objs;
    }

    class VehiculoInfo extends RecursiveTreeObject<VehiculoInfo>{
        public IntegerProperty id;
        public StringProperty tipo;
        public StringProperty placa;
        public StringProperty nombre;

        public VehiculoInfo(Vehiculo obj) {
            Vehiculo.Tipo vtipo;
            vtipo = obj.getTipo();
            id = new SimpleIntegerProperty(obj.getId().intValue());
            tipo = new SimpleStringProperty(vtipo.getNombre());
            placa = new SimpleStringProperty(obj.getPlaca());
            nombre = new SimpleStringProperty(obj.getNombre());
        }
    }

    /**
     * @return the mainController
     */
    public VehiculoController getMainController() {
        return mainController;
    }

    /**
     * @param mainController the mainController to set
     */
    public void setMainController(VehiculoController mainController) {
        this.mainController = mainController;
    }
}
