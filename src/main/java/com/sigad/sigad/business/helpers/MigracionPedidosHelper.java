/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.google.maps.errors.ApiException;
import com.sigad.sigad.business.Constantes;
import com.sigad.sigad.business.DetallePedido;
import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.Pedido;
import com.sigad.sigad.business.PedidoEstado;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Producto;
import com.sigad.sigad.business.ProductoCategoriaDescuento;
import com.sigad.sigad.business.ProductoDescuento;
import com.sigad.sigad.business.ProductoInsumo;
import com.sigad.sigad.business.Tienda;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.deposito.helper.PedidoEstadoHelper;
import com.sigad.sigad.pedido.controller.SolicitarDireccionController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author Alexandra
 */
public class MigracionPedidosHelper {

    private ArrayList<Pedido> pedidos;
    private Integer cantidadDetalles;
    private Integer cantidadPedidos;
    private Random rand;
    private ArrayList<Producto> productos;
    private ArrayList<Usuario> clientes;
    private ArrayList<String> direcciones = new ArrayList<>();
    String[][] dir = {{"Av. Tingo Maria 840, Lima", "Avenida Naciones Unidas 1721, Lima", "Av. Arica 1391, Breña", "Av. la Alborada 1327, Lima", "Av. Rep. de Venezuela 2291"},
    {"Av de los Precursores 1031, Lima", "Av. Rafael Escardó 545", "Av. Rafael Escardó 414, San Miguel", "Av. de los Patriotas 323, San Miguel", "Av. de la Marina 2563, San Miguel"},
    {"Av. Angelica Gamarra 1902", "Av. Universitaria 201, San Martín de Porres", "Av. Tomas Valle 1530, Los Olivos", "Av. German Aguirre, San German 569", "Av. German Aguirre 640"},
    {"Av. Universitaria 1851", "Avenida Manuel Cipriano Dulanto 1898, Pueblo Libre", "Av. La Mar 2382, San Miguel", "Calle Mantaro 356, San Miguel", "Av. La Mal 2342, San Miguel"},
    {"Av. Sucre 593, Lima", "Jr. Castilla 842, San Miguel", "Av. Miguel Grau 853, Callao", "Av Saenz Peña 603, Callao", "Av. Miguel Grau 819, Callao"}};

    public MigracionPedidosHelper() {
        //crear
        pedidos = new ArrayList<>();
        cantidadPedidos = 5;
        cantidadDetalles = 5;
        rand = new Random();
        ProductoHelper helper = new ProductoHelper();
        productos = helper.getProducts();
        helper.close();

    }

    public void direcciones() {
        //obtenerPerfiles
        //-12.054172, -77.056751
    }

   

