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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
@Named(value = "codigosUnoController")
@ViewScoped
public class CodigosUnoController implements Serializable {

    private GuiaDaoImpl guiaDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDaoImpl;
    private EgresoRecaudacionDao egresoRecaudacionDao;
    private EstadoGuiaDao estadoGuiaDao;
    private BusDao busDao;
    private TrabajadorDao trabajadorDao;

    private List<Guia> items;
    private List<ProcesoRecaudacion> procesoRecaudacionItems;
    private ArrayList<EgresoRecaudacion> egresosRecaudacionItems;
    private ArrayList<EgresoGuia> egresosGuiaItems;
    private List<PorcentajeHelper> porcentajesList;
    private List<Bus> busItems;
    private ArrayList<EgresoGuia> arrayEgresosGuias;
    private List<Trabajador> trabajadorItems;
    private Set<ServicioProcesoRecaudacion> setServicioProcesoRecaudacion;
    private Map folios;

    private Guia selected;
    private LinkedHashMap selectedHashMap;

    private String numeroBus;
    private String numeroConductor;
    private Trabajador trabajador;
    private Terminal terminal;
    private Bus bus;
    private Date fechaRecaudacion;
    private Date fechaGuiaDate;
    private String stringHeader;
    private SimpleDateFormat format;
    private ProcesoRecaudacion procesoRecaudacion;
    private EstadoGuia estadoGuia;
    private int codigoConductor;

    private int mes;
    private int anio;
    
    private boolean guiaIngresada;

