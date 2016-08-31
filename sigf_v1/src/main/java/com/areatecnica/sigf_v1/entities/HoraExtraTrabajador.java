package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


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
 * HoraExtraTrabajador generated by hbm2java
 */
@Entity
@Table(name="hora_extra_trabajador"
    ,catalog="sigf_v1"
)
public class HoraExtraTrabajador  implements java.io.Serializable {


     private Integer idHorasExtrasTrabajador;
     private Trabajador trabajador;
     private int cantidad;
     private Date fecha;

    public HoraExtraTrabajador() {
    }

	
    public HoraExtraTrabajador(Trabajador trabajador, int cantidad) {
        this.trabajador = trabajador;
        this.cantidad = cantidad;
    }
    public HoraExtraTrabajador(Trabajador trabajador, int cantidad, Date fecha) {
       this.trabajador = trabajador;
       this.cantidad = cantidad;
       this.fecha = fecha;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_horas_extras_trabajador", unique=true, nullable=false)
    public Integer getIdHorasExtrasTrabajador() {
        return this.idHorasExtrasTrabajador;
    }
    
    public void setIdHorasExtrasTrabajador(Integer idHorasExtrasTrabajador) {
        this.idHorasExtrasTrabajador = idHorasExtrasTrabajador;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_trabajador", nullable=false)
    public Trabajador getTrabajador() {
        return this.trabajador;
    }
    
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    
    @Column(name="cantidad", nullable=false)
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha", length=10)
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


