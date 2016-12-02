package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RelacionLaboral generated by hbm2java
 */
@Entity
@Table(name = "relacion_laboral", catalog = "sigf_v1"
)
public class RelacionLaboral implements java.io.Serializable {

    private Integer idRelacionLaboral;
    private Empresa empresa;
    private TipoContrato tipoContrato;
    private TipoTrabajador tipoTrabajador;
    private Trabajador trabajador;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoContrato_1;
    private Integer sueldoBase;
    private String rutaArchivoRespaldo;
    private Boolean estado;
    private Integer idOperador;
    private Set<FiniquitoRelacionLaboral> finiquitoRelacionLaborals = new HashSet<FiniquitoRelacionLaboral>(0);
    private Set<LiquidacionSueldo> liquidacionSueldos = new HashSet<LiquidacionSueldo>(0);

    public RelacionLaboral() {
    }

    public RelacionLaboral(Empresa empresa, TipoContrato tipoContrato, TipoTrabajador tipoTrabajador, Trabajador trabajador) {
        this.empresa = empresa;
        this.tipoContrato = tipoContrato;
        this.tipoTrabajador = tipoTrabajador;
        this.trabajador = trabajador;
    }

    public RelacionLaboral(Empresa empresa, TipoContrato tipoContrato, TipoTrabajador tipoTrabajador, Trabajador trabajador, Date fechaInicio, Date fechaFin, String tipoContrato_1, Integer sueldoBase, String rutaArchivoRespaldo, Boolean estado, Integer idOperador, Set<FiniquitoRelacionLaboral> finiquitoRelacionLaborals, Set<LiquidacionSueldo> liquidacionSueldos) {
        this.empresa = empresa;
        this.tipoContrato = tipoContrato;
        this.tipoTrabajador = tipoTrabajador;
        this.trabajador = trabajador;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoContrato_1 = tipoContrato_1;
        this.sueldoBase = sueldoBase;
        this.rutaArchivoRespaldo = rutaArchivoRespaldo;
        this.estado = estado;
        this.idOperador = idOperador;
        this.finiquitoRelacionLaborals = finiquitoRelacionLaborals;
        this.liquidacionSueldos = liquidacionSueldos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_relacion_laboral", unique = true, nullable = false)
    public Integer getIdRelacionLaboral() {
        return this.idRelacionLaboral;
    }

    public void setIdRelacionLaboral(Integer idRelacionLaboral) {
        this.idRelacionLaboral = idRelacionLaboral;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contrato", nullable = false)
    public TipoContrato getTipoContrato() {
        return this.tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_trabajador", nullable = false)
    public TipoTrabajador getTipoTrabajador() {
        return this.tipoTrabajador;
    }

    public void setTipoTrabajador(TipoTrabajador tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador", nullable = false)
    public Trabajador getTrabajador() {
        return this.trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio", length = 10)
    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin", length = 10)
    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Column(name = "tipo_contrato")
    public String getTipoContrato_1() {
        return this.tipoContrato_1;
    }

    public void setTipoContrato_1(String tipoContrato_1) {
        this.tipoContrato_1 = tipoContrato_1;
    }

    @Column(name = "sueldo_base")
    public Integer getSueldoBase() {
        return this.sueldoBase;
    }

    public void setSueldoBase(Integer sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    @Column(name = "ruta_archivo_respaldo")
    public String getRutaArchivoRespaldo() {
        return this.rutaArchivoRespaldo;
    }

    public void setRutaArchivoRespaldo(String rutaArchivoRespaldo) {
        this.rutaArchivoRespaldo = rutaArchivoRespaldo;
    }

    @Column(name = "estado")
    public Boolean getEstado() {
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Column(name = "id_operador")
    public Integer getIdOperador() {
        return this.idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relacionLaboral")
    public Set<FiniquitoRelacionLaboral> getFiniquitoRelacionLaborals() {
        return this.finiquitoRelacionLaborals;
    }

    public void setFiniquitoRelacionLaborals(Set<FiniquitoRelacionLaboral> finiquitoRelacionLaborals) {
        this.finiquitoRelacionLaborals = finiquitoRelacionLaborals;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relacionLaboral")
    public Set<LiquidacionSueldo> getLiquidacionSueldos() {
        return this.liquidacionSueldos;
    }

    public void setLiquidacionSueldos(Set<LiquidacionSueldo> liquidacionSueldos) {
        this.liquidacionSueldos = liquidacionSueldos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idRelacionLaboral);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RelacionLaboral other = (RelacionLaboral) obj;
        if (!Objects.equals(this.idRelacionLaboral, other.idRelacionLaboral)) {
            return false;
        }
        return true;
    }

}
