/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.deposito.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author chrs
 */
public class FXMLAlmacenIngresoDetalleOrdenCompraController implements Initializable {
    public static final String viewPath="/com/sigad/sigad/deposito/view/FXMLAlmacenIngresoDetalleOrdenCompra.fxml";
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<OrdenCompraRecibida> ordenesTable;
    
    class OrdenCompraRecibida extends RecursiveTreeObject<OrdenCompraRecibida>{

        /**
         * @return the nombre
         */
        public SimpleStringProperty getNombre() {
            return nombre;
        }

        /**
         * @return the precioLote
         */
        public SimpleStringProperty getPrecioLote() {
            return precioLote;
        }

        /**
         * @return the cantidadLote
         */
        public SimpleStringProperty getCantidadLote() {
            return cantidadLote;
        }

        /**
         * @return the fechaVencimiento
         */
        public SimpleStringProperty getFechaVencimiento() {
            return fechaVencimiento;
        }

        /**
         * @return the cantidadRecibida
         */
        public SimpleStringProperty getCantidadRecibida() {
            return cantidadRecibida;
        }

        /**
         * @param nombre the nombre to set
         */
        public void setNombre(SimpleStringProperty nombre) {
            this.nombre = nombre;
        }

        /**
         * @param precioLote the precioLote to set
         */
        public void setPrecioLote(String precioLote) {
            this.precioLote = new SimpleStringProperty(precioLote);
        }

        /**
         * @param cantidadLote the cantidadLote to set
         */
        public void setCantidadLote(String cantidadLote) {
            this.cantidadLote = new SimpleStringProperty(cantidadLote);
        }

        /**
         * @param fechaVencimiento the fechaVencimiento to set
         */
        public void setFechaVencimiento(String fechaVencimiento) {
            this.fechaVencimiento = new SimpleStringProperty(fechaVencimiento);
        }

        /**
         * @param cantidadRecibida the cantidadRecibida to set
         */
        public void setCantidadRecibida(String cantidadRecibida) {
            this.cantidadRecibida = new SimpleStringProperty(cantidadRecibida);
        }
        
        private SimpleStringProperty nombre;
        private SimpleStringProperty  precioLote;
        private SimpleStringProperty cantidadLote;
        private SimpleStringProperty fechaVencimiento;
        private SimpleStringProperty cantidadRecibida;
        
        
        public OrdenCompraRecibida(String nombre,String precioLote,String cantidadLote,String fechaVencimiento,String cantidadRecibida){
            this.nombre = new SimpleStringProperty(nombre);
            this.precioLote = new SimpleStringProperty(precioLote);
            this.cantidadLote = new SimpleStringProperty(cantidadLote);
            this.fechaVencimiento = new SimpleStringProperty(fechaVencimiento);
            this.cantidadRecibida = new SimpleStringProperty(cantidadRecibida);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXTreeTableColumn<OrdenCompraRecibida,String> nombCol = new JFXTreeTableColumn<>("Nombre");
        nombCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getNombre();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,String> precioCol = new JFXTreeTableColumn<>("Precio");
        precioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida,String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getPrecioLote();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,String> cantidadCol = new JFXTreeTableColumn<>("Cantidad");
        cantidadCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getCantidadLote();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,String> fechaCol = new JFXTreeTableColumn<>("Fecha Vencimiento");
        fechaCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getFechaVencimiento();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,String> cantidadRecCol = new JFXTreeTableColumn<>("Cantidad Recibida");
        cantidadRecCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getCantidadRecibida();
            }
        });
        
        nombCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        ordenesTable.setEditable(true);
        ObservableList<OrdenCompraRecibida> ordenes = FXCollections.observableArrayList();
        OrdenCompraRecibida  aux = new OrdenCompraRecibida("Insumo 1","10.0","10","10/10/2017","10");
        ordenes.add(new OrdenCompraRecibida("Insumo 1","10.0","10","10/10/2017","10"));
        ordenes.add(new OrdenCompraRecibida("Insumo 2","10.0","10","10/10/2017","10"));
        ordenes.add(new OrdenCompraRecibida("Insumo 3","10.0","10","10/10/2017","10"));
        ordenes.add(new OrdenCompraRecibida("Insumo 4","10.0","10","10/10/2017","10"));
        
        final TreeItem<OrdenCompraRecibida> root = new RecursiveTreeItem<>(ordenes,RecursiveTreeObject::getChildren);
        ordenesTable.getColumns().setAll(nombCol,precioCol,cantidadCol,fechaCol,cantidadRecCol);
        ordenesTable.setRoot(root);
        ordenesTable.setShowRoot(false);
        
    }    
    
}