package com.sigad.sigad.perfiles.helper;

import com.sigad.sigad.usuarios.helper.*;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Usuario;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorgeespinoza
 */
public class PerfilesHelper {

    Session session = null;
    private static String errorMessage = "";
    
    public PerfilesHelper() {
        session = LoginController.serviceInit();
    }
    
    /*Get all*/
    public ArrayList<Perfil> getProfiles(){
        String cad;
        cad = "from Perfil";
        
        Query query = session.createQuery(cad);
        return new ArrayList<>( query.list());
    };
    
    /*Get profile by id, if nothin then null*/
    public Perfil getProfile(int id){
        Query query = session.createQuery("from Perfil where id=" + Integer.toString(id));
        if(query.list().size()==0){
            return null;
        }
        return (Perfil) query.list().get(0);
    }
    
    /*Get profile by name, if nothin then null*/
    public Perfil getProfile(String nombre){
        Query query = null;
        try {
            query = session.createQuery("from Perfil where nombre='" + nombre + "'");
            
            if(query.list().isEmpty()){
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return (Perfil) query.list().get(0);
    }
    
    /*verify if this field exits, it must be an index*/
    public boolean existProfileName(String nombre){
        Query query = null;
        try {
            query = session.createQuery("from Perfil where nombre='" + nombre + "'");
            
            if(query.list().isEmpty()){
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean saveProfile(Perfil p){
        try {
            Transaction tx = session.getTransaction();
            Long id = (Long) session.save(p);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean updateProfile(Perfil pOld){
        try {
            Transaction tx = session.getTransaction();
            Perfil pNew = session.load(Perfil.class, pOld.getId());
            pNew.setNombre(pOld.getNombre());
            pNew.setDescripcion(pOld.getDescripcion());
            pNew.setPermisos(pOld.getPermisos());
            pNew.setActivo(pOld.isActivo());
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /*Close session*/
    public void close(){
        session.close();
    }

    /**
     * @return the errorMessage
     */
    public static String getErrorMessage() {
        return errorMessage;
    }
}
