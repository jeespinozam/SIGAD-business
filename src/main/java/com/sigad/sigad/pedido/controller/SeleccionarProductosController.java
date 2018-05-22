/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.pedido.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        JFXTreeTableColumn<PedidoLista, String> eliminar = new JFXTreeTableColumn<>("Eliminar");
        eliminar.setPrefWidth(80);
        eliminar.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PedidoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PedidoLista, String> param) {
                return param.getValue().getValue().nombre;
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
        cantidadPedido.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PedidoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PedidoLista, String> param) {
                return param.getValue().getValue().cantidad;
            }
        });
        cantidadPedido.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        cantidadPedido.setOnEditCommit(new EventHandler<JFXTreeTableColumn.CellEditEvent<PedidoLista, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<PedidoLista, String> event) {
                TreeItem<PedidoLista> productoEditado = treeViewPedido.getTreeItem(event.getTreeTablePosition().getRow());
                productoEditado.getValue().cantidad = new SimpleStringProperty(event.getNewValue());
            }
        });

        JFXTreeTableColumn<PedidoLista, String> precioPedido = new JFXTreeTableColumn<>("Precio");
        precioPedido.setPrefWidth(80);
        precioPedido.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PedidoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PedidoLista, String> param) {
                return param.getValue().getValue().precio;
            }
        });

        JFXTreeTableColumn<PedidoLista, String> subTotalPedido = new JFXTreeTableColumn<>("SubTotal");
        subTotalPedido.setPrefWidth(80);
        subTotalPedido.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PedidoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PedidoLista, String> param) {
                return param.getValue().getValue().subtotal;
            }
        });

        JFXTreeTableColumn<ProductoLista, Boolean> select = new JFXTreeTableColumn<>("Seleccionar");
        select.setPrefWidth(80);
        select.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<ProductoLista, Boolean> param) {
                return param.getValue().getValue().seleccion;
            }
        });
        select.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(select));
        select.setCellValueFactory(cellData -> cellData.getValue().getValue().seleccion);

        JFXTreeTableColumn<ProductoLista, Image> imagen = new JFXTreeTableColumn<>("Imagen");
        imagen.setPrefWidth(120);
        imagen.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, Image>, ObservableValue<Image>>() {
            @Override
            public ObservableValue<Image> call(TreeTableColumn.CellDataFeatures<ProductoLista, Image> param) {
                return param.getValue().getValue().imagen.imageProperty();
            }
        });
        imagen.setCellFactory(new Callback<TreeTableColumn<ProductoLista, Image>, TreeTableCell<ProductoLista, Image>>() {
            @Override
            public TreeTableCell<ProductoLista, Image> call(TreeTableColumn<ProductoLista, Image> param) {
                final ImageView imageview = new ImageView();
                imageview.setFitHeight(50);
                imageview.setFitWidth(50);

                return new TreeTableCell<ProductoLista, Image>() {
                    @Override
                    protected void updateItem(Image item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {  // choice of image is based on values from item, but it doesn't matter now
                            imageview.setImage(item);
                        } else {
                            imageview.setImage(null);
                        }
                        this.setGraphic(imageview);
                    }
                };
            }
        });

        JFXTreeTableColumn<ProductoLista, String> nombre = new JFXTreeTableColumn<>("Nombre");
        nombre.setPrefWidth(120);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoLista, String> param) -> param.getValue().getValue().nombre);

        JFXTreeTableColumn<ProductoLista, String> precio = new JFXTreeTableColumn<>("Precio");
        precio.setPrefWidth(120);
        precio.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista, String> param) {
                return param.getValue().getValue().precio;
            }
        });

        JFXTreeTableColumn<ProductoLista, String> stock = new JFXTreeTableColumn<>("Stock");
        stock.setPrefWidth(120);
        stock.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista, String> param) {
                return param.getValue().getValue().stock;
            }
        });
        JFXTreeTableColumn<ProductoLista, String> categoria = new JFXTreeTableColumn<>("Categoria");
        categoria.setPrefWidth(120);
        categoria.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista, String> param) {
                return param.getValue().getValue().categoria;
            }
        });
        JFXTreeTableColumn<ProductoLista, String> almacen = new JFXTreeTableColumn<>("Almacen");
        almacen.setPrefWidth(120);
        almacen.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista, String> param) {
                return param.getValue().getValue().almacen;
            }
        });

        JFXTreeTableColumn<ProductoLista, String> cantidad = new JFXTreeTableColumn<>("Cantidad");
        cantidad.setPrefWidth(120);
        cantidad.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductoLista, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductoLista, String> param) {
                return param.getValue().getValue().cantidad;
            }
        });
        cantidad.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        cantidad.setOnEditCommit(new EventHandler<JFXTreeTableColumn.CellEditEvent<ProductoLista, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<ProductoLista, String> event) {
                TreeItem<ProductoLista> productoEditado = treeView.getTreeItem(event.getTreeTablePosition().getRow());
                int index = pedidos.indexOf(new PedidoLista("", "", "", "", productoEditado.getValue().codigo));
                System.out.println(index);
                if (index >= 0) {
                    pedidos.get(index).cantidad = new SimpleStringProperty(event.getNewValue());
                }
                productoEditado.getValue().cantidad = new SimpleStringProperty(event.getNewValue());
            }
        });

        prod.add(new ProductoLista("Prod1", "precio", "stock", "categoria", "almacen", "cantidad", 1));
        prod.add(new ProductoLista("Prod2", "precio2", "stock2", "categoria2", "almacen2", "cantidad2", 2));
        prod.add(new ProductoLista("Prod3", "precio", "stock", "categoria", "almacen", "cantidad", 3));
        prod.add(new ProductoLista("Prod4", "precio2", "stock2", "categoria2", "almacen2", "cantidad2", 4));
        prod.add(new ProductoLista("Prod5", "precio", "stock", "categoria", "almacen", "cantidad", 5));
        prod.add(new ProductoLista("Prod6", "precio2", "stock2", "categoria2", "almacen2", "cantidad2", 6));
        prod.add(new ProductoLista("Prod7", "precio", "stock", "categoria", "almacen", "cantidad", 7));
        prod.add(new ProductoLista("Prod8", "precio2", "stock2", "categoria2", "almacen2", "cantidad2", 8));
        prod.add(new ProductoLista("Prod9", "precio", "stock", "categoria", "almacen", "cantidad", 9));
        prod.add(new ProductoLista("Prod10", "precio2", "stock2", "categoria2", "almacen2", "cantidad2", 10));

        final TreeItem<ProductoLista> root = new RecursiveTreeItem<>(prod, RecursiveTreeObject::getChildren);

        treeView.setEditable(true);
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.getColumns().setAll(select, imagen, nombre, precio, stock, categoria, almacen, cantidad);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        final TreeItem<PedidoLista> rootPedido = new RecursiveTreeItem<>(pedidos, RecursiveTreeObject::getChildren);

        treeViewPedido.setEditable(true);
        treeViewPedido.getColumns().setAll(nombrePedido, precioPedido, cantidadPedido, subTotalPedido);
        treeViewPedido.setRoot(rootPedido);
        treeViewPedido.setShowRoot(false);

        filtro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<ProductoLista>>() {
                    @Override
                    public boolean test(TreeItem<ProductoLista> t) {
                        Boolean flag = t.getValue().nombre.getValue().contains(newValue) || t.getValue().categoria.getValue().contains(newValue);
                        return flag;
                    }
                });
            }
        });
        btnContinuar.addEventHandler(EventType.ROOT, (event) -> {
            for (Iterator<PedidoLista> iterator = pedidos.iterator(); iterator.hasNext();) {

                PedidoLista next = iterator.next();
                System.out.println(next.nombre + next.cantidad.toString());
            }

//            for (Iterator<ProductoLista> iterator = prod.iterator(); iterator.hasNext();) {
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

        public ProductoLista(String nombre, String precio, String stock, String categoria, String almacen, String cantidad, Integer codigo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.precio = new SimpleStringProperty(precio);
            this.stock = new SimpleStringProperty(stock);
            this.categoria = new SimpleStringProperty(categoria);
            this.almacen = new SimpleStringProperty(almacen);
            this.cantidad = new SimpleStringProperty(cantidad);
            this.imagen = new ImageView(new Image("/images/rosa.jpg"));
            this.seleccion = new SimpleBooleanProperty(false);
            this.codigo = codigo;
            seleccion.addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
                System.out.println(oldValue);
                System.out.println(observable);
                if (newValue) {
                    pedidos.add(new PedidoLista(nombre, precio, this.cantidad.getValue() , "", codigo));
                    //prod.remove(this);
                } else {
                    pedidos.remove(new PedidoLista(nombre, precio, this.cantidad.getValue() , "", codigo));
                }
            });
        }

//        @Override
//        public boolean equals(Object o) {
//            if (o instanceof PedidoLista) {
//                PedidoLista pl = (PedidoLista) o;
//                return pl.nombre.toString().equals(nombre.toString())
//                        && pl.precio.toString().equals(precio.toString());
//            }
//            return super.equals(o); //To change body of generated methods, choose Tools | Templates.
//        }
//        
//        @Override
//        public int hashCode() {
//            return Objects.hash(nombre.toString(), precio.toString());
//        }
    }

    class PedidoLista extends RecursiveTreeObject<PedidoLista> {

        StringProperty nombre;
        StringProperty precio;
        StringProperty cantidad;
        StringProperty subtotal;
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
            hash = 79 * hash + Objects.hashCode(this.codigo);
            return hash;
        }

    }
}
