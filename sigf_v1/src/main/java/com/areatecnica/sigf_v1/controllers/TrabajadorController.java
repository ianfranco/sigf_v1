/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.dao.InstitucionAPVDaoImpl;
import com.areatecnica.sigf_v1.dao.InstitucionPrevisionDaoImpl;
import com.areatecnica.sigf_v1.dao.InstitucionSaludDaoImpl;
import com.areatecnica.sigf_v1.dao.MonedaPactadaInstitucionSaludImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.InstitucionApv;
import com.areatecnica.sigf_v1.entities.InstitucionPrevision;
import com.areatecnica.sigf_v1.entities.InstitucionSalud;
import com.areatecnica.sigf_v1.entities.MonedaPactadaInstitucionSalud;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.TipoContrato;
import com.areatecnica.sigf_v1.entities.TipoTrabajador;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ianfr
 */
@Named(value = "trabajadorController")
@SessionScoped
public class TrabajadorController implements Serializable {

    //Daos
    private TrabajadorDao trabajadorDao;
    private InstitucionSaludDaoImpl institucionSaludDaoImpl;
    private InstitucionAPVDaoImpl institucionAPVDaoImpl;
    private MonedaPactadaInstitucionSaludImpl monedaPactadaInstitucionSaludImpl;
    private AsignacionFamiliarDaoImpl asignacionFamiliarDaoImpl;
    private InstitucionPrevisionDaoImpl institucionPrevisionDaoImpl;
    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;

    private List<Trabajador> items;
    private Trabajador selected;

    //helpers
    private String nacionalidad;
    private String sexo;
    private String estadoCivil;
    private boolean regimen;
    private boolean fonasa;
    private boolean ahorro;
    private boolean validaRut;
    private boolean skip;
    private int sueldo;
    

    //Entidades
    private InstitucionSalud saludFonasa;
    private InstitucionApv institucionApv;
    private MonedaPactadaInstitucionSalud monedaPactadaInstitucionSalud;
    private AsignacionFamiliar asignacionFamiliar;
    private InstitucionPrevision institucionPrevision;
    private RelacionLaboral relacionLaboral;

    private Empresa selectedEmpresa;
    private TipoContrato tipoContrato;
    private TipoTrabajador tipoTrabajador;
    private Date selectedInicioContrato;
    private Date selectedFinContrato;

    /**
     * Creates a new instance of TrabajadorController
     */
    public TrabajadorController() {
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.items = this.trabajadorDao.findAll();
        this.relacionLaboral = new RelacionLaboral();
        this.relacionLaboral.setSueldoBase(0);
    }

    @PostConstruct
    public void init() {

    }

    public List<Trabajador> getItems() {
        return items;
    }

