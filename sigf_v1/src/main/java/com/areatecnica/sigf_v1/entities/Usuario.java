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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario", catalog = "sigf_v1"
)
public class Usuario implements java.io.Serializable {

    private Integer idUsuario;
    private Rol rol;
    private Terminal terminal;
    private String rut;
    private String pass;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private Date fechaIngresoUsuario;
    private Boolean activo;
    private Set<Despacho> despachos = new HashSet<Despacho>(0);
    private Set<Log> logs = new HashSet<Log>(0);
    private Set<ProcesoRecaudacion> procesoRecaudacions = new HashSet<ProcesoRecaudacion>(0);

    public Usuario() {
    }

    public Usuario(Rol rol, Terminal terminal, String rut, String pass, Date fechaIngresoUsuario) {
        this.rol = rol;
        this.terminal = terminal;
        this.rut = rut;
        this.pass = pass;
        this.fechaIngresoUsuario = fechaIngresoUsuario;
    }

    public Usuario(Rol rol, Terminal terminal, String rut, String pass, String nombres, String apellidoPaterno, String apellidoMaterno, String email, Date fechaIngresoUsuario, Boolean activo, Set<Despacho> despachos, Set<Log> logs, Set<ProcesoRecaudacion> procesoRecaudacions) {
        this.rol = rol;
        this.terminal = terminal;
        this.rut = rut;
        this.pass = pass;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.fechaIngresoUsuario = fechaIngresoUsuario;
        this.activo = activo;
        this.despachos = despachos;
        this.logs = logs;
        this.procesoRecaudacions = procesoRecaudacions;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_usuario", unique = true, nullable = false)
    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_terminal", nullable = false)
    public Terminal getTerminal() {
        return this.terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Column(name = "rut", nullable = false, length = 9)
    public String getRut() {
        return this.rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Column(name = "pass", nullable = false, length = 45)
    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Column(name = "nombres")
    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Column(name = "apellido_paterno")
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    @Column(name = "apellido_materno")
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso_usuario", nullable = false, length = 19)
    public Date getFechaIngresoUsuario() {
        return this.fechaIngresoUsuario;
    }

    public void setFechaIngresoUsuario(Date fechaIngresoUsuario) {
        this.fechaIngresoUsuario = fechaIngresoUsuario;
    }

    @Column(name = "activo")
    public Boolean getActivo() {
        return this.activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    public Set<Despacho> getDespachos() {
        return this.despachos;
    }

    public void setDespachos(Set<Despacho> despachos) {
        this.despachos = despachos;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="usuario")
    public Set<Log> getLogs() {
        return this.logs;
    }
    
    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    public Set<ProcesoRecaudacion> getProcesoRecaudacions() {
        return this.procesoRecaudacions;
    }

    public void setProcesoRecaudacions(Set<ProcesoRecaudacion> procesoRecaudacions) {
        this.procesoRecaudacions = procesoRecaudacions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombres;
    }

}
