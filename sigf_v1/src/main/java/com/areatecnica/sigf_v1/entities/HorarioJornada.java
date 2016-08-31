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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HorarioJornada generated by hbm2java
 */
@Entity
@Table(name="horario_jornada"
    ,catalog="sigf_v1"
)
public class HorarioJornada  implements java.io.Serializable {


     private Integer idHorarioJornada;
     private Date inicioHorario;
     private Date terminoHorario;
     private Boolean horarioVariable;
     private Set<JornadaLaboral> jornadaLaborals = new HashSet<JornadaLaboral>(0);

    public HorarioJornada() {
    }

	
    public HorarioJornada(Date inicioHorario, Date terminoHorario) {
        this.inicioHorario = inicioHorario;
        this.terminoHorario = terminoHorario;
    }
    public HorarioJornada(Date inicioHorario, Date terminoHorario, Boolean horarioVariable, Set<JornadaLaboral> jornadaLaborals) {
       this.inicioHorario = inicioHorario;
       this.terminoHorario = terminoHorario;
       this.horarioVariable = horarioVariable;
       this.jornadaLaborals = jornadaLaborals;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_horario_jornada", unique=true, nullable=false)
    public Integer getIdHorarioJornada() {
        return this.idHorarioJornada;
    }
    
    public void setIdHorarioJornada(Integer idHorarioJornada) {
        this.idHorarioJornada = idHorarioJornada;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="inicio_horario", nullable=false, length=8)
    public Date getInicioHorario() {
        return this.inicioHorario;
    }
    
    public void setInicioHorario(Date inicioHorario) {
        this.inicioHorario = inicioHorario;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="termino_horario", nullable=false, length=8)
    public Date getTerminoHorario() {
        return this.terminoHorario;
    }
    
    public void setTerminoHorario(Date terminoHorario) {
        this.terminoHorario = terminoHorario;
    }

    
    @Column(name="horario_variable")
    public Boolean getHorarioVariable() {
        return this.horarioVariable;
    }
    
    public void setHorarioVariable(Boolean horarioVariable) {
        this.horarioVariable = horarioVariable;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="horarioJornada")
    public Set<JornadaLaboral> getJornadaLaborals() {
        return this.jornadaLaborals;
    }
    
    public void setJornadaLaborals(Set<JornadaLaboral> jornadaLaborals) {
        this.jornadaLaborals = jornadaLaborals;
    }




}


