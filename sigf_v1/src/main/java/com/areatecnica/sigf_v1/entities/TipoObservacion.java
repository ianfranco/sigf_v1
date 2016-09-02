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
 * TipoObservacion generated by hbm2java
 */
@Entity
@Table(name="tipo_observacion"
    ,catalog="sigf_v1"
)
public class TipoObservacion  implements java.io.Serializable {


     private Integer idTipoObservacion;
     private String nombreTipoObservacion;
     private Set<ObservacionTrabajador> observacionTrabajadors = new HashSet<ObservacionTrabajador>(0);

    public TipoObservacion() {
    }

	
    public TipoObservacion(String nombreTipoObservacion) {
        this.nombreTipoObservacion = nombreTipoObservacion;
    }
    public TipoObservacion(String nombreTipoObservacion, Set<ObservacionTrabajador> observacionTrabajadors) {
       this.nombreTipoObservacion = nombreTipoObservacion;
       this.observacionTrabajadors = observacionTrabajadors;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_tipo_observacion", unique=true, nullable=false)
    public Integer getIdTipoObservacion() {
        return this.idTipoObservacion;
    }
    
    public void setIdTipoObservacion(Integer idTipoObservacion) {
        this.idTipoObservacion = idTipoObservacion;
    }

    
    @Column(name="nombre_tipo_observacion", nullable=false, length=100)
    public String getNombreTipoObservacion() {
        return this.nombreTipoObservacion;
    }
    
    public void setNombreTipoObservacion(String nombreTipoObservacion) {
        this.nombreTipoObservacion = nombreTipoObservacion;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="tipoObservacion")
    public Set<ObservacionTrabajador> getObservacionTrabajadors() {
        return this.observacionTrabajadors;
    }
    
    public void setObservacionTrabajadors(Set<ObservacionTrabajador> observacionTrabajadors) {
        this.observacionTrabajadors = observacionTrabajadors;
    }




}

