/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.OrdenCompra;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author chrs
 */
public class OrdenCompraHelper {
    Session session = null;
    private String errorMessage = "";
    public OrdenCompraHelper(){
        session = LoginController.serviceInit();
    }
    public void close(){
        session.close();
    }
    public String getErrorMessage(){
        return errorMessage;
    }
    public OrdenCompra getOrden(Integer id){
        session.beginTransaction();
        OrdenCompra orden =  (OrdenCompra)session.get(OrdenCompra.class, id);
        session.getTransaction().commit();
        return orden;
    }
    
    public Integer saveOrden(OrdenCompra newOrden){
        Integer id = null;
        try {
            Transaction tx = session.beginTransaction();
            session.save(newOrden);
            if(newOrden.getId() != 0) {
                id = newOrden.getId();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            this.errorMessage = e.getMessage();
        }
        return id;
    }
    
    
    public ArrayList<OrdenCompra> getOrdenes(){
        ArrayList<OrdenCompra> ordenes = null;
        Query query = null;
        try {
            query = session.createQuery("from OrdenCompra");
            if(!query.list().isEmpty()){
                ordenes = (ArrayList<OrdenCompra>)query.list();
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
        return ordenes;
    }
}
