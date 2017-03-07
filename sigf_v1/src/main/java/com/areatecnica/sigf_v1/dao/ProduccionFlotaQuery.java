/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class ProduccionFlotaQuery {

    private ArrayList<LinkedHashMap> array;
    private List<String> resultsTotals;
    private String query;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private Date fecha;

    public ProduccionFlotaQuery(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<LinkedHashMap> loadQuery() {
        this.array = new ArrayList<>();

        List list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "                flota.nombre_flota, \n"
                + "                bus.numero_bus, \n"
                + "                bus.patente, \n"
                + "                unidad_negocio.numero_unidad_negocio,    \n"
                + "                FLOOR(COUNT(guia.folio)) AS numeroGuia, \n"
                + "                FLOOR(sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 1)) AS Administracion, \n"
                + "                FLOOR(sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 7)) AS Licitacion, \n"
                + "                FLOOR(sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 4)) AS Taller, \n"
                + "                convert(int,(cargos(bus.id_bus))) AS Cargo, \n" 
                + "                FLOOR(sum_saldos(bus.id_bus, '" + format.format(fecha) + "')-cargos(bus.id_bus)) AS Total \n"
                + "                FROM guia \n"
                + "                LEFT JOIN bus ON guia.id_bus = bus.id_bus \n"
                + "                LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "                LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "                WHERE guia.fecha_recaudacion BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "') \n"
                + "                GROUP BY guia.id_bus \n"
                + "                ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        try {

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("Flota", a[0]);
                link.put("N°Bus", a[1]);
                link.put("Patente", a[2]);
                link.put("Unidad", a[3]);
                link.put("N°Guias", a[4]);
                link.put("Administracion", a[5]);
                link.put("Licitacion", a[6]);
                link.put("Taller", a[7]);
                link.put("Cargos", a[8]);
                link.put("Saldo", a[9]);
                //}
                
                
                                
                this.array.add(link);
            }
           
            System.err.println("TERMINÓOOOOOOO");
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }

        return array;
    }

    public ArrayList<LinkedHashMap> loadQueryByFlota(Flota flota) {
        this.array = new ArrayList<>();
        this.resultsTotals = new ArrayList<>();
        
        this.resultsTotals.add("");
        this.resultsTotals.add("");
        this.resultsTotals.add("");
        this.resultsTotals.add("");
        this.resultsTotals.add("");
        
        
        
        List list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "                flota.nombre_flota, \n"
                + "                bus.numero_bus, \n"
                + "                bus.patente, \n"
                + "                unidad_negocio.numero_unidad_negocio,    \n"
                + "                COUNT(guia.folio) AS numeroGuia, \n"
                + "                sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 1) AS Administracion, \n"
                + "                sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 7) AS Licitacion, \n"
                + "                sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 4) AS Taller, \n"
                + "                convert(int,cargos(bus.id_bus)) AS Cargo, \n" 
                + "                sum_saldos(bus.id_bus, '" + format.format(fecha) + "') - convert(int,cargos(bus.id_bus)) AS Total \n"                
                + "                FROM guia \n"
                + "                LEFT JOIN bus ON guia.id_bus = bus.id_bus \n"
                + "                LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "                LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "                WHERE guia.fecha_recaudacion BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "') AND flota.id_flota = "+flota.getIdFlota()+"\n"
                + "                GROUP BY guia.id_bus \n"
                + "                ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        try {
            
            int administracion = 0;
            int licitacion = 0;
            int taller = 0;
            int cargos = 0;
            int saldo = 0;

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("Flota", a[0]);
                link.put("N°Bus", a[1]);
                link.put("Patente", a[2]);
                link.put("Unidad", a[3]);
                link.put("N°Guias", a[4]);
                link.put("Administracion", a[5]);
                link.put("Licitacion", a[6]);
                link.put("Taller", a[7]);
                link.put("Cargos", a[8]);
                link.put("Saldo", a[9]);
                //}
                
                
                int auxAdministracion = ((Integer)a[5]);
                int auxLicitacion = ((Integer)a[6]);
                int auxTaller = ((Integer)a[7]);
                int auxCargos = ((Integer)a[8]);
                int auxSaldo = ((Integer)a[9]);
                
                administracion = administracion + (auxAdministracion);
                licitacion = licitacion + (auxLicitacion);
                taller = taller  + (auxTaller);
                cargos = cargos  + (auxCargos);
                saldo = saldo  + (auxSaldo);
                
                
                
                this.array.add(link);
            }
            
            this.resultsTotals.add(String.valueOf(administracion));
            this.resultsTotals.add(String.valueOf(licitacion));
            this.resultsTotals.add(String.valueOf(taller));
            this.resultsTotals.add(String.valueOf(cargos));
            this.resultsTotals.add(String.valueOf(saldo));
            

            System.err.println("TERMINÓOOOOOOO");
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }

        return array;
    }

    public ArrayList<LinkedHashMap> getArray() {
        return array;
    }

    public void setArray(ArrayList<LinkedHashMap> array) {
        this.array = array;
    }

    public List<String> getResultsTotals() {
        return resultsTotals;
    }

    public void setResultsTotals(List<String> resultsTotals) {
        this.resultsTotals = resultsTotals;
    }

}