    public void setItems(List<Trabajador> items) {
        this.items = items;
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
        this.selected = selected;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public boolean isFonasa() {
        return fonasa;
    }

    public void setFonasa(boolean fonasa) {
        this.fonasa = fonasa;
    }

    public boolean getRegimen() {
        return regimen;
    }

    public void setRegimen(boolean regimen) {
        this.regimen = regimen;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean isAhorro() {
        return !ahorro;
    }

    public void setAhorro(boolean ahorro) {
        this.ahorro = ahorro;
    }

    public RelacionLaboral getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

    public boolean isValidaRut() {
        return validaRut;
    }

    public void setValidaRut(boolean validaRut) {
        this.validaRut = validaRut;
    }

    public void updateAhorro() {
        ahorro = !ahorro;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public Empresa getSelectedEmpresa() {
        return selectedEmpresa;
    }

    public void setSelectedEmpresa(Empresa selectedEmpresa) {
        this.selectedEmpresa = selectedEmpresa;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public TipoTrabajador getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(TipoTrabajador tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public Date getSelectedInicioContrato() {
        return selectedInicioContrato;
    }

    public void setSelectedInicioContrato(Date selectedInicioContrato) {
        this.selectedInicioContrato = selectedInicioContrato;
    }

    public Date getSelectedFinContrato() {
        return selectedFinContrato;
    }

    public void setSelectedFinContrato(Date selectedFinContrato) {
        this.selectedFinContrato = selectedFinContrato;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public Trabajador prepareCreate(ActionEvent event) {
        nacionalidad = "1";
        estadoCivil = "1";
        sexo = "1";
        fonasa = true;
        ahorro = false;
        regimen = true;

        this.institucionSaludDaoImpl = new InstitucionSaludDaoImpl();
        this.institucionAPVDaoImpl = new InstitucionAPVDaoImpl();
        this.monedaPactadaInstitucionSaludImpl = new MonedaPactadaInstitucionSaludImpl();
        this.asignacionFamiliarDaoImpl = new AsignacionFamiliarDaoImpl();
        this.institucionPrevisionDaoImpl = new InstitucionPrevisionDaoImpl();

        this.saludFonasa = this.institucionSaludDaoImpl.findById(7);
        this.institucionApv = this.institucionAPVDaoImpl.findById(1000);
        this.monedaPactadaInstitucionSalud = this.monedaPactadaInstitucionSaludImpl.findById(1);
        this.asignacionFamiliar = this.asignacionFamiliarDaoImpl.findById(5);

        Trabajador newTrabajador;
        newTrabajador = new Trabajador(true);

        this.selected = newTrabajador;
        this.selected.setCodigoTrabajador(trabajadorDao.maxId());
        this.selected.setInstitucionSalud(saludFonasa);
        this.selected.setInstitucionApv(institucionApv);
        this.selected.setMonedaPactadaInstitucionSalud(monedaPactadaInstitucionSalud);
        this.selected.setAsignacionFamiliar(asignacionFamiliar);
        this.selected.setNumeroCargas(0);
        this.selected.setMontoApv(0);
        this.selected.setMontoSalud(BigDecimal.ZERO);
        this.selected.setFechaIngresoTrabajador(new Date());

        return newTrabajador;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            System.err.println("FECHA INGRESO TRABAJADOR:" + this.selected.getFechaIngresoTrabajador());
            System.err.println("SUELDO:" + this.relacionLaboral.getSueldoBase());
            System.err.println("Tipo Trabajador:" + this.tipoTrabajador);
            System.err.println("Fechas:" + this.selectedInicioContrato + " - " + this.selectedFinContrato);
            System.err.println("EMPRESA:" + this.selectedEmpresa);
            System.err.println("Tipo Contrato" + this.tipoContrato);

            try {
                this.selected.setFechaIngresoTrabajador(new Date());
                this.selected.setEstadoCivil(Short.parseShort(estadoCivil));
                if (sexo.equals("1")) {
                    this.selected.setSexo(true);
                } else {
                    this.selected.setSexo(false);
                }

                if (nacionalidad.equals("1")) {
                    this.selected.setNacionalidad(true);
                } else {
                    this.selected.setNacionalidad(false);
                }

                session.saveOrUpdate(this.selected);

                this.relacionLaboral.setEstado(true);
                this.relacionLaboral.setTrabajador(selected);
                this.relacionLaboral.setEmpresa(this.selectedEmpresa);
                this.relacionLaboral.setTipoContrato(tipoContrato);
                this.relacionLaboral.setTipoTrabajador(tipoTrabajador);
                this.relacionLaboral.setFechaFin(selectedFinContrato);
                this.relacionLaboral.setFechaInicio(selectedInicioContrato);
                this.relacionLaboral.setSueldoBase(sueldo);

                session.saveOrUpdate(this.relacionLaboral);

                tx.commit();
                this.items.add(selected);

                this.selected = null;

            } catch (HibernateException e) {
                System.err.println("Error SAVE:Trabajador");
                System.err.println(e.getMessage());
            }

        } else {

        }
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
                tx.commit();
            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void setDefaultValues() {        
        if (this.selected != null) {
                        
            if (this.selected.getNacionalidad()) {
                nacionalidad = String.valueOf("1");
            } else {
                nacionalidad = String.valueOf("0");
            }

            estadoCivil = String.valueOf(this.selected.getEstadoCivil());

            if (this.selected.getSexo()) {
                sexo = "1";
            } else {
                sexo = "0";
            }

            if (this.selected.getInstitucionSalud().getIdInstitucionSalud() == 7) {
                fonasa = true;
            }

            if (this.selected.getInstitucionApv().getIdInstitucionApv() == 1000) {
                ahorro = false;
            }

            if (this.selected.getInstitucionPrevision().getIdInstitucionPrevision() > 98) {
                regimen = false;
            }            
        }
    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public void updateInstitucionPrevision() {

        switch (this.selected.getTipoCotizacionTrabajador().getIdTipoCotizacionTrabajador()) {
            case 1:
            case 2:
                System.err.println("Habilitado" + this.selected.getTipoCotizacionTrabajador().getNombreTipoCotizacionTrabajador());
                regimen = false;
                break;
            case 3:
            case 4:
                System.err.println("DESHabilitado" + this.selected.getTipoCotizacionTrabajador().getNombreTipoCotizacionTrabajador());
                regimen = true;
                if (this.selected.getTipoCotizacionTrabajador().getIdTipoCotizacionTrabajador() == 3) {
                    this.institucionPrevision = this.institucionPrevisionDaoImpl.findById(99);
                } else {
                    this.institucionPrevision = this.institucionPrevisionDaoImpl.findById(100);
                }
                this.selected.setInstitucionPrevision(institucionPrevision);
                break;
            default:
                regimen = false;
        }
    }

    public String onFlowProcess(FlowEvent event) {
        /*if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            
        }*/
        return event.getNewStep();
    }

    public void validarRut() {
        validaRut = false;

        String rut = this.selected.getRutTrabajador();

        try {
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

                if (trabajadorDao.existeTrabajador(rut)) {
                    this.selected.setRutTrabajador("");
                    JsfUtil.addErrorMessage("El rut se encuentra registrado");
                }

            } else {
                this.selected.setRutTrabajador("");
                JsfUtil.addErrorMessage("Rut Mal Formado");
            }

        } catch (java.lang.NumberFormatException e) {

        } catch (Exception e) {

        }
    }

    public void addMessageContrato() {
        JsfUtil.addSuccessMessage("Estado Civil:" + estadoCivil);
    }

    public void addMessageEmpresa() {
        JsfUtil.addSuccessMessage("Empresa:" + selectedEmpresa.toString());
    }

    public void addMessageTipo() {
        JsfUtil.addSuccessMessage("Tipo:" + tipoTrabajador);
    }

    public void addMessageInicio() {
        JsfUtil.addSuccessMessage("Inicio:" + selectedInicioContrato);
    }

    public void addMessageTermino() {
        JsfUtil.addSuccessMessage("Tipo:" + selectedFinContrato);
    }

}
