/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author cfoch
 */
public class SIGADBusinessMain {
    public static void main(String[] args) {
        Configuration config;
        SessionFactory sessionFactory;
        Session session;

        config = new Configuration();
        config.configure("hibernate.cfg.xml");
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();
        
        System.out.println("================================");
        System.out.println("open : " + session.isOpen());
        
        session.beginTransaction();

        Permiso permisoVerProducto = new Permiso("ver_producto");
        Grupo grupoCliente = new Grupo("Cliente");
        
        grupoCliente.getPermisos().add(permisoVerProducto);

        PersonaNatural persona1 = new PersonaNatural("19345678", "César Fabián",
                "Orccón", "Chipana");
        PersonaJuridica empresa1 = new PersonaJuridica("1234567890",
                "Valle Andino Pokemon S.A.C");
        Usuario usuarioPersona1 = new Usuario(persona1,
                "cfoch.fabian@gmail.com", "123456");
        Usuario usuarioEmpresa1 = new Usuario(empresa1,
                "admin@valleandino.com", "123456");
        
        TarjetaCredito tarjetaCreditoPersona1 =
                new TarjetaCredito(persona1, "4422324423449324");
        
        Vehiculo.Tipo vehiculoTipo1 = new Vehiculo.Tipo("BMW Azul", 13.4);
        Vehiculo vehiculo1 = new Vehiculo(vehiculoTipo1, "YDEED-3242");

        grupoCliente.getUsuarios().add(usuarioPersona1);
        grupoCliente.getUsuarios().add(usuarioEmpresa1);

        session.save(persona1);
        session.save(empresa1);
        session.save(usuarioPersona1);
        session.save(usuarioEmpresa1);
        session.save(grupoCliente);
        session.save(tarjetaCreditoPersona1);
        session.save(permisoVerProducto);
        session.save(vehiculoTipo1);
        session.save(vehiculo1);

        session.getTransaction().commit();
        
        session.close();
        sessionFactory.close();
    }
}
