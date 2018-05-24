package com.sigad.sigad.usuarios.helper;

import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.app.controller.LoginController;
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
public class UsuariosHelper {

    Session session = null;
    
    public UsuariosHelper() {
        session = LoginController.serviceInit();
    }
    
    /*Get users with the same idPerfil, if -1 then get all*/
    public ArrayList<Usuario> getUsers(int idPerfil){
        String cad;
        //todos
        if(idPerfil==-1){
            cad = "from Usuario";
        }
        //usamos el idPerfil
        else{
            cad = "from Usuario where idPerfil=" + Integer.toString(idPerfil);
        }
        Query query = session.createQuery(cad);
        return new ArrayList<>( query.list());
    };
    
    /*Get user by id*/
    public Usuario getUser(int id){
        Query query = session.createQuery("from Usuario where id=" + Integer.toString(id));
        if(query.list().size()==0){
            return null;
        }
        return (Usuario) query.list().get(0);
    }
    
    /*Get user by email*/
    public Usuario getUser(String email){
        Query query = null;
        try {
            query = session.createQuery("from Usuario where correo='" + email + "'");
            
            if(query.list().isEmpty()){
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        
        return (Usuario) query.list().get(0);
    }
    
    /*Add email as index*/
    public boolean existUserEmail(String email){
        Query query = null;
        try {
            query = session.createQuery("from Usuario where correo='" + email + "'");
            
            if(query.list().isEmpty()){
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public void saveUser(Usuario newUser){
        Transaction tx = session.beginTransaction();
        session.save(newUser);
        tx.commit();
    }

    public void close(){
        session.close();
    }
}
