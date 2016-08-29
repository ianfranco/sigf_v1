package com.areatecnica.sigf_v1.entities;
// Generated 28-08-2016 23:51:18 by Hibernate Tools 4.3.1


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
 * Privilegio generated by hbm2java
 */
@Entity
@Table(name="privilegio"
    ,catalog="sigf_v1"
)
public class Privilegio  implements java.io.Serializable {


     private Integer idPrivilegio;
     private Rol rol;
     private String nombrePrivilegio;
     private String descripcion;

    public Privilegio() {
    }

    public Privilegio(Rol rol, String nombrePrivilegio, String descripcion) {
       this.rol = rol;
       this.nombrePrivilegio = nombrePrivilegio;
       this.descripcion = descripcion;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_privilegio", unique=true, nullable=false)
    public Integer getIdPrivilegio() {
        return this.idPrivilegio;
    }
    
    public void setIdPrivilegio(Integer idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_rol", nullable=false)
    public Rol getRol() {
        return this.rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
    @Column(name="nombre_privilegio", nullable=false)
    public String getNombrePrivilegio() {
        return this.nombrePrivilegio;
    }
    
    public void setNombrePrivilegio(String nombrePrivilegio) {
        this.nombrePrivilegio = nombrePrivilegio;
    }

    
    @Column(name="descripcion", nullable=false)
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




}


