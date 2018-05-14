/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import com.sigad.sigad.business.ids.DetalleOrdenCompraId;
import com.sun.istack.internal.NotNull;
import javax.persistence.EmbeddedId;

/**
 *
 * @author jorgeespinoza
 */
public class DetalleOrdenCompra {
    @EmbeddedId
    private DetalleOrdenCompraId id = new DetalleOrdenCompraId();
    @NotNull
    private double precioDetalle;

    /**
     * @return the id
     */
    public DetalleOrdenCompraId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(DetalleOrdenCompraId id) {
        this.id = id;
    }

    /**
     * @return the precioDetalle
     */
    public double getPrecioDetalle() {
        return precioDetalle;
    }

    /**
     * @param precioDetalle the precioDetalle to set
     */
    public void setPrecioDetalle(double precioDetalle) {
        this.precioDetalle = precioDetalle;
    }
    
}
