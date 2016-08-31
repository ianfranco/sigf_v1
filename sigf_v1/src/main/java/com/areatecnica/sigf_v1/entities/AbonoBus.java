package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


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
 * AbonoBus generated by hbm2java
 */
@Entity
@Table(name="abono_bus"
    ,catalog="sigf_v1"
)
public class AbonoBus  implements java.io.Serializable {


     private Integer idAbonoBus;
     private Bus bus;
     private TipoAbono tipoAbono;
     private Date fechaIngresoAbonoBus;
     private Date fechaInicioAbonoBus;
     private Integer numeroCuotasAbonoBus;
     private int montoAbonoBus;
     private boolean activoAbonoBus;
     private Set<AbonoLiquidacion> abonoLiquidacions = new HashSet<AbonoLiquidacion>(0);

    public AbonoBus() {
    }

	
    public AbonoBus(Bus bus, TipoAbono tipoAbono, Date fechaIngresoAbonoBus, Date fechaInicioAbonoBus, int montoAbonoBus, boolean activoAbonoBus) {
        this.bus = bus;
        this.tipoAbono = tipoAbono;
        this.fechaIngresoAbonoBus = fechaIngresoAbonoBus;
        this.fechaInicioAbonoBus = fechaInicioAbonoBus;
        this.montoAbonoBus = montoAbonoBus;
        this.activoAbonoBus = activoAbonoBus;
    }
    public AbonoBus(Bus bus, TipoAbono tipoAbono, Date fechaIngresoAbonoBus, Date fechaInicioAbonoBus, Integer numeroCuotasAbonoBus, int montoAbonoBus, boolean activoAbonoBus, Set<AbonoLiquidacion> abonoLiquidacions) {
       this.bus = bus;
       this.tipoAbono = tipoAbono;
       this.fechaIngresoAbonoBus = fechaIngresoAbonoBus;
       this.fechaInicioAbonoBus = fechaInicioAbonoBus;
       this.numeroCuotasAbonoBus = numeroCuotasAbonoBus;
       this.montoAbonoBus = montoAbonoBus;
       this.activoAbonoBus = activoAbonoBus;
       this.abonoLiquidacions = abonoLiquidacions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_abono_bus", unique=true, nullable=false)
    public Integer getIdAbonoBus() {
        return this.idAbonoBus;
    }
    
    public void setIdAbonoBus(Integer idAbonoBus) {
        this.idAbonoBus = idAbonoBus;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_bus", nullable=false)
    public Bus getBus() {
        return this.bus;
    }
    
    public void setBus(Bus bus) {
        this.bus = bus;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tipo_abono", nullable=false)
    public TipoAbono getTipoAbono() {
        return this.tipoAbono;
    }
    
    public void setTipoAbono(TipoAbono tipoAbono) {
        this.tipoAbono = tipoAbono;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_ingreso_abono_bus", nullable=false, length=19)
    public Date getFechaIngresoAbonoBus() {
        return this.fechaIngresoAbonoBus;
    }
    
    public void setFechaIngresoAbonoBus(Date fechaIngresoAbonoBus) {
        this.fechaIngresoAbonoBus = fechaIngresoAbonoBus;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_inicio_abono_bus", nullable=false, length=10)
    public Date getFechaInicioAbonoBus() {
        return this.fechaInicioAbonoBus;
    }
    
    public void setFechaInicioAbonoBus(Date fechaInicioAbonoBus) {
        this.fechaInicioAbonoBus = fechaInicioAbonoBus;
    }

    
    @Column(name="numero_cuotas_abono_bus")
    public Integer getNumeroCuotasAbonoBus() {
        return this.numeroCuotasAbonoBus;
    }
    
    public void setNumeroCuotasAbonoBus(Integer numeroCuotasAbonoBus) {
        this.numeroCuotasAbonoBus = numeroCuotasAbonoBus;
    }

    
    @Column(name="monto_abono_bus", nullable=false)
    public int getMontoAbonoBus() {
        return this.montoAbonoBus;
    }
    
    public void setMontoAbonoBus(int montoAbonoBus) {
        this.montoAbonoBus = montoAbonoBus;
    }

    
    @Column(name="activo_abono_bus", nullable=false)
    public boolean isActivoAbonoBus() {
        return this.activoAbonoBus;
    }
    
    public void setActivoAbonoBus(boolean activoAbonoBus) {
        this.activoAbonoBus = activoAbonoBus;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="abonoBus")
    public Set<AbonoLiquidacion> getAbonoLiquidacions() {
        return this.abonoLiquidacions;
    }
    
    public void setAbonoLiquidacions(Set<AbonoLiquidacion> abonoLiquidacions) {
        this.abonoLiquidacions = abonoLiquidacions;
    }




}


