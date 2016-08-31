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
 * TipoContrato generated by hbm2java
 */
@Entity
@Table(name="tipo_contrato"
    ,catalog="sigf_v1"
)
public class TipoContrato  implements java.io.Serializable {


     private Integer idTipoContrato;
     private String nombreTipoContrato;
     private Set<RelacionLaboral> relacionLaborals = new HashSet<RelacionLaboral>(0);

    public TipoContrato() {
    }

	
    public TipoContrato(String nombreTipoContrato) {
        this.nombreTipoContrato = nombreTipoContrato;
    }
    public TipoContrato(String nombreTipoContrato, Set<RelacionLaboral> relacionLaborals) {
       this.nombreTipoContrato = nombreTipoContrato;
       this.relacionLaborals = relacionLaborals;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_tipo_contrato", unique=true, nullable=false)
    public Integer getIdTipoContrato() {
        return this.idTipoContrato;
    }
    
    public void setIdTipoContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    
    @Column(name="nombre_tipo_contrato", nullable=false, length=100)
    public String getNombreTipoContrato() {
        return this.nombreTipoContrato;
    }
    
    public void setNombreTipoContrato(String nombreTipoContrato) {
        this.nombreTipoContrato = nombreTipoContrato;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="tipoContrato")
    public Set<RelacionLaboral> getRelacionLaborals() {
        return this.relacionLaborals;
    }
    
    public void setRelacionLaborals(Set<RelacionLaboral> relacionLaborals) {
        this.relacionLaborals = relacionLaborals;
    }




}


