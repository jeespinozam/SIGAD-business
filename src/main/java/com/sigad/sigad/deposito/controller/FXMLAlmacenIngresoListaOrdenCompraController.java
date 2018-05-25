/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.deposito.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.app.controller.HomeController;
import com.sigad.sigad.business.OrdenCompra;
import com.sigad.sigad.deposito.helper.DepositoHelper;
import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseButton;
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
    private JFXTreeTableView<OrdenCompraViewer> tblAlmacenOrdenesCompra;
    
    class OrdenCompraViewer extends RecursiveTreeObject<OrdenCompraViewer>{

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
        
        public OrdenCompraViewer(String codigo,String fecha,Double precio){
            this.codigo = new SimpleStringProperty(codigo);
            this.fecha = new SimpleStringProperty(fecha);
            this.precio = new SimpleDoubleProperty(precio);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXTreeTableColumn<OrdenCompraViewer,String> codCol = new JFXTreeTableColumn<>("Codigo");
        codCol.setPrefWidth(425);
        codCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraViewer, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraViewer, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getCodigo();
            }
        });
        
        JFXTreeTableColumn<OrdenCompraViewer,String> fechaCol = new JFXTreeTableColumn<>("Fecha Compra");
        fechaCol.setPrefWidth(200);
        fechaCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraViewer, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<OrdenCompraViewer, String> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getFecha();
            }
        });
        JFXTreeTableColumn<OrdenCompraViewer,Number> precioCol = new JFXTreeTableColumn<>("Precio");
        precioCol.setPrefWidth(200);
        precioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<OrdenCompraViewer, Number>, ObservableValue<Number>>(){
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<OrdenCompraViewer, Number> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return param.getValue().getValue().getPrecio();
            }
        });
        
        //Double click on row
        tblAlmacenOrdenesCompra.setRowFactory(ord -> {
            JFXTreeTableRow<OrdenCompraViewer> row = new JFXTreeTableRow<>();
            row.setOnMouseClicked((event) -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
                     && event.getClickCount() == 2) {
                    OrdenCompraViewer clickedRow = row.getItem();
                    System.out.println(clickedRow.getCodigo());
//                    try {
//                        Node node;
//                        node = (Node) FXMLLoader.load(FXMLAlmacenIngresoListaOrdenCompraController.this.getClass().getResource(FXMLAlmacenIngresoDetalleOrdenCompraController.viewPath));
//                        HomeController.changeChildren(node);
//                    } catch (IOException ex) {
//                        Logger.getLogger(FXMLAlmacenIngresoListaOrdenCompraController.class.getName()).log(Level.SEVERE, null, ex);
//                    }                  
                }
            });
            return row;
        });        
        
        ObservableList<OrdenCompraViewer> ordenes = FXCollections.observableArrayList();
           
        final TreeItem<OrdenCompraViewer> root = new RecursiveTreeItem<>(ordenes,RecursiveTreeObject::getChildren);
        tblAlmacenOrdenesCompra.getColumns().setAll(codCol,fechaCol,precioCol);
        tblAlmacenOrdenesCompra.setRoot(root);
        tblAlmacenOrdenesCompra.setShowRoot(false);
        DepositoHelper depositoHelper = new DepositoHelper();
        List<OrdenCompra> ordenesBD= depositoHelper.getOrdenes();
        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");
        ordenesBD.forEach((orden)->{
            OrdenCompraViewer ordenViewer = new OrdenCompraViewer(Integer.toString(orden.getId()),sdf.format(orden.getFecha()),orden.getPrecioTotal());
            ordenes.add(ordenViewer);
            System.out.println(orden.getId() + " " + orden.getPrecioTotal() + " " + orden.getFecha());
            
        });
    }    
    
}
