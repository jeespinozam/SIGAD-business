/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import com.sun.istack.internal.NotNull;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author cfoch
 */
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @OneToOne(mappedBy = "cliente")
    private PersonaNatural persona;
    private String intereses;
    @OneToMany
    private Set<Locacion> locaciones;
    @ManyToMany(mappedBy="clientes")
    private Set<FechaImportante> fechasImportantes;

    public Cliente(PersonaNatural persona) {
        setPersona(persona);
        fechasImportantes = new HashSet<>();
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
     * @return the intereses
     */
    public String getIntereses() {
        return intereses;
    }

    /**
     * @param intereses the intereses to set
     */
    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }

    /**
     * @return the locaciones
     */
    public Set<Locacion> getLocaciones() {
        return locaciones;
    }

    /**
     * @param locacion the locacion to add
     */
    public void addLocacion(Locacion locacion) {
        this.locaciones.add(locacion);
    }
}
