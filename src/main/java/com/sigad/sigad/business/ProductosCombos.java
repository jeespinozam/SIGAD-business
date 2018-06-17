/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Alexandra
 */
@Entity
public class ProductosCombos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer cantidad;
    @ManyToOne
    private Producto producto;
    @ManyToOne
    private ComboPromocion combopromocion;
    
    public ProductosCombos() {
    }
    
    public ProductosCombos(Producto pd, Integer c, ComboPromocion cb) {
        this.producto = pd;
        this.cantidad = c;
        this.combopromocion = cb;
    }
    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the combopromocion
     */
    public ComboPromocion getCombopromocion() {
        return combopromocion;
    }

    /**
     * @param combopromocion the combopromocion to set
     */
    public void setCombopromocion(ComboPromocion combopromocion) {
        this.combopromocion = combopromocion;
    }
}
