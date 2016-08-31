package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


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
 * HaberLiquidacion generated by hbm2java
 */
@Entity
@Table(name="haber_liquidacion"
    ,catalog="sigf_v1"
)
public class HaberLiquidacion  implements java.io.Serializable {


     private Integer idHaberLiquidacion;
     private HaberTrabajadorLiquidacion haberTrabajadorLiquidacion;
     private LiquidacionSueldo liquidacionSueldo;
     private int montoHaber;

    public HaberLiquidacion() {
    }

    public HaberLiquidacion(HaberTrabajadorLiquidacion haberTrabajadorLiquidacion, LiquidacionSueldo liquidacionSueldo, int montoHaber) {
       this.haberTrabajadorLiquidacion = haberTrabajadorLiquidacion;
       this.liquidacionSueldo = liquidacionSueldo;
       this.montoHaber = montoHaber;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_haber_liquidacion", unique=true, nullable=false)
    public Integer getIdHaberLiquidacion() {
        return this.idHaberLiquidacion;
    }
    
    public void setIdHaberLiquidacion(Integer idHaberLiquidacion) {
        this.idHaberLiquidacion = idHaberLiquidacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_haber_trabajador_liquidacion", nullable=false)
    public HaberTrabajadorLiquidacion getHaberTrabajadorLiquidacion() {
        return this.haberTrabajadorLiquidacion;
    }
    
    public void setHaberTrabajadorLiquidacion(HaberTrabajadorLiquidacion haberTrabajadorLiquidacion) {
        this.haberTrabajadorLiquidacion = haberTrabajadorLiquidacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_liquidacion_sueldo", nullable=false)
    public LiquidacionSueldo getLiquidacionSueldo() {
        return this.liquidacionSueldo;
    }
    
    public void setLiquidacionSueldo(LiquidacionSueldo liquidacionSueldo) {
        this.liquidacionSueldo = liquidacionSueldo;
    }

    
    @Column(name="monto_haber", nullable=false)
    public int getMontoHaber() {
        return this.montoHaber;
    }
    
    public void setMontoHaber(int montoHaber) {
        this.montoHaber = montoHaber;
    }




}


