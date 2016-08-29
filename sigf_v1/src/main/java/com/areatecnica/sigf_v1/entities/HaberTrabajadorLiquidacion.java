package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HaberTrabajadorLiquidacion generated by hbm2java
 */
@Entity
@Table(name="haber_trabajador_liquidacion"
    ,catalog="sigf_v1"
)
public class HaberTrabajadorLiquidacion  implements java.io.Serializable {


     private Integer idHaberTrabajadorLiquidacion;
     private HaberTrabajador haberTrabajador;
     private Trabajador trabajador;
     private Date fechaInicioHaber;
     private Date fechaIngresoHaber;
     private Integer numeroCuotas;
     private int monto;
     private boolean activoHaberTrabajador;
     private Set<HaberLiquidacion> haberLiquidacions = new HashSet<HaberLiquidacion>(0);

    public HaberTrabajadorLiquidacion() {
    }

	
    public HaberTrabajadorLiquidacion(HaberTrabajador haberTrabajador, Trabajador trabajador, Date fechaInicioHaber, Date fechaIngresoHaber, int monto, boolean activoHaberTrabajador) {
        this.haberTrabajador = haberTrabajador;
        this.trabajador = trabajador;
        this.fechaInicioHaber = fechaInicioHaber;
        this.fechaIngresoHaber = fechaIngresoHaber;
        this.monto = monto;
        this.activoHaberTrabajador = activoHaberTrabajador;
    }
    public HaberTrabajadorLiquidacion(HaberTrabajador haberTrabajador, Trabajador trabajador, Date fechaInicioHaber, Date fechaIngresoHaber, Integer numeroCuotas, int monto, boolean activoHaberTrabajador, Set<HaberLiquidacion> haberLiquidacions) {
       this.haberTrabajador = haberTrabajador;
       this.trabajador = trabajador;
       this.fechaInicioHaber = fechaInicioHaber;
       this.fechaIngresoHaber = fechaIngresoHaber;
       this.numeroCuotas = numeroCuotas;
       this.monto = monto;
       this.activoHaberTrabajador = activoHaberTrabajador;
       this.haberLiquidacions = haberLiquidacions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_haber_trabajador_liquidacion", unique=true, nullable=false)
    public Integer getIdHaberTrabajadorLiquidacion() {
        return this.idHaberTrabajadorLiquidacion;
    }
    
    public void setIdHaberTrabajadorLiquidacion(Integer idHaberTrabajadorLiquidacion) {
        this.idHaberTrabajadorLiquidacion = idHaberTrabajadorLiquidacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_haber_trabajador", nullable=false)
    public HaberTrabajador getHaberTrabajador() {
        return this.haberTrabajador;
    }
    
    public void setHaberTrabajador(HaberTrabajador haberTrabajador) {
        this.haberTrabajador = haberTrabajador;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_trabajador", nullable=false)
    public Trabajador getTrabajador() {
        return this.trabajador;
    }
    
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_inicio_haber", nullable=false, length=10)
    public Date getFechaInicioHaber() {
        return this.fechaInicioHaber;
    }
    
    public void setFechaInicioHaber(Date fechaInicioHaber) {
        this.fechaInicioHaber = fechaInicioHaber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_ingreso_haber", nullable=false, length=19)
    public Date getFechaIngresoHaber() {
        return this.fechaIngresoHaber;
    }
    
    public void setFechaIngresoHaber(Date fechaIngresoHaber) {
        this.fechaIngresoHaber = fechaIngresoHaber;
    }

    
    @Column(name="numero_cuotas")
    public Integer getNumeroCuotas() {
        return this.numeroCuotas;
    }
    
    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    
    @Column(name="monto", nullable=false)
    public int getMonto() {
        return this.monto;
    }
    
    public void setMonto(int monto) {
        this.monto = monto;
    }

    
    @Column(name="activo_haber_trabajador", nullable=false)
    public boolean isActivoHaberTrabajador() {
        return this.activoHaberTrabajador;
    }
    
    public void setActivoHaberTrabajador(boolean activoHaberTrabajador) {
        this.activoHaberTrabajador = activoHaberTrabajador;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="haberTrabajadorLiquidacion")
    public Set<HaberLiquidacion> getHaberLiquidacions() {
        return this.haberLiquidacions;
    }
    
    public void setHaberLiquidacions(Set<HaberLiquidacion> haberLiquidacions) {
        this.haberLiquidacions = haberLiquidacions;
    }




}


