/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.remuneraciones;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.dao.ComunaDaoImpl;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.dao.InstitucionAPVDaoImpl;
import com.areatecnica.sigf_v1.dao.InstitucionPrevisionDaoImpl;
import com.areatecnica.sigf_v1.dao.InstitucionSaludDaoImpl;
import com.areatecnica.sigf_v1.dao.MonedaPactadaInstitucionSaludImpl;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCotizacionTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.Comuna;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.InstitucionApv;
import com.areatecnica.sigf_v1.entities.InstitucionPrevision;
import com.areatecnica.sigf_v1.entities.InstitucionSalud;
import com.areatecnica.sigf_v1.entities.MonedaPactadaInstitucionSalud;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.TipoCotizacionTrabajador;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class RegistroTrabajadorController implements Serializable {

    //Dao
    private TrabajadorDaoImpl trabajadorDaoImpl;
    private TerminalDaoImpl terminalDaoImpl;
    private EmpresaDaoImpl empresaDaoImpl;
    private InstitucionPrevisionDaoImpl institucionPrevisionDaoImpl;
    private InstitucionSaludDaoImpl institucionSaludDaoImpl;
    private AsignacionFamiliarDaoImpl asignacionFamiliarDaoImpl;
    private TipoCotizacionTrabajadorDaoImpl tipoCotizacionTrabajadorDaoImpl;
    private InstitucionAPVDaoImpl institucionAPVDaoImpl;
    private MonedaPactadaInstitucionSaludImpl monedaPactadaInstitucionSaludImpl;
    private ComunaDaoImpl comunaDaoImpl;

    //List
    private List<Trabajador> items;
    private List<Terminal> itemsTerminal;
    private List<Empresa> itemsEmpresa;
    private List<InstitucionPrevision> itemsInstitucionPrevision;
    private List<InstitucionSalud> itemsInstitucionSalud;
    private List<AsignacionFamiliar> itemsAsignacionFamiliar;
    private List<TipoCotizacionTrabajador> itemsTipoCotizacion;
    private List<InstitucionApv> itemsInstitucionApv;
    private List<MonedaPactadaInstitucionSalud> itemsMonedaPactadaInstitucionSalud;
    private List<Comuna> itemsComuna;

    private Trabajador selected;
    private InstitucionSalud institucionSalud;
    private InstitucionApv institucionAPV;
    private InstitucionPrevision institucionPrevision;
    private TipoCotizacionTrabajador cotizacionTrabajador;
    private MonedaPactadaInstitucionSalud monedaPactadaInstitucionSalud;

    private int nacionalidad;
    private int sexo;
    private short estadoCivil;
    private boolean regimen;
    private boolean fonasa;
    private boolean ahorro;
    private boolean validaRut;

    /**
     * Creates a new instance of RegistroTrabajadorController
     */
    public RegistroTrabajadorController() {
        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.items = this.trabajadorDaoImpl.findAll();

        this.selected = new Trabajador();
        this.selected.setApellidoMaternoTrabajador("");
        this.selected.setApellidoPaternoTrabajador("");
        this.selected.setNombreTrabajador("");

    }

    public List<Trabajador> getItems() {
        return items;
    }

    public void setItems(List<Trabajador> items) {
        this.items = items;
    }

    public List<Terminal> getItemsTerminal() {
        return itemsTerminal;
    }

    public void setItemsTerminal(List<Terminal> itemsTerminal) {
        this.itemsTerminal = itemsTerminal;
    }

    public List<Empresa> getItemsEmpresa() {
        return itemsEmpresa;
    }

    public void setItemsEmpresa(List<Empresa> itemsEmpresa) {
        this.itemsEmpresa = itemsEmpresa;
    }

    public List<InstitucionPrevision> getItemsInstitucionPrevision() {
        return itemsInstitucionPrevision;
    }

    public void setItemsInstitucionPrevision(List<InstitucionPrevision> itemsInstitucionPrevision) {
        this.itemsInstitucionPrevision = itemsInstitucionPrevision;
    }

    public List<InstitucionSalud> getItemsInstitucionSalud() {
        return itemsInstitucionSalud;
    }

    public void setItemsInstitucionSalud(List<InstitucionSalud> itemsInstitucionSalud) {
        this.itemsInstitucionSalud = itemsInstitucionSalud;
    }

    public List<AsignacionFamiliar> getItemsAsignacionFamiliar() {
        return itemsAsignacionFamiliar;
    }

    public void setItemsAsignacionFamiliar(List<AsignacionFamiliar> itemsAsignacionFamiliar) {
        this.itemsAsignacionFamiliar = itemsAsignacionFamiliar;
    }

    public List<TipoCotizacionTrabajador> getItemsTipoCotizacion() {
        return itemsTipoCotizacion;
    }

    public void setItemsTipoCotizacion(List<TipoCotizacionTrabajador> itemsTipoCotizacion) {
        this.itemsTipoCotizacion = itemsTipoCotizacion;
    }

    public List<InstitucionApv> getItemsInstitucionApv() {
        return itemsInstitucionApv;
    }

    public void setItemsInstitucionApv(List<InstitucionApv> itemsInstitucionApv) {
        this.itemsInstitucionApv = itemsInstitucionApv;
    }

    public List<MonedaPactadaInstitucionSalud> getItemsMonedaPactadaInstitucionSalud() {
        return itemsMonedaPactadaInstitucionSalud;
    }

    public void setItemsMonedaPactadaInstitucionSalud(List<MonedaPactadaInstitucionSalud> itemsMonedaPactadaInstitucionSalud) {
        this.itemsMonedaPactadaInstitucionSalud = itemsMonedaPactadaInstitucionSalud;
    }

    public List<Comuna> getItemsComuna() {
        return itemsComuna;
    }

    public void setItemsComuna(List<Comuna> itemsComuna) {
        this.itemsComuna = itemsComuna;
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
        this.selected = selected;
    }

    public int getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(int nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public short getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(short estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean isRegimen() {
        return regimen;
    }

    public void setRegimen(boolean regimen) {
        this.regimen = regimen;
    }

    public boolean isFonasa() {
        return fonasa;
    }

    public void setFonasa(boolean fonasa) {
        this.fonasa = fonasa;
    }

    public boolean isAhorro() {
        return ahorro;
    }

    public void setAhorro(boolean ahorro) {
        this.ahorro = ahorro;
    }

    public Trabajador prepareCreate(ActionEvent event) {
        nacionalidad = 1;
        estadoCivil = 1;
        sexo = 1;
        fonasa = true;
        ahorro = false;
        regimen = false;

        this.institucionSaludDaoImpl = new InstitucionSaludDaoImpl();
        this.institucionAPVDaoImpl = new InstitucionAPVDaoImpl();
        this.monedaPactadaInstitucionSaludImpl = new MonedaPactadaInstitucionSaludImpl();
        this.asignacionFamiliarDaoImpl = new AsignacionFamiliarDaoImpl();
        this.institucionPrevisionDaoImpl = new InstitucionPrevisionDaoImpl();
        this.tipoCotizacionTrabajadorDaoImpl = new TipoCotizacionTrabajadorDaoImpl();

        this.institucionSalud = this.institucionSaludDaoImpl.findById(7);
        this.institucionAPV = this.institucionAPVDaoImpl.findById(1000);
        this.monedaPactadaInstitucionSalud = this.monedaPactadaInstitucionSaludImpl.findById(1);
        this.cotizacionTrabajador = this.tipoCotizacionTrabajadorDaoImpl.findById(1);
        this.institucionPrevision = this.institucionPrevisionDaoImpl.findById(34);

        this.selected = new Trabajador(true);;
        this.selected.setCodigoTrabajador(trabajadorDaoImpl.maxId());
        this.selected.setInstitucionSalud(institucionSalud);
        this.selected.setInstitucionApv(institucionAPV);
        this.selected.setMonedaPactadaInstitucionSalud(monedaPactadaInstitucionSalud);
        this.selected.setTipoCotizacionTrabajador(cotizacionTrabajador);
        this.selected.setNumeroCargas(0);
        this.selected.setMontoApv(0);
        this.selected.setMontoSalud(BigDecimal.ZERO);
        this.selected.setFechaIngresoTrabajador(new Date());

        JsfUtil.addErrorMessage("SE HA CREADO EL CONDUCTOR" + this.selected.getCodigoTrabajador());

        setDefaultValues();

        return this.selected;
    }

    public void setDefaultValues() {
        if (this.selected != null) {

            if (this.selected.getNacionalidad()) {
                nacionalidad = 1;
            } else {
                nacionalidad = 0;
            }

            estadoCivil = this.selected.getEstadoCivil();

            if (this.selected.getSexo()) {
                sexo = 1;
            } else {
                sexo = 0;
            }

            if (this.selected.getInstitucionSalud().getIdInstitucionSalud() == 7) {
                fonasa = true;
            }

            if (this.selected.getInstitucionApv().getIdInstitucionApv() == 1000) {
                ahorro = false;
            }

        }
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoTrabajador(new Date());
                this.selected.setEstadoCivil(estadoCivil);
                if (sexo == 1) {
                    this.selected.setSexo(true);
                } else {
                    this.selected.setSexo(false);
                }

                if (nacionalidad == 1) {
                    this.selected.setNacionalidad(true);
                } else {
                    this.selected.setNacionalidad(false);
                }

                this.selected.setCesantia(false);

                session.save(this.selected);

                tx.commit();
                this.items.add(this.items.size() - 1, selected);

                JsfUtil.addSuccessMessage("Se ha registrado el nuevo trabajador: " + this.selected + " con código: " + this.selected.getCodigoTrabajador());
                this.selected = null;

            } catch (HibernateException e) {
                System.err.println("Error SAVE:Trabajador");
                tx.rollback();
                System.err.println(e.getMessage());
            }

        } else {

        }
    }

    public void validarRut() {
        validaRut = false;
        JsfUtil.addErrorMessage("rut"+this.selected.getRutTrabajador());
        try {
            String rut = null;
            rut = this.selected.getRutTrabajador();
                        
            if (rut!=null && rut.length()>0) {
                rut = rut.toUpperCase();
                rut = rut.replace(".", "");
                rut = rut.replace("-", "");
                int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
                
                char dv = rut.charAt(rut.length() - 1);
                
                int m = 0, s = 1;
                for (; rutAux != 0; rutAux /= 10) {
                    s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
                }
                if (dv == (char) (s != 0 ? s + 47 : 75)) {
                    validaRut = true;
                    
                    if (trabajadorDaoImpl.existeTrabajador(rut)) {
                        this.selected.setRutTrabajador("");
                        JsfUtil.addErrorMessage("El rut se encuentra registrado");
                    }
                    
                } else {
                    this.selected.setRutTrabajador("");
                    JsfUtil.addErrorMessage("Rut Mal Formado");
                }
            }else{
                JsfUtil.addErrorMessage("Debe ingresar un rut válido");
            }

        } catch (java.lang.NumberFormatException | javax.el.PropertyNotFoundException | NullPointerException | javax.faces.FacesException e) {
            JsfUtil.addErrorMessage("Rut Mal Formado");
        }
    }
    
}
