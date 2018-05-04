/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import com.sun.istack.internal.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author cfoch
 */
@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @OneToOne(mappedBy = "empleado")
    private PersonaNatural persona;
    private boolean esActivo;

    public Empleado(PersonaNatural persona) {
        setPersona(persona);
        setEsActivo(true);
        persona.setEmpleado(this);
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
     * @return the persona
     */
    public PersonaNatural getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaNatural persona) {
        this.persona = persona;
    }

    /**
     * @return the esActivo
     */
    public boolean isEsActivo() {
        return esActivo;
    }

    /**
     * @param esActivo the esActivo to set
     */
    public void setEsActivo(boolean esActivo) {
        this.esActivo = esActivo;
    }

}
