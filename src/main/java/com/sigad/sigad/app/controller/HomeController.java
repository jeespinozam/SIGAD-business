/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.sigad.sigad.controller.cargaMasiva.CargaMasivaViewController;
import com.sigad.sigad.personal.controller.PersonalController;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import com.sigad.sigad.deposito.controller.FXMLAlmacenIngresoListaOrdenCompraController;
import com.sigad.sigad.perfil.controller.PerfilController;
import com.sigad.sigad.productos.controller.ProductosSubmenuController;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author jorgeespinoza
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    public static final String viewPath = "/com/sigad/sigad/app/view/home.fxml";
    public static String windowName = "Home";
    @FXML
    private JFXButton profileBtn, productoBtn,offertBtn;
    @FXML
    private List<MaterialDesignIcon> sidebarIcons = new ArrayList<MaterialDesignIcon>();
    private List<JFXButton> sidebarBtns = new ArrayList<JFXButton>();
    @FXML
    private JFXButton workersBtn,refundBtn,statisticBtn,settingsBtn;
    @FXML
    private JFXButton menuBtn,menuProfile;
    @FXML
    private AnchorPane containerPane, firstPanel;
    @FXML
    private AnchorPane sidebarPane;
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private JFXPopup popup;
    
    private Color mainColor = new Color(0.27, 0.31, 0.42, 1);
    private double  baseTop = 80.0;
    
    private enum Container{
        SIDEBAR, MENU, PANEL
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initsidebar();
    }   
    
    private void initsidebar(){
        
        sidebarBtns.add(new JFXButton("Perfil"));
        sidebarBtns.add(new JFXButton("Productos"));
        sidebarBtns.add(new JFXButton("Personal"));
        sidebarBtns.add(new JFXButton("Repartos"));
        sidebarBtns.add(new JFXButton("Pedidos"));
        sidebarBtns.add(new JFXButton("Estadísticas"));
        sidebarBtns.add(new JFXButton("Carga Masiva"));
        sidebarBtns.add(new JFXButton("Configuraciones"));
        
        sidebarIcons.add(MaterialDesignIcon.ACCOUNT_CIRCLE);
        sidebarIcons.add(MaterialDesignIcon.CLIPBOARD_TEXT);
        sidebarIcons.add(MaterialDesignIcon.ACCOUNT_MULTIPLE);
        sidebarIcons.add(MaterialDesignIcon.CAR);
        sidebarIcons.add(MaterialDesignIcon.BACKUP_RESTORE);
        sidebarIcons.add(MaterialDesignIcon.ELEVATION_RISE);
        sidebarIcons.add(MaterialDesignIcon.ARCHIVE);
        sidebarIcons.add(MaterialDesignIcon.SETTINGS);
        
        
        
        for (int i = 0; i < sidebarBtns.size(); i++) {
            setConfBtn(i, sidebarBtns.get(i), 70, 20, Pos.BASELINE_LEFT,
                    sidebarIcons.get(i), "30");
        }
        
        sidebarBtns.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(ProductosSubmenuController.viewPath));
                    firstPanel.getChildren().setAll(node);
                }catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "sidebarBtns.get(1).setOnAction", ex);
                }
            }
        });
        
        sidebarBtns.get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(PerfilController.viewPath));
                    firstPanel.getChildren().setAll(node);
                }catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "sidebarBtns.get(5).setOnAction", ex);
                }
            }
        });
        
        sidebarBtns.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(PersonalController.viewPath));
                    firstPanel.getChildren().setAll(node);
                }catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "sidebarBtns.get(2).setOnAction", ex);
                }
            }
        });
        sidebarBtns.get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(FXMLAlmacenIngresoListaOrdenCompraController.viewPath));
                    firstPanel.getChildren().setAll(node);
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "", ex);
                }
            }
        });
        
        sidebarBtns.get(6).setOnAction((event) -> {
            try {
                Node node;
                node = (Node) FXMLLoader.load(getClass().getResource(CargaMasivaViewController.viewPath));
                firstPanel.getChildren().setAll(node);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
    }
    
    private void setConfBtn(
            int count, JFXButton newButton, int height, int gap,
            Pos pos, MaterialDesignIcon iconType, String size) {
        //sidebarBtns creation
        newButton.setPrefHeight(height);
        newButton.setGraphicTextGap(gap);
        newButton.setAlignment(pos);
        newButton.getStyleClass().add("sidebarBtn");
        
        //icon
        MaterialDesignIconView icon;
        icon = new MaterialDesignIconView(iconType);
        icon.setSize(size);
	icon.setFill(mainColor);
        
        newButton.setGraphic(icon);
        
        sidebarPane.getChildren().add(newButton);   
        
        AnchorPane.setTopAnchor(newButton, baseTop + 70*(count));
        AnchorPane.setLeftAnchor(newButton, 0.0);
        AnchorPane.setRightAnchor(newButton, 0.0);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        
        if(event.getSource() == profileBtn){
            //firstPanel.toFront();
        }if (event.getSource() == sidebarBtns.get(1)) {
            System.err.println(">>>>");
            
        }
        
        System.out.println(event.getEventType());
    }

    @FXML
    private void menuBtnClicked(MouseEvent event) {
        if(sidebarPane.getPrefWidth()==50){
            sidebarPane.setPrefWidth(200);
            AnchorPane.setLeftAnchor(menuPanel, 200.00);
            AnchorPane.setLeftAnchor(firstPanel, 200.00);
            
            addClassToContainer(Container.SIDEBAR,"active");
        }else{
            sidebarPane.setPrefWidth(50);
            AnchorPane.setLeftAnchor(menuPanel, 50.00);
            AnchorPane.setLeftAnchor(firstPanel, 50.00);
            
            removeClassFromContainer(Container.SIDEBAR,"active");
        }
    }
    @FXML
    private void menuProfileClicked(MouseEvent event) {
        initPopup();
        popup.show(menuProfile, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
    }
    
    private void addClassToContainer(Container container, String clase){
        switch (container){
            case SIDEBAR:
                profileBtn.getStyleClass().add(clase);
                productoBtn.getStyleClass().add(clase);
                offertBtn.getStyleClass().add(clase);
                workersBtn.getStyleClass().add(clase);
                refundBtn.getStyleClass().add(clase);
                statisticBtn.getStyleClass().add(clase);
                settingsBtn.getStyleClass().add(clase);
                break;
            case MENU:
                break;
            case PANEL:
                break;
        }
    }
    
    private void removeClassFromContainer(Container container, String clase){
        switch (container){
            case SIDEBAR:
                profileBtn.getStyleClass().removeAll(clase);
                productoBtn.getStyleClass().removeAll(clase);
                offertBtn.getStyleClass().removeAll(clase);
                workersBtn.getStyleClass().removeAll(clase);
                refundBtn.getStyleClass().removeAll(clase);
                statisticBtn.getStyleClass().removeAll(clase);
                settingsBtn.getStyleClass().removeAll(clase);
                break;
            case MENU:
                break;
            case PANEL:
                break;
        }
    }
    
    private void initPopup(){
        JFXButton bl = new JFXButton("Mi perfil");
        JFXButton bl1 = new JFXButton("Configuraciones");
        JFXButton bl2 = new JFXButton("Logout");
        
        bl.setPadding(new Insets(20));
        bl1.setPadding(new Insets(20));
        bl2.setPadding(new Insets(20));
        
        bl.setPrefHeight(40);
        bl1.setPrefHeight(40);
        bl2.setPrefHeight(40);
        
        bl.setPrefWidth(145);
        bl1.setPrefWidth(145);
        bl2.setPrefWidth(145);
        
        VBox vBox = new VBox(bl, bl1, bl2);
        
        
        popup = new JFXPopup();
        popup.setPopupContent(vBox);
    }
    
//    public static void changeChildren(Node node){
//        firstPanel.getChildren().setAll(node);
//    }
    
    
}
