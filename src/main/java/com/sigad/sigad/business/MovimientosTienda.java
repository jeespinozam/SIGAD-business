/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

/**
 *
 * @author Alexandra
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MovimientosTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
}
