package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ParametroInterpolacion generated by hbm2java
 */
@Entity
@Table(name="parametro_interpolacion"
    ,catalog="sigf_v1"
)
public class ParametroInterpolacion  implements java.io.Serializable {


     private Integer idParametroInterpolacion;
     private String nombreParametro;
     private float valorParametro;
     private String unidadParametro;

    public ParametroInterpolacion() {
    }

    public ParametroInterpolacion(String nombreParametro, float valorParametro, String unidadParametro) {
       this.nombreParametro = nombreParametro;
       this.valorParametro = valorParametro;
       this.unidadParametro = unidadParametro;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_parametro_interpolacion", unique=true, nullable=false)
    public Integer getIdParametroInterpolacion() {
        return this.idParametroInterpolacion;
    }
    
    public void setIdParametroInterpolacion(Integer idParametroInterpolacion) {
        this.idParametroInterpolacion = idParametroInterpolacion;
    }

    
    @Column(name="nombre_parametro", nullable=false, length=100)
    public String getNombreParametro() {
        return this.nombreParametro;
    }
    
    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    
    @Column(name="valor_parametro", nullable=false, precision=6, scale=3)
    public float getValorParametro() {
        return this.valorParametro;
    }
    
    public void setValorParametro(float valorParametro) {
        this.valorParametro = valorParametro;
    }

    
    @Column(name="unidad_parametro", nullable=false, length=45)
    public String getUnidadParametro() {
        return this.unidadParametro;
    }
    
    public void setUnidadParametro(String unidadParametro) {
        this.unidadParametro = unidadParametro;
    }




}


