package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
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

/**
 * InstitucionPrevision generated by hbm2java
 */
@Entity
@Table(name="institucion_prevision"
    ,catalog="sigf_v1"
)
public class InstitucionPrevision  implements java.io.Serializable {


     private Integer idInstitucionPrevision;
     private String nombreInstitucionPrevision;
     private BigDecimal comision;
     private BigDecimal porcentajeDescuento;
     private Set<Trabajador> trabajadors = new HashSet<Trabajador>(0);

    public InstitucionPrevision() {
    }

	
    public InstitucionPrevision(String nombreInstitucionPrevision, BigDecimal porcentajeDescuento) {
        this.nombreInstitucionPrevision = nombreInstitucionPrevision;
        this.porcentajeDescuento = porcentajeDescuento;
    }
    public InstitucionPrevision(String nombreInstitucionPrevision, BigDecimal comision, BigDecimal porcentajeDescuento, Set<Trabajador> trabajadors) {
       this.nombreInstitucionPrevision = nombreInstitucionPrevision;
       this.comision = comision;
       this.porcentajeDescuento = porcentajeDescuento;
       this.trabajadors = trabajadors;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_institucion_prevision", unique=true, nullable=false)
    public Integer getIdInstitucionPrevision() {
        return this.idInstitucionPrevision;
    }
    
    public void setIdInstitucionPrevision(Integer idInstitucionPrevision) {
        this.idInstitucionPrevision = idInstitucionPrevision;
    }

    
    @Column(name="nombre_institucion_prevision", nullable=false)
    public String getNombreInstitucionPrevision() {
        return this.nombreInstitucionPrevision;
    }
    
    public void setNombreInstitucionPrevision(String nombreInstitucionPrevision) {
        this.nombreInstitucionPrevision = nombreInstitucionPrevision;
    }

    
    @Column(name="comision", precision=10, scale=5)
    public BigDecimal getComision() {
        return this.comision;
    }
    
    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    
    @Column(name="porcentaje_descuento", nullable=false, precision=10, scale=5)
    public BigDecimal getPorcentajeDescuento() {
        return this.porcentajeDescuento;
    }
    
    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="institucionPrevision")
    public Set<Trabajador> getTrabajadors() {
        return this.trabajadors;
    }
    
    public void setTrabajadors(Set<Trabajador> trabajadors) {
        this.trabajadors = trabajadors;
    }




}


