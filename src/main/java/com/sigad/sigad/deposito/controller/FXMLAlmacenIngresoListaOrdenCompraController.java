/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.deposito.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.deposito.helper.DepositoHelper;
import java.beans.EventHandler;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
//import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public class FXMLAlmacenIngresoListaOrdenCompraController implements Initializable {
    /**
     * Initializes the controller class.
     */
    
    public static final String viewPath="/com/sigad/sigad/deposito/view/FXMLAlmacenIngresoListaOrdenCompra.fxml";
    @FXML
    private JFXTreeTableView<OrdenCompra> tblAlmacenOrdenesCompra;
    
    class OrdenCompra extends RecursiveTreeObject<OrdenCompra>{

        /**
         * @return the codigo
         */
        public SimpleStringProperty getCodigo() {
            return codigo;
        }

        /**
         * @param codigo the codigo to set
         */
        public void setCodigo(SimpleStringProperty codigo) {
            this.codigo = codigo;
        }

        /**
         * @return the fecha
         */
        public SimpleStringProperty getFecha() {
            return fecha;
        }

        /**
         * @param fecha the fecha to set
         */
        public void setFecha(SimpleStringProperty fecha) {
            this.fecha = fecha;
        }

        /**
         * @return the precio
         */
        public SimpleDoubleProperty getPrecio() {
            return precio;
        }

        /**
         * @param precio the precio to set
         */
        public void setPrecio(Double precio) {
            this.precio = new SimpleDoubleProperty(precio);
        }
        private SimpleStringProperty codigo;
        private SimpleStringProperty fecha;
        private SimpleDoubleProperty  precio;
        
        public OrdenCompra(String codigo,String fecha,Double precio){
            this.codigo = new SimpleStringProperty(codigo);
            this.fecha = new SimpleStringProperty(fecha);
            this.precio = new SimpleDoubleProperty(precio);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXTreeTableColumn<OrdenCompra,String> codCol = new JFXTreeTableColumn<>("Codigo");
        codCol.setPrefWidth(425);
        codCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompra, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompra, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getCodigo();
            }
        });
        
        JFXTreeTableColumn<OrdenCompra,String> fechaCol = new JFXTreeTableColumn<>("Fecha Compra");
        fechaCol.setPrefWidth(200);
        fechaCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompra, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompra, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getFecha();
            }
        });
        JFXTreeTableColumn<OrdenCompra,Number> precioCol = new JFXTreeTableColumn<>("Precio");
        precioCol.setPrefWidth(200);
        precioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompra, Number>, ObservableValue<Number>>(){
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<OrdenCompra, Number> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getPrecio();
            }
        });
        ObservableList<OrdenCompra> ordenes = FXCollections.observableArrayList();
        ordenes.add(new OrdenCompra("COD-GG", "10/10/2017",10.0));
        ordenes.add(new OrdenCompra("COD-GG", "10/10/2017",10.0));
        ordenes.add(new OrdenCompra("COD-GG", "10/10/2017",10.0));
        ordenes.add(new OrdenCompra("COD-GG", "10/10/2017",10.0));
           
        final TreeItem<OrdenCompra> root = new RecursiveTreeItem<>(ordenes,RecursiveTreeObject::getChildren);
        tblAlmacenOrdenesCompra.getColumns().setAll(codCol,fechaCol,precioCol);
        tblAlmacenOrdenesCompra.setRoot(root);
        tblAlmacenOrdenesCompra.setShowRoot(false);
    }    
    
}
