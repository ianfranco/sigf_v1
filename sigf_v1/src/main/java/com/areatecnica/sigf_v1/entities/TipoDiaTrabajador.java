package com.areatecnica.sigf_v1.entities;
// Generated 31-08-2016 4:14:02 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Objects;
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
 * TipoDiaTrabajador generated by hbm2java
 */
@Entity
@Table(name = "tipo_dia_trabajador", catalog = "sigf_v1"
)
public class TipoDiaTrabajador implements java.io.Serializable {

    private Integer idTipoDiaTrabajador;
    private String nombre;
    private boolean sueldo;
    private Set<DiaTrabajador> diaTrabajadors = new HashSet<DiaTrabajador>(0);

    public TipoDiaTrabajador() {
    }

    public TipoDiaTrabajador(String nombre, boolean sueldo) {
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public TipoDiaTrabajador(String nombre, boolean sueldo, Set<DiaTrabajador> diaTrabajadors) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.diaTrabajadors = diaTrabajadors;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_tipo_dia_trabajador", unique = true, nullable = false)
    public Integer getIdTipoDiaTrabajador() {
        return this.idTipoDiaTrabajador;
    }

    public void setIdTipoDiaTrabajador(Integer idTipoDiaTrabajador) {
        this.idTipoDiaTrabajador = idTipoDiaTrabajador;
    }

    @Column(name = "nombre", nullable = false, length = 100)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "sueldo", nullable = false)
    public boolean isSueldo() {
        return this.sueldo;
    }

    public void setSueldo(boolean sueldo) {
        this.sueldo = sueldo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDiaTrabajador")
    public Set<DiaTrabajador> getDiaTrabajadors() {
        return this.diaTrabajadors;
    }

    public void setDiaTrabajadors(Set<DiaTrabajador> diaTrabajadors) {
        this.diaTrabajadors = diaTrabajadors;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.idTipoDiaTrabajador);
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
        final TipoDiaTrabajador other = (TipoDiaTrabajador) obj;
        if (!Objects.equals(this.idTipoDiaTrabajador, other.idTipoDiaTrabajador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
