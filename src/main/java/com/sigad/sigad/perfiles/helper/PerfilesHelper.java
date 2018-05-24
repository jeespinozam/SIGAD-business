package com.sigad.sigad.perfiles.helper;

import com.sigad.sigad.usuarios.helper.*;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Usuario;
import java.util.ArrayList;
import org.hibernate.Session;
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
    
    /*Close session*/
    public void close(){
        session.close();
    }
}
