package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Bus generated by hbm2java
 */
@Entity
@Table(name = "bus", catalog = "sigf_v1"
)
public class Bus implements java.io.Serializable {

    private Integer idBus;
    private Empresa empresa;
    private EstadoBus estadoBus;
    private Flota flota;
    private ModeloMarcaBus modeloMarcaBus;
    private Terminal terminal;
    private UnidadNegocio unidadNegocio;
    private int numeroBus;
    private String patente;
    private String administrador;
    private int anio;
    private Date fechaRevisionTecnica;
    private String numeroMotor;
    private String numeroChasis;
    private String carroceria;
    private Boolean activo;
    private Date fechaIngresoBus;
    private Set<BusDevice> busDevices = new HashSet<BusDevice>(0);
    private Set<CargoBus> cargoBuses = new HashSet<CargoBus>(0);
    private Set<BusServicio> busServicios = new HashSet<BusServicio>(0);
    private Set<Guia> guias = new HashSet<Guia>(0);
    private Set<AbonoBus> abonoBuses = new HashSet<AbonoBus>(0);
    private Set<Despacho> despachos = new HashSet<Despacho>(0);

    public Bus() {
    }

    public Bus(Empresa empresa, EstadoBus estadoBus, Flota flota, ModeloMarcaBus modeloMarcaBus, Terminal terminal, UnidadNegocio unidadNegocio, int numeroBus, String patente, String administrador, int anio) {
        this.empresa = empresa;
        this.estadoBus = estadoBus;
        this.flota = flota;
        this.modeloMarcaBus = modeloMarcaBus;
        this.terminal = terminal;
        this.unidadNegocio = unidadNegocio;
        this.numeroBus = numeroBus;
        this.patente = patente;
        this.administrador = administrador;
        this.anio = anio;
    }

    public Bus(Empresa empresa, EstadoBus estadoBus, Flota flota, ModeloMarcaBus modeloMarcaBus, Terminal terminal, UnidadNegocio unidadNegocio, int numeroBus, String patente, String administrador, int anio, Date fechaRevisionTecnica, String numeroMotor, String numeroChasis, String carroceria, Boolean activo, Date fechaIngresoBus, Set<BusDevice> busDevices, Set<CargoBus> cargoBuses, Set<BusServicio> busServicios, Set<Guia> guias, Set<AbonoBus> abonoBuses, Set<Despacho> despachos) {
        this.empresa = empresa;
        this.estadoBus = estadoBus;
        this.flota = flota;
        this.modeloMarcaBus = modeloMarcaBus;
        this.terminal = terminal;
        this.unidadNegocio = unidadNegocio;
        this.numeroBus = numeroBus;
        this.patente = patente;
        this.administrador = administrador;
        this.anio = anio;
        this.fechaRevisionTecnica = fechaRevisionTecnica;
        this.numeroMotor = numeroMotor;
        this.numeroChasis = numeroChasis;
        this.carroceria = carroceria;
        this.activo = activo;
        this.fechaIngresoBus = fechaIngresoBus;
        this.busDevices = busDevices;
        this.cargoBuses = cargoBuses;
        this.busServicios = busServicios;
        this.guias = guias;
        this.abonoBuses = abonoBuses;
        this.despachos = despachos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_bus", unique = true, nullable = false)
    public Integer getIdBus() {
        return this.idBus;
    }

    public void setIdBus(Integer idBus) {
        this.idBus = idBus;
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
    @JoinColumn(name = "id_estado_bus", nullable = false)
    public EstadoBus getEstadoBus() {
        return this.estadoBus;
    }

    public void setEstadoBus(EstadoBus estadoBus) {
        this.estadoBus = estadoBus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_flota", nullable = false)
    public Flota getFlota() {
        return this.flota;
    }

    public void setFlota(Flota flota) {
        this.flota = flota;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false)
    public ModeloMarcaBus getModeloMarcaBus() {
        return this.modeloMarcaBus;
    }

    public void setModeloMarcaBus(ModeloMarcaBus modeloMarcaBus) {
        this.modeloMarcaBus = modeloMarcaBus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_terminal", nullable = false)
    public Terminal getTerminal() {
        return this.terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_negocio", nullable = false)
    public UnidadNegocio getUnidadNegocio() {
        return this.unidadNegocio;
    }

    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    @Column(name = "numero_bus", nullable = false)
    public int getNumeroBus() {
        return this.numeroBus;
    }

    public void setNumeroBus(int numeroBus) {
        this.numeroBus = numeroBus;
    }

    @Column(name = "patente", nullable = false, length = 45)
    public String getPatente() {
        return this.patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }
    
    @Column(name = "administrador", nullable = false, length = 100)
    public String getAdministrador() {
        return this.administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    @Column(name = "anio", nullable = false)
    public int getAnio() {
        return this.anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_revision_tecnica", length = 10)
    public Date getFechaRevisionTecnica() {
        return this.fechaRevisionTecnica;
    }

    public void setFechaRevisionTecnica(Date fechaRevisionTecnica) {
        this.fechaRevisionTecnica = fechaRevisionTecnica;
    }

    @Column(name = "numero_motor", length = 45)
    public String getNumeroMotor() {
        return this.numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    @Column(name = "numero_chasis", length = 45)
    public String getNumeroChasis() {
        return this.numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    @Column(name = "carroceria", length = 45)
    public String getCarroceria() {
        return this.carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    @Column(name = "activo")
    public Boolean getActivo() {
        return this.activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso_bus", length = 19)
    public Date getFechaIngresoBus() {
        return this.fechaIngresoBus;
    }

    public void setFechaIngresoBus(Date fechaIngresoBus) {
        this.fechaIngresoBus = fechaIngresoBus;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    public Set<BusDevice> getBusDevices() {
        return this.busDevices;
    }

    public void setBusDevices(Set<BusDevice> busDevices) {
        this.busDevices = busDevices;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    public Set<CargoBus> getCargoBuses() {
        return this.cargoBuses;
    }

    public void setCargoBuses(Set<CargoBus> cargoBuses) {
        this.cargoBuses = cargoBuses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    public Set<BusServicio> getBusServicios() {
        return this.busServicios;
    }

    public void setBusServicios(Set<BusServicio> busServicios) {
        this.busServicios = busServicios;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    public Set<Guia> getGuias() {
        return this.guias;
    }

    public void setGuias(Set<Guia> guias) {
        this.guias = guias;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    public Set<AbonoBus> getAbonoBuses() {
        return this.abonoBuses;
    }

    public void setAbonoBuses(Set<AbonoBus> abonoBuses) {
        this.abonoBuses = abonoBuses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    public Set<Despacho> getDespachos() {
        return this.despachos;
    }

    public void setDespachos(Set<Despacho> despachos) {
        this.despachos = despachos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBus != null ? idBus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bus)) {
            return false;
        }
        Bus other = (Bus) object;
        if ((this.idBus == null && other.idBus != null) || (this.idBus != null && !this.idBus.equals(other.idBus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroBus) + "-" + this.unidadNegocio.getNumeroUnidadNegocio();
    }

}
