/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.descuentos.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.business.ProductoDescuento;
import com.sigad.sigad.business.helpers.ProductoDescuentoHelper;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class MantenimientoDescuentosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/descuentos/view/mantenimientoDescuentos.fxml";

    @FXML
    private StackPane stackPane;

    @FXML
    public static JFXDialog descDialog;

    @FXML
    JFXTreeTableColumn<DescuentosLista, Integer> id = new JFXTreeTableColumn<>("id");
    @FXML
    JFXTreeTableColumn<DescuentosLista, String> producto = new JFXTreeTableColumn<>("Producto");
    @FXML
    JFXTreeTableColumn<DescuentosLista, String> fechaInicio = new JFXTreeTableColumn<>("F. Inicio");
    @FXML
    JFXTreeTableColumn<DescuentosLista, String> fechaFin = new JFXTreeTableColumn<>("F. Fin");
    @FXML
    JFXTreeTableColumn<DescuentosLista, Double> valorPct = new JFXTreeTableColumn<>("Valor(%)");

    @FXML
    private JFXTreeTableView<DescuentosLista> tblDescuentos;
    public static final ObservableList<DescuentosLista> descuentos = FXCollections.observableArrayList();
    private Boolean isEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        isEdit = Boolean.FALSE;
        columnasDescuentos();
        agregarColumnas();
        llenarTabla();
    }

    public  void llenarTabla() {
        descuentos.clear();
        ProductoDescuentoHelper pdhelper = new ProductoDescuentoHelper();
        ArrayList<ProductoDescuento> pd = pdhelper.getDescuentos();
        pdhelper.close();
        pd.forEach((t) -> {
            descuentos.add(new DescuentosLista(t));
        });

    }

    public void columnasDescuentos() {
        id.setPrefWidth(120);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<DescuentosLista, Integer> param) -> param.getValue().getValue().id.asObject());

        fechaFin.setPrefWidth(120);
        fechaFin.setCellValueFactory((TreeTableColumn.CellDataFeatures<DescuentosLista, String> param) -> param.getValue().getValue().fechaFin);

        fechaInicio.setPrefWidth(120);
        fechaInicio.setCellValueFactory((TreeTableColumn.CellDataFeatures<DescuentosLista, String> param) -> param.getValue().getValue().fechaInicio);

        valorPct.setPrefWidth(120);
        valorPct.setCellValueFactory((TreeTableColumn.CellDataFeatures<DescuentosLista, Double> param) -> param.getValue().getValue().valorPct.asObject());

        producto.setPrefWidth(120);
        producto.setCellValueFactory((TreeTableColumn.CellDataFeatures<DescuentosLista, String> param) -> param.getValue().getValue().producto);

    }

    public void agregarColumnas() {
        final TreeItem<DescuentosLista> rootPedido = new RecursiveTreeItem<>(descuentos, RecursiveTreeObject::getChildren);
        tblDescuentos.setEditable(true);
        tblDescuentos.getColumns().setAll(id, fechaInicio, fechaFin, valorPct, producto);
        tblDescuentos.setRoot(rootPedido);
        tblDescuentos.setShowRoot(false);
        tblDescuentos.setRowFactory(new Callback<TreeTableView<DescuentosLista>, TreeTableRow<DescuentosLista>>() {
            @Override
            public TreeTableRow<DescuentosLista> call(TreeTableView<DescuentosLista> param) {
                TreeTableRow<DescuentosLista> row = new TreeTableRow<>();
                row.setOnMouseClicked((event) -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        DescuentosLista rowData = row.getItem();
                        isEdit= Boolean.TRUE;
                        editRegistrarDescuento(rowData.descuentoObj);
                    }
                });
                return row; //To change body of generated lambdas, choose Tools | Templates.
            }
        });
    }

    public void editRegistrarDescuento(ProductoDescuento pd) {
        try {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Agregar descuento"));
            Node node;
            FXMLLoader loader = new FXMLLoader(MantenimientoDescuentosController.this.getClass().getResource(RegistrarDescuentoProductoController.viewPath));
            node = (Node) loader.load();
            RegistrarDescuentoProductoController desc = loader.getController();
            desc.initModel(isEdit, pd, stackPane);
            content.setBody(node);
            descDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            descDialog.show();
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoDescuentosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotAgregarDescuento(MouseEvent event) {
        isEdit= Boolean.FALSE;
        editRegistrarDescuento(null);
    }

    public static class DescuentosLista extends RecursiveTreeObject<DescuentosLista> {

        IntegerProperty id;
        StringProperty fechaInicio;
        StringProperty fechaFin;
        DoubleProperty valorPct;
        StringProperty producto;
        ProductoDescuento descuentoObj;

        public DescuentosLista(ProductoDescuento descuento) {
            this.id = new SimpleIntegerProperty(descuento.getId().intValue());
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            this.fechaFin = new SimpleStringProperty(f.format(descuento.getFechaFin()));
            this.fechaInicio = new SimpleStringProperty(f.format(descuento.getFechaInicio()));
            this.valorPct = new SimpleDoubleProperty(descuento.getValorPct() * 100);
            this.producto = new SimpleStringProperty(descuento.getProducto().getNombre());
            this.descuentoObj = descuento;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof DescuentosLista) {
                DescuentosLista dl = (DescuentosLista) o;
                return dl.id.equals(this.id);

            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + Objects.hashCode(this.id);
            return hash;
        }

    }

}
