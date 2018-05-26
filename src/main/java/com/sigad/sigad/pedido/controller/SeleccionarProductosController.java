/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sigad.sigad.business.Producto;
import com.sigad.sigad.pedido.helper.ProductoHelper;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class SeleccionarProductosController implements Initializable {
    
    public static final String viewPath = "/com/sigad/sigad/pedido/view/seleccionarProductos.fxml";
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<ProductoLista> treeView;
    @FXML
    private JFXTreeTableView<PedidoLista> treeViewPedido;
    @FXML
    private JFXTextField filtro;
    @FXML
    private JFXButton btnContinuar;
    private final ObservableList<PedidoLista> pedidos = FXCollections.observableArrayList();
    private final ObservableList<ProductoLista> prod = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXTreeTableColumn<PedidoLista, Boolean> eliminar = new JFXTreeTableColumn<>("Eliminar");
        eliminar.setPrefWidth(80);
        eliminar.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<PedidoLista, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<PedidoLista, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        eliminar.setCellFactory(new Callback<TreeTableColumn<PedidoLista, Boolean>, TreeTableCell<PedidoLista, Boolean>>() {
            @Override
            public TreeTableCell<PedidoLista, Boolean> call(TreeTableColumn<PedidoLista, Boolean> p) {
                return new ButtonCell();
            }

        });
//        eliminar.setCellFactory(new Callback<TreeTableColumn<PedidoLista, Image>, TreeTableCell<PedidoLista, Image>>() {
//            @Override
//            public TreeTableCell<PedidoLista, Image> call(TreeTableColumn<PedidoLista, Image> param) {
//                final ImageView imageview = new ImageView();
//                imageview.setFitHeight(50);
//                imageview.setFitWidth(50);
//
//                return new TreeTableCell<PedidoLista, Image>() {
//                    @Override
//                    protected void updateItem(Image item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (item != null) {  // choice of image is based on values from item, but it doesn't matter now
//                            imageview.setImage(item);
//                        } else {
//                            imageview.setImage(null);
//                        }
//                        this.setGraphic(imageview);
//                    }
//                };
//            }
//        });

        JFXTreeTableColumn<PedidoLista, String> nombrePedido = new JFXTreeTableColumn<>("Nombre");
        nombrePedido.setPrefWidth(80);
        nombrePedido.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PedidoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PedidoLista, String> param) {
                return param.getValue().getValue().nombre;
            }
        });

        JFXTreeTableColumn<PedidoLista, String> cantidadPedido = new JFXTreeTableColumn<>("Cantidad");
        cantidadPedido.setPrefWidth(80);
        cantidadPedido.setCellValueFactory((TreeTableColumn.CellDataFeatures<PedidoLista, String> param) -> param.getValue().getValue().cantidad);
        cantidadPedido.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        cantidadPedido.setOnEditCommit((TreeTableColumn.CellEditEvent<PedidoLista, String> event) -> {
            TreeItem<PedidoLista> productoEditado = treeViewPedido.getTreeItem(event.getTreeTablePosition().getRow());
            if (isNumeric(event.getNewValue())) {
                Double subtotal = Double.valueOf(event.getNewValue()) * Double.valueOf(productoEditado.getValue().precio.getValue());
                productoEditado.getValue().subtotal = new SimpleStringProperty(subtotal.toString());
                productoEditado.getValue().cantidad = new SimpleStringProperty(event.getNewValue());
            } else {

            }

        });

        JFXTreeTableColumn<PedidoLista, String> precioPedido = new JFXTreeTableColumn<>("Precio");
        precioPedido.setPrefWidth(80);
        precioPedido.setCellValueFactory((TreeTableColumn.CellDataFeatures<PedidoLista, String> param) -> param.getValue().getValue().precio);

        JFXTreeTableColumn<PedidoLista, String> subTotalPedido = new JFXTreeTableColumn<>("SubTotal");
        subTotalPedido.setPrefWidth(80);
        subTotalPedido.setCellValueFactory((TreeTableColumn.CellDataFeatures<PedidoLista, String> param) -> param.getValue().getValue().subtotal);
        
        JFXTreeTableColumn<PedidoLista, String> descuentos = new JFXTreeTableColumn<>("Descuentos");
        subTotalPedido.setPrefWidth(80);
        subTotalPedido.setCellValueFactory((TreeTableColumn.CellDataFeatures<PedidoLista, String> param) -> param.getValue().getValue().descuento);


        JFXTreeTableColumn<ProductoLista, Boolean> select = new JFXTreeTableColumn<>("Seleccionar");
        select.setPrefWidth(80);
        select.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, Boolean> param) -> param.getValue().getValue().seleccion);
        //select.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(select));
        select.setCellFactory((TreeTableColumn<ProductoLista, Boolean> p) -> {
            CheckBoxTreeTableCell<ProductoLista, Boolean> cell = new CheckBoxTreeTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        JFXTreeTableColumn<ProductoLista, Image> imagen = new JFXTreeTableColumn<>("Imagen");
        imagen.setPrefWidth(120);
        imagen.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, Image> param) -> param.getValue().getValue().imagen.imageProperty());
        imagen.setCellFactory((TreeTableColumn<ProductoLista, Image> param) -> {
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(50);
            return new TreeTableCell<ProductoLista, Image>() {
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setAlignment(Pos.CENTER);
                    if (item != null) {  // choice of image is based on values from item, but it doesn't matter now
                        imageview.setImage(item);
                    } else {
                        imageview.setImage(null);
                    }
                    this.setGraphic(imageview);
                }
            };
        });

        JFXTreeTableColumn<ProductoLista, String> nombre = new JFXTreeTableColumn<>("Nombre");
        nombre.setPrefWidth(120);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().nombre);

        JFXTreeTableColumn<ProductoLista, String> precio = new JFXTreeTableColumn<>("Precio");
        precio.setPrefWidth(120);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().precio);

        JFXTreeTableColumn<ProductoLista, String> stock = new JFXTreeTableColumn<>("Stock");
        stock.setPrefWidth(120);
        stock.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().stock);
        JFXTreeTableColumn<ProductoLista, String> categoria = new JFXTreeTableColumn<>("Categoria");
        categoria.setPrefWidth(120);
        categoria.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().categoria);
        JFXTreeTableColumn<ProductoLista, String> almacen = new JFXTreeTableColumn<>("Almacen");
        almacen.setPrefWidth(120);
        almacen.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().almacen);

        //Basede datos
        ProductoHelper gest = new ProductoHelper();
        ArrayList<Producto> productosDB = gest.getProducts();
        productosDB.forEach((p) -> {
            Producto t = (Producto) p;
            System.out.println(t.getPrecio());
            prod.add(new ProductoLista(t.getNombre(), t.getPrecio().toString(), Integer.toString(t.getStock()), "", "", t.getImagen(), t.getId().intValue()));
        });
        gest.close();

        final TreeItem<ProductoLista> root = new RecursiveTreeItem<>(prod, RecursiveTreeObject::getChildren);

        treeView.setEditable(true);
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.getColumns().setAll(select, imagen, nombre, precio, stock, categoria, almacen);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        final TreeItem<PedidoLista> rootPedido = new RecursiveTreeItem<>(pedidos, RecursiveTreeObject::getChildren);

        treeViewPedido.setEditable(true);
        treeViewPedido.getColumns().setAll(nombrePedido, precioPedido, cantidadPedido, descuentos, subTotalPedido, eliminar);
        treeViewPedido.setRoot(rootPedido);
        treeViewPedido.setShowRoot(false);

        filtro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            treeView.setPredicate((TreeItem<ProductoLista> t) -> {
                Boolean flag = t.getValue().nombre.getValue().contains(newValue) || t.getValue().categoria.getValue().contains(newValue);
                return flag;
            });
        });
        btnContinuar.addEventHandler(EventType.ROOT, (event) -> {

            pedidos.forEach((next) -> {
                System.out.println(next.nombre + next.cantidad.toString());
            }); //            for (Iterator<ProductoLista> iterator = prod.iterator(); iterator.hasNext();) {
//
//                ProductoLista next = iterator.next();
//                System.out.println(next.nombre + next.seleccion.toString());
//            }
        });
        //Para seleccionar 
