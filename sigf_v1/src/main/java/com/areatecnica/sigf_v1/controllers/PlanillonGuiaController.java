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
import com.areatecnica.sigf_v1.dao.PrivilegioDaoImpl;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.EstadoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.Log;
import com.areatecnica.sigf_v1.entities.Privilegio;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.ServicioProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.entities.Usuario;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "planillonGuiaController")
@ViewScoped
public class PlanillonGuiaController implements Serializable {

    @Inject
    private LoginBean sessionController;

    private GuiaDaoImpl guiaDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDaoImpl;
    private EgresoRecaudacionDao egresoRecaudacionDao;
    private EstadoGuiaDao estadoGuiaDao;
    private BusDao busDao;
    private TrabajadorDao trabajadorDao;
    private PrivilegioDaoImpl privilegioDao;

    private List<Guia> items;
    private List<ProcesoRecaudacion> procesoRecaudacionItems;
    private ArrayList<EgresoRecaudacion> egresosRecaudacionItems;
    private ArrayList<LinkedHashMap> listOfMaps;
    private ArrayList<EgresoGuia> egresosGuiaItems;
    private List<String> resultsHeader;// = service.getResultsValues(...);
    private List<String> resultsTotals;
    private LinkedHashMap totales;
    private List<PorcentajeHelper> porcentajesList;
    private List<Bus> busItems;
    private List<Trabajador> trabajadorItems;
    private ArrayList<EgresoGuia> arrayEgresosGuias;
    private Set<ServicioProcesoRecaudacion> setServicioProcesoRecaudacion;
    private Map folios;

    private Guia selected;
    private LinkedHashMap selectedHashMap;

    private String numeroBus;
    private String numeroConductor;
    private Usuario user;
    private Trabajador trabajador;
    private Terminal terminal;
    private Bus bus;
    private Date fechaRecaudacion;
    private Date fechaGuiaDate;
    private String stringHeader;
    private SimpleDateFormat format;
    private ProcesoRecaudacion procesoRecaudacion;
    private EstadoGuia estadoGuia;
    private Log log;
    private Privilegio priviliegio;
    private int codigoConductor;

    private boolean guiaIngresada;

    private Object header;
    private boolean registroGuias = false;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public PlanillonGuiaController() {

    }

