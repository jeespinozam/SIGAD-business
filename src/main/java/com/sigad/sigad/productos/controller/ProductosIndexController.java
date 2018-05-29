/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.productos.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import com.sigad.sigad.productos.helper.ProductoHelper;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.TreeItem;

/**
 * FXML Controller class
 *
 * @author jorgito-stark
 */
public class ProductosIndexController implements Initializable {
    @FXML
    private JFXTreeTableView<Producto> productosTabla;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        JFXTreeTableColumn<Producto,String> productoNombre = new JFXTreeTableColumn<>("Nombre");
        productoNombre.setPrefWidth(200);
        productoNombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto,String> param){
                return param.getValue().getValue().nombre;
            }
        });
        
        JFXTreeTableColumn<Producto,String> productoPrecio = new JFXTreeTableColumn<>("Precio");
        productoPrecio.setPrefWidth(100);
        productoPrecio.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto,String> param){
                return param.getValue().getValue().precio;
            }
        });

        JFXTreeTableColumn<Producto,String> productoStock = new JFXTreeTableColumn<>("Stock");
        productoStock.setPrefWidth(100);
        productoStock.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto,String> param){
                return param.getValue().getValue().stock;
            }
        });

        /*
        JFXTreeTableColumn<Producto,String> productoCategoria = new JFXTreeTableColumn<>("Categoria");
        productoCategoria.setPrefWidth(200);
        productoCategoria.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto,String> param){
                return param.getValue().getValue().categoria;
            }
        });
        */
        
        ObservableList<Producto> productosList = FXCollections.observableArrayList();
        ProductoHelper productoHelper = new ProductoHelper();
        
        ArrayList<com.sigad.sigad.business.Producto> productos = productoHelper.getProducts();
        for (Iterator<com.sigad.sigad.business.Producto> iterator = productos.iterator(); iterator.hasNext();) {
            com.sigad.sigad.business.Producto next = iterator.next();
            productosList.add(new Producto(
                                        next.getNombre(),
                                        String.valueOf(next.getPrecio()),
                                        String.valueOf(next.getStock()))
            );
        }
        
        final TreeItem<Producto> root = new RecursiveTreeItem<Producto>(productosList, RecursiveTreeObject::getChildren);
        productosTabla.getColumns().setAll(productoNombre,productoPrecio,productoStock);
        productosTabla.setRoot(root);
        productosTabla.setShowRoot(false);
    }    
    
    class Producto extends RecursiveTreeObject<Producto>{
        StringProperty nombre;
        StringProperty precio;
        StringProperty stock;
        StringProperty categoria;
        
        public Producto(String nombre, String precio, String stock){
            this.nombre = new SimpleStringProperty(nombre);
            this.precio = new SimpleStringProperty(precio);
            this.stock = new SimpleStringProperty(stock);
        }
    }
    
}
