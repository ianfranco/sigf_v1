package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * VentaBoletoGuia generated by hbm2java
 */
@Entity
@Table(name="venta_boleto_guia"
    ,catalog="sigf_v1"
)
public class VentaBoletoGuia  implements java.io.Serializable {


     private Integer idVentaBoletoGuia;
     private Guia guia;
     private InventarioTerminal inventarioTerminal;
     private int valor;
     private Boolean recaudado;
     private Date fechaVentaBoletoGuia;

    public VentaBoletoGuia() {
    }

	
    public VentaBoletoGuia(Guia guia, InventarioTerminal inventarioTerminal, int valor, Date fechaVentaBoletoGuia) {
        this.guia = guia;
        this.inventarioTerminal = inventarioTerminal;
        this.valor = valor;
        this.fechaVentaBoletoGuia = fechaVentaBoletoGuia;
    }
    public VentaBoletoGuia(Guia guia, InventarioTerminal inventarioTerminal, int valor, Boolean recaudado, Date fechaVentaBoletoGuia) {
       this.guia = guia;
       this.inventarioTerminal = inventarioTerminal;
       this.valor = valor;
       this.recaudado = recaudado;
       this.fechaVentaBoletoGuia = fechaVentaBoletoGuia;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_venta_boleto_guia", unique=true, nullable=false)
    public Integer getIdVentaBoletoGuia() {
        return this.idVentaBoletoGuia;
    }
    
    public void setIdVentaBoletoGuia(Integer idVentaBoletoGuia) {
        this.idVentaBoletoGuia = idVentaBoletoGuia;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_guia", nullable=false)
    public Guia getGuia() {
        return this.guia;
    }
    
    public void setGuia(Guia guia) {
        this.guia = guia;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_inventario_terminal", nullable=false)
    public InventarioTerminal getInventarioTerminal() {
        return this.inventarioTerminal;
    }
    
    public void setInventarioTerminal(InventarioTerminal inventarioTerminal) {
        this.inventarioTerminal = inventarioTerminal;
    }

    
    @Column(name="valor", nullable=false)
    public int getValor() {
        return this.valor;
    }
    
    public void setValor(int valor) {
        this.valor = valor;
    }

    
    @Column(name="recaudado")
    public Boolean getRecaudado() {
        return this.recaudado;
    }
    
    public void setRecaudado(Boolean recaudado) {
        this.recaudado = recaudado;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_venta_boleto_guia", nullable=false, length=19)
    public Date getFechaVentaBoletoGuia() {
        return this.fechaVentaBoletoGuia;
    }
    
    public void setFechaVentaBoletoGuia(Date fechaVentaBoletoGuia) {
        this.fechaVentaBoletoGuia = fechaVentaBoletoGuia;
    }




}


