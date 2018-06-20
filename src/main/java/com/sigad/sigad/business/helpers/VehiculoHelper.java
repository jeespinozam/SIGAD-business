/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Vehiculo;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author chrs
 */
public class VehiculoHelper {
    
    Session session = null;
    private String errorMessage = "";
    private final static Logger LOGGER = Logger.getLogger(InsumosHelper.class.getName());
    
    /////////////////////////
    
    public VehiculoHelper() {
        session = LoginController.serviceInit();
    }
    
    public void close(){
        session.close();
    }
    
    public String getErrorMessage(){
        return errorMessage;
    }
    
    public Long saveVehiculo(Vehiculo vehiculo){
        Long id = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(vehiculo);
            LOGGER.log(Level.INFO, "Vehiculo generado con exito");
            if(vehiculo.getId() != 0) {
                id = vehiculo.getId();
            }
            tx.commit();
        } catch (Exception e) {
            if (tx!=null)   tx.rollback();
            LOGGER.log(Level.SEVERE, "Error al intentar realizar la transaccion de vehiculo");
            System.out.println("====================================================================");
            System.out.println(e);
            System.out.println("====================================================================");
            this.errorMessage = e.getMessage();
        }
        return id;
    }
    
    public ArrayList<Vehiculo> getVehiculos(){
        ArrayList<Vehiculo> vehiculos = null;
        Query query = null;
        try {
            query = session.createQuery("from Vehiculo");
            if(!query.list().isEmpty()){
                vehiculos = (ArrayList<Vehiculo>)query.list();
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
        return vehiculos;
    }
    
    public Vehiculo getVehiculo(Long id){
        Vehiculo vehiculo = null;
        Query query = null;
        try {
            query = session.createQuery("from Vehiculo where id= " + id);
            if(!query.list().isEmpty()){
                vehiculo = (Vehiculo) query.list().get(0);
            }
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
        return vehiculo;
    }
    
    public boolean updateVehiculo(Vehiculo tOld){
        boolean ok = false;
        try {
            Transaction tx;
            if(session.getTransaction().isActive()){
                tx = session.getTransaction();
            }else{
                tx = session.beginTransaction();
            }
            
            Vehiculo tNew = session.load(Vehiculo.class, tOld.getId());
            
            tNew.setNombre(tOld.getNombre());
            tNew.setPlaca(tOld.getPlaca());
            tNew.setDescripcion(tOld.getDescripcion());
            tNew.setTipo(tOld.getTipo());
            
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
