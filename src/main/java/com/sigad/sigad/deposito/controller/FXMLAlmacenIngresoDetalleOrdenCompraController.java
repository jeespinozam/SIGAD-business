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
         * @return the fechaVencimiento
         */
        public SimpleStringProperty getFechaVencimiento() {
            return fechaVencimiento;
        }

        /**
         * @param fechaVencimiento the fechaVencimiento to set
         */
        public void setFechaVencimiento(SimpleStringProperty fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }

        /**
         * @return the nombre
         */
        public SimpleStringProperty getNombre() {
            return nombre;
        }

        /**
         * @param nombre the nombre to set
         */
        public void setNombre(String nombre) {
            this.nombre = new SimpleStringProperty(nombre);
        }

        /**
         * @return the precioLote
         */
        public SimpleDoubleProperty getPrecioLote() {
            return precioLote;
        }

        /**
         * @param precioLote the precioLote to set
         */
        public void setPrecioLote(Double precioLote) {
            this.precioLote = new SimpleDoubleProperty(precioLote);
        }

        /**
         * @return the cantidadLote
         */
        public SimpleIntegerProperty getCantidadLote() {
            return cantidadLote;
        }

        /**
         * @param cantidadLote the cantidadLote to set
         */
        public void setCantidadLote(Integer cantidadLote) {
            this.cantidadLote = new SimpleIntegerProperty(cantidadLote);
        }


        /**
         * @return the cantidadRecibida
         */
        public SimpleIntegerProperty getCantidadRecibida() {
            return cantidadRecibida;
        }

        /**
         * @param cantidadRecibida the cantidadRecibida to set
         */
        public void setCantidadRecibida(Integer cantidadRecibida) {
            this.cantidadRecibida = new SimpleIntegerProperty(cantidadRecibida);
        }
        
        private SimpleStringProperty nombre;
        private SimpleDoubleProperty  precioLote;
        private SimpleIntegerProperty cantidadLote;
        private SimpleStringProperty fechaVencimiento;
        private SimpleIntegerProperty cantidadRecibida;
        
        
        public OrdenCompraRecibida(String nombre,Double precioLote,Integer cantidadLote,String fechaVencimiento,Integer cantidadRecibida){
            this.nombre = new SimpleStringProperty(nombre);
            this.precioLote = new SimpleDoubleProperty(precioLote);
            this.cantidadLote = new SimpleIntegerProperty(cantidadLote);
            this.fechaVencimiento = new SimpleStringProperty(fechaVencimiento);
            this.cantidadRecibida = new SimpleIntegerProperty(cantidadRecibida);
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
        JFXTreeTableColumn<OrdenCompraRecibida,Number> precioCol = new JFXTreeTableColumn<>("Precio");
        precioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, Number>, ObservableValue<Number>>(){
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, Number> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getPrecioLote();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,Number> cantidadCol = new JFXTreeTableColumn<>("Precio");
        precioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, Number>, ObservableValue<Number>>(){
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, Number> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getCantidadLote();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,String> fechaCol = new JFXTreeTableColumn<>("Fecha Vencimiento");
        nombCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getFechaVencimiento();
            }
        });
        JFXTreeTableColumn<OrdenCompraRecibida,Number> cantidadRecCol = new JFXTreeTableColumn<>("Cantidad Recibida");
        cantidadRecCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, Number>, ObservableValue<Number>>(){
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<OrdenCompraRecibida, Number> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getCantidadRecibida();
            }
        });
        
        nombCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        ordenesTable.setEditable(true);
        ObservableList<OrdenCompraRecibida> ordenes = FXCollections.observableArrayList();
        ordenes.add(new OrdenCompraRecibida("Insumo 1",10.0,10,"10/10/2017",10));
        ordenes.add(new OrdenCompraRecibida("Insumo 2",10.0,10,"10/10/2017",10));
        ordenes.add(new OrdenCompraRecibida("Insumo 3",10.0,10,"10/10/2017",10));
        ordenes.add(new OrdenCompraRecibida("Insumo 4",10.0,10,"10/10/2017",10));
        
        final TreeItem<OrdenCompraRecibida> root = new RecursiveTreeItem<>(ordenes,RecursiveTreeObject::getChildren);
        ordenesTable.getColumns().setAll(nombCol,precioCol,cantidadCol,fechaCol,cantidadRecCol);
        ordenesTable.setRoot(root);
        ordenesTable.setShowRoot(false);
        
    }    
    
}