    public void crearPedidos() {
        TiendaHelper helper = new TiendaHelper();
        ArrayList<Tienda> tiendas = helper.getStores();
        Integer z = 0;
        for (Tienda tienda : tiendas) {
            HashMap<Insumo, Integer> insumosTienda = tienda.getInsumos();
            HashMap<Producto, Integer> maxStockProductos = new HashMap<>();
            ArrayList<Producto> prod = new ArrayList<>(); //Para sacar random productos con stock
            productos.forEach((t) -> {
                Integer s = mostrarMaximoStockProducto(t, insumosTienda);
                if (s > 0) {
                    maxStockProductos.put(t, s);
                    prod.add(t);
                }

            });
            String[] dirUsuario = dir[z];
            Integer r = 0;
            for (int i = 0; i < cantidadPedidos; i++) {
                if (prod.isEmpty()) {
                    break;
                }
                ArrayList<Producto> disponibles = new ArrayList(prod);
                Usuario cliente = clientes.get(i);
                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setDireccionDeEnvio(dirUsuario[r]);
                pedido.setTienda(tienda);
                pedido.setMensajeDescripicion("Mensaje");
                PedidoEstadoHelper hp = new PedidoEstadoHelper();
                PedidoEstado estado = hp.getEstadoByName(Constantes.ESTADO_VENTA);
                pedido.addEstado(estado);
                pedido.setEstado(estado);
                r = r + 1;
                Set<DetallePedido> detalles = new HashSet<>();
                int n = rand.nextInt(cantidadDetalles) + 1;
                for (int j = 0; j < n; j++) {
                    if (disponibles.isEmpty()) {
                        break;
                    }
                    int indexProd = rand.nextInt(prod.size() - 1) + 1;
                    Producto producto = disponibles.get(indexProd);
                    DetallePedido detalle = new DetallePedido();
                    detalle.setProducto(producto);
                    detalle.setActivo(true);
                    detalle.setPrecioUnitario(producto.getPrecio());
                    int cant = rand.nextInt(5) + 1;
                    if (cant > maxStockProductos.get(producto)) {
                        cant = maxStockProductos.get(producto);
                    }
                    if (cant == 0) {
                        disponibles.remove(producto);
                        prod.remove(producto);//Si ya no hay posibilida de armar el producto en esta tienda se retira
                        continue;
                    }
                    detalle.setCantidad(cant);
                    detalle.setNumEntregados(0);

                    //Verifica si tiene descuentos
                    ProductoDescuentoHelper helperdescprod = new ProductoDescuentoHelper();
                    ProductoDescuento descuento = helperdescprod.getDescuentoByProducto(producto.getId().intValue());
                    helper.close();
                    ProductoCategoriaDescuentoHelper hcar = new ProductoCategoriaDescuentoHelper();
                    ProductoCategoriaDescuento descCat = hcar.getDescuentoByCategoria(producto.getCategoria().getId().intValue());
                    hcar.close();
                    if (descuento != null && descCat != null) {
                        if (descuento.getValorPct() > descCat.getValue()) {
                            detalle.setDescuentoProducto(descuento);
                            detalle.setDescuentoCategoria(null);
                        } else {
                            detalle.setDescuentoCategoria(descCat);
                            detalle.setDescuentoProducto(null);
                        }

                    } else if (descuento != null) {
                        detalle.setDescuentoProducto(descuento);
                        detalle.setDescuentoCategoria(null);
                    } else if (descCat != null) {
                        detalle.setDescuentoCategoria(descCat);
                        detalle.setDescuentoProducto(null);
                    } else {
                        detalle.setDescuentoProducto(null);
                        detalle.setDescuentoCategoria(null);
                    }
                    detalle.setCombo(null);
                    detalles.add(detalle);
                    disponibles.remove(producto);
                    recalcularStockProducto(producto, cant, 0, insumosTienda);
                }
                if (detalles.size() > 0) {
                    pedido.setDetallePedido(detalles);
                    HashMap<Insumo, Integer> insumosRequeridos = new HashMap<>();
                    for (DetallePedido dp : pedido.getDetallePedido()) {
                        if (dp.getProducto() != null) {
                            calcularInsumos(dp.getProducto(), dp.getCantidad(), insumosRequeridos);
                        }
                    }

                    LoteInsumoHelper lihelper = new LoteInsumoHelper();
                    Boolean ok = lihelper.descontarInsumos(insumosRequeridos, pedido.getTienda(), pedido);
                    pedidos.add(pedido);
                }

            }
            z = z + 1;
        }
        setDirecciones();

    }

    void setDirecciones() {
        pedidos.forEach((t) -> {
            try {
                GMapsHelper helper = GMapsHelper.getInstance();
                Pair<Double, Double> pair = helper.geocodeAddress(t.getDireccionDeEnvio());
                t.setCooXDireccion(pair.getLeft());
                t.setCooYDireccion(pair.getRight());
            } catch (ApiException | InterruptedException | IOException ex) {
                Logger.getLogger(SolicitarDireccionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    void calcularInsumos(Producto p, Integer cantidad, HashMap<Insumo, Integer> insumos) {
        ArrayList<ProductoInsumo> pxi = new ArrayList(p.getProductoxInsumos());
        for (ProductoInsumo productoInsumo : pxi) {
            if (insumos.get(productoInsumo.getInsumo()) != null) {
                insumos.put(productoInsumo.getInsumo(), productoInsumo.getCantidad().intValue() * cantidad + insumos.get(productoInsumo.getInsumo()));
            } else {
                insumos.put(productoInsumo.getInsumo(), productoInsumo.getCantidad().intValue() * cantidad);
            }
        }
    }

    public void recalcularStockProducto(Producto producto, Integer nuevoValor, Integer viejoValor, HashMap<Insumo, Integer> insumos) {//Producto

        ArrayList<ProductoInsumo> productoxinsumos = new ArrayList(producto.getProductoxInsumos());
        for (int i = 0; i < productoxinsumos.size(); i++) {
            ProductoInsumo get = productoxinsumos.get(i);
            System.out.println(nuevoValor + " + " + viejoValor);
            if (insumos.get(get.getInsumo()) != null) {
                Double nuevoStockInsumo = insumos.get(get.getInsumo()) - nuevoValor * get.getCantidad() + (viejoValor) * get.getCantidad();
                insumos.put(get.getInsumo(), nuevoStockInsumo.intValue());
            }
        }
        insumos.forEach((t, u) -> {
            System.out.println(t.getNombre() + "->" + u.toString());
        });

    }

    public Integer mostrarMaximoStockProducto(Producto t, HashMap<Insumo, Integer> insumos) {
        Integer st = Integer.MAX_VALUE;
        for (ProductoInsumo p : t.getProductoxInsumos()) {
            Integer cantidadInsumo = insumos.get(p.getInsumo());
            cantidadInsumo = (cantidadInsumo == null) ? 0 : cantidadInsumo;
            Double posStock = cantidadInsumo / (p.getCantidad());// Si no tengo insumos que se requiere no se debe caer
            st = (posStock.intValue() < st) ? posStock.intValue() : st;
        }
        return st;
    }
}
