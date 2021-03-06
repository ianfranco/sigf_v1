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
 * ContratoUnidad generated by hbm2java
 */
@Entity
@Table(name="contrato_unidad"
    ,catalog="sigf_v1"
)
public class ContratoUnidad  implements java.io.Serializable {


     private Integer idContratoUnidad;
     private UnidadNegocio unidadNegocio;
     private String identificadorContratoUnidad;
     private Date desdeContratoUnidad;
     private Date hastaContratoUnidad;
     private String nombreContratoUnidad;
     private String descripcionContratoUnidad;

    public ContratoUnidad() {
    }

	
    public ContratoUnidad(UnidadNegocio unidadNegocio, String identificadorContratoUnidad, Date desdeContratoUnidad, Date hastaContratoUnidad, String nombreContratoUnidad) {
        this.unidadNegocio = unidadNegocio;
        this.identificadorContratoUnidad = identificadorContratoUnidad;
        this.desdeContratoUnidad = desdeContratoUnidad;
        this.hastaContratoUnidad = hastaContratoUnidad;
        this.nombreContratoUnidad = nombreContratoUnidad;
    }
    public ContratoUnidad(UnidadNegocio unidadNegocio, String identificadorContratoUnidad, Date desdeContratoUnidad, Date hastaContratoUnidad, String nombreContratoUnidad, String descripcionContratoUnidad) {
       this.unidadNegocio = unidadNegocio;
       this.identificadorContratoUnidad = identificadorContratoUnidad;
       this.desdeContratoUnidad = desdeContratoUnidad;
       this.hastaContratoUnidad = hastaContratoUnidad;
       this.nombreContratoUnidad = nombreContratoUnidad;
       this.descripcionContratoUnidad = descripcionContratoUnidad;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_contrato_unidad", unique=true, nullable=false)
    public Integer getIdContratoUnidad() {
        return this.idContratoUnidad;
    }
    
    public void setIdContratoUnidad(Integer idContratoUnidad) {
        this.idContratoUnidad = idContratoUnidad;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_unidad_negocio", nullable=false)
    public UnidadNegocio getUnidadNegocio() {
        return this.unidadNegocio;
    }
    
    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    
    @Column(name="identificador_contrato_unidad", nullable=false, length=45)
    public String getIdentificadorContratoUnidad() {
        return this.identificadorContratoUnidad;
    }
    
    public void setIdentificadorContratoUnidad(String identificadorContratoUnidad) {
        this.identificadorContratoUnidad = identificadorContratoUnidad;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="desde_contrato_unidad", nullable=false, length=10)
    public Date getDesdeContratoUnidad() {
        return this.desdeContratoUnidad;
    }
    
    public void setDesdeContratoUnidad(Date desdeContratoUnidad) {
        this.desdeContratoUnidad = desdeContratoUnidad;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="hasta_contrato_unidad", nullable=false, length=10)
    public Date getHastaContratoUnidad() {
        return this.hastaContratoUnidad;
    }
    
    public void setHastaContratoUnidad(Date hastaContratoUnidad) {
        this.hastaContratoUnidad = hastaContratoUnidad;
    }

    
    @Column(name="nombre_contrato_unidad", nullable=false, length=100)
    public String getNombreContratoUnidad() {
        return this.nombreContratoUnidad;
    }
    
    public void setNombreContratoUnidad(String nombreContratoUnidad) {
        this.nombreContratoUnidad = nombreContratoUnidad;
    }

    
    @Column(name="descripcion_contrato_unidad", length=400)
    public String getDescripcionContratoUnidad() {
        return this.descripcionContratoUnidad;
    }
    
    public void setDescripcionContratoUnidad(String descripcionContratoUnidad) {
        this.descripcionContratoUnidad = descripcionContratoUnidad;
    }




}


