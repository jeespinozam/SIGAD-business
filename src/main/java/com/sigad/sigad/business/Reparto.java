/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alexandra
 */
@Entity
public class Reparto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull
    private Date fecha;
    
    @OneToMany(mappedBy="reparto")
    private List<Pedido> pedidos = new ArrayList<Pedido>();
    //fk
    @ManyToOne
    private Usuario repartidor;
    
    @ManyToOne
    private Vehiculo vehiculo;

    // "M", "T", "N": 'Mañana', 'Tarde', 'Noche'
    @NotNull
    private String turno;
    @ManyToOne
    private Tienda tienda;
    
    @OneToMany(mappedBy = "reparto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetalleReparto> detallesReparto = new HashSet<>();

    public Reparto() {
    }
    
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the repartidor
     */
    public Usuario getRepartidor() {
        return repartidor;
    }

    /**
     * @param repartidor the repartidor to set
     */
    public void setRepartidor(Usuario repartidor) {
        this.repartidor = repartidor;
    }

    /**
     * @return the vehiculo
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * @return the detallesReparto
     */
    public Set<DetalleReparto> getDetallesReparto() {
        return detallesReparto;
    }

    /**
     * @param detallesReparto the detallesReparto to set
     */
    public void setDetallesReparto(Set<DetalleReparto> detallesReparto) {
        this.detallesReparto = detallesReparto;
    }

    /**
     * @return the pedidos
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos the pedidos to set
     */
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * @return the turno
     */
    public String getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     * @return the tienda
     */
    public Tienda getTienda() {
        return tienda;
    }

    /**
     * @param tienda the tienda to set
     */
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
