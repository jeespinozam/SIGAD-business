/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.helpers.cargaMasiva;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author paul
 */
public class CargaMasivaConstantes {
    // Tablas a cargar mediante consulta masiva
    public static final String TABLA_PRODUCTOCATEGORIA = "Categoria de Productos";
    public static final String TABLA_PERFILES = "Perfiles";
    public static final String TABLA_USUARIOS = "Usuarios";
    public static final String TABLA_PROVEEDORES = "Proveedores";
    public static final String TABLA_INSUMOS = "Insumos";
    public static final String TABLA_TIENDAS = "Tiendas";
    public static final String TABLA_TIPOMOV = "Tipo de Movimientos";
    public static final String TABLA_PERMISOS = "Permisos";
    public static final String TABLA_PERFILXPERMISO = "Perfil x Permiso";
    
    public static ObservableList<String> getList(){
        return FXCollections.observableArrayList(
                TABLA_PRODUCTOCATEGORIA, TABLA_PERFILES, TABLA_USUARIOS, TABLA_PROVEEDORES, TABLA_INSUMOS, TABLA_TIENDAS, 
                TABLA_TIPOMOV, TABLA_PERMISOS, TABLA_PERFILXPERMISO
        );
    };
}
