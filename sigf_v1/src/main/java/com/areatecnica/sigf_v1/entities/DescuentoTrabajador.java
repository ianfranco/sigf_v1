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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DescuentoTrabajador generated by hbm2java
 */
@Entity
@Table(name="descuento_trabajador"
    ,catalog="sigf_v1"
)
public class DescuentoTrabajador  implements java.io.Serializable {


     private Integer idDescuentoTrabajador;
     private String nombreTipoDescuento;
     private Date fechaIngreso;
     private Integer montoDefecto;
     private Set<DescuentoTrabajadorLiquidacion> descuentoTrabajadorLiquidacions = new HashSet<DescuentoTrabajadorLiquidacion>(0);

    public DescuentoTrabajador() {
    }

	
    public DescuentoTrabajador(String nombreTipoDescuento, Date fechaIngreso) {
        this.nombreTipoDescuento = nombreTipoDescuento;
        this.fechaIngreso = fechaIngreso;
    }
    public DescuentoTrabajador(String nombreTipoDescuento, Date fechaIngreso, Integer montoDefecto, Set<DescuentoTrabajadorLiquidacion> descuentoTrabajadorLiquidacions) {
       this.nombreTipoDescuento = nombreTipoDescuento;
       this.fechaIngreso = fechaIngreso;
       this.montoDefecto = montoDefecto;
       this.descuentoTrabajadorLiquidacions = descuentoTrabajadorLiquidacions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_descuento_trabajador", unique=true, nullable=false)
    public Integer getIdDescuentoTrabajador() {
        return this.idDescuentoTrabajador;
    }
    
    public void setIdDescuentoTrabajador(Integer idDescuentoTrabajador) {
        this.idDescuentoTrabajador = idDescuentoTrabajador;
    }

    
    @Column(name="nombre_tipo_descuento", nullable=false)
    public String getNombreTipoDescuento() {
        return this.nombreTipoDescuento;
    }
    
    public void setNombreTipoDescuento(String nombreTipoDescuento) {
        this.nombreTipoDescuento = nombreTipoDescuento;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="descuentoTrabajador")
    public Set<DescuentoTrabajadorLiquidacion> getDescuentoTrabajadorLiquidacions() {
        return this.descuentoTrabajadorLiquidacions;
    }
    
    public void setDescuentoTrabajadorLiquidacions(Set<DescuentoTrabajadorLiquidacion> descuentoTrabajadorLiquidacions) {
        this.descuentoTrabajadorLiquidacions = descuentoTrabajadorLiquidacions;
    }




}


