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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CentroCosto generated by hbm2java
 */
@Entity
@Table(name="centro_costo"
    ,catalog="sigf_v1"
)
public class CentroCosto  implements java.io.Serializable {


     private Integer idCentroCosto;
     private String nombreCentroCosto;
     private Set<CentroCostoTrabajador> centroCostoTrabajadors = new HashSet<CentroCostoTrabajador>(0);

    public CentroCosto() {
    }

	
    public CentroCosto(String nombreCentroCosto) {
        this.nombreCentroCosto = nombreCentroCosto;
    }
    public CentroCosto(String nombreCentroCosto, Set<CentroCostoTrabajador> centroCostoTrabajadors) {
       this.nombreCentroCosto = nombreCentroCosto;
       this.centroCostoTrabajadors = centroCostoTrabajadors;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_centro_costo", unique=true, nullable=false)
    public Integer getIdCentroCosto() {
        return this.idCentroCosto;
    }
    
    public void setIdCentroCosto(Integer idCentroCosto) {
        this.idCentroCosto = idCentroCosto;
    }

    
    @Column(name="nombre_centro_costo", nullable=false, length=100)
    public String getNombreCentroCosto() {
        return this.nombreCentroCosto;
    }
    
    public void setNombreCentroCosto(String nombreCentroCosto) {
        this.nombreCentroCosto = nombreCentroCosto;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="centroCosto")
    public Set<CentroCostoTrabajador> getCentroCostoTrabajadors() {
        return this.centroCostoTrabajadors;
    }
    
    public void setCentroCostoTrabajadors(Set<CentroCostoTrabajador> centroCostoTrabajadors) {
        this.centroCostoTrabajadors = centroCostoTrabajadors;
    }




}


