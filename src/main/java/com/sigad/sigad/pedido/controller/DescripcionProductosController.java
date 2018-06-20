/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.business.ClienteDescuento;
import com.sigad.sigad.business.ComboPromocion;
import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.Producto;
import com.sigad.sigad.business.ProductoCategoriaDescuento;
import com.sigad.sigad.business.ProductoDescuento;
import com.sigad.sigad.business.ProductoInsumo;
import com.sigad.sigad.business.ProductosCombos;
import com.sigad.sigad.business.helpers.ComboPromocionHelper;
import com.sigad.sigad.business.helpers.GeneralHelper;
import com.sigad.sigad.business.helpers.ProductoCategoriaDescuentoHelper;
import com.sigad.sigad.business.helpers.ProductoDescuentoHelper;
import com.sigad.sigad.business.helpers.ProductoHelper;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class DescripcionProductosController implements Initializable {

    public static final String viewPath = "/com/sigad/sigad/pedido/view/descripcionProductos.fxml";
    /**
     * Initializes the controller class.
     */
    private final String TIPO_DESCPROD = "PROD";
    private final String TIPO_DESCCAT = "CAT";
    private final String TIPO_COMBO = "COMBO";

    private Integer idProducto;
    private Producto producto;

    private ComboPromocion combo;

    @FXML
    private ImageView imageProducto;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtPrecioBase;

    @FXML
    private JFXTextField txtCategoria;

    @FXML
    private JFXTextField txtFragilidad;

    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private Label lblProductosInsumos;

    @FXML
    private Label lblPromociones;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTreeTableView<PromocionesLista> tablaPromociones;
    private ObservableList<PromocionesLista> promociones = FXCollections.observableArrayList();
    JFXTreeTableColumn<PromocionesLista, String> promocion = new JFXTreeTableColumn<>("Promocion");
    JFXTreeTableColumn<PromocionesLista, String> tipo = new JFXTreeTableColumn<>("Tipo");
    JFXTreeTableColumn<PromocionesLista, String> descuento = new JFXTreeTableColumn<>("Descuento");

    @FXML
    private JFXTreeTableView<InsumosLista> tablaInsumos;
    private ObservableList<InsumosLista> insumos = FXCollections.observableArrayList();
    JFXTreeTableColumn<InsumosLista, String> nombre = new JFXTreeTableColumn<>("Nombre");
    JFXTreeTableColumn<InsumosLista, String> cantidad = new JFXTreeTableColumn<>("Cantidad");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        columnasPromociones();
        columnasInsumos();
        agregarColumnasTablasInsumos();
        agregarColumnasTablasPromociones();
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void initModel(Integer idProducto) {
        //db
        this.idProducto = idProducto;
        ProductoHelper helper = new ProductoHelper();
        producto = helper.getProductById(idProducto);
        
        if (producto != null) {
            txtNombre.setText((producto.getNombre() != null) ? producto.getNombre() : "");
            txtPrecioBase.setText((producto.getPrecio() != null) ? producto.getPrecio().toString() : "");
            txtCategoria.setText((producto.getCategoria() != null && producto.getCategoria().getNombre() != null) ? producto.getCategoria().getNombre() : "");
            txtDescripcion.setText((producto.getDescripcion() != null) ? producto.getDescripcion() : "");
            txtFragilidad.setText((producto.getFragilidad() != null) ? producto.getFragilidad().getDescripcion() : "");
            try {
                Image image = new Image(producto.getImagen());
                imageProducto.setImage(image);
            } catch (Exception e) {
                Image image = new Image(GeneralHelper.defaultImage);
                imageProducto.setImage(image);
            }

        }
        ArrayList<ProductoInsumo> pd = new ArrayList((producto.getProductoxInsumos() != null) ? producto.getProductoxInsumos() : new ArrayList<>());
        pd.forEach((t) -> {
            insumos.add(new InsumosLista(t.getInsumo(), t.getCantidad().intValue()));
        });
        llenarTablaDescuento();

    }

    public void initModel(ComboPromocion combo) {
        //db
        ComboPromocionHelper ch = new ComboPromocionHelper();
        this.combo = ch.getComboById(combo.getId().intValue());

        if (combo != null) {
            lblProductosInsumos.setText("Productos");
            txtNombre.setText((combo.getNombre() != null) ? combo.getNombre() : "");
            txtPrecioBase.setText((combo.getPreciounireal() != null) ? combo.getPreciounireal().toString() : "");
            txtCategoria.setText("Combo");
            txtDescripcion.setText((combo.getDescripcion() != null) ? combo.getDescripcion() : "");
            lblPromociones.setText("");
            anchorPane.getChildren().remove(tablaPromociones);
            try {
                Image image = new Image(combo.getImagen());
                imageProducto.setImage(image);
            } catch (Exception e) {
                Image image = new Image(GeneralHelper.defaultImage);
                imageProducto.setImage(image);
            }

        }
        ArrayList<ProductosCombos> pd = new ArrayList((this.combo.getProductosxComboArray() != null) ? this.combo.getProductosxComboArray() : new ArrayList<>());
        pd.forEach((t) -> {
            insumos.add(new InsumosLista(t.getProducto(), t.getCantidad()));
        });
    }

    @FXML
    void clickCerrar(MouseEvent event) {
        SeleccionarProductosController.userDialog.close();
    }

    public void llenarTablaDescuento() {
        ProductoDescuentoHelper helper = new ProductoDescuentoHelper();

        List<ProductoDescuento> descuentos = helper.getDescuentosByProducto(idProducto);
        if (descuentos != null) {
            descuentos.forEach((t) -> {
                if (t.getFechaInicio().before(new Date()) && t.getFechaFin().after(new Date())) {
                    promociones.add(new PromocionesLista(t));
                }

            });
        }
        helper.close();
        ProductoCategoriaDescuentoHelper helperCat = new ProductoCategoriaDescuentoHelper();
        List<ProductoCategoriaDescuento> catdescuentos = helperCat.getDescuentosByCategoria(producto.getCategoria().getId().intValue());
        if (catdescuentos != null) {
            System.out.println("--->l" + catdescuentos.size());
            catdescuentos.forEach((t) -> {
                if (t.getFechaInicio().before(new Date()) && t.getFechaFin().after(new Date())) {
                    promociones.add(new PromocionesLista(t));
                }

            });
        }
        helperCat.close();
        ComboPromocionHelper cmbHelper = new ComboPromocionHelper();
        List<ProductosCombos> combos = cmbHelper.getCombosByProducto(idProducto);
        if (combos != null) {
            combos.forEach((t) -> {
                if (t.getCombopromocion().getFechaInicio().before(new Date()) && t.getCombopromocion().getFechaFin().after(new Date())) {
                    promociones.add(new PromocionesLista(t.getCombopromocion()));
                }

            });
        }
        cmbHelper.close();

    }

    public void columnasPromociones() {
        promocion.setPrefWidth(120);
        promocion.setCellValueFactory((TreeTableColumn.CellDataFeatures<PromocionesLista, String> param) -> param.getValue().getValue().promocion);
        tipo.setPrefWidth(120);
        tipo.setCellValueFactory((TreeTableColumn.CellDataFeatures<PromocionesLista, String> param) -> param.getValue().getValue().tipo);
        descuento.setPrefWidth(120);
        descuento.setCellValueFactory((TreeTableColumn.CellDataFeatures<PromocionesLista, String> param) -> param.getValue().getValue().descuento);

    }

    public void columnasInsumos() {
        nombre.setPrefWidth(120);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<InsumosLista, String> param) -> param.getValue().getValue().nombre);
        cantidad.setPrefWidth(120);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<InsumosLista, String> param) -> param.getValue().getValue().cantidad);

    }

    public void agregarColumnasTablasInsumos() {
        final TreeItem<InsumosLista> rootPedido = new RecursiveTreeItem<>(insumos, RecursiveTreeObject::getChildren);
        tablaInsumos.setEditable(true);
        tablaInsumos.getColumns().setAll(nombre, cantidad);
        tablaInsumos.setRoot(rootPedido);
        tablaInsumos.setShowRoot(false);
    }

    public void agregarColumnasTablasPromociones() {
        final TreeItem<PromocionesLista> rootPedido = new RecursiveTreeItem<>(promociones, RecursiveTreeObject::getChildren);
        tablaPromociones.setEditable(true);
        tablaPromociones.getColumns().setAll(promocion, tipo, descuento);
        tablaPromociones.setRoot(rootPedido);
        tablaPromociones.setShowRoot(false);
    }

    public String getPct(Double pct) {
        if (pct != null) {
            Double p = GeneralHelper.roundTwoDecimals(pct * 100);
            return p.toString() + " %";
        } else {
            return "";
        }
    }

    class PromocionesLista extends RecursiveTreeObject<PromocionesLista> {

        StringProperty promocion;
        StringProperty tipo;
        StringProperty descuento;
        StringProperty descripcion;
        Integer codigo;
        ProductoDescuento descuentoProd;
        ClienteDescuento descuentoCliente;
        ProductoCategoriaDescuento descuentoCategoria;

        public PromocionesLista(String promocion, String tipo, String descuento, String descripcion, Integer codigo) {
            this.promocion = new SimpleStringProperty(promocion);
            this.codigo = codigo;
            this.tipo = new SimpleStringProperty(tipo);
            this.descuento = new SimpleStringProperty(descuento);
        }

        public PromocionesLista(ProductoDescuento descuentos) {
            this.codigo = descuentos.getId().intValue();
            this.descuento = new SimpleStringProperty(getPct(descuentos.getValorPct()));
            this.promocion = new SimpleStringProperty(TIPO_DESCPROD + codigo);
            this.tipo = new SimpleStringProperty(TIPO_DESCPROD);
        }

        public PromocionesLista(ProductoCategoriaDescuento descuentos) {
            this.codigo = descuentos.getId().intValue();
            this.descuento = new SimpleStringProperty(getPct(descuentos.getValue()));
            this.promocion = new SimpleStringProperty(TIPO_DESCCAT + codigo);
            this.tipo = new SimpleStringProperty(TIPO_DESCCAT);
        }

        public PromocionesLista(ComboPromocion combo) {
            this.codigo = combo.getId().intValue();
            this.descuento = new SimpleStringProperty(combo.getPreciounireal().toString());
            this.promocion = new SimpleStringProperty(combo.getNombre());
            this.tipo = new SimpleStringProperty(TIPO_COMBO);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof PromocionesLista) {
                PromocionesLista pl = (PromocionesLista) o;
                return pl.codigo.equals(codigo);
            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + Objects.hashCode(this.codigo);
            return hash;
        }

    }

    class InsumosLista extends RecursiveTreeObject<InsumosLista> {

        StringProperty nombre;
        StringProperty cantidad;
        Insumo insumo;
        Producto producto;

        public InsumosLista(Insumo insumo, Integer cantidad) {
            this.nombre = new SimpleStringProperty(insumo.getNombre());
            this.cantidad = new SimpleStringProperty(cantidad.toString());
            this.insumo = insumo;
            this.producto = null;
        }

        public InsumosLista(Producto producto, Integer cantidad) {
            this.nombre = new SimpleStringProperty(producto.getNombre());
            this.cantidad = new SimpleStringProperty(cantidad.toString());
            this.insumo = null;
            this.producto = producto;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof InsumosLista) {
                InsumosLista pl = (InsumosLista) o;
                if (pl.insumo != null) {
                    return pl.insumo.equals(insumo);
                } else {
                    return pl.producto.equals(producto);
                }

            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            int hash = 3;
            if (insumo != null) {
                hash = 23 * hash + Objects.hashCode(this.insumo);
            } else {
                hash = 23 * hash + Objects.hashCode(this.producto);
            }
            return hash;
        }

    }
}
