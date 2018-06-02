/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.insumos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.Proveedor;
import com.sigad.sigad.business.helpers.InsumosHelper;
import com.sigad.sigad.business.helpers.ProveedorHelper;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author chrs
 */
public class CrearEditarInsumoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    public static String viewPath = "/com/sigad/sigad/insumos/view/crearEditarInsumo.fxml";
    
    @FXML
    private StackPane hiddenSp;

    @FXML
    private AnchorPane containerPane;

//    @FXML
//    private JFXTextField precioTxt;

    @FXML
    private JFXTextField nombreTxt;


//    @FXML
//    private JFXComboBox<Proveedor> cbxProv;

    @FXML
    private JFXTextField tiempoTxt;

    @FXML
    private JFXTextArea descripcionTxt;

    @FXML
    private JFXTreeTableView<ProveedorViewer> tblProveedor;
    
    public static Insumo insumo = null;
    
    JFXTreeTableColumn<ProveedorViewer,String> nombreCol = new JFXTreeTableColumn<>("Nombre");
    JFXTreeTableColumn<ProveedorViewer,String> cantidadCol = new JFXTreeTableColumn<>("Precio");
    static ObservableList<ProveedorViewer> listaProv;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaProv = FXCollections.observableArrayList();
        setColumns();
        addColumns();
        fillData();
        addDialogBtns();
        if(!ListaInsumoController.isInsumoCreate) {
            System.out.println(ListaInsumoController.selectedInsumo);
            loadFields();
        }
        else {
            insumo = new Insumo();
        }
        
        initValidator();
    }
    
    
    private void addDialogBtns() {
        JFXButton save = new JFXButton("Guardar");
        save.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(save, -20.0);
        AnchorPane.setRightAnchor(save, 0.0);
        save.setOnAction((ActionEvent event) -> {
            if(validateFields()){
                System.out.println("VALIDADO ALL FIELDS");
                
                fillFields();
                InsumosHelper helper = new InsumosHelper();                
                //edicion
                if(!ListaInsumoController.isInsumoCreate){
                    System.out.println("editando");
                    System.out.println("waaa"+insumo.getNombre());
                    Long id = helper.updateInsumo(insumo);
                    if(id != null){
                        ListaInsumoController.insumosList.remove(ListaInsumoController.selectedInsumo);
                        ListaInsumoController.updateTable(insumo);
                        ListaInsumoController.insumoDialog.close();
                    }else{
                        ErrorController error = new ErrorController();
                        error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                    }
                }
                //creacion
                else{
                    Long id = helper.disableInsumo(insumo);
                    if(id != null){
                        ListaInsumoController.updateTable(insumo);
                        ListaInsumoController.insumoDialog.close();
                    }else {
                        ErrorController error = new ErrorController();
                        error.loadDialog("Error", helper.getErrorMessage(), "Ok", hiddenSp);
                    }
                }
            }
        });
        
        JFXButton cancel = new JFXButton("Cancelar");
        cancel.setPrefSize(80, 25);
        AnchorPane.setBottomAnchor(cancel, -20.0);
        AnchorPane.setRightAnchor(cancel, 85.0);
        cancel.setOnAction((ActionEvent event) -> {
            ListaInsumoController.insumoDialog.close();
            //PersonalController.getDataFromDB();
        });
        
        containerPane.getChildren().add(save);
        containerPane.getChildren().add(cancel);
    }
    
    private void loadFields(){
        
        ProveedorHelper helperp = new ProveedorHelper();             
        ArrayList<Proveedor> listaprov = helperp.getProveedores();
        if(listaprov != null) {
            listaprov.forEach((p)-> {
//                cbxProv.getItems().add(p);
            });
//            cbxProv.setPromptText("Seleccionar proveedor");
//            cbxProv.setConverter(new StringConverter<Proveedor>() {
//                Long id = null;
//                String des = null;
//                String ruc = null;
//                @Override
//                public String toString(Proveedor object) {
//                    id = object.getId();
//                    des = object.getDescripcion();
//                    ruc = object.getRuc();
//                    return object==null? "" : object.getNombre();
//                }
//
//                @Override
//                public Proveedor fromString(String string) {
//                    Proveedor pr= new Proveedor();
//                    pr.setNombre(string);
//                    pr.setId(id);
//                    pr.setDescripcion(des);
//                    pr.setRuc(ruc);
//                    return pr;
//                }
//            });
        }
        helperp.close();
        
        InsumosHelper helper = new InsumosHelper();
        insumo = helper.getInsumo(ListaInsumoController.selectedInsumo.getId());
        
        if(insumo != null) {
            nombreTxt.setText(insumo.getNombre());
            nombreTxt.setDisable(true);
            tiempoTxt.setText(insumo.getTiempoVida() + "");
            descripcionTxt.setText(insumo.getDescripcion());
//            precioTxt.setText(insumo.getPrecio()+ "");
        }
        helper.close();
        
        
    }
    private void fillFields(){
        insumo.setNombre(nombreTxt.getText());
        insumo.setTiempoVida(Integer.parseInt(tiempoTxt.getText()));
//        insumo.setPrecio(Double.parseDouble(precioTxt.getText()));
        insumo.setActivo(true);
        insumo.setStockTotalFisico(0);
        insumo.setStockTotalLogico(0);
        insumo.setDescripcion(descripcionTxt.getText());
    }
    
    public boolean validateFields() {
        if(!nombreTxt.validate()){
            nombreTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            nombreTxt.requestFocus();
            return false;
        }else if(!tiempoTxt.validate()){
            tiempoTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            tiempoTxt.requestFocus();
            return false;
        }else if(!descripcionTxt.validate()){
            descripcionTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
            descripcionTxt.requestFocus();
            return false;
        }
//        else if(!precioTxt.validate()){
//            precioTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
//            precioTxt.requestFocus();
//            return false;
//        }
        else return true;
    }
    
    private void initValidator() {
        RequiredFieldValidator r;
        NumberValidator n;
        DoubleValidator d;
                
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");  
        nombreTxt.getValidators().add(r);
        nombreTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!nombreTxt.validate()){
                    nombreTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else nombreTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });
        
        /*Tiempo de vida*/
        r = new RequiredFieldValidator();
        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        r.setMessage("Campo obligatorio");
        tiempoTxt.getValidators().add(r);
        
        n = new NumberValidator();
        n.setMessage("Campo numérico");
        n.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
        tiempoTxt.getValidators().add(n);
        
        tiempoTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if(!tiempoTxt.validate()){
                    tiempoTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
                }
                else tiempoTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
            }
        });

        
        /*Precio proveedor*/
