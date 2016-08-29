/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.TarifaServicio;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class TarifaServicioDaoImpl implements TarifaServicioDao {

    @Override
    public List<TarifaServicio> findAll() {
        List<TarifaServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TarifaServicio c ";
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:" + list.size());
            for (TarifaServicio t : list) {

                Hibernate.initialize(t.getBoleto());
                Hibernate.initialize(t.getServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<TarifaServicio> findByServicio(Servicio servicio) {
        List<TarifaServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TarifaServicio c WHERE c.servicio = " + servicio.getIdServicio();
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:" + list.size());
            for (TarifaServicio t : list) {

                Hibernate.initialize(t.getBoleto());
                Hibernate.initialize(t.getServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<TarifaServicio> findByServicioMonthYear(Servicio servicio, int month, int year) {
        List<TarifaServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TarifaServicio c WHERE c.servicio = " + servicio.getIdServicio()+" AND mes="+month+" AND año="+year;
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:" + list.size());
            for (TarifaServicio t : list) {

                Hibernate.initialize(t.getBoleto());
                Hibernate.initialize(t.getServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<TarifaServicio> findByServicioYear(Servicio servicio, int year) {
        List<TarifaServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TarifaServicio c WHERE c.servicio = " + servicio.getIdServicio()+" AND año="+year;
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:" + list.size());
            for (TarifaServicio t : list) {

                Hibernate.initialize(t.getBoleto());
                Hibernate.initialize(t.getServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
