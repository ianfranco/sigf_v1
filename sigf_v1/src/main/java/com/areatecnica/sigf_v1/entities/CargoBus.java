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
 * CargoBus generated by hbm2java
 */
@Entity
@Table(name="cargo_bus"
    ,catalog="sigf_v1"
)
public class CargoBus  implements java.io.Serializable {


     private Integer idCargoBus;
     private Bus bus;
     private TipoCargo tipoCargo;
     private Date fechaIngresoCargoBus;
     private Date fechaInicioCargoBus;
     private Integer numeroCuotasCargoBus;
     private int montoCargoBus;
     private boolean activoCargoBus;
     private Set<CargoLiquidacion> cargoLiquidacions = new HashSet<CargoLiquidacion>(0);

    public CargoBus() {
    }

	
    public CargoBus(Bus bus, TipoCargo tipoCargo, Date fechaIngresoCargoBus, Date fechaInicioCargoBus, int montoCargoBus, boolean activoCargoBus) {
        this.bus = bus;
        this.tipoCargo = tipoCargo;
        this.fechaIngresoCargoBus = fechaIngresoCargoBus;
        this.fechaInicioCargoBus = fechaInicioCargoBus;
        this.montoCargoBus = montoCargoBus;
        this.activoCargoBus = activoCargoBus;
    }
    public CargoBus(Bus bus, TipoCargo tipoCargo, Date fechaIngresoCargoBus, Date fechaInicioCargoBus, Integer numeroCuotasCargoBus, int montoCargoBus, boolean activoCargoBus, Set<CargoLiquidacion> cargoLiquidacions) {
       this.bus = bus;
       this.tipoCargo = tipoCargo;
       this.fechaIngresoCargoBus = fechaIngresoCargoBus;
       this.fechaInicioCargoBus = fechaInicioCargoBus;
       this.numeroCuotasCargoBus = numeroCuotasCargoBus;
       this.montoCargoBus = montoCargoBus;
       this.activoCargoBus = activoCargoBus;
       this.cargoLiquidacions = cargoLiquidacions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_cargo_bus", unique=true, nullable=false)
    public Integer getIdCargoBus() {
        return this.idCargoBus;
    }
    
    public void setIdCargoBus(Integer idCargoBus) {
        this.idCargoBus = idCargoBus;
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
    @JoinColumn(name="id_tipo_cargo", nullable=false)
    public TipoCargo getTipoCargo() {
        return this.tipoCargo;
    }
    
    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_ingreso_cargo_bus", nullable=false, length=19)
    public Date getFechaIngresoCargoBus() {
        return this.fechaIngresoCargoBus;
    }
    
    public void setFechaIngresoCargoBus(Date fechaIngresoCargoBus) {
        this.fechaIngresoCargoBus = fechaIngresoCargoBus;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_inicio_cargo_bus", nullable=false, length=10)
    public Date getFechaInicioCargoBus() {
        return this.fechaInicioCargoBus;
    }
    
    public void setFechaInicioCargoBus(Date fechaInicioCargoBus) {
        this.fechaInicioCargoBus = fechaInicioCargoBus;
    }

    
    @Column(name="numero_cuotas_cargo_bus")
    public Integer getNumeroCuotasCargoBus() {
        return this.numeroCuotasCargoBus;
    }
    
    public void setNumeroCuotasCargoBus(Integer numeroCuotasCargoBus) {
        this.numeroCuotasCargoBus = numeroCuotasCargoBus;
    }

    
    @Column(name="monto_cargo_bus", nullable=false)
    public int getMontoCargoBus() {
        return this.montoCargoBus;
    }
    
    public void setMontoCargoBus(int montoCargoBus) {
        this.montoCargoBus = montoCargoBus;
    }

    
    @Column(name="activo_cargo_bus", nullable=false)
    public boolean isActivoCargoBus() {
        return this.activoCargoBus;
    }
    
    public void setActivoCargoBus(boolean activoCargoBus) {
        this.activoCargoBus = activoCargoBus;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="cargoBus")
    public Set<CargoLiquidacion> getCargoLiquidacions() {
        return this.cargoLiquidacions;
    }
    
    public void setCargoLiquidacions(Set<CargoLiquidacion> cargoLiquidacions) {
        this.cargoLiquidacions = cargoLiquidacions;
    }




}


