/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.LoteInsumo;
import com.sigad.sigad.business.LoteTienda;
import com.sigad.sigad.business.Tienda;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author chrs
 */
public class LoteInsumoHelper {
    Session session = null;
    private String errorMessage = "";
    
    public LoteInsumoHelper() {
        session = LoginController.serviceInit();
    }
    
    /*Close session*/
    public void close(){
        session.close();
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /*Get all stores*/
    public ArrayList<LoteInsumo> getLoteInsumos(){
        ArrayList<LoteInsumo> lotesInsumos = null;
        Query query = null;
        try {
            query = session.createQuery("from LoteInsumo");
            
            if(!query.list().isEmpty()){
               lotesInsumos = (ArrayList<LoteInsumo>)( query.list());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            errorMessage = e.getMessage();
        } finally{
            return lotesInsumos;
        }
    }

    public ArrayList<LoteInsumo> getLoteInsumos(Tienda currentStore) {
        ArrayList<LoteInsumo> lotesInsumos = null;
        Query query = null;
        try {
            query = session.createQuery("from LoteInsumo where tienda_id = " + currentStore.getId().toString());
            
            if(!query.list().isEmpty()){
               lotesInsumos = (ArrayList<LoteInsumo>)( query.list());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            errorMessage = e.getMessage();
        } finally{
            return lotesInsumos;
        }
    }
    
    public LoteInsumo getLoteInsumo(Long id){
        LoteInsumo insumo = null;
        Query query = null;
        try {
            query = session.createQuery("from LoteInsumo where id=" + id);
            if(!query.list().isEmpty()){
                insumo = (LoteInsumo) query.list().get(0);
            }
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
        return insumo;
    }
    
    public boolean updateLoteInsumo(LoteInsumo tOld){
        boolean ok = false;
        try {
            Transaction tx;
            if(session.getTransaction().isActive()){
                tx = session.getTransaction();
            }else{
                tx = session.beginTransaction();
            }
            LoteInsumo tNew = session.load(LoteInsumo.class, tOld.getId());
            
            tNew.setStockLogico(tOld.getStockLogico());
            tNew.setStockFisico(tOld.getStockFisico());
            tNew.setCostoUnitario(tOld.getCostoUnitario());
            tNew.setDetalleOrdenCompra(tOld.getDetalleOrdenCompra());
            tNew.setFechaVencimiento(tOld.getFechaVencimiento());
            tNew.setInsumo(tOld.getInsumo());
            tNew.setTienda(tOld.getTienda());
            
            session.merge(tNew);
            tx.commit();
            ok = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.errorMessage = e.getMessage();
        }
        return ok;
    }
    
}
