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
 * Departamento generated by hbm2java
 */
@Entity
@Table(name = "departamento", catalog = "sigf_v1"
)
public class Departamento implements java.io.Serializable {

    private Integer idDepartamento;
    private String nombre;
    private Boolean activo;
    private Set<GastoAdministracionMensual> gastoAdministracionMensuals = new HashSet<GastoAdministracionMensual>(0);

    public Departamento() {
    }

    public Departamento(String nombre) {
        this.nombre = nombre;
    }

    public Departamento(String nombre, Boolean activo, Set<GastoAdministracionMensual> gastoAdministracionMensuals) {
        this.nombre = nombre;
        this.activo = activo;
        this.gastoAdministracionMensuals = gastoAdministracionMensuals;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_departamento", unique = true, nullable = false)
    public Integer getIdDepartamento() {
        return this.idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Column(name = "nombre", nullable = false, length = 45)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "activo")
    public Boolean getActivo() {
        return this.activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    public Set<GastoAdministracionMensual> getGastoAdministracionMensuals() {
        return this.gastoAdministracionMensuals;
    }

    public void setGastoAdministracionMensuals(Set<GastoAdministracionMensual> gastoAdministracionMensuals) {
        this.gastoAdministracionMensuals = gastoAdministracionMensuals;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idDepartamento);
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
        final Departamento other = (Departamento) obj;
        if (!Objects.equals(this.idDepartamento, other.idDepartamento)) {
            return false;
        }
        return true;
    }

}
