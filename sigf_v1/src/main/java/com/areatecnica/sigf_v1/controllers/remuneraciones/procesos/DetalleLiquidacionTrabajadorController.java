/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.remuneraciones.procesos;

import com.areatecnica.sigf_v1.controllers.RegistroLiquidacionController;
import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajador;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "detalleLiquidacionTrabajadorController")
@ViewScoped
public class DetalleLiquidacionTrabajadorController implements Serializable {

    private DescuentoTrabajadorDaoImpl descuentoTrabajadorDao;
    private DescuentoTrabajadorLiquidacionDaoImpl descuentoTrabajadorLiquidacionDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;
    private GuiaDaoImpl guiaDaoImpl;
    private EgresoGuiaDaoImpl egresoGuiaDao;

    private DescuentoTrabajador descuentoTrabajador;
    private DescuentoTrabajadorLiquidacion selected;

    private List<DescuentoTrabajadorLiquidacion> items;
    private List<DescuentoTrabajador> descuentosItems;
    private List<Trabajador> trabajadorItems;
    private List<Guia> guiaItems;
    private List<GuiaLiquidacionHelper> guiaHelper;

    private List<RelacionLaboral> relacionLaboralItems;
    private RelacionLaboral selectedRelacionLaboral;
    private RelacionLaboralDaoImpl relacionLaboralDao;
    private Trabajador trabajador;
    private EgresoGuia egresoGuia;
    private GuiaLiquidacionHelper selectedGuiaHelper;
    private int mes;
    private int anio;
    private Date fecha;
    private String header;
    private int codigo;
    private int cinco;
    private int saldo;
    private int ingresos;
    private int saldoFinal;

    /**
     * Creates a new instance of DetalleLiquidacionTrabajadorController
     */
    public DetalleLiquidacionTrabajadorController() {

        this.descuentoTrabajadorLiquidacionDaoImpl = new DescuentoTrabajadorLiquidacionDaoImpl();
        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAllClean();
        //this.items = this.descuentoTrabajadorLiquidacionDaoImpl.findWithLimit();
        this.selected = prepareCreate();
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
    }

    public DescuentoTrabajador getDescuentoTrabajador() {
        return descuentoTrabajador;
    }

    public void setDescuentoTrabajador(DescuentoTrabajador descuentoTrabajador) {
        this.descuentoTrabajador = descuentoTrabajador;
    }

    public DescuentoTrabajadorLiquidacion prepareCreate() {
        DescuentoTrabajadorLiquidacion newDescuentoTrabajadorLiquidacion;
        newDescuentoTrabajadorLiquidacion = new DescuentoTrabajadorLiquidacion();
        newDescuentoTrabajadorLiquidacion.setMonto(0);
        newDescuentoTrabajadorLiquidacion.setNumeroCuotas(0);
        return newDescuentoTrabajadorLiquidacion;
    }

