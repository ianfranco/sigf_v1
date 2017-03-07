/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class CargosBusQuery {

    private ArrayList<LinkedHashMap> array;
    private String query;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");    
    private Bus bus;

    public CargosBusQuery(Bus bus) {
        this.bus = bus;
    }

    public ArrayList<LinkedHashMap> loadQuery() {
        this.array = new ArrayList<>();

        List list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "	cargo_bus.fecha_inicio_cargo_bus AS fecha, \n"
                + "     tipo_cargo.nombre_tipo_cargo AS nombre,\n"
                + "     cargo_bus.monto_cargo_bus AS monto\n"
                + "     cargo_bus.descripcion AS descripcion\n "
                + "     \n"
                + "     FROM\n"
                + "     cargo_bus\n"
                + "     LEFT JOIN\n"
                + "     tipo_cargo ON cargo_bus.id_tipo_cargo = tipo_cargo.id_tipo_cargo\n"
                + "     WHERE id_bus = "+bus.getIdBus()+" ORDER BY cargo_bus.fecha_inicio_cargo ASC";
        try {

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("fecha", a[0]);
                link.put("nombre", a[1]);
                link.put("descripcion", a[2]);
                link.put("monto", a[3]);
                
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


    public ArrayList<LinkedHashMap> getArray() {
        return array;
    }

    public void setArray(ArrayList<LinkedHashMap> array) {
        this.array = array;
    }

}