//        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                tableview2.getSelectionModel().clearSelection();
//            }
//        });

    }

    public boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // s is not numeric
            return false;
        }
    }

    class ProductoLista extends RecursiveTreeObject<ProductoLista> {

        ImageView imagen;
        Integer codigo;
        BooleanProperty seleccion;
        StringProperty nombre;
        StringProperty precio;
        StringProperty stock;
        StringProperty categoria;
        StringProperty almacen;
        StringProperty cantidad;

        public ProductoLista(String nombre, String precio, String stock, String categoria, String almacen, String pathImagen, Integer codigo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.precio = new SimpleStringProperty(precio);
            this.stock = new SimpleStringProperty(stock);
            this.categoria = new SimpleStringProperty(categoria);
            this.almacen = new SimpleStringProperty(almacen);
            this.imagen = new ImageView(new Image(pathImagen));
            this.seleccion = new SimpleBooleanProperty(false);
            this.codigo = codigo;
            seleccion.addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
                System.out.println(oldValue);
                System.out.println(observable);
                if (newValue) {
                    pedidos.add(new PedidoLista(nombre, precio, "", "", codigo));
                    //prod.remove(this);
                } else {
                    pedidos.remove(new PedidoLista(nombre, precio, "", "", codigo));
                }
            });
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ProductoLista) {
                ProductoLista pl = (ProductoLista) o;
                return pl.codigo.equals(codigo);
            }
            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigo, codigo.toString());
        }
    }

    class PedidoLista extends RecursiveTreeObject<PedidoLista> {

        StringProperty nombre;
        StringProperty precio;
        StringProperty cantidad;
        StringProperty subtotal;
        StringProperty descuento;
        Integer codigo;

        public PedidoLista(String nombre, String precio, String cantidad, String subtotal, Integer codigo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.precio = new SimpleStringProperty(precio);
            this.cantidad = new SimpleStringProperty(cantidad);
            this.subtotal = new SimpleStringProperty(subtotal);
            this.codigo = codigo;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof PedidoLista) {
                PedidoLista pl = (PedidoLista) o;
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

    private class ButtonCell extends TreeTableCell<PedidoLista, Boolean> {

        final JFXButton cellButton = new JFXButton("Delete");

        ButtonCell() {

            //Action when the button is pressed
            cellButton.setButtonType(JFXButton.ButtonType.RAISED);
            cellButton.setBorder(Border.EMPTY);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    PedidoLista current = (PedidoLista) ButtonCell.this.getTreeTableRow().getItem();
                    //remove selected item from the table list
                    prod.get(prod.indexOf(new ProductoLista("", "", viewPath, viewPath, viewPath, viewPath, current.codigo))).seleccion.setValue(Boolean.FALSE);
                    pedidos.remove(current);
                }
            });
        }
        //Display button if the row is not empty

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
}
