/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Alexandra
 */
@Entity
public class ClienteDescuento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Boolean activo;
    private String tipo;// Monto o NumeroDePedidos
    private Double value;
    private Date fechaInicio;
    private Date fechaFin;
    @ManyToMany
    private Set<Usuario> clientes;

}
