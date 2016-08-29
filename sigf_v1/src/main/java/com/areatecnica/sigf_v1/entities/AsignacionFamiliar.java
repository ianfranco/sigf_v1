package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


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
 * AsignacionFamiliar generated by hbm2java
 */
@Entity
@Table(name="asignacion_familiar"
    ,catalog="sigf_v1"
)
public class AsignacionFamiliar  implements java.io.Serializable {


     private Integer idAsignacionFamiliar;
     private String codigoAsignacionFamiliar;
     private String tramo;
     private int monto;
     private int desde;
     private int hasta;
     private Integer anio;
     private Set<GrupoAsignacionTrabajador> grupoAsignacionTrabajadors = new HashSet<GrupoAsignacionTrabajador>(0);

    public AsignacionFamiliar() {
    }

	
    public AsignacionFamiliar(String codigoAsignacionFamiliar, String tramo, int monto, int desde, int hasta) {
        this.codigoAsignacionFamiliar = codigoAsignacionFamiliar;
        this.tramo = tramo;
        this.monto = monto;
        this.desde = desde;
        this.hasta = hasta;
    }
    public AsignacionFamiliar(String codigoAsignacionFamiliar, String tramo, int monto, int desde, int hasta, Integer anio, Set<GrupoAsignacionTrabajador> grupoAsignacionTrabajadors) {
       this.codigoAsignacionFamiliar = codigoAsignacionFamiliar;
       this.tramo = tramo;
       this.monto = monto;
       this.desde = desde;
       this.hasta = hasta;
       this.anio = anio;
       this.grupoAsignacionTrabajadors = grupoAsignacionTrabajadors;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_asignacion_familiar", unique=true, nullable=false)
    public Integer getIdAsignacionFamiliar() {
        return this.idAsignacionFamiliar;
    }
    
    public void setIdAsignacionFamiliar(Integer idAsignacionFamiliar) {
        this.idAsignacionFamiliar = idAsignacionFamiliar;
    }

    
    @Column(name="codigo_asignacion_familiar", nullable=false, length=45)
    public String getCodigoAsignacionFamiliar() {
        return this.codigoAsignacionFamiliar;
    }
    
    public void setCodigoAsignacionFamiliar(String codigoAsignacionFamiliar) {
        this.codigoAsignacionFamiliar = codigoAsignacionFamiliar;
    }

    
    @Column(name="tramo", nullable=false)
    public String getTramo() {
        return this.tramo;
    }
    
    public void setTramo(String tramo) {
        this.tramo = tramo;
    }

    
    @Column(name="monto", nullable=false)
    public int getMonto() {
        return this.monto;
    }
    
    public void setMonto(int monto) {
        this.monto = monto;
    }

    
    @Column(name="desde", nullable=false)
    public int getDesde() {
        return this.desde;
    }
    
    public void setDesde(int desde) {
        this.desde = desde;
    }

    
    @Column(name="hasta", nullable=false)
    public int getHasta() {
        return this.hasta;
    }
    
    public void setHasta(int hasta) {
        this.hasta = hasta;
    }

    
    @Column(name="anio")
    public Integer getAnio() {
        return this.anio;
    }
    
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="asignacionFamiliar")
    public Set<GrupoAsignacionTrabajador> getGrupoAsignacionTrabajadors() {
        return this.grupoAsignacionTrabajadors;
    }
    
    public void setGrupoAsignacionTrabajadors(Set<GrupoAsignacionTrabajador> grupoAsignacionTrabajadors) {
        this.grupoAsignacionTrabajadors = grupoAsignacionTrabajadors;
    }




}