    @PostConstruct
    public void start() {
        if (this.sessionController.isRegistroGuias()) {

            System.err.println("SI TIENE ACCESO A LAS GUÍAS");

            this.user = this.sessionController.getUsuario();

            System.err.println("CARACTERISTICAS DEL USUARIO:" + this.user.getRut() + " " + this.user.getEmail());

            this.privilegioDao = new PrivilegioDaoImpl();
            this.guiaDao = new GuiaDaoImpl();
            this.procesoRecaudacionDaoImpl = new ProcesoRecaudacionDaoImpl();
            this.trabajadorDao = new TrabajadorDaoImpl();
            this.busDao = new BusDaoImpl();
            this.trabajadorItems = new ArrayList<Trabajador>();
            this.procesoRecaudacionItems = this.procesoRecaudacionDaoImpl.findAllClean();

            this.stringHeader = "Datos Guías";
            if (this.selected == null) {
                this.selected = new Guia();
            }

            this.selected.setFechaGuia(this.fechaRecaudacion);
            this.listOfMaps = new ArrayList<LinkedHashMap>();

            LinkedHashMap link = new LinkedHashMap();
            //link.put("Datos", header);
            this.listOfMaps.add(link);

            this.priviliegio = this.privilegioDao.findById(2);

        } else {
            System.err.println("NO TIENE ACCESO A LAS GUÍAS");
            sessionController.logout();
        }

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
        resultsHeader.add("Bus");
        resultsTotals.add("");
        resultsHeader.add("Codigo");
        resultsTotals.add("");
        resultsHeader.add("Conductor");
        resultsTotals.add("Totales");

        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        if (!items.isEmpty()) {
            for (Guia g : items) {
                LinkedHashMap hashMap = new LinkedHashMap();

                this.folios.put(g.getFolio(), g.getIdGuia());

                hashMap.put("Folio", g.getFolio());
                hashMap.put("Fecha", format.format(g.getFechaGuia()));
                hashMap.put("Bus", g.getBus().getNumeroBus());
                hashMap.put("Codigo", g.getTrabajador().getCodigoTrabajador());
                hashMap.put("Nombre Conductor", g.getTrabajador());

                /*Por todos los egresos que estén asociados al proceso de recaudación*/
                for (EgresoRecaudacion er : this.egresosRecaudacionItems) {
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

            hashMap.put("Folio", "");
            hashMap.put("Fecha", "");
            hashMap.put("Bus", "");
            hashMap.put("Codigo", "");
            hashMap.put("Nombre Conductor", "");

            for (EgresoRecaudacion er : this.egresosRecaudacionItems) {
                /*Pregunto si el egreso es recaudable o no*/
                if (er.getEgreso().isActivo()) {
                    resultsHeader.add(er.getEgreso().getNombreEgreso());
                    hashMap.put(er.getEgreso().getNombreEgreso(), "");
                }
            }
            this.listOfMaps.add(hashMap);
        }

    }

    public Guia prepareCreate(ActionEvent event) {

        this.trabajadorItems = this.trabajadorDao.findAllClean();

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

    public void init() {
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

                Set<Bus> listAuxiliarBuses = this.terminal.getBuses();

                for (Bus b : listAuxiliarBuses) {
                    if (b.getUnidadNegocio().getIdUnidadNegocio() == this.procesoRecaudacion.getIdUnidad()) {
                        this.busItems.add(b);
                    }
                }

                //this.busItems.addAll(listAuxiliarBuses);
            }

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
        setPorcentajes();
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

        if (this.trabajadorItems.isEmpty()) {
            this.trabajadorItems = this.trabajadorDao.findAllClean();
        } else {
            System.err.println("NO ESTA VACIA LA LISTA");
        }

    }

    private ArrayList<EgresoGuia> loadEgresosByGuia(int idGuia) {
        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        this.arrayEgresosGuias = new ArrayList<>();

        for (EgresoRecaudacion eg : this.egresosRecaudacionItems) {
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

                /*hashMap.put("Folio", this.selected.getFolio());
                hashMap.put("Fecha", format.format(this.selected.getFechaGuia()));
                hashMap.put("Bus", this.selected.getBus().getNumeroBus());
                hashMap.put("Codigo", this.selected.getTrabajador().getCodigoTrabajador());
                hashMap.put("Nombre Conductor", this.selected.getTrabajador());*/
                for (EgresoGuia e : this.egresosGuiaItems) {
                    e.setGuia(selected);
                    session.save(e);
                    /*if (e.getEgresoRecaudacion().getEgreso().isActivo()) {
                        String key = e.getEgresoRecaudacion().getEgreso().getNombreEgreso();
                        hashMap.put(key, e.getMonto());

                        if (totales.containsKey(key)) {
                            int aux = (int) totales.get(key);
                            aux += e.getMonto();
                            totales.put(key, aux);
                        } else {
                            totales.put(key, e.getMonto());
                        }
                    }*/
                    e = null;
                }

                this.log = new Log();
                this.log.setPrivilegio(priviliegio);
                this.log.setUsuario(user);
                this.log.setTipoAccion("Ingreso");
                this.log.setFechaRegistroLog(new Date());
                this.log.setDescripcionLog("Guía Folio N°: " + this.selected.getFolio() + "  Proceso: " + this.procesoRecaudacion.getNombreProceso());

                session.save(this.log);

                JsfUtil.addSuccessMessage("SE INGRESÓ LA GUÍA N°:" + this.selected.getFolio() + " EN LA RECUADACIÓN:" + this.selected.getProcesoRecaudacion().getNombreProceso() + " CON FECHA:" + format.format(fechaRecaudacion));
                tx.commit();

                //this.items.add(selected);

                /*for (Object i : totales.values()) {
                    int totali = (int) i;
                    resultsTotals.add(String.valueOf(totali));
                }*/

 /*Por todos los egresos que estén asociados al proceso de recaudación*/
 /*if (this.listOfMaps.size() == 1) {
                    LinkedHashMap auxHash = this.listOfMaps.get(0);
                    String auxFolio = String.valueOf(auxHash.get("Folio"));
                    if (auxFolio.equals("")) {
                        this.listOfMaps = new ArrayList<>();
                    } else {
                        System.err.println("HAY UNA GUÍA INGRESADA CON EL FOLIO:" + auxFolio);
                    }
                }*/

 /*if(.size()==0){
                    this.listOfMaps = new ArrayList<LinkedHashMap>();
                }*/
                //this.listOfMaps.add(hashMap);
                /*Termina de agregar la información*/
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

                for (EgresoGuia e : arrayEgresosGuias) {
                    session.saveOrUpdate(e);
                    this.selectedHashMap.put(e.getEgresoRecaudacion().getEgreso().getNombreEgreso(), e.getMonto());
                    session.flush();
                }

                this.log = new Log();
                this.log.setPrivilegio(priviliegio);
                this.log.setUsuario(user);
                this.log.setTipoAccion("Edición");
                this.log.setFechaRegistroLog(new Date());
                this.log.setDescripcionLog("Guía Folio N°: " + this.selected.getFolio() + " Proceso: " + this.procesoRecaudacion.getNombreProceso());

                session.save(this.log);

                tx.commit();

                this.listOfMaps.set(index, this.selectedHashMap);

                JsfUtil.addSuccessMessage("SE HA EDITADO LA GUÍA N°:" + this.selected.getFolio());

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
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);

                this.log = new Log();
                this.log.setPrivilegio(priviliegio);
                this.log.setUsuario(user);
                this.log.setTipoAccion("Borrado");
                this.log.setFechaRegistroLog(new Date());
                this.log.setDescripcionLog("Guía Folio N°: " + this.selected.getFolio() + " Proceso: " + this.procesoRecaudacion.getNombreProceso());

                session.save(this.log);

                tx.commit();
                this.listOfMaps.remove(this.selectedHashMap);
                init();
                JsfUtil.addSuccessMessage("SE HA ELIMINADO LA GUÍA DE FOLIO N°: " + this.selected.getFolio());

                this.selected = null;
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Guia");
            }
        } else {

        }
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
        setTotal();
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

        System.err.println("CALCULADO EL TOTAL");

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

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public LoginBean getSessionController() {
        return sessionController;
    }

    public void setSessionController(LoginBean sessionController) {
        this.sessionController = sessionController;
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
