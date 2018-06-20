/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.ProductoCategoriaDescuento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Alexandra
 */
public class ProductoCategoriaDescuentoHelper extends BaseHelper {

    public ProductoCategoriaDescuentoHelper() {
        super();
    }

    public ArrayList<ProductoCategoriaDescuento> getDescuentos() {
        ArrayList<ProductoCategoriaDescuento> list = null;
        try {
            Query query = session.createQuery("from ProductoCategoriaDescuento");
            list = (ArrayList<ProductoCategoriaDescuento>) query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Error: " + e.getMessage());
            errorMessage = e.getMessage();
        } finally {
            return list;
        }
    }

    ;
    
    public ProductoCategoriaDescuento getDescuentoById(Integer id) {
        ProductoCategoriaDescuento descuento = null;
        Query query = null;
        try {
            query = session.createQuery("from ProductoCategoriaDescuento where id='" + id + "'");

            if (!query.list().isEmpty()) {
                descuento = (ProductoCategoriaDescuento) query.list().get(0);
            }
        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            this.errorMessage = e.getMessage();
        }
        return descuento;
    }

    ;

    public ProductoCategoriaDescuento getDescuentoByCategoria(Integer productocategoria_id) {
        ProductoCategoriaDescuento descuento = null;
        ArrayList<ProductoCategoriaDescuento> descuentos = new ArrayList<>();
        Query query = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            query = session.createQuery("SELECT c FROM ProductoCategoriaDescuento AS c WHERE c.fechaInicio <= :today AND c.fechaFin >= :today  AND categoria_id='" + productocategoria_id + "'");
            query.setParameter("today", new Date());
            if (!query.list().isEmpty()) {
                descuentos = (ArrayList<ProductoCategoriaDescuento>) query.list();

                descuento = descuentos.stream().max(Comparator.comparing(ProductoCategoriaDescuento::getValue)).orElseThrow(NoSuchElementException::new);
            }
        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            session.getTransaction().rollback();
            this.errorMessage = e.getMessage();
        } finally {
            return descuento;
        }

    }

    ;
    
    public List<ProductoCategoriaDescuento> getDescuentosByCategoria(Integer productocategoria_id) {
        List<ProductoCategoriaDescuento> descuentos = null;
        Query query = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            query = session.createQuery("SELECT c FROM ProductoCategoriaDescuento AS c WHERE c.fechaInicio <= :today AND c.fechaFin >= :today  AND categoria_id='" + productocategoria_id + "'");
            query.setParameter("today", new Date());
            descuentos = (List<ProductoCategoriaDescuento>) query.list();
        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            session.getTransaction().rollback();
            this.errorMessage = e.getMessage();
        } finally {
            return descuentos;
        }

    }

    ;
    
     public Long saveDescuento(ProductoCategoriaDescuento p) {
        Long id = null;
        try {
            Transaction tx;
            if (session.getTransaction().isActive()) {
                tx = session.getTransaction();
            } else {
                tx = session.beginTransaction();
            }

            session.save(p);
            if (p.getId() != null) {
                id = p.getId();
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.errorMessage = e.getMessage();
        }
        return id;
    }

    public boolean updateDescuento(ProductoCategoriaDescuento uOld) {
        boolean ok = false;
        try {
            Transaction tx;
            if (session.getTransaction().isActive()) {
                tx = session.getTransaction();
            } else {
                tx = session.beginTransaction();
            }

            ProductoCategoriaDescuento uNew = session.load(ProductoCategoriaDescuento.class, uOld.getId());

            uNew.setFechaInicio(uOld.getFechaInicio());
            uNew.setFechaFin(uOld.getFechaFin());
            uNew.setValue(uOld.getValue());

            session.merge(uNew);
            tx.commit();
            session.close();
            ok = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.errorMessage = e.getMessage();
        }
        return ok;
    }

}
