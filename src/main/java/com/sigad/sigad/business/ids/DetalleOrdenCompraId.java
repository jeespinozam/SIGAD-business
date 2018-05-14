/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.ids;

import com.sigad.sigad.business.LoteInsumo;
import com.sigad.sigad.business.OrdenCompra;
import javax.persistence.ManyToOne;

/**
 *
 * @author jorgeespinoza
 */
public class DetalleOrdenCompraId {
    @ManyToOne
    private OrdenCompra orden;
    @ManyToOne
    private LoteInsumo loteInsumo;

    /**
     * @return the orden
     */
    public OrdenCompra getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(OrdenCompra orden) {
        this.orden = orden;
    }

    /**
     * @return the loteInsumo
     */
    public LoteInsumo getLoteInsumo() {
        return loteInsumo;
    }

    /**
     * @param loteInsumo the loteInsumo to set
     */
    public void setLoteInsumo(LoteInsumo loteInsumo) {
        this.loteInsumo = loteInsumo;
    }
    
}
