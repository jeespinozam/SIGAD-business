/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author cfoch
 */
@Entity
public class ClienteDireccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String direccionCliente;
    private String nombreDireccion;
    @NotNull
    private Boolean principal;
    @NotNull
    private Boolean activo;
    @NotNull
    private Double cooXDireccion;
    @NotNull
    private Double cooYDireccion;
    @ManyToOne(optional=false)
    private Usuario usuario;

    /**
     * Constructor.
     */
    public ClienteDireccion() {
    }

    public ClienteDireccion(Long id, String direccionCliente, String nombreDireccion, Boolean principal, Boolean activo, Double cooXDireccion, Double cooYDireccion, Usuario usuario) {
        this.id = id;
        this.direccionCliente = direccionCliente;
        this.nombreDireccion = nombreDireccion;
        this.principal = principal;
        this.activo = activo;
        this.cooXDireccion = cooXDireccion;
        this.cooYDireccion = cooYDireccion;
        this.usuario = usuario;
    }

   
    public ClienteDireccion(String direccionCliente, String nombreDireccion, Boolean principal,  Usuario usuario) {
        this.direccionCliente = direccionCliente;
        this.nombreDireccion = nombreDireccion;
        this.principal = principal;
        this.activo = Boolean.TRUE;
        this.usuario = usuario;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        
        if (o instanceof ClienteDireccion){
            ClienteDireccion  c = (ClienteDireccion) o;
            return c.getId().equals(this.getId());
        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
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
     * @return the direccionCliente
     */
    public String getDireccionCliente() {
        return direccionCliente;
    }

    /**
     * @param direccionCliente the direccionCliente to set
     */
    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    /**
     * @return the nombreDireccion
     */
    public String getNombreDireccion() {
        return nombreDireccion;
    }

    /**
     * @param nombreDireccion the nombreDireccion to set
     */
    public void setNombreDireccion(String nombreDireccion) {
        this.nombreDireccion = nombreDireccion;
    }

    /**
     * @return the principal
     */
    public boolean isPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the cooXDireccion
     */
    public double getCooXDireccion() {
        return cooXDireccion;
    }

    /**
     * @param cooXDireccion the cooXDireccion to set
     */
    public void setCooXDireccion(double cooXDireccion) {
        this.cooXDireccion = cooXDireccion;
    }

    /**
     * @return the cooYDireccion
     */
    public double getCooYDireccion() {
        return cooYDireccion;
    }

    /**
     * @param cooYDireccion the cooYDireccion to set
     */
    public void setCooYDireccion(double cooYDireccion) {
        this.cooYDireccion = cooYDireccion;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
