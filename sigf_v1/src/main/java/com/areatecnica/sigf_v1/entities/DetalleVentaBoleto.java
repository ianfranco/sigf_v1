package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DetalleVentaBoleto generated by hbm2java
 */
@Entity
@Table(name="detalle_venta_boleto"
    ,catalog="sigf_v1"
)
public class DetalleVentaBoleto  implements java.io.Serializable {


     private Integer idDetalleVentaBoleto;
     private InventarioInterno inventarioInterno;
     private VentaBoleto ventaBoleto;
     private int total;

    public DetalleVentaBoleto() {
    }

    public DetalleVentaBoleto(InventarioInterno inventarioInterno, VentaBoleto ventaBoleto, int total) {
       this.inventarioInterno = inventarioInterno;
       this.ventaBoleto = ventaBoleto;
       this.total = total;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_detalle_venta_boleto", unique=true, nullable=false)
    public Integer getIdDetalleVentaBoleto() {
        return this.idDetalleVentaBoleto;
    }
    
    public void setIdDetalleVentaBoleto(Integer idDetalleVentaBoleto) {
        this.idDetalleVentaBoleto = idDetalleVentaBoleto;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_inventario_interno", nullable=false)
    public InventarioInterno getInventarioInterno() {
        return this.inventarioInterno;
    }
    
    public void setInventarioInterno(InventarioInterno inventarioInterno) {
        this.inventarioInterno = inventarioInterno;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_venta_boleto", nullable=false)
    public VentaBoleto getVentaBoleto() {
        return this.ventaBoleto;
    }
    
    public void setVentaBoleto(VentaBoleto ventaBoleto) {
        this.ventaBoleto = ventaBoleto;
    }

    
    @Column(name="total", nullable=false)
    public int getTotal() {
        return this.total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }




}