    private ArrayList<LinkedHashMap> listOfMaps;
    private List<String> resultsHeader;// = service.getResultsValues(...);
    private List<String> resultsTotals;
    private LinkedHashMap totales;
    private Object header;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public CodigosUnoController() {
        this.guiaDao = new GuiaDaoImpl();
        this.procesoRecaudacionDaoImpl = new ProcesoRecaudacionDaoImpl();
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.busDao = new BusDaoImpl();

        this.procesoRecaudacionItems = this.procesoRecaudacionDaoImpl.findAllClean();
        this.trabajadorItems = new ArrayList<Trabajador>();
        
        this.stringHeader = "";
        if (this.selected == null) {
            this.selected = new Guia();
        }

        this.selected.setFechaGuia(this.fechaRecaudacion);
        this.listOfMaps = new ArrayList<LinkedHashMap>();

        LinkedHashMap link = new LinkedHashMap();
        //link.put("Datos", header);
        this.listOfMaps.add(link);
        
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
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

    public ArrayList<LinkedHashMap> getListOfMaps() {
        return listOfMaps;
    }

    public void setListOfMaps(ArrayList<LinkedHashMap> listOfMaps) {
        this.listOfMaps = listOfMaps;
    }

    public List<String> getResultsHeader() {
        return resultsHeader;
    }

    public void setResultsHeader(List<String> resultsHeader) {
        this.resultsHeader = resultsHeader;
    }

    public LinkedHashMap getSelectedHashMap() {
        return selectedHashMap;
    }

    public void setSelectedHashMap(LinkedHashMap selectedHashMap) {
        this.selectedHashMap = selectedHashMap;
    }

    public List<String> getResultsTotals() {
        return resultsTotals;
    }

    public void setResultsTotals(List<String> resultsTotals) {
        this.resultsTotals = resultsTotals;
    }

    public void findFolio() {
        Guia auxGuia = this.guiaDao.findByFolio(this.selected.getFolio());
        if (auxGuia != null) {
            this.guiaIngresada = true;
            JsfUtil.addErrorMessage("Guía N°:" + this.selected.getFolio() + " Ingresada");
            JsfUtil.isValidationFailed();

        } else {
            this.guiaIngresada = false;
        }
    }

    public void createDynamicColumns() {
        this.listOfMaps = new ArrayList<LinkedHashMap>();
        HashMap header = new HashMap();
        this.folios = new HashMap<Integer, Integer>();

        resultsHeader = new ArrayList<String>();
        resultsTotals = new ArrayList<>();
        totales = new LinkedHashMap();

        resultsHeader.add("Folio");
        resultsTotals.add("");
        resultsHeader.add("Fecha");
        resultsTotals.add("");
        resultsHeader.add("Recaudación");
        resultsTotals.add("");
        resultsHeader.add("Bus");
        resultsTotals.add("");
        resultsHeader.add("Recaudación");
        resultsTotals.add("");
        resultsHeader.add("Codigo");
        resultsTotals.add("");
        resultsHeader.add("Conductor");
        resultsTotals.add("Totales");
        resultsHeader.add("T.Ingresos");
        
        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        if (!items.isEmpty()) {
            for (Guia g : items) {
                LinkedHashMap hashMap = new LinkedHashMap();

                this.folios.put(g.getFolio(), g.getIdGuia());

                hashMap.put("Folio", g.getFolio());
                hashMap.put("Fecha", format.format(g.getFechaGuia()));
                hashMap.put("Recaudación", format.format(g.getFechaRecaudacion()));
                hashMap.put("Bus", g.getBus().getNumeroBus());
                hashMap.put("Proceso", g.getProcesoRecaudacion());
                hashMap.put("Codigo", g.getTrabajador().getCodigoTrabajador());
                hashMap.put("Nombre Conductor", g.getTrabajador());
                hashMap.put("T.Ingresos", g.getTotalIngresos());

                if(totales.containsKey("T.Ingresos")){
                    int totalIngresos = (int)totales.get("T.Ingresos");
                    totalIngresos = totalIngresos+g.getTotalIngresos();
                    totales.put("T.Ingresos", totalIngresos);
                }else{
                    totales.put("T.Ingresos", g.getTotalIngresos());
                }
                
                
                /*Por todos los egresos que estén asociados al proceso de recaudación*/
                for (EgresoRecaudacion er : g.getProcesoRecaudacion().getEgresoRecaudacions()) {
                    /*Pregunto si el egreso es recaudable o no*/
                    if (er.getEgreso().isActivo()) {
                        /*Si el egreso es recaudable, busco en la db el egreso asociado a la guía*/
                        EgresoGuia egresoGuia = daoImpl.findByGuiaAndEgreso(g.getIdGuia(), er);
                        /*Agrego el header*/
                        resultsHeader.add(er.getEgreso().getNombreEgreso());
                        if (egresoGuia != null) {
                            String key = er.getEgreso().getNombreEgreso();
                            hashMap.put(key, egresoGuia.getMonto());

                            if (totales.containsKey(key)) {
                                int aux = (int) totales.get(key);
                                aux += egresoGuia.getMonto();
                                totales.put(key, aux);
                            } else {
                                totales.put(key, egresoGuia.getMonto());
                            }
                        } else {
                            hashMap.put(er.getEgreso().getNombreEgreso(), 0);
                        }
                    }
                }

                this.listOfMaps.add(hashMap);

            }

            for (Object i : totales.values()) {
                int totali = (int) i;
                resultsTotals.add(String.valueOf(totali));
            }

        } else {
            LinkedHashMap hashMap = new LinkedHashMap();

            resultsHeader.add("T.Ingresos");
            hashMap.put("T.Ingresos", "0");
            resultsTotals.add("0");
            
            resultsHeader.add("Administracion");
            hashMap.put("Administracion", "0");
            resultsTotals.add("0");
            resultsHeader.add("Licitación");
            hashMap.put("Licitación", "0");
            resultsTotals.add("0");
            resultsHeader.add("Taller");
            hashMap.put("Taller", "0");
            resultsTotals.add("0");
            resultsHeader.add("5%");
            hashMap.put("5%", "0");
            resultsTotals.add("0");
            resultsHeader.add("Aseo");
            hashMap.put("Aseo", "0");
            resultsTotals.add("0");

            this.listOfMaps.add(hashMap);
        }

    }

    public Guia prepareCreate(ActionEvent event) {
        Guia newGuia;
        newGuia = new Guia();
        this.selected = newGuia;
        this.selected.setFechaGuia(this.fechaRecaudacion);

        this.stringHeader = "Fecha Recaudación:" + format.format(fechaRecaudacion) + " Proceso:" + this.procesoRecaudacion.getNombreProceso();
        
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

    public void init() {
        format = new SimpleDateFormat("dd/MM/yyyy");
        this.stringHeader = "Códigos Uno / Mes: " + getStringMonth() + " Año:" + anio;

        String from = "01/" + mes + "/" + anio;
        try {
            this.fechaRecaudacion = format.parse(from);
        } catch (ParseException p) {

        }
        
        //CARGAR LAS GUIAS OBTENIDAS AL ARRAY DE REGISTROGUIALIST
        this.items = this.guiaDao.findCodigosUnoBetweenDates(fechaRecaudacion);
        
        createDynamicColumns();
    }

    public void loadGuia() {
        LinkedHashMap link = this.selectedHashMap;
        int folioGuia = (int) link.get("Folio");
        int idGuia = (int) this.folios.get(folioGuia);

        this.guiaIngresada = true;
        this.selected = this.guiaDao.findByFolio(folioGuia);
        this.egresosGuiaItems = loadEgresosByGuia(idGuia);
        this.bus = this.selected.getBus();
        this.trabajador = this.selected.getTrabajador();
        if(this.trabajadorItems.isEmpty()){
            this.trabajadorItems = this.trabajadorDao.findAllClean();
        }
    }

    private ArrayList<EgresoGuia> loadEgresosByGuia(int idGuia) {
        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        this.arrayEgresosGuias = new ArrayList<>();

        for (EgresoRecaudacion eg : this.selected.getProcesoRecaudacion().getEgresoRecaudacions()) {
            EgresoGuia egresoGuia = daoImpl.findByGuiaAndEgreso(idGuia, eg);
            if (egresoGuia != null) {
                this.arrayEgresosGuias.add(egresoGuia);
            }
        }
        return this.arrayEgresosGuias;
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

                session.save(this.selected);

                this.folios.put(this.selected.getFolio(), this.selected.getIdGuia());
                
                JsfUtil.addSuccessMessage("SE INGRESÓ LA GUÍA N°:"+this.selected.getFolio()+" EN LA RECUADACIÓN:"+this.selected.getProcesoRecaudacion().getNombreProceso()+" CON FECHA:"+format.format(fechaRecaudacion));
                tx.commit();
                
                this.fechaGuiaDate = this.selected.getFechaGuia();

                this.selected = new Guia();
                this.selected.setFechaGuia(fechaGuiaDate);

                this.selected.setTotalEgresos(0);
                this.selected.setTotalIngresos(0);
                this.selectedHashMap = null;

                setPorcentajes();

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
                session.saveOrUpdate(this.selected);
                int index = this.listOfMaps.indexOf(this.selectedHashMap);
                LinkedHashMap auxHash = this.selectedHashMap;

                this.selectedHashMap.put("Folio", this.selected.getFolio());
                this.selectedHashMap.put("Fecha", format.format(this.selected.getFechaGuia()));
                this.selectedHashMap.put("Bus", this.selected.getBus().getNumeroBus());
                this.selectedHashMap.put("Codigo", this.selected.getTrabajador().getCodigoTrabajador());
                this.selectedHashMap.put("Nombre Conductor", this.selected.getTrabajador());

                tx.commit();

                this.listOfMaps.remove(this.selectedHashMap);

                //this.items.add(selected);
                this.selected = new Guia();
                this.selected.setTotalEgresos(0);
                this.selected.setTotalIngresos(0);
                this.selectedHashMap = null;

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Guia");
            }
        } else {

        }
    }

    public void resetParents() {
        //JsfUtil.addSuccessMessage("Guía:" + this.selected.getFolio());
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

    public LinkedHashMap getTotales() {
        return totales;
    }

    public void setTotales(LinkedHashMap totales) {
        this.totales = totales;
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

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
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
