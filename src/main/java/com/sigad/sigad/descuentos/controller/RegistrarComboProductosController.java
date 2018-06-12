/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.descuentos.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.business.ComboPromocion;
import com.sigad.sigad.business.Producto;
import com.sigad.sigad.business.ProductosCombos;
import com.sigad.sigad.business.helpers.ComboPromocionHelper;
import com.sigad.sigad.business.helpers.GeneralHelper;
import com.sigad.sigad.business.helpers.ProductoHelper;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class RegistrarComboProductosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static final String viewPath = "/com/sigad/sigad/descuentos/view/registrarComboProductos.fxml";

    @FXML
    private JFXDatePicker txtFechaInicio;

    @FXML
    private JFXDatePicker txtFechaFin;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextArea txtDescripcion;

    @FXML
    private JFXTextField filtro;

    @FXML
    private JFXTextField txtPrecioBase;

    @FXML
    private JFXTreeTableView<ProductoLista> tblProductos;

    @FXML
    private JFXTextField txtPrecioReal;

    private Boolean isEdit;

    private StackPane stackpane;

    private ComboPromocion combo;

    @FXML
    JFXTreeTableColumn<ProductoLista, Integer> id = new JFXTreeTableColumn<>("ID");
    @FXML
    JFXTreeTableColumn<ProductoLista, String> nombre = new JFXTreeTableColumn<>("Nombre");
    @FXML
    JFXTreeTableColumn<ProductoLista, String> categoria = new JFXTreeTableColumn<>("Categoria");
    @FXML
    JFXTreeTableColumn<ProductoLista, String> pu = new JFXTreeTableColumn<>("Precio Unitario");
    @FXML
    JFXTreeTableColumn<ProductoLista, Integer> cantidad = new JFXTreeTableColumn<>("Cantidad");
    private final ObservableList<ProductoLista> prod = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtPrecioBase.setDisable(true);
        columnas();
        agregarColumnas();
        cargarTabla();

    }

    public void agregarFiltro() {
        filtro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblProductos.setPredicate((TreeItem<ProductoLista> t) -> {
                Boolean flag = t.getValue().nombre.getValue().contains(newValue) || t.getValue().categoria.getValue().contains(newValue);
                return flag;
            });
        });
    }

    public void columnas() {
        id.setPrefWidth(80);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, Integer> param) -> param.getValue().getValue().id.asObject());

        nombre.setPrefWidth(80);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().nombre);

        categoria.setPrefWidth(80);
        categoria.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().categoria);

        pu.setPrefWidth(80);
        pu.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().precio);

        cantidad.setPrefWidth(80);
        cantidad.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<ProductoLista, Integer> param) {
                return param.getValue().getValue().cantidad.asObject();
            }
        });
        cantidad.setCellFactory(new Callback<TreeTableColumn<ProductoLista, Integer>, TreeTableCell<ProductoLista, Integer>>() {
            @Override
            public TreeTableCell<ProductoLista, Integer> call(TreeTableColumn<ProductoLista, Integer> param) {
                return new EditingCell();
            }
        });
        cantidad.setOnEditCommit((event) -> {
            ProductoLista p = event.getRowValue().getValue();
            p.cantidad.setValue(event.getNewValue());

            Integer i = prod.indexOf(p);

            if (i >= 0) {
                p = prod.get(i);
                prod.remove(p);
                prod.add(i, p);
                calcularPrecioBase();
            }

            // Colorear la huevada
        });
    }

    public void calcularPrecioBase() {
        Double precioBase = 0.0;
        for (ProductoLista t : prod) {
            if (t.cantidad.getValue() > 0) {
                precioBase = precioBase + t.cantidad.getValue() * t.producto.getPrecio();
            }
        }
        txtPrecioBase.setText(precioBase.toString());
    }

    public void cargarTabla() {
        ProductoHelper helper = new ProductoHelper();
        prod.clear();
        ArrayList<Producto> productos = helper.getProducts();
        helper.close();
        productos.forEach((t) -> {
            prod.add(new ProductoLista(t, 0));
        });

    }

    public void agregarColumnas() {
        final TreeItem<ProductoLista> rootPedido = new RecursiveTreeItem<>(prod, RecursiveTreeObject::getChildren);
        tblProductos.setEditable(true);
        tblProductos.getColumns().setAll(id, nombre, categoria, pu, cantidad);
        tblProductos.setRoot(rootPedido);
        tblProductos.setShowRoot(false);
        tblProductos.getSortOrder().add(cantidad);
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

        });

    }

    public void cargarDatos() {
        LocalDate localDate = combo.getFechaInicio().toLocalDate();
        txtFechaInicio.setValue(localDate);
        localDate = combo.getFechaFin().toLocalDate();
        txtFechaFin.setValue(localDate);
        txtNombre.setText(combo.getNombre());

        Double precio = combo.getPreciounitario();
        Double precioreal = combo.getPreciounireal();
        txtPrecioBase.setText(precio.toString());
        txtPrecioReal.setText(GeneralHelper.roundTwoDecimals(precioreal).toString());
        txtDescripcion.setText(combo.getDescripcion());
        combo.getProductosxComboArray().forEach((t) -> {
            Integer i = prod.indexOf(new ProductoLista(t.getProducto(), Integer.SIZE));
            if (i >= 0) {
                prod.get(i).cantidad.set(t.getCantidad());
            }
        });

    }

    public void initModel(Boolean isedit, ComboPromocion cb, StackPane stackpane) {
        this.isEdit = isedit;
        this.stackpane = stackpane;
        agregarFiltro();
        this.combo = cb;
        if (isedit) {
            cargarDatos();
        } else {
            this.combo = new ComboPromocion();
        }

    }

    public void construirDescuento(ComboPromocion nuevo) {

        nuevo.setActivo(Boolean.TRUE);
        Date dateIni = Date.valueOf(txtFechaInicio.getValue());
        Date dateFin = Date.valueOf(txtFechaFin.getValue());
        nuevo.setFechaInicio(dateIni);
        nuevo.setFechaFin(dateFin);
        nuevo.setDescripcion(txtDescripcion.getText());
        nuevo.setPreciounireal(Double.valueOf(txtPrecioReal.getText()));
        nuevo.setPreciounitario(Double.valueOf(txtPrecioBase.getText()));
        nuevo.setNombre(txtNombre.getText());
        ArrayList<ProductosCombos> pc = new ArrayList<>();
        prod.forEach((t) -> {
            if (t.cantidad.getValue() > 0) {
                pc.add(new ProductosCombos(t.producto, t.cantidad.getValue(), nuevo));
            }
        });
        nuevo.getProductosxCombo().clear();
        nuevo.setProductosxCombo(new HashSet(pc));

    }

    @FXML
    void close(MouseEvent event) {
        MantenimientoDescuentosController.comboDialog.close();
    }

    @FXML
    void guardarDescuento(MouseEvent event) {
        ComboPromocionHelper helper = new ComboPromocionHelper();
        construirDescuento(combo);
        if (!isEdit) {
            combo.setNumVendidos(0);
            helper.saveCombo(combo);
        } else {
            helper.updateCombo(combo);
        }
    }

    class ProductoLista extends RecursiveTreeObject<ProductoLista> {

        private IntegerProperty id;
        private StringProperty nombre;
        private StringProperty precio;
        private StringProperty categoria;
        private IntegerProperty cantidad;
        private Producto producto;

        public ProductoLista(Producto producto, Integer cantidad) {
            this.id = new SimpleIntegerProperty(producto.getId().intValue());
            this.nombre = new SimpleStringProperty(producto.getNombre());
            this.precio = new SimpleStringProperty(producto.getPrecio().toString());
            this.categoria = new SimpleStringProperty(producto.getCategoria().getNombre());
            this.cantidad = new SimpleIntegerProperty(cantidad);
            this.producto = producto;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ProductoLista) {
                ProductoLista pl = (ProductoLista) o;
                return pl.id.equals(this.id);
            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + Objects.hashCode(this.id);
            return hash;
        }
    }

    class EditingCell extends TreeTableCell<ProductoLista, Integer> {

        private JFXTextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {
            textField = new JFXTextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        if (GeneralHelper.isNumeric(textField.getText())) {

                            commitEdit(Integer.parseInt(textField.getText()));
                        }

                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

    }

}
