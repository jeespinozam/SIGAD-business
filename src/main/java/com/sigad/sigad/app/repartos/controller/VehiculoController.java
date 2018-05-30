/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.repartos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPopup;
import com.sigad.sigad.utils.ui.UIFuncs.Dialogs;
import com.sigad.sigad.utils.ui.UIFuncs.Dialogs.SimplePopupMenuFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author cfoch
 */
public class VehiculoController implements Initializable {

    public static enum Modo {
        EDITAR,
        CREAR,
        BORRAR
    };

    public static final String VIEW_PATH =
            "/com/sigad/sigad/repartos/view/vehiculo.fxml";

    private VehiculoController.Modo modo;

    //@FXML
    private JFXButton borrarBtn;
    //@FXML
    private JFXButton editarBtn;
    //@FXML
    private JFXButton nuevoBtn;
    //@FXML
    private JFXButton listarBtn;
    @FXML
    private JFXButton menuBtn;
    @FXML
    private VBox box;
    @FXML
    private StackPane stackPane;

    private SimplePopupMenuFactory<Modo> menuFactory;
    private VehiculoListaController listarController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setListarNode();
        } catch (IOException ex) {
            Logger.getLogger(VehiculoController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        menuFactory = new SimplePopupMenuFactory<Modo>(Modo.values());

        resetMenuBtnVisibility();

        getMenuFactory().getButton(Modo.CREAR).setOnAction((event) -> {
            onMenuCrearBtnClicked(event);
        });
        /*

        getMenuFactory().getButton(Modo.EDITAR).setOnAction((event) -> {
            onMenuEditarBtnClicked(event);
        });
        */
    }

    @FXML
    private void onMenuBtnClicked(MouseEvent e) {
        JFXPopup popup;
        popup = getMenuFactory().getPopup();
        popup.show(menuBtn);
    }

    private void onMenuCrearBtnClicked(ActionEvent e) {
        try {
            JFXDialog dialog;
            Node node;
            VehiculoCrearController controller;
            JFXButton button = new JFXButton(Dialogs.BUTTON.CREAR);
            FXMLLoader loader = getCrearLoader();
            node = (Node) loader.load();
            controller = loader.<VehiculoCrearController>getController();
            controller.setCrearButton(button);
            dialog = Dialogs.buildDialog(stackPane,
                    Dialogs.HEADINGS.EMPTY, node, button, false);
            dialog.setOnDialogClosed(closeEvent -> {
                try {
                    setListarNode();
                    resetMenuBtnVisibility();
                } catch (IOException ex) {
                    Logger.getLogger(VehiculoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            });
            dialog.show();

        } catch (IOException ex) {
            Logger.getLogger(VehiculoController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void onMenuEditarBtnClicked(ActionEvent e) {
        try {
            JFXDialog dialog;
            Node node;
            VehiculoCrearController controller;
            Long selectedId;
            JFXButton button = new JFXButton(Dialogs.BUTTON.EDITAR);
            FXMLLoader loader = getCrearLoader();
            node = (Node) loader.load();
            controller = loader.<VehiculoCrearController>getController();
            controller.setCrearButton(button);
            controller.setModo(VehiculoCrearController.Modo.EDITAR);
            selectedId = listarController.getSelectedId();
            controller.setData(selectedId);
            dialog = Dialogs.buildDialog(stackPane,
                    Dialogs.HEADINGS.EMPTY, node, button, false);
            dialog.setOnDialogClosed(closeEvent -> {
                try {
                    setListarNode();
                    resetMenuBtnVisibility();
                } catch (IOException ex) {
                    Logger.getLogger(VehiculoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            });
            dialog.show();

        } catch (IOException ex) {
            Logger.getLogger(VehiculoController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VehiculoController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Dialogs.showMsg(stackPane, Dialogs.HEADINGS.ERROR,
                    Dialogs.MESSAGES.CRUD_UPDATE_ERROR,
                    Dialogs.BUTTON.CERRAR);
        }
    }

    /**
     * @return the modo
     */
    public VehiculoController.Modo getModo() {
        return modo;
    }

    private void setListarNode() throws IOException {
        Node node;
        URL resource;
        String resourcePath;
        FXMLLoader loader;
        resourcePath = VehiculoListaController.VIEW_PATH;
        resource = getClass().getResource(resourcePath);

        loader = new FXMLLoader(resource);

        node = (Node) loader.load();
        listarController = loader.<VehiculoListaController>getController();
        listarController.setMainController(this);
        box.getChildren().setAll(node);
    }

    private FXMLLoader getCrearLoader() throws IOException {
        Node node;
        URL resource;
        String resourcePath;
        FXMLLoader loader;
        VehiculoCrearController controller;

        resourcePath = VehiculoCrearController.VIEW_PATH;
        resource = getClass().getResource(resourcePath);

        loader = new FXMLLoader(resource);

        return loader;
    }

    /**
     * @return the menuFactory
     */
    public SimplePopupMenuFactory<Modo> getMenuFactory() {
        return menuFactory;
    }

    public void resetMenuBtnVisibility() {
        getMenuFactory().getButton(Modo.EDITAR).setDisable(true);
        getMenuFactory().getButton(Modo.BORRAR).setDisable(true);
    }
}