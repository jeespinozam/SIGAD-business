/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Tienda;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author jorgeespinoza
 */
public class TiendaHelper {
    
    Session session = null;
    private String errorMessage = "";
    
    public TiendaHelper() {
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
    public ArrayList<Tienda> getStores(){
        ArrayList<Tienda> stores = null;
        Query query = null;
        try {
            query = session.createQuery("from Tienda");
            
            if(!query.list().isEmpty()){
               stores = (ArrayList<Tienda>)( query.list());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            errorMessage = e.getMessage();
        } finally{
            return stores;
        }
    }
    
    /*Get store by id, if nothin then null*/
    public Tienda getStore(int id){
        Tienda t = null;
        Query query = null;
        try {
            query = session.createQuery("from Tienda where id=" + Integer.toString(id));
            
            if(!query.list().isEmpty()){
                t = (Tienda) query.list().get(0);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally{
            return t;
        }
    }
    
    /*Get store by name, if nothin then null*/
    public Tienda getStore(String nombre){
        Tienda t = null;
        Query query = null;
        try {
            query = session.createQuery("from Tienda where nombre='" + nombre + "'");
            
            if(!query.list().isEmpty()){
                t = (Tienda) query.list().get(0);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally{
            return t;
        }
    }
    
    public Long saveStore(Tienda t){
        Long id = new Long(-1);
        try {
            Transaction tx = session.getTransaction();
            id = (Long) session.save(t);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    
    public boolean updateStore(Tienda tOld){
        boolean ok = false;
        try {
            Transaction tx = session.getTransaction();
            Tienda tNew = session.load(Tienda.class, tOld.getId());
            
            tNew.setCapacidad(tOld.getCapacidad());
            tNew.setCooXDireccion(tOld.getCooXDireccion());
            tNew.setCooYDireccion(tOld.getCooYDireccion());
            tNew.setDescripcion(tOld.getDescripcion());
            tNew.setDireccion(tOld.getDireccion());
            
            tx.commit();
            ok = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.errorMessage = e.getMessage();
        }
        return ok;
    }
}
