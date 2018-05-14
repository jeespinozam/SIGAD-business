/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.ids;

import com.sigad.sigad.business.LoteInsumo;
import com.sigad.sigad.business.Tienda;
import javax.persistence.ManyToOne;

/**
 *
 * @author jorgeespinoza
 */
public class LoteTiendaId {
    @ManyToOne
    private Tienda tienda;
    @ManyToOne
    private LoteInsumo loteInsumo;

    /**
     * @return the tienda
     */
    public Tienda getTienda() {
        return tienda;
    }

    /**
     * @param tienda the tienda to set
     */
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
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
