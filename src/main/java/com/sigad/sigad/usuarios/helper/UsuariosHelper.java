package com.sigad.sigad.usuarios.helper;

import com.sigad.sigad.app.controller.ErrorController;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.perfiles.helper.PerfilesHelper;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sun.java2d.cmm.Profile;

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
    private String errorMessage = "";
    
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
            this.errorMessage = e.getMessage();
            return null;
        }
        
        return (Usuario) query.list().get(0);
    }
    
    /*verify if this field exits, it must be an index*/
    public boolean existUserEmail(String email){
        Query query = null;
        try {
            query = session.createQuery("from Usuario where correo='" + email + "'");
            
            if(query.list().isEmpty()){
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            this.errorMessage = e.getMessage();
            return false;
        }
        
        return true;
    }
    
    public boolean saveUser(Usuario newUser){
        try {
            Transaction tx = session.beginTransaction();
        
            PerfilesHelper helper = new PerfilesHelper();
            boolean existProfile = helper.existProfileName(newUser.getPerfil().getNombre());

            if(existProfile){
                Perfil p = helper.getProfile(newUser.getPerfil().getNombre());
                newUser.setPerfil(p);
                session.save(newUser);
            }else{
                tx.commit();
                return false;
            }
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.errorMessage = e.getMessage();
            return false;
        }
        
        return true;
    }

    public void close(){
        session.close();
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
