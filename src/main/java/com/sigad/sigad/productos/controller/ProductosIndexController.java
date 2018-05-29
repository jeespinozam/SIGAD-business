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
import com.sigad.sigad.business.Producto;
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
    private JFXTreeTableView<ProductoLista> productosTabla;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        JFXTreeTableColumn<ProductoLista,String> productoNombre = new JFXTreeTableColumn<>("Nombre");
        productoNombre.setPrefWidth(200);
        productoNombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista,String> param){
                return param.getValue().getValue().nombre;
            }
        });
        
        JFXTreeTableColumn<ProductoLista,String> productoPrecio = new JFXTreeTableColumn<>("Precio");
        productoPrecio.setPrefWidth(100);
        productoPrecio.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista,String> param){
                return param.getValue().getValue().precio;
            }
        });

        JFXTreeTableColumn<ProductoLista,String> productoStock = new JFXTreeTableColumn<>("Stock");
        productoStock.setPrefWidth(100);
        productoStock.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista,String> param){
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
        
        ObservableList<ProductoLista> productosList = FXCollections.observableArrayList();
        ProductoHelper productoHelper = new ProductoHelper();
        
        ArrayList<Producto> productos = productoHelper.getProducts();
        
        if(productos != null){
            productos.forEach((p) ->{
                Producto t = p;
                productosList.add(new ProductoLista(
                    t.getNombre(),
                    String.valueOf(t.getPrecio()),
                    String.valueOf(t.getStock()))
                );
            });
        }
        
        productoHelper.close();
        
        final TreeItem<ProductoLista> root = new RecursiveTreeItem<ProductoLista>(productosList, RecursiveTreeObject::getChildren);
        productosTabla.getColumns().setAll(productoNombre,productoPrecio,productoStock);
        productosTabla.setRoot(root);
        productosTabla.setShowRoot(false);
    }    
    
    class ProductoLista extends RecursiveTreeObject<ProductoLista>{
        StringProperty nombre;
        StringProperty precio;
        StringProperty stock;
        StringProperty categoria;
        
        public ProductoLista(String nombre, String precio, String stock){
            this.nombre = new SimpleStringProperty(nombre);
            this.precio = new SimpleStringProperty(precio);
            this.stock = new SimpleStringProperty(stock);
        }
    }
    
}
