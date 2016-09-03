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
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.InstitucionApv;
import com.areatecnica.sigf_v1.entities.InstitucionPrevision;
import com.areatecnica.sigf_v1.entities.InstitucionSalud;
import com.areatecnica.sigf_v1.entities.MonedaPactadaInstitucionSalud;
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

    private List<Trabajador> items;
    private Trabajador selected;

    //helpers
    private String nacionalidad;
    private String sexo;
    private String estadoCivil;
    private boolean regimen;
    private boolean fonasa;
    private boolean ahorro;

    //Entidades
    private InstitucionSalud saludFonasa;
    private InstitucionApv institucionApv;
    private MonedaPactadaInstitucionSalud monedaPactadaInstitucionSalud;
    private AsignacionFamiliar asignacionFamiliar;
    private InstitucionPrevision institucionPrevision;

    /**
     * Creates a new instance of TrabajadorController
     */
    public TrabajadorController() {
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.items = this.trabajadorDao.findAll();
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
            try {
                this.selected.setFechaIngresoTrabajador(new Date());
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
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
                //this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void setDefaultValues() {
        System.err.println("SETEA VALORES");
        if (this.selected != null) {
            System.err.println("correctos valores");
            
            if(this.selected.getNacionalidad()){
                nacionalidad = String.valueOf("1");
            }else{
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

            if (this.selected.getInstitucionPrevision().getIdInstitucionPrevision() > 98){
                regimen = false;
            }
            
            System.err.println("IMPRIME VALORES:");
            System.err.println("Nacionalidad:"+nacionalidad);
            System.err.println("Estado Civil:"+estadoCivil);
            System.err.println("Sexo:"+sexo);
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

    public void updateAhorro() {
        ahorro = !ahorro;
    }

}
