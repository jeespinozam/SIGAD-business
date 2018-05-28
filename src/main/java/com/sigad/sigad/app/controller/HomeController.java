/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.sigad.sigad.controller.cargaMasiva.CargaMasivaViewController;
import com.sigad.sigad.deposito.controller.FXMLAlmacenIngresoListaOrdenCompraController;
import com.sigad.sigad.pedido.controller.SeleccionarProductosController;
import com.sigad.sigad.perfil.controller.PerfilController;
import com.sigad.sigad.personal.controller.PersonalController;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
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
    
    public static enum SIGADMenuItemType {
        PERFIL {
            @Override
            public String toString() {
                return "Perfil";
            }
        },
        PRODUCTOS {
            @Override
            public String toString() {
                return "Productos";
            }
        },
        PERSONAL {
            @Override
            public String toString() {
                return "Personal";
            }
        },
        REPARTOS {
            @Override
            public String toString() {
                return "Repartos";
            }
        },
        PEDIDOS {
            @Override
            public String toString() {
                return "Pedidos";
            }
        },
        ESTADISTICAS {
            @Override
            public String toString() {
                return "Estadísticas";
            }
        },
        CARGA_MASIVA {
            @Override
            public String toString() {
                return "Carga Masiva";
            }
        },
        CONFIGURACIONES {
            @Override
            public String toString() {
                return "Configuraciones";
            }
        };

        public static final MaterialDesignIcon getIcon(SIGADMenuItemType type) {
            MaterialDesignIcon icon;

            icon = MaterialDesignIcon.ALERT;
            switch (type) {
                case PERFIL:
                    icon = MaterialDesignIcon.ACCOUNT_CIRCLE;
                    break;
                case PRODUCTOS:
                    icon = MaterialDesignIcon.CLIPBOARD_TEXT;
                    break;
                case PERSONAL:
                    icon = MaterialDesignIcon.ACCOUNT_MULTIPLE;
                    break;
                case REPARTOS:
                    icon = MaterialDesignIcon.CAR;
                    break;
                case PEDIDOS:
                    icon = MaterialDesignIcon.BACKUP_RESTORE;
                    break;
                case ESTADISTICAS:
                    icon = MaterialDesignIcon.ELEVATION_RISE;
                    break;
                case CARGA_MASIVA:
                    icon = MaterialDesignIcon.ARCHIVE;
                    break;
                case CONFIGURACIONES:
                    icon = MaterialDesignIcon.SETTINGS;
                    break;
            }
            return icon;
        }
    };

    public static String viewPath = "/com/sigad/sigad/app/view/home.fxml";
    public static String windowName = "Home";

    public static final int SIDEBAR_BUTTON_HEIGHT = 70;
    public static final int SIDEBAR_BUTTON_GAP = 20;
    public static final Pos SIDEBAR_BUTTON_ALIGNMENT = Pos.BASELINE_LEFT;
    public static final String SIDEBAR_BUTTON_ICON_SIZE = "30";

    private Map<SIGADMenuItemType, JFXButton> sidebarBtns;

    @FXML
    private JFXButton profileBtn, productoBtn,offertBtn;
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
        int i;
        sidebarBtns = new EnumMap<>(SIGADMenuItemType.class);

        for (SIGADMenuItemType item : SIGADMenuItemType.values()) {
            JFXButton button;
            MaterialDesignIcon icon;

            button = new JFXButton(item.toString());
            icon = SIGADMenuItemType.getIcon(item);
            sidebarBtns.put(item, button);

            setConfBtn(item.ordinal(), button, icon);
        }

        sidebarBtns.get(SIGADMenuItemType.PRODUCTOS).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(SeleccionarProductosController.viewPath));
                    firstPanel.getChildren().setAll(node);
                }catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,
                            SIGADMenuItemType.PRODUCTOS.toString(), ex);
                }
            }
        });
        
        sidebarBtns.get(SIGADMenuItemType.ESTADISTICAS).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(PerfilController.viewPath));
                    firstPanel.getChildren().setAll(node);
                }catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,
                            SIGADMenuItemType.ESTADISTICAS.toString(), ex);
                }
            }
        });
        
        sidebarBtns.get(SIGADMenuItemType.PERSONAL).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(PersonalController.viewPath));
                    firstPanel.getChildren().setAll(node);
                }catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,
                            SIGADMenuItemType.PERSONAL.toString(), ex);
                }
            }
        });
        sidebarBtns.get(SIGADMenuItemType.PEDIDOS).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node;
                    node = (Node) FXMLLoader.load(HomeController.this.getClass().getResource(FXMLAlmacenIngresoListaOrdenCompraController.viewPath));
                    firstPanel.getChildren().setAll(node);
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,
                            SIGADMenuItemType.PEDIDOS.toString(), ex);
                }
            }
        });
        sidebarBtns.get(SIGADMenuItemType.CARGA_MASIVA).setOnAction((event) -> {
            try {
                Node node;
                node = (Node) FXMLLoader.load(getClass().getResource(CargaMasivaViewController.viewPath));
                firstPanel.getChildren().setAll(node);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,
                        SIGADMenuItemType.CARGA_MASIVA.toString(), ex);
            }
        });        
    }
    
    private void setConfBtn(
            int count, JFXButton newButton, MaterialDesignIcon iconType) {
        //sidebarBtns creation
        newButton.setPrefHeight(SIDEBAR_BUTTON_HEIGHT);
        newButton.setGraphicTextGap(SIDEBAR_BUTTON_GAP);
        newButton.setAlignment(SIDEBAR_BUTTON_ALIGNMENT);
        newButton.getStyleClass().add("sidebarBtn");
        
        //icon
        MaterialDesignIconView icon;
        icon = new MaterialDesignIconView(iconType);
        icon.setSize(SIDEBAR_BUTTON_ICON_SIZE);
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
