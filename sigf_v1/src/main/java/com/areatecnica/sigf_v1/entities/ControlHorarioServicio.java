package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ControlHorarioServicio generated by hbm2java
 */
@Entity
@Table(name="control_horario_servicio"
    ,catalog="sigf_v1"
)
public class ControlHorarioServicio  implements java.io.Serializable {


     private Integer idControlHorarioServicio;
     private ControlServicio controlServicio;
     private HorarioServicio horarioServicio;
     private int tiempo;
     private int multa;
     private int peso;

    public ControlHorarioServicio() {
    }

    public ControlHorarioServicio(ControlServicio controlServicio, HorarioServicio horarioServicio, int tiempo, int multa, int peso) {
       this.controlServicio = controlServicio;
       this.horarioServicio = horarioServicio;
       this.tiempo = tiempo;
       this.multa = multa;
       this.peso = peso;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_control_horario_servicio", unique=true, nullable=false)
    public Integer getIdControlHorarioServicio() {
        return this.idControlHorarioServicio;
    }
    
    public void setIdControlHorarioServicio(Integer idControlHorarioServicio) {
        this.idControlHorarioServicio = idControlHorarioServicio;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_control_servicio", nullable=false)
    public ControlServicio getControlServicio() {
        return this.controlServicio;
    }
    
    public void setControlServicio(ControlServicio controlServicio) {
        this.controlServicio = controlServicio;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_horario_servicio", nullable=false)
    public HorarioServicio getHorarioServicio() {
        return this.horarioServicio;
    }
    
    public void setHorarioServicio(HorarioServicio horarioServicio) {
        this.horarioServicio = horarioServicio;
    }

    
    @Column(name="tiempo", nullable=false)
    public int getTiempo() {
        return this.tiempo;
    }
    
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    
    @Column(name="multa", nullable=false)
    public int getMulta() {
        return this.multa;
    }
    
    public void setMulta(int multa) {
        this.multa = multa;
    }

    
    @Column(name="peso", nullable=false)
    public int getPeso() {
        return this.peso;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }




}


