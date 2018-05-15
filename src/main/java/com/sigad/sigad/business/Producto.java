/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Alexandra
 */
class Producto {
    @OneToOne
    @JoinColumn(name = "detallePedido_id")
    private DetallePedido detallePedido;
    
    @OneToMany (mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoInsumo> insumos = new HashSet<>();

    /**
     * @return the detallePedido
     */
    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    /**
     * @param detallePedido the detallePedido to set
     */
    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }

    /**
     * @return the insumos
     */
    public Set<ProductoInsumo> getInsumos() {
        return insumos;
    }

    /**
     * @param insumos the insumos to set
     */
    public void setInsumos(Set<ProductoInsumo> insumos) {
        this.insumos = insumos;
    }
}
