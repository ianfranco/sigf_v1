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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Empresa generated by hbm2java
 */
@Entity
@Table(name="empresa"
    ,catalog="sigf_v1"
)
public class Empresa  implements java.io.Serializable {


     private Integer idEmpresa;
     private CajaCompensacion cajaCompensacion;
     private Mutual mutual;
     private String rutEmpresa;
     private String nombreEmpresa;
     private String giro;
     private String direccionEmpresa;
     private String telefonoEmpresa;
     private String fax;
     private String emailEmpresa;
     private Set<CuentaBancariaEmpresa> cuentaBancariaEmpresas = new HashSet<CuentaBancariaEmpresa>(0);
     private Set<LiquidacionEmpresa> liquidacionEmpresas = new HashSet<LiquidacionEmpresa>(0);
     private Set<RelacionLaboral> relacionLaborals = new HashSet<RelacionLaboral>(0);
     private Set<Bus> buses = new HashSet<Bus>(0);
     private Set<RepresentanteEmpresa> representanteEmpresas = new HashSet<RepresentanteEmpresa>(0);

    public Empresa() {
    }

	
    public Empresa(CajaCompensacion cajaCompensacion, Mutual mutual, String rutEmpresa, String nombreEmpresa, String giro) {
        this.cajaCompensacion = cajaCompensacion;
        this.mutual = mutual;
        this.rutEmpresa = rutEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.giro = giro;
    }
    public Empresa(CajaCompensacion cajaCompensacion, Mutual mutual, String rutEmpresa, String nombreEmpresa, String giro, String direccionEmpresa, String telefonoEmpresa, String fax, String emailEmpresa, Set<CuentaBancariaEmpresa> cuentaBancariaEmpresas, Set<LiquidacionEmpresa> liquidacionEmpresas, Set<RelacionLaboral> relacionLaborals, Set<Bus> buses, Set<RepresentanteEmpresa> representanteEmpresas) {
       this.cajaCompensacion = cajaCompensacion;
       this.mutual = mutual;
       this.rutEmpresa = rutEmpresa;
       this.nombreEmpresa = nombreEmpresa;
       this.giro = giro;
       this.direccionEmpresa = direccionEmpresa;
       this.telefonoEmpresa = telefonoEmpresa;
       this.fax = fax;
       this.emailEmpresa = emailEmpresa;
       this.cuentaBancariaEmpresas = cuentaBancariaEmpresas;
       this.liquidacionEmpresas = liquidacionEmpresas;
       this.relacionLaborals = relacionLaborals;
       this.buses = buses;
       this.representanteEmpresas = representanteEmpresas;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_empresa", unique=true, nullable=false)
    public Integer getIdEmpresa() {
        return this.idEmpresa;
    }
    
    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_caja_compensacion", nullable=false)
    public CajaCompensacion getCajaCompensacion() {
        return this.cajaCompensacion;
    }
    
    public void setCajaCompensacion(CajaCompensacion cajaCompensacion) {
        this.cajaCompensacion = cajaCompensacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_mutual", nullable=false)
    public Mutual getMutual() {
        return this.mutual;
    }
    
    public void setMutual(Mutual mutual) {
        this.mutual = mutual;
    }

    
    @Column(name="rut_empresa", nullable=false)
    public String getRutEmpresa() {
        return this.rutEmpresa;
    }
    
    public void setRutEmpresa(String rutEmpresa) {
        this.rutEmpresa = rutEmpresa;
    }

    
    @Column(name="nombre_empresa", nullable=false)
    public String getNombreEmpresa() {
        return this.nombreEmpresa;
    }
    
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    
    @Column(name="giro", nullable=false)
    public String getGiro() {
        return this.giro;
    }
    
    public void setGiro(String giro) {
        this.giro = giro;
    }

    
    @Column(name="direccion_empresa")
    public String getDireccionEmpresa() {
        return this.direccionEmpresa;
    }
    
    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    
    @Column(name="telefono_empresa")
    public String getTelefonoEmpresa() {
        return this.telefonoEmpresa;
    }
    
    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    
    @Column(name="fax")
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }

    
    @Column(name="email_empresa")
    public String getEmailEmpresa() {
        return this.emailEmpresa;
    }
    
    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
    public Set<CuentaBancariaEmpresa> getCuentaBancariaEmpresas() {
        return this.cuentaBancariaEmpresas;
    }
    
    public void setCuentaBancariaEmpresas(Set<CuentaBancariaEmpresa> cuentaBancariaEmpresas) {
        this.cuentaBancariaEmpresas = cuentaBancariaEmpresas;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
    public Set<LiquidacionEmpresa> getLiquidacionEmpresas() {
        return this.liquidacionEmpresas;
    }
    
    public void setLiquidacionEmpresas(Set<LiquidacionEmpresa> liquidacionEmpresas) {
        this.liquidacionEmpresas = liquidacionEmpresas;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
    public Set<RelacionLaboral> getRelacionLaborals() {
        return this.relacionLaborals;
    }
    
    public void setRelacionLaborals(Set<RelacionLaboral> relacionLaborals) {
        this.relacionLaborals = relacionLaborals;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
    public Set<Bus> getBuses() {
        return this.buses;
    }
    
    public void setBuses(Set<Bus> buses) {
        this.buses = buses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
    public Set<RepresentanteEmpresa> getRepresentanteEmpresas() {
        return this.representanteEmpresas;
    }
    
    public void setRepresentanteEmpresas(Set<RepresentanteEmpresa> representanteEmpresas) {
        this.representanteEmpresas = representanteEmpresas;
    }




}


