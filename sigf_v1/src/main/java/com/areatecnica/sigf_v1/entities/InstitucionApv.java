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
 * InstitucionApv generated by hbm2java
 */
@Entity
@Table(name = "institucion_apv", catalog = "sigf_v1"
)
public class InstitucionApv implements java.io.Serializable {

    private Integer idInstitucionApv;
    private TipoInstitucionApv tipoInstitucionApv;
    private String nombreInstitucionApv;
    private Set<Trabajador> trabajadors = new HashSet<Trabajador>(0);

    public InstitucionApv() {
    }

    public InstitucionApv(TipoInstitucionApv tipoInstitucionApv, String nombreInstitucionApv) {
        this.tipoInstitucionApv = tipoInstitucionApv;
        this.nombreInstitucionApv = nombreInstitucionApv;
    }

    public InstitucionApv(TipoInstitucionApv tipoInstitucionApv, String nombreInstitucionApv, Set<Trabajador> trabajadors) {
        this.tipoInstitucionApv = tipoInstitucionApv;
        this.nombreInstitucionApv = nombreInstitucionApv;
        this.trabajadors = trabajadors;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_institucion_apv", unique = true, nullable = false)
    public Integer getIdInstitucionApv() {
        return this.idInstitucionApv;
    }

    public void setIdInstitucionApv(Integer idInstitucionApv) {
        this.idInstitucionApv = idInstitucionApv;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_institucion_apv", nullable = false)
    public TipoInstitucionApv getTipoInstitucionApv() {
        return this.tipoInstitucionApv;
    }

    public void setTipoInstitucionApv(TipoInstitucionApv tipoInstitucionApv) {
        this.tipoInstitucionApv = tipoInstitucionApv;
    }

    @Column(name = "nombre_institucion_apv", nullable = false, length = 100)
    public String getNombreInstitucionApv() {
        return this.nombreInstitucionApv;
    }

    public void setNombreInstitucionApv(String nombreInstitucionApv) {
        this.nombreInstitucionApv = nombreInstitucionApv;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "institucionApv")
    public Set<Trabajador> getTrabajadors() {
        return this.trabajadors;
    }

    public void setTrabajadors(Set<Trabajador> trabajadors) {
        this.trabajadors = trabajadors;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstitucionApv != null ? idInstitucionApv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstitucionApv)) {
            return false;
        }
        InstitucionApv other = (InstitucionApv) object;
        if ((this.idInstitucionApv == null && other.idInstitucionApv != null) || (this.idInstitucionApv != null && !this.idInstitucionApv.equals(other.idInstitucionApv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreInstitucionApv;
    }

}
