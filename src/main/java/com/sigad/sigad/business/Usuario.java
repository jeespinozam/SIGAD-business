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
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ColumnDefault;

/**
 *
 * @author cfoch
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String apodo;
    @NotNull
    private String clave;
    @NotNull
    private String correo;
    @ColumnDefault("true")
    private boolean esActivo;
    @NotNull
    @ManyToOne
    private Persona persona;
    @ManyToMany(mappedBy="usuarios")
    private Set<Grupo> grupos;

    public Usuario(Persona persona, String correo, String clave) {
        setPersona(persona);
        setCorreo(correo);
        setClave(clave);
        grupos = new HashSet<>();
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
     * @return the apodo
     */
    public String getApodo() {
        return apodo;
    }

    /**
     * @param apodo the apodo to set
     */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
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

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the grupos
     */
    public Set<Grupo> getGrupos() {
        return grupos;
    }

    /**
     * @param grupos the grupos to set
     */
    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }
}
