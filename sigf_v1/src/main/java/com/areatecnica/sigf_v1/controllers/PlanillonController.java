/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDao;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDao;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.EstadoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.ServicioProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "planillonController")
@ViewScoped
public class PlanillonController implements Serializable {

    private List<Object[]> resultsValues;
    private List<String> resultsHeader;
    private List<Guia> items;
    private List<ProcesoRecaudacion> procesoRecaudacionItems;
    private ArrayList<EgresoRecaudacion> egresosRecaudacionItems;
    private ArrayList<EgresoGuia> egresosGuiaItems;
    private List<PorcentajeHelper> porcentajesList;
    private List<Bus> busItems;
    private Set<ServicioProcesoRecaudacion> setServicioProcesoRecaudacion;
    
    private GuiaDaoImpl guiaDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDaoImpl;
    private EgresoRecaudacionDao egresoRecaudacionDao;
    private EstadoGuiaDao estadoGuiaDao;
    private BusDao busDao;
    private TrabajadorDao trabajadorDao;

    private Guia selected;

    private String numeroBus;
    private String numeroConductor;
    private Trabajador trabajador;
    private Terminal terminal;
    private Bus bus;
    private Date fechaRecaudacion;
    private String stringHeader;
    private SimpleDateFormat format;
    private ProcesoRecaudacion procesoRecaudacion;
    private EstadoGuia estadoGuia;
    private boolean estado;
    private int codigoConductor;

    //Fecha 
    private String dia;
    private String mes;
    private String anio;
    private String fechaGuia;
    private boolean guiaIngresada;
    
    /**
     * Creates a new instance of PlanillonController
     */
    public PlanillonController() {
        this.resultsHeader = new ArrayList<>();
        this.resultsHeader = new ArrayList<>();
        
        this.guiaDao = new GuiaDaoImpl();
        this.procesoRecaudacionDaoImpl = new ProcesoRecaudacionDaoImpl();
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.busDao = new BusDaoImpl();        
        this.procesoRecaudacionItems = this.procesoRecaudacionDaoImpl.findAll();

        this.estado = Boolean.TRUE;
        this.stringHeader = "Datos Guías";
        if (this.selected == null) {
            this.selected = new Guia();
        }

        Calendar calendar = GregorianCalendar.getInstance();

        this.dia = String.valueOf(calendar.get(Calendar.DATE) - 2);
        this.mes = String.valueOf(calendar.get(Calendar.MONTH));
        this.anio = String.valueOf(calendar.get(Calendar.YEAR));

        this.fechaGuia = String.valueOf(this.dia) + String.valueOf(this.mes) + String.valueOf(this.anio);
    }
    
    public List<Guia> getItems() {
        return items;
    }

    public void setItems(List<Guia> items) {
        this.items = items;
    }

    public Guia getSelected() {
        return selected;
    }

    public void setSelected(Guia selected) {
        this.selected = selected;
    }

    public Date getFechaRecaudacion() {
        return fechaRecaudacion;
    }

    public void setFechaRecaudacion(Date fechaRecaudacion) {
        this.fechaRecaudacion = fechaRecaudacion;
    }

    public ProcesoRecaudacion getProcesoRecaudacion() {
        return procesoRecaudacion;
    }

    public void setProcesoRecaudacion(ProcesoRecaudacion procesoRecaudacion) {
        this.procesoRecaudacion = procesoRecaudacion;
    }

    public List<ProcesoRecaudacion> getProcesoRecaudacionItems() {
        return procesoRecaudacionItems;
    }

