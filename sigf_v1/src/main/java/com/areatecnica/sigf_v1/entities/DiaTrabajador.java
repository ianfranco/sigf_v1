package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DiaTrabajador generated by hbm2java
 */
@Entity
@Table(name="dia_trabajador"
    ,catalog="sigf_v1"
)
public class DiaTrabajador  implements java.io.Serializable {


     private Integer idDiaTrabajador;
     private TipoDiaTrabajador tipoDiaTrabajador;
     private Trabajador trabajador;
     private Date desde;
     private Date hasta;

    public DiaTrabajador() {
    }

    public DiaTrabajador(TipoDiaTrabajador tipoDiaTrabajador, Trabajador trabajador, Date desde, Date hasta) {
       this.tipoDiaTrabajador = tipoDiaTrabajador;
       this.trabajador = trabajador;
       this.desde = desde;
       this.hasta = hasta;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_dia_trabajador", unique=true, nullable=false)
    public Integer getIdDiaTrabajador() {
        return this.idDiaTrabajador;
    }
    
    public void setIdDiaTrabajador(Integer idDiaTrabajador) {
        this.idDiaTrabajador = idDiaTrabajador;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tipo_dia_trabajador", nullable=false)
    public TipoDiaTrabajador getTipoDiaTrabajador() {
        return this.tipoDiaTrabajador;
    }
    
    public void setTipoDiaTrabajador(TipoDiaTrabajador tipoDiaTrabajador) {
        this.tipoDiaTrabajador = tipoDiaTrabajador;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_trabajador", nullable=false)
    public Trabajador getTrabajador() {
        return this.trabajador;
    }
    
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="desde", nullable=false, length=10)
    public Date getDesde() {
        return this.desde;
    }
    
    public void setDesde(Date desde) {
        this.desde = desde;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="hasta", nullable=false, length=10)
    public Date getHasta() {
        return this.hasta;
    }
    
    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idDiaTrabajador);
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
        final DiaTrabajador other = (DiaTrabajador) obj;
        if (!Objects.equals(this.idDiaTrabajador, other.idDiaTrabajador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return tipoDiaTrabajador.getNombre() + ", desde=" + dateFormat.format(desde) + ", hasta=" + dateFormat.format(hasta);
    }




}