//        r = new RequiredFieldValidator();
//        r.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
//        r.setMessage("Campo obligatorio");
////        precioTxt.getValidators().add(r);
//        
//        
//        d = new DoubleValidator();
//        d.setMessage("Campo numérico");
//        d.setIcon(new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE));
//        precioTxt.getValidators().add(d);
//        
//        precioTxt.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//            if (!newValue) {
//                if(!precioTxt.validate()){
//                    precioTxt.setFocusColor(new Color(0.58, 0.34, 0.09, 1));
//                }
//                else precioTxt.setFocusColor(new Color(0.30,0.47,0.23, 1));
//            }
//        });
        

    }
    
    
    public static class ProveedorViewer extends RecursiveTreeObject<ProveedorViewer>{

        public SimpleStringProperty getNombre() {
            return nombre;
        }

        public SimpleStringProperty getPrecio() {
            return precio;
        }

        public void setNombre(SimpleStringProperty nombre) {
            this.nombre = nombre;
        }

        public void setPrecio(SimpleStringProperty precio) {
            this.precio = precio;
        }

        private SimpleStringProperty nombre;
        private SimpleStringProperty precio;


        public ProveedorViewer(String nombre,String precio) {
            this.nombre = new SimpleStringProperty(nombre);
            this.precio = new SimpleStringProperty(precio);
        }
    }
    
    
    private void setColumns(){
        nombreCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProveedorViewer, String> param) -> param.getValue().getValue().getNombre()//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );
        cantidadCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProveedorViewer, String> param) -> param.getValue().getValue().getPrecio() //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );
    }
    private void addColumns(){
        final TreeItem<ProveedorViewer> rootProveedor = new RecursiveTreeItem<>(listaProv,RecursiveTreeObject::getChildren);
        tblProveedor.setEditable(true);
        tblProveedor.getColumns().setAll(nombreCol,cantidadCol);
        tblProveedor.setRoot(rootProveedor);
        tblProveedor.setShowRoot(false);
    }
    private void fillData(){
        ProveedorHelper helper = new ProveedorHelper();
        ArrayList<Proveedor> listaProvGet = helper.getProveedores();
        if(listaProvGet != null) {
            listaProvGet.forEach((i)->{
                updateTable(i);
            });
        }
        helper.close();
    }
    
    public static void updateTable(Proveedor p){
        listaProv.add(new ProveedorViewer(p.getNombre(),"0"));
    }
    
       
}
