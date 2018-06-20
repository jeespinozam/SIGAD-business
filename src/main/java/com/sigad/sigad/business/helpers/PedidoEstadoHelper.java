/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.PedidoEstado;
import com.sigad.sigad.business.helpers.BaseHelper;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Alexandra
 */
public class PedidoEstadoHelper extends BaseHelper{


    public PedidoEstadoHelper() {
        super();
    }

    /*Close session*/
    

    public PedidoEstado getEstadoByName(String name) {
        PedidoEstado p = null;
        Query query = null;
        try {
            query = session.createQuery("from PedidoEstado where nombre='" + name + "'");

            List<PedidoEstado> l = (List) query.list();
            if (l.size() > 0) {
                p = l.get(0);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            return p;
        }
    }

}
