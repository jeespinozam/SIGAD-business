/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import com.sun.istack.internal.NotNull;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

/**
 *
 * @author cfoch
 */
@Entity
public class PersonaJuridica extends Persona {
    @NotNull
    private String razonSocial;
    @Pattern(regexp="[\\d]{11}")
    private String ruc;
    
    public PersonaJuridica(String ruc, String razonSocial) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
}
