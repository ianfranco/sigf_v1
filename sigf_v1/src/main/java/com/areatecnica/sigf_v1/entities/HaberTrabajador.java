package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HaberTrabajador generated by hbm2java
 */
@Entity
@Table(name="haber_trabajador"
    ,catalog="sigf_v1"
)
public class HaberTrabajador  implements java.io.Serializable {


     private Integer idHaberTrabajador;
     private String nombreTipoHaber;
     private Date fechaIngreso;
     private Integer montoDefecto;
     private boolean imponible;
     private Set<HaberTrabajadorLiquidacion> haberTrabajadorLiquidacions = new HashSet<HaberTrabajadorLiquidacion>(0);

    public HaberTrabajador() {
    }

	
    public HaberTrabajador(String nombreTipoHaber, Date fechaIngreso, boolean imponible) {
        this.nombreTipoHaber = nombreTipoHaber;
        this.fechaIngreso = fechaIngreso;
        this.imponible = imponible;
    }
    public HaberTrabajador(String nombreTipoHaber, Date fechaIngreso, Integer montoDefecto, boolean imponible, Set<HaberTrabajadorLiquidacion> haberTrabajadorLiquidacions) {
       this.nombreTipoHaber = nombreTipoHaber;
       this.fechaIngreso = fechaIngreso;
       this.montoDefecto = montoDefecto;
       this.imponible = imponible;
       this.haberTrabajadorLiquidacions = haberTrabajadorLiquidacions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_haber_trabajador", unique=true, nullable=false)
    public Integer getIdHaberTrabajador() {
        return this.idHaberTrabajador;
    }
    
    public void setIdHaberTrabajador(Integer idHaberTrabajador) {
        this.idHaberTrabajador = idHaberTrabajador;
    }

    
    @Column(name="nombre_tipo_haber", nullable=false)
    public String getNombreTipoHaber() {
        return this.nombreTipoHaber;
    }
    
    public void setNombreTipoHaber(String nombreTipoHaber) {
        this.nombreTipoHaber = nombreTipoHaber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_ingreso", nullable=false, length=19)
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    
    @Column(name="monto_defecto")
    public Integer getMontoDefecto() {
        return this.montoDefecto;
    }
    
    public void setMontoDefecto(Integer montoDefecto) {
        this.montoDefecto = montoDefecto;
    }

    
    @Column(name="imponible", nullable=false)
    public boolean isImponible() {
        return this.imponible;
    }
    
    public void setImponible(boolean imponible) {
        this.imponible = imponible;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="haberTrabajador")
    public Set<HaberTrabajadorLiquidacion> getHaberTrabajadorLiquidacions() {
        return this.haberTrabajadorLiquidacions;
    }
    
    public void setHaberTrabajadorLiquidacions(Set<HaberTrabajadorLiquidacion> haberTrabajadorLiquidacions) {
        this.haberTrabajadorLiquidacions = haberTrabajadorLiquidacions;
    }

    @Override
    public String toString() {
        return nombreTipoHaber;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idHaberTrabajador);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HaberTrabajador other = (HaberTrabajador) obj;
        if (!Objects.equals(this.idHaberTrabajador, other.idHaberTrabajador)) {
            return false;
        }
        return true;
    }




}