    /*public void findRelaciones() {

        if (this.relacionLaboralItems.size() > 0) {
            /*this.finiquitoDaoImpl = new FiniquitoDaoImpl();
            this.finiquitoRelacionLaboralItems = this.finiquitoDaoImpl.findHistoricoByTrabajador(this.selected);
        //}//
    }*/
    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            this.header = "Mes:" + getStringMonth() + " Año:" + anio;

        } catch (ParseException p) {

        }

        this.descuentoTrabajadorLiquidacionDaoImpl = new DescuentoTrabajadorLiquidacionDaoImpl();
        this.items = this.descuentoTrabajadorLiquidacionDaoImpl.findByTrabajadorAndDate(trabajador, fecha);
         
        
        this.guiaDaoImpl = new GuiaDaoImpl();
        this.guiaItems = this.guiaDaoImpl.findByConductorBetweenDates(this.trabajador, fecha);

        this.egresoGuiaDao = new EgresoGuiaDaoImpl();
        this.guiaHelper = new ArrayList<>();  
        for (Guia g : guiaItems) {

            GuiaLiquidacionHelper helper = new GuiaLiquidacionHelper();

            this.egresoGuia = this.egresoGuiaDao.findByGuiaAndEgresoClean(g.getIdGuia(), 12);
            helper.setCinco(this.egresoGuia.getMonto());

            this.egresoGuia = this.egresoGuiaDao.findByGuiaAndEgresoClean(g.getIdGuia(), 14);
            helper.setAhorro(this.egresoGuia.getMonto());

            helper.setSaldo(helper.getCinco() + helper.getAhorro());
            
            helper.setFolio(g.getFolio());
            helper.setFechaRecaudacion(g.getFechaGuia());
            

            this.guiaHelper.add(helper);

            this.ingresos = this.ingresos + helper.saldo;
        }
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoDescuento(new Date());
                this.selected.setActivoDescuentoTrabajador(Boolean.TRUE);

                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);

                Date fecha = this.selected.getFechaInicioDescuento();

                this.selected = null;
                this.selected = new DescuentoTrabajadorLiquidacion();
                this.selected.setMonto(0);
                this.selected.setNumeroCuotas(0);
                this.selected.setFechaInicioDescuento(fecha);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
            }
        } else {

        }
    }

    public void save() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {

            for (DescuentoTrabajadorLiquidacion d : this.items) {
                session.update(d);
            }
            JsfUtil.addSuccessMessage("Se han actualizado los descuentos ");
            tx.commit();
            this.items = new ArrayList<DescuentoTrabajadorLiquidacion>();
            this.trabajador = null;

        } catch (HibernateException e) {
            tx.rollback();
            System.err.println("NULL:selected");
        }

    }

    public void resetParents() {

    }

    public void delete() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);
                tx.commit();
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
            }
        } else {

        }
    }

    public String getStringMonth() {
        switch (this.mes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
        }
        return "";
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<DescuentoTrabajadorLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<DescuentoTrabajadorLiquidacion> items) {
        this.items = items;
    }

    public DescuentoTrabajadorLiquidacion getSelected() {
        return selected;
    }

    public void setSelected(DescuentoTrabajadorLiquidacion selected) {
        this.selected = selected;
    }

    public List<DescuentoTrabajador> getDescuentosItems() {
        return descuentosItems;
    }

    public void setDescuentosItems(List<DescuentoTrabajador> descuentosItems) {
        this.descuentosItems = descuentosItems;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getCinco() {
        return cinco;
    }

    public void setCinco(int cinco) {
        this.cinco = cinco;
    }

    public List<RelacionLaboral> getRelacionLaboralItems() {
        return relacionLaboralItems;
    }

    public void setRelacionLaboralItems(List<RelacionLaboral> relacionLaboralItems) {
        this.relacionLaboralItems = relacionLaboralItems;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public RelacionLaboral getSelectedRelacionLaboral() {
        return selectedRelacionLaboral;
    }

    public void setSelectedRelacionLaboral(RelacionLaboral selectedRelacionLaboral) {
        this.selectedRelacionLaboral = selectedRelacionLaboral;
    }

    public List<Guia> getGuiaItems() {
        return guiaItems;
    }

    public void setGuiaItems(List<Guia> guiaItems) {
        this.guiaItems = guiaItems;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }

    public int getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(int saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public List<GuiaLiquidacionHelper> getGuiaHelper() {
        return guiaHelper;
    }

    public void setGuiaHelper(List<GuiaLiquidacionHelper> guiaHelper) {
        this.guiaHelper = guiaHelper;
    }

    public GuiaLiquidacionHelper getSelectedGuiaHelper() {
        return selectedGuiaHelper;
    }

    public void setSelectedGuiaHelper(GuiaLiquidacionHelper selectedGuiaHelper) {
        this.selectedGuiaHelper = selectedGuiaHelper;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public class GuiaLiquidacionHelper {

        private int cinco;
        private int ahorro;
        private int saldo;
        private int folio;
        private Date fechaRecaudacion;

        public int getFolio() {
            return folio;
        }

        public void setFolio(int folio) {
            this.folio = folio;
        }

        public int getCinco() {
            return cinco;
        }

        public void setCinco(int cinco) {
            this.cinco = cinco;
        }

        public int getAhorro() {
            return ahorro;
        }

        public void setAhorro(int ahorro) {
            this.ahorro = ahorro;
        }

        public int getSaldo() {
            return saldo;
        }

        public void setSaldo(int saldo) {
            this.saldo = saldo;
        }

        public Date getFechaRecaudacion() {
            return fechaRecaudacion;
        }

        public void setFechaRecaudacion(Date fechaRecaudacion) {
            this.fechaRecaudacion = fechaRecaudacion;
        }

    }

}
