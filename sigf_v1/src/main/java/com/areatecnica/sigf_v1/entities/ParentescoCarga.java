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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ParentescoCarga generated by hbm2java
 */
@Entity
@Table(name="parentesco_carga"
    ,catalog="sigf_v1"
)
public class ParentescoCarga  implements java.io.Serializable {


     private Integer idParentescoCarga;
     private String nombreParentescoCarga;
     private Set<CargaTrabajador> cargaTrabajadors = new HashSet<CargaTrabajador>(0);

    public ParentescoCarga() {
    }

	
    public ParentescoCarga(String nombreParentescoCarga) {
        this.nombreParentescoCarga = nombreParentescoCarga;
    }
    public ParentescoCarga(String nombreParentescoCarga, Set<CargaTrabajador> cargaTrabajadors) {
       this.nombreParentescoCarga = nombreParentescoCarga;
       this.cargaTrabajadors = cargaTrabajadors;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_parentesco_carga", unique=true, nullable=false)
    public Integer getIdParentescoCarga() {
        return this.idParentescoCarga;
    }
    
    public void setIdParentescoCarga(Integer idParentescoCarga) {
        this.idParentescoCarga = idParentescoCarga;
    }

    
    @Column(name="nombre_parentesco_carga", nullable=false, length=45)
    public String getNombreParentescoCarga() {
        return this.nombreParentescoCarga;
    }
    
    public void setNombreParentescoCarga(String nombreParentescoCarga) {
        this.nombreParentescoCarga = nombreParentescoCarga;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="parentescoCarga")
    public Set<CargaTrabajador> getCargaTrabajadors() {
        return this.cargaTrabajadors;
    }
    
    public void setCargaTrabajadors(Set<CargaTrabajador> cargaTrabajadors) {
        this.cargaTrabajadors = cargaTrabajadors;
    }




}