    public void setProcesoRecaudacionItems(List<ProcesoRecaudacion> procesoRecaudacionItems) {
        this.procesoRecaudacionItems = procesoRecaudacionItems;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getStringHeader() {
        return stringHeader;
    }

    public void setStringHeader(String stringHeader) {
        this.stringHeader = stringHeader;
    }

    public int getCodigoConductor() {
        return codigoConductor;
    }

    public void setCodigoConductor(int codigoConductor) {
        this.codigoConductor = codigoConductor;
    }

    public List<EgresoRecaudacion> getEgresosRecaudacionItems() {
        return egresosRecaudacionItems;
    }

    public void setEgresosRecaudacionItems(ArrayList<EgresoRecaudacion> egresosRecaudacionItems) {
        this.egresosRecaudacionItems = egresosRecaudacionItems;
    }

    public List<EgresoGuia> getEgresosGuiaItems() {
        return egresosGuiaItems;
    }

    public void setEgresosGuiaItems(ArrayList<EgresoGuia> egresosGuiaItems) {
        this.egresosGuiaItems = egresosGuiaItems;
    }

    public List<PorcentajeHelper> getPorcentajesList() {
        return porcentajesList;
    }

    public void setPorcentajesList(List<PorcentajeHelper> porcentajesList) {
        this.porcentajesList = porcentajesList;
    }

    public String getFechaGuia() {
        return fechaGuia;
    }

    public void setFechaGuia(String fechaGuia) {
        this.fechaGuia = fechaGuia;
    }

    public String getNumeroBus() {
        return numeroBus;
    }

    public void setNumeroBus(String numeroBus) {
        this.numeroBus = numeroBus;
    }

    public String getNumeroConductor() {
        return numeroConductor;
    }

    public void setNumeroConductor(String numeroConductor) {
        this.numeroConductor = numeroConductor;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public List<Bus> getBusItems() {
        return busItems;
    }

    public void setBusItems(List<Bus> busItems) {
        this.busItems = busItems;
    }

    public boolean isGuiaIngresada() {
        return guiaIngresada;
    }

    public void setGuiaIngresada(boolean guiaIngresada) {
        this.guiaIngresada = guiaIngresada;
    }
    
    public void findFolio() {
        if (this.guiaIngresada == false) {
            Guia auxGuia = this.guiaDao.findByFolio(this.selected.getFolio());
            if (auxGuia != null) {
                JsfUtil.addErrorMessage("Guía N°:" + this.selected.getFolio() + " Ingresada");
                this.selected = auxGuia;
            }
        }
    }
    
    public Guia prepareCreate(ActionEvent event) {
        Guia newGuia;
        newGuia = new Guia();
        this.selected = newGuia;
        this.selected.setFechaGuia(fechaRecaudacion);
        this.estadoGuiaDao = new EstadoGuiaDaoImpl();
        this.estadoGuia = this.estadoGuiaDao.findById(1);

        this.trabajador = new Trabajador();
        this.trabajador.setApellidoPaternoTrabajador("Nombre Conductor");
        this.trabajador.setApellidoMaternoTrabajador(" ");
        this.trabajador.setNombreTrabajador(" ");
        this.trabajador.setRutTrabajador("XXXXXXXXX");
        this.bus = new Bus();
        this.bus.setPatente("XXXXXX");
        this.bus.setAnio(2016);
        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa("Nombre Empresa");
        this.bus.setEmpresa(empresa);
        setPorcentajes();
        return newGuia;
    }

    public void init() {
        this.estado = Boolean.FALSE;
        System.err.println("HOLA");
        format = new SimpleDateFormat("dd/MM/yyyy");
        this.stringHeader = "Fecha Recaudación:" + format.format(fechaRecaudacion) + " Proceso:" + this.procesoRecaudacion.getNombreProceso();

        //CARGAR LAS GUIAS OBTENIDAS AL ARRAY DE REGISTROGUIALIST
        this.items = this.guiaDao.findByFechaAndProceso(fechaRecaudacion, procesoRecaudacion);

        /*Busqueda de buses por proceso de recaudacion*/
        this.setServicioProcesoRecaudacion = this.procesoRecaudacion.getServicioProcesoRecaudacions();

        this.busItems = new ArrayList<>();
        this.terminal = new Terminal();

        for (ServicioProcesoRecaudacion spr : this.setServicioProcesoRecaudacion) {
            if (this.terminal != spr.getServicio().getTerminal()) {
                this.terminal = spr.getServicio().getTerminal();
                this.busItems.addAll(this.terminal.getBuses());
            }
        }
        
        for(Guia g:this.items){
            
        }

        /*Ordena los buses de menor a mayor*/
        Collections.sort(this.busItems, new Comparator<Bus>() {
            @Override
            public int compare(Bus o1, Bus o2) {
                if (o1.getNumeroBus() == o2.getNumeroBus()) {
                    return 0;
                } else if (o1.getNumeroBus() < o2.getNumeroBus()) {
                    return -1;
                }
                return 1;
            }
        });

        this.egresoRecaudacionDao = new EgresoRecaudacionDaoImpl();
        this.egresosRecaudacionItems = (ArrayList<EgresoRecaudacion>) this.egresoRecaudacionDao.findByProceso(procesoRecaudacion);
        System.err.println("cantidad egresos:" + this.egresosRecaudacionItems.size());
        System.err.println("cantidad de guias:" + this.items.size());
        setPorcentajes();

    }

    public void loadGuia() {
        this.guiaIngresada = true;
        /*this.egresoRecaudacionDao = new EgresoRecaudacionDaoImpl();
        this.egresosRecaudacionItems = this.egresoRecaudacionDao.findByProceso(procesoRecaudacion);*/

        this.egresosGuiaItems = loadEgresosByGuia();

        this.selected.setEstadoGuia(estadoGuia);
        this.bus = this.selected.getBus();
        this.trabajador = this.selected.getTrabajador();
    }

    private ArrayList<EgresoGuia> loadEgresosByGuia() {
        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        ArrayList<EgresoGuia> array = new ArrayList<>();

        for (EgresoRecaudacion eg : this.egresosRecaudacionItems) {
            System.err.println("PRINT:" + eg + " Guia:" + selected);
            EgresoGuia egresoGuia = daoImpl.findByGuiaAndEgreso(selected.getFolio(), eg);
            if (egresoGuia != null) {
                array.add(egresoGuia);
            }
        }
        return array;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

                this.selected.setEstadoGuia(estadoGuia);
                this.selected.setFechaIngresoGuia(new Date());
                this.selected.setFechaRecaudacion(fechaRecaudacion);
                this.selected.setProcesoRecaudacion(procesoRecaudacion);
                this.selected.setRecaudada(Boolean.TRUE);

                session.saveOrUpdate(this.selected);

                for (EgresoGuia e : this.egresosGuiaItems) {
                    e.setGuia(selected);
                    session.saveOrUpdate(e);

                }

                tx.commit();

                this.items.add(selected);
                this.selected = new Guia();
                this.selected.setTotalEgresos(0);
                this.selected.setTotalIngresos(0);

                for (EgresoGuia e : this.egresosGuiaItems) {
                    e.setMonto(0);
                }

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Guia");
            }
        } else {

        }
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setEstadoGuia(estadoGuia);
                this.selected.setFechaIngresoGuia(new Date());
                this.selected.setFechaRecaudacion(fechaRecaudacion);
                this.selected.setProcesoRecaudacion(procesoRecaudacion);
                this.selected.setRecaudada(Boolean.TRUE);

                session.saveOrUpdate(this.selected);

                for (EgresoGuia e : this.egresosGuiaItems) {
                    e.setGuia(selected);
                    session.saveOrUpdate(e);
                    e.setMonto(0);
                }

                tx.commit();
                this.items.add(selected);
                this.selected = new Guia();
                this.selected.setTotalEgresos(0);
                this.selected.setTotalIngresos(0);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Guia");
            }
        } else {

        }
    }

    public void resetParents() {
        JsfUtil.addSuccessMessage("Guía:" + this.selected.getFolio());
    }

    public void delete() {

    }

    private void setPorcentajes() {
        this.egresosGuiaItems = new ArrayList<EgresoGuia>();
        this.porcentajesList = new ArrayList<PorcentajeHelper>();
        BigDecimal bd = new BigDecimal("0");
        int i = 0;
        for (EgresoRecaudacion e : this.egresosRecaudacionItems) {

            EgresoGuia egreso = new EgresoGuia();
            egreso.setEgresoRecaudacion(e);
            egreso.setMonto(e.getValorDefectoEgreso());

            if (e.getPorcentaje().compareTo(bd) == 1) {
                porcentajesList.add(new PorcentajeHelper(e.getPorcentaje(), i));
            }

            this.egresosGuiaItems.add(egreso);
            i++;
        }

    }

    public String setValoresVariables() {
        int i = 0;
        for (PorcentajeHelper a : porcentajesList) {
            egresosGuiaItems.get(a.getIndex()).setMonto((int) (a.getBd().floatValue() / 100 * this.selected.getTotalIngresos()));
            i++;
        }
        return null;
    }

    public int setTotal() {
        int total = 0;
        for (EgresoGuia e : egresosGuiaItems) {
            total += e.getMonto();
        }
        //list.get(getRowCount()-1).setMonto(total);
        this.selected.setTotalEgresos(total);
        this.selected.setSaldo(this.selected.getTotalIngresos() - this.selected.getTotalEgresos());
        return total;
    }

    public void findBus() {
        this.bus = this.selected.getBus();
    }

    public void findConductor() {
        this.trabajador = this.selected.getTrabajador();
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    private class PorcentajeHelper {

        private BigDecimal bd;
        private Integer index;

        public PorcentajeHelper(BigDecimal bd, Integer index) {
            this.bd = bd;
            this.index = index;
        }

        public void setBd(BigDecimal bd) {
            this.bd = bd;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public BigDecimal getBd() {
            return bd;
        }

        public Integer getIndex() {
            return index;
        }

    }
    
}
