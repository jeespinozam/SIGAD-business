/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import com.sigad.sigad.business.ids.OrdenCompraId;
import com.sun.istack.internal.NotNull;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author jorgeespinoza
 */
public class OrdenCompra {
    @EmbeddedId
    private OrdenCompraId id = new OrdenCompraId();
    @NotNull
    private double precioTotal;
    @NotNull
    private Date fecha; 

    /**
     * @return the id
     */
    public OrdenCompraId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(OrdenCompraId id) {
        this.id = id;
    }

    /**
     * @return the precioTotal
     */
    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * @param precioTotal the precioTotal to set
     */
    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
