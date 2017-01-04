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
@Table(name = "tipo_contrato", catalog = "sigf_v1"
)
public class TipoContrato implements java.io.Serializable {

    private Integer idTipoContrato;
    private String nombreTipoContrato;
    private Set<RelacionLaboral> relacionLaborals = new HashSet<RelacionLaboral>(0);
    private Set<LiquidacionSueldo> liquidacionSueldos = new HashSet<LiquidacionSueldo>(0);

    public TipoContrato() {
    }

    public TipoContrato(String nombreTipoContrato) {
        this.nombreTipoContrato = nombreTipoContrato;
    }

    public TipoContrato(String nombreTipoContrato, Set<RelacionLaboral> relacionLaborals, Set<LiquidacionSueldo> liquidacionSueldos) {
       this.nombreTipoContrato = nombreTipoContrato;
       this.relacionLaborals = relacionLaborals;
       this.liquidacionSueldos = liquidacionSueldos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_tipo_contrato", unique = true, nullable = false)
    public Integer getIdTipoContrato() {
        return this.idTipoContrato;
    }

    public void setIdTipoContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    @Column(name = "nombre_tipo_contrato", nullable = false, length = 100)
    public String getNombreTipoContrato() {
        return this.nombreTipoContrato;
    }

    public void setNombreTipoContrato(String nombreTipoContrato) {
        this.nombreTipoContrato = nombreTipoContrato;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoContrato")
    public Set<RelacionLaboral> getRelacionLaborals() {
        return this.relacionLaborals;
    }

    public void setRelacionLaborals(Set<RelacionLaboral> relacionLaborals) {
        this.relacionLaborals = relacionLaborals;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoContrato")
    public Set<LiquidacionSueldo> getLiquidacionSueldos() {
        return this.liquidacionSueldos;
    }
    
    public void setLiquidacionSueldos(Set<LiquidacionSueldo> liquidacionSueldos) {
        this.liquidacionSueldos = liquidacionSueldos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoContrato != null ? idTipoContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoContrato)) {
            return false;
        }
        TipoContrato other = (TipoContrato) object;
        if ((this.idTipoContrato == null && other.idTipoContrato != null) || (this.idTipoContrato != null && !this.idTipoContrato.equals(other.idTipoContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreTipoContrato;
    }

}
