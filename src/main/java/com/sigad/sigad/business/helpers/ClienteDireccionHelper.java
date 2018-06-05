/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.ClienteDireccion;
import org.hibernate.Session;

/**
 *
 * @author Alexandra
 */
public class ClienteDireccionHelper {
    Session session = null;
    private String errorMessage = "";
    
    public ClienteDireccionHelper() {
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
    
    public void save(ClienteDireccion cli){
        
    }
}
