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
 * Control generated by hbm2java
 */
@Entity
@Table(name="control"
    ,catalog="sigf_v1"
)
public class Control  implements java.io.Serializable {


     private Integer idControl;
     private TipoControl tipoControl;
     private String nombreControl;
     private double latitud;
     private double longitud;
     private int radio;
     private Set<ControlServicio> controlServicios = new HashSet<ControlServicio>(0);

    public Control() {
    }

	
    public Control(TipoControl tipoControl, String nombreControl, double latitud, double longitud, int radio) {
        this.tipoControl = tipoControl;
        this.nombreControl = nombreControl;
        this.latitud = latitud;
        this.longitud = longitud;
        this.radio = radio;
    }
    public Control(TipoControl tipoControl, String nombreControl, double latitud, double longitud, int radio, Set<ControlServicio> controlServicios) {
       this.tipoControl = tipoControl;
       this.nombreControl = nombreControl;
       this.latitud = latitud;
       this.longitud = longitud;
       this.radio = radio;
       this.controlServicios = controlServicios;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_control", unique=true, nullable=false)
    public Integer getIdControl() {
        return this.idControl;
    }
    
    public void setIdControl(Integer idControl) {
        this.idControl = idControl;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tipo_control", nullable=false)
    public TipoControl getTipoControl() {
        return this.tipoControl;
    }
    
    public void setTipoControl(TipoControl tipoControl) {
        this.tipoControl = tipoControl;
    }

    
    @Column(name="nombre_control", nullable=false, length=45)
    public String getNombreControl() {
        return this.nombreControl;
    }
    
    public void setNombreControl(String nombreControl) {
        this.nombreControl = nombreControl;
    }

    
    @Column(name="latitud", nullable=false, precision=12, scale=8)
    public double getLatitud() {
        return this.latitud;
    }
    
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    
    @Column(name="longitud", nullable=false, precision=12, scale=8)
    public double getLongitud() {
        return this.longitud;
    }
    
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    
    @Column(name="radio", nullable=false)
    public int getRadio() {
        return this.radio;
    }
    
    public void setRadio(int radio) {
        this.radio = radio;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="control")
    public Set<ControlServicio> getControlServicios() {
        return this.controlServicios;
    }
    
    public void setControlServicios(Set<ControlServicio> controlServicios) {
        this.controlServicios = controlServicios;
    }




}


