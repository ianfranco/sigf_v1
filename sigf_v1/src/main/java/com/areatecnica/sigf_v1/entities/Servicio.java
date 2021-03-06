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
 * Servicio generated by hbm2java
 */
@Entity
@Table(name="servicio"
    ,catalog="sigf_v1"
)
public class Servicio  implements java.io.Serializable {


     private Integer idServicio;
     private Terminal terminal;
     private UnidadNegocio unidadNegocio;
     private int numeroServicio;
     private int longitud;
     private String nombreServicio;
     private String origenServicio;
     private String destinoServicio;
     private String folioServicio;
     private Set<CalleServicio> calleServicios = new HashSet<CalleServicio>(0);
     private Set<FrecuenciaServicio> frecuenciaServicios = new HashSet<FrecuenciaServicio>(0);
     private Set<TarifaServicio> tarifaServicios = new HashSet<TarifaServicio>(0);
     private Set<ServicioProcesoRecaudacion> servicioProcesoRecaudacions = new HashSet<ServicioProcesoRecaudacion>(0);
     private Set<ControlServicio> controlServicios = new HashSet<ControlServicio>(0);
     private Set<BusServicio> busServicios = new HashSet<BusServicio>(0);
     private Set<Despacho> despachos = new HashSet<Despacho>(0);
     private Set<HorarioServicio> horarioServicios = new HashSet<HorarioServicio>(0);

    public Servicio() {
    }

	
    public Servicio(Terminal terminal, UnidadNegocio unidadNegocio, int numeroServicio, int longitud, String nombreServicio, String origenServicio, String destinoServicio) {
        this.terminal = terminal;
        this.unidadNegocio = unidadNegocio;
        this.numeroServicio = numeroServicio;
        this.longitud = longitud;
        this.nombreServicio = nombreServicio;
        this.origenServicio = origenServicio;
        this.destinoServicio = destinoServicio;
    }
    public Servicio(Terminal terminal, UnidadNegocio unidadNegocio, int numeroServicio, int longitud, String nombreServicio, String origenServicio, String destinoServicio, String folioServicio, Set<CalleServicio> calleServicios, Set<FrecuenciaServicio> frecuenciaServicios, Set<TarifaServicio> tarifaServicios, Set<ServicioProcesoRecaudacion> servicioProcesoRecaudacions, Set<ControlServicio> controlServicios, Set<BusServicio> busServicios, Set<Despacho> despachos, Set<HorarioServicio> horarioServicios) {
       this.terminal = terminal;
       this.unidadNegocio = unidadNegocio;
       this.numeroServicio = numeroServicio;
       this.longitud = longitud;
       this.nombreServicio = nombreServicio;
       this.origenServicio = origenServicio;
       this.destinoServicio = destinoServicio;
       this.folioServicio = folioServicio;
       this.calleServicios = calleServicios;
       this.frecuenciaServicios = frecuenciaServicios;
       this.tarifaServicios = tarifaServicios;
       this.servicioProcesoRecaudacions = servicioProcesoRecaudacions;
       this.controlServicios = controlServicios;
       this.busServicios = busServicios;
       this.despachos = despachos;
       this.horarioServicios = horarioServicios;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_servicio", unique=true, nullable=false)
    public Integer getIdServicio() {
        return this.idServicio;
    }
    
    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_terminal", nullable=false)
    public Terminal getTerminal() {
        return this.terminal;
    }
    
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_unidad", nullable=false)
    public UnidadNegocio getUnidadNegocio() {
        return this.unidadNegocio;
    }
    
    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    
    @Column(name="numero_servicio", nullable=false)
    public int getNumeroServicio() {
        return this.numeroServicio;
    }
    
    public void setNumeroServicio(int numeroServicio) {
        this.numeroServicio = numeroServicio;
    }

    
    @Column(name="longitud", nullable=false)
    public int getLongitud() {
        return this.longitud;
    }
    
    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    
    @Column(name="nombre_servicio", nullable=false, length=45)
    public String getNombreServicio() {
        return this.nombreServicio;
    }
    
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    
    @Column(name="origen_servicio", nullable=false, length=45)
    public String getOrigenServicio() {
        return this.origenServicio;
    }
    
    public void setOrigenServicio(String origenServicio) {
        this.origenServicio = origenServicio;
    }

    
    @Column(name="destino_servicio", nullable=false, length=45)
    public String getDestinoServicio() {
        return this.destinoServicio;
    }
    
    public void setDestinoServicio(String destinoServicio) {
        this.destinoServicio = destinoServicio;
    }

    
    @Column(name="folio_servicio", length=45)
    public String getFolioServicio() {
        return this.folioServicio;
    }
    
    public void setFolioServicio(String folioServicio) {
        this.folioServicio = folioServicio;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<CalleServicio> getCalleServicios() {
        return this.calleServicios;
    }
    
    public void setCalleServicios(Set<CalleServicio> calleServicios) {
        this.calleServicios = calleServicios;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<FrecuenciaServicio> getFrecuenciaServicios() {
        return this.frecuenciaServicios;
    }
    
    public void setFrecuenciaServicios(Set<FrecuenciaServicio> frecuenciaServicios) {
        this.frecuenciaServicios = frecuenciaServicios;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<TarifaServicio> getTarifaServicios() {
        return this.tarifaServicios;
    }
    
    public void setTarifaServicios(Set<TarifaServicio> tarifaServicios) {
        this.tarifaServicios = tarifaServicios;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<ServicioProcesoRecaudacion> getServicioProcesoRecaudacions() {
        return this.servicioProcesoRecaudacions;
    }
    
    public void setServicioProcesoRecaudacions(Set<ServicioProcesoRecaudacion> servicioProcesoRecaudacions) {
        this.servicioProcesoRecaudacions = servicioProcesoRecaudacions;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<ControlServicio> getControlServicios() {
        return this.controlServicios;
    }
    
    public void setControlServicios(Set<ControlServicio> controlServicios) {
        this.controlServicios = controlServicios;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<BusServicio> getBusServicios() {
        return this.busServicios;
    }
    
    public void setBusServicios(Set<BusServicio> busServicios) {
        this.busServicios = busServicios;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<Despacho> getDespachos() {
        return this.despachos;
    }
    
    public void setDespachos(Set<Despacho> despachos) {
        this.despachos = despachos;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="servicio")
    public Set<HorarioServicio> getHorarioServicios() {
        return this.horarioServicios;
    }
    
    public void setHorarioServicios(Set<HorarioServicio> horarioServicios) {
        this.horarioServicios = horarioServicios;
    }




}


