/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.descuentos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.business.Producto;
import com.sigad.sigad.business.ProductoDescuento;
import com.sigad.sigad.business.helpers.GeneralHelper;
import com.sigad.sigad.business.helpers.ProductoDescuentoHelper;
import com.sigad.sigad.business.helpers.ProductoHelper;
import static com.sigad.sigad.descuentos.controller.MantenimientoDescuentosController.descuentos;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class RegistrarDescuentoProductoController implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/descuentos/view/registrarDescuentoProducto.fxml";
    /**
     * Initializes the controller class.
     */
    StackPane stackpane;

    @FXML
    private JFXDatePicker txtFechaInicio;

    @FXML
    private JFXDatePicker txtFechaFin;

    @FXML
    private JFXTreeTableView<ProductoLista> tblProductos;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblNuevoPrecio;

    @FXML
    private JFXTextField txtDescuentopct;

    @FXML
    private JFXCheckBox checkboxStock;

    @FXML
    private JFXTextField filtro;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXTextField txtStockMaximo;

    private Boolean isEdit;
    private ProductoDescuento pd;
    @FXML
    JFXTreeTableColumn<ProductoLista, Integer> id = new JFXTreeTableColumn<>("ID");
    @FXML
    JFXTreeTableColumn<ProductoLista, String> nombre = new JFXTreeTableColumn<>("Nombre");
    @FXML
    JFXTreeTableColumn<ProductoLista, String> categoria = new JFXTreeTableColumn<>("Categoria");

    private final ObservableList<ProductoLista> prod = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        columnas();
        agregarColumnas();
        seleccionStock();
        cargarDatos();
    }

    public void agregarFiltro() {
        filtro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblProductos.setPredicate((TreeItem<ProductoLista> t) -> {
                Boolean flag = t.getValue().nombre.getValue().contains(newValue) || t.getValue().categoria.getValue().contains(newValue);
                return flag;
            });
        });
    }

    public void initModel(Boolean isedit, ProductoDescuento pd, StackPane stackpane) {

        this.isEdit = isedit;
        this.stackpane = stackpane;
        checkboxStock.setSelected(false);
        agregarFiltro();
        this.pd = pd;
        if (isedit) {
            filtro.setText(pd.getProducto().getNombre());
            Instant instant = pd.getFechaInicio().toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            txtFechaInicio.setValue(localDate);
            instant = pd.getFechaFin().toInstant();
            localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            txtFechaFin.setValue(localDate);
            Double pct = pd.getValorPct() * 100;
            txtDescuentopct.setText(pct.toString());
            if (pd.getStockMaximo() != null) {
                checkboxStock.setSelected(true);
                txtStockMaximo.setText(pd.getStockMaximo().toString());
            }

        }
    }

    public void reloadTable() {
        descuentos.clear();
        ProductoDescuentoHelper pdhelper = new ProductoDescuentoHelper();
        ArrayList<ProductoDescuento> ps = pdhelper.getDescuentos();
        pdhelper.close();
        ps.forEach((t) -> {
            descuentos.add(new MantenimientoDescuentosController.DescuentosLista(t));
        });

    }

    public void cargarDatos() {
        ProductoHelper helper = new ProductoHelper();
        ArrayList<Producto> productos = helper.getProducts();
        helper.close();
        productos.forEach((t) -> {
            prod.add(new ProductoLista(t));
        });
    }

    public void agregarColumnas() {
        final TreeItem<ProductoLista> rootPedido = new RecursiveTreeItem<>(prod, RecursiveTreeObject::getChildren);
        tblProductos.setEditable(true);
        tblProductos.getColumns().setAll(id, nombre, categoria);
        tblProductos.setRoot(rootPedido);
        tblProductos.setShowRoot(false);
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblPrecio.setText(newSelection.getValue().precio.getValue());
            }
        });

    }

    public Boolean validarCampos() {
        return txtFechaFin.getValue().toString() != "" && txtFechaFin.getValue().toString() != "" && txtDescuentopct.getText() != ""
                && (checkboxStock.isSelected()) ? txtStockMaximo.getText() != "" : true;

    }

    public void construirDescuento(ProductoDescuento nuevo) {

        nuevo.setActivo(Boolean.TRUE);
        Date dateIni = Date.valueOf(txtFechaInicio.getValue());
        Date dateFin = Date.valueOf(txtFechaFin.getValue());
        nuevo.setFechaInicio(dateIni);
        nuevo.setFechaFin(dateFin);
        nuevo.setValorPct(Double.valueOf(txtDescuentopct.getText()) / 100.0);
        nuevo.setProducto(tblProductos.getSelectionModel().getSelectedItem().getValue().producto);
        nuevo.setStockMaximo((checkboxStock.isSelected()) ? Integer.valueOf(txtStockMaximo.getText()) : null);
    }

    @FXML
    void guardarDescuento(ActionEvent event) {
        Integer cant = tblProductos.getSelectionModel().getSelectedItems().size();
        ProductoDescuentoHelper helper = new ProductoDescuentoHelper();
        if (validarCampos() && cant == 1 && !isEdit) {
            ProductoDescuento nuevo = new ProductoDescuento();
            nuevo.setActivo(Boolean.TRUE);
            construirDescuento(nuevo);

            helper.saveDescuento(nuevo);
        } else if (validarCampos() && cant == 1 && isEdit) {
            construirDescuento(pd);
            helper.updateDescuento(pd);
        }
        reloadTable();
        MantenimientoDescuentosController.descDialog.close();
    }

    @FXML
    void close(MouseEvent event) {
        MantenimientoDescuentosController.descDialog.close();
    }

    public void seleccionStock() {
        
        checkboxStock.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (checkboxStock.isSelected()) {
                    txtStockMaximo.setEditable(true);
                } else {
                    txtStockMaximo.setText("");
                    txtStockMaximo.setEditable(false);

                }
            }
        });
        checkboxStock.setSelected(false);

    }

    @FXML
    void calcularNuevoPrecio(MouseEvent event) {
        if (GeneralHelper.isNumeric(txtDescuentopct.getText())) {
            Double pct = Double.valueOf(txtDescuentopct.getText()) / 100;
            Double nprecio = (1.0 - pct) * Double.valueOf(lblPrecio.getText());
            lblNuevoPrecio.setText(GeneralHelper.roundTwoDecimals(nprecio).toString());
        } else {
            txtDescuentopct.setText("");
        }

    }

    public void columnas() {
        id.setPrefWidth(80);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, Integer> param) -> param.getValue().getValue().id.asObject());

        nombre.setPrefWidth(80);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().nombre);

        categoria.setPrefWidth(80);
        categoria.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().categoria);
    }

    class ProductoLista extends RecursiveTreeObject<ProductoLista> {

        private IntegerProperty id;
        private StringProperty nombre;
        private StringProperty precio;
        private StringProperty categoria;
        private Producto producto;

        public ProductoLista(Producto producto) {
            this.id = new SimpleIntegerProperty(producto.getId().intValue());
            this.nombre = new SimpleStringProperty(producto.getNombre());
            this.precio = new SimpleStringProperty(producto.getPrecio().toString());
            this.categoria = new SimpleStringProperty(producto.getCategoria().getNombre());
            this.producto = producto;
        }
    }

}
