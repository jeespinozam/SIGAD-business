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
    public static final String TABLA_PRODUCTOCATEGORIA = "Producto Categoria";
    public static final String TABLA_PERFILES = "Perfiles";
    public static final String TABLA_USUARIOS = "Usuarios";
    public static final String TABLA_PROVEEDORES = "Proveedores";
    
    public static ObservableList<String> getList(){
        return FXCollections.observableArrayList(
            TABLA_PRODUCTOCATEGORIA, TABLA_PERFILES, TABLA_USUARIOS, TABLA_PROVEEDORES
        );
    };
}
