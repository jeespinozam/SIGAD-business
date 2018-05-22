/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import java.io.IOException;
/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static String viewPath = "/com/sigad/sigad/app/view/home.fxml";
    public static String windowName = "Home";
    @FXML
    private JFXButton profileBtn, productoBtn, offertBtn;
    @FXML
    private JFXButton workersBtn, menuBtn;
    @FXML
    private AnchorPane containerPane, firstPanel;
    @FXML
    private AnchorPane sidebarPane;
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private Label profileLbl, refundLbl, statisticsLbl;
    @FXML
    private Label productLbl, offertLbl, workersLbl, settingsLbl;
    @FXML
    private JFXButton refundBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fixed view

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == profileBtn) {
            //firstPanel.toFront();
        }
        if (event.getSource() == productoBtn) {
            Node node;
            node = (Node) FXMLLoader.load(getClass().getResource(SeleccionarProductosController.viewPath));
            firstPanel.getChildren().setAll(node);
        }
        System.out.println(event.getEventType());
    }

    @FXML
    private void menuBtnClicked(MouseEvent event) {
        if (sidebarPane.getPrefWidth() == 50) {
            sidebarPane.setPrefWidth(200);
            AnchorPane.setLeftAnchor(menuPanel, 200.00);
            AnchorPane.setLeftAnchor(firstPanel, 200.00);
        } else {
            sidebarPane.setPrefWidth(50);
            AnchorPane.setLeftAnchor(menuPanel, 50.00);
            AnchorPane.setLeftAnchor(firstPanel, 50.00);
        }
    }

}
