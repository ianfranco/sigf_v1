package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


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

/**
 * Calle generated by hbm2java
 */
@Entity
@Table(name="calle"
    ,catalog="sigf_v1"
)
public class Calle  implements java.io.Serializable {


     private Integer idCalle;
     private Comuna comuna;
     private String nombreCalle;
     private Set<CalleServicio> calleServicios = new HashSet<CalleServicio>(0);

    public Calle() {
    }

	
    public Calle(Comuna comuna, String nombreCalle) {
        this.comuna = comuna;
        this.nombreCalle = nombreCalle;
    }
    public Calle(Comuna comuna, String nombreCalle, Set<CalleServicio> calleServicios) {
       this.comuna = comuna;
       this.nombreCalle = nombreCalle;
       this.calleServicios = calleServicios;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_calle", unique=true, nullable=false)
    public Integer getIdCalle() {
        return this.idCalle;
    }
    
    public void setIdCalle(Integer idCalle) {
        this.idCalle = idCalle;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_comuna", nullable=false)
    public Comuna getComuna() {
        return this.comuna;
    }
    
    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    
    @Column(name="nombre_calle", nullable=false, length=45)
    public String getNombreCalle() {
        return this.nombreCalle;
    }
    
    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="calle")
    public Set<CalleServicio> getCalleServicios() {
        return this.calleServicios;
    }
    
    public void setCalleServicios(Set<CalleServicio> calleServicios) {
        this.calleServicios = calleServicios;
    }




}


