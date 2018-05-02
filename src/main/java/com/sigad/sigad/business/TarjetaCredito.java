/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import com.sun.istack.internal.NotNull;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

/**
 *
 * @author cfoch
 */
@Entity
public class TarjetaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Pattern(regexp="[\\d]{16}")
    private String numero;
    @NotNull
    private Date fechaExpiración;
    @ManyToOne
    private Persona persona;

    public TarjetaCredito(Persona persona, String numero) {
        setNumero(numero);
        setPersona(persona);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the fechaExpiración
     */
    public Date getFechaExpiración() {
        return fechaExpiración;
    }

    /**
     * @param fechaExpiración the fechaExpiración to set
     */
    public void setFechaExpiración(Date fechaExpiración) {
        this.fechaExpiración = fechaExpiración;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
