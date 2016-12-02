/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.FiniquitoDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.FiniquitoRelacionLaboral;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "fichaTrabajadorController")
@ViewScoped
public class FichaTrabajadorController implements Serializable {

    private TrabajadorDao trabajadorDao;
    private Trabajador selected;
    private ArrayList<Trabajador> items;
    private List<RelacionLaboral> relacionLaboralItems;
    private List<FiniquitoRelacionLaboral> finiquitoRelacionLaboralItems;
    private RelacionLaboral selectedRelacionLaboral;
    private FiniquitoRelacionLaboral selectedFiniquito;
    private FiniquitoDaoImpl finiquitoDaoImpl;
    private RelacionLaboralDaoImpl relacionLaboralDao;
    private Empresa empresa;

    private int codigo;
    private String rut;

    //helpers
    private String nacionalidad;
    private String sexo;
    private String estadoCivil;
    private boolean regimen;
    private boolean fonasa;
    private boolean ahorro;
    private boolean validaRut;
    private int sueldo;

    /**
     * Creates a new instance of FichaTrabajadorController
     */
    public FichaTrabajadorController() {
    }

    @PostConstruct
    public void init() {
        this.selected = new Trabajador();
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
        this.selected = selected;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
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

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
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

    public void updateAhorro() {
        ahorro = !ahorro;
    }

    public boolean isValidaRut() {
        return validaRut;
    }

    public void setValidaRut(boolean validaRut) {
        this.validaRut = validaRut;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
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
            } else {
                fonasa = false;
            }

            if (this.selected.getInstitucionApv().getIdInstitucionApv() == 1000) {
                ahorro = false;
            } else {
                ahorro = true;
            }

            if (this.selected.getTipoCotizacionTrabajador().getIdTipoCotizacionTrabajador() > 2) {
                regimen = true;
            } else {
                regimen = false;
            }
            
            findRelaciones();
        }
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
                break;
            default:
                regimen = false;
        }
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

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
                tx.commit();
                //this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
                tx.rollback();
            }
        } else {
            JsfUtil.addErrorMessage("ERROR AL GUARDAR" + codigo);
            System.err.println("El trabajador está en NULL:Trabajador");
        }
    }

    public void findByCodigo() {
        try {
            this.selected = this.trabajadorDao.findByCodigo(codigo);
            if (this.selected == null) {
                JsfUtil.addErrorMessage("No se ha encontrado un trabajador con el código: " + codigo);
            } else {
                setDefaultValues();//acá
            }
        } catch (NullPointerException | NumberFormatException e) {
            JsfUtil.addErrorMessage("No se ha encontrado un trabajador con el código: ");
        }
    }

    public void findByRut() {
        System.err.println("SI LLEGA LLEGA");
        try {
            this.selected = this.trabajadorDao.findByRut(rut);
            if (this.selected == null) {
                JsfUtil.addErrorMessage("No se ha encontrado un trabajador con el RUT: " + rut);
            } else {
                setDefaultValues();//acá
            }
        } catch (NullPointerException | NumberFormatException e) {
            JsfUtil.addErrorMessage("No se ha encontrado un trabajador con el RUT: " + rut);
        }

    }
    
    public void findRelaciones(){
        this.relacionLaboralItems = this.relacionLaboralDao.findHistoricoByTrabajador(selected);
        
        if(this.relacionLaboralItems.size()>0){
            this.finiquitoDaoImpl = new FiniquitoDaoImpl();
            this.finiquitoRelacionLaboralItems = this.finiquitoDaoImpl.findHistoricoByTrabajador(this.selected);
        }
    }
    

    public void findByRut(String rut) {
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.selected = this.trabajadorDao.findByRut(rut);
    }

    public void setMessage() {
        JsfUtil.addSuccessMessage("VALOR DEL CODIGO:" + codigo);
    }

    public void handleEmpresaChange() {
        if (this.empresa != null) {
            this.trabajadorDao = new TrabajadorDaoImpl();
            ArrayList<RelacionLaboral> al = new ArrayList<>();

            al.addAll(this.empresa.getRelacionLaborals());

            System.err.println("tamaño relaciones laborales:" + al.size());
            this.items = new ArrayList<>();
            for (RelacionLaboral r : al) {
                this.items.add(r.getTrabajador());
            }
        }
    }

    public ArrayList<Trabajador> getItems() {
        return items;
    }

    public void setItems(ArrayList<Trabajador> items) {
        this.items = items;
    }

    public List<RelacionLaboral> getRelacionLaboralItems() {
        return relacionLaboralItems;
    }

    public void setRelacionLaboralItems(List<RelacionLaboral> relacionLaboralItems) {
        this.relacionLaboralItems = relacionLaboralItems;
    }

    public RelacionLaboral getSelectedRelacionLaboral() {
        return selectedRelacionLaboral;
    }

    public void setSelectedRelacionLaboral(RelacionLaboral selectedRelacionLaboral) {
        this.selectedRelacionLaboral = selectedRelacionLaboral;
    }

    public List<FiniquitoRelacionLaboral> getFiniquitoRelacionLaboralItems() {
        return finiquitoRelacionLaboralItems;
    }

    public void setFiniquitoRelacionLaboralItems(List<FiniquitoRelacionLaboral> finiquitoRelacionLaboralItems) {
        this.finiquitoRelacionLaboralItems = finiquitoRelacionLaboralItems;
    }

    public FiniquitoRelacionLaboral getSelectedFiniquito() {
        return selectedFiniquito;
    }

    public void setSelectedFiniquito(FiniquitoRelacionLaboral selectedFiniquito) {
        this.selectedFiniquito = selectedFiniquito;
    }

}
