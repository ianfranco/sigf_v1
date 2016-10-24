/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.CargoBus;
import com.areatecnica.sigf_v1.entities.TipoCargo;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class CargoBusDaoImpl implements GenericDao<CargoBus>{

    @Override
    public CargoBus findById(int id) {
        CargoBus asignacionFamiliar = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus WHERE idCargoBus=" + id;
        try {
            asignacionFamiliar = (CargoBus) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return asignacionFamiliar;
    }

    @Override
    public List<CargoBus> findAll() {
        List<CargoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus ORDER BY idCargoBus DESC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<CargoBus> findByCargo(int cargo) {
        List<CargoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus WHERE idCargo ="+cargo+"";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<CargoBus> findGROUP() {
        List<CargoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus GROUP BY idCargo ORDER BY descripcion ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<CargoBus> findByBus(Bus bus) {
        List<CargoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus WHERE bus = "+bus.getIdBus()+" ORDER BY idCargoBus DESC";
        try {
            System.err.println(sql);
            list = session.createQuery(sql).list();
            System.err.println("tamaño"+list.size());
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<CargoBus> findByCargoAndDate(TipoCargo tipoCargo, Date date) {
        List<CargoBus> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus WHERE tipoCargo = "+tipoCargo.getIdTipoCargo()+" AND fechaInicioCargoBus BETWEEN '"+format.format(date)+"' AND LAST_DAY('"+format.format(date)+"') ORDER BY bus.numeroBus ASC";
        try {
            System.err.println(sql);
            list = session.createQuery(sql).list();
            System.err.println("tamaño"+list.size());
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<CargoBus> findByBusAndDate(Bus bus, Date date) {
        List<CargoBus> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus WHERE bus = "+bus.getIdBus()+" AND fechaInicioCargoBus BETWEEN '"+format.format(date)+"' AND LAST_DAY('"+format.format(date)+"') ORDER BY idCargoBus DESC";
        try {
            System.err.println(sql);
            list = session.createQuery(sql).list();
            System.err.println("tamaño"+list.size());
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<CargoBus> findByBusAndDateAndCargo(Bus bus, Date date, TipoCargo cargo) {
        List<CargoBus> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/01");
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CargoBus WHERE bus = "+bus.getIdBus()+" AND fechaInicioCargoBus BETWEEN '"+format.format(date)+"' AND LAST_DAY('"+format.format(date)+"') AND tipoCargo="+cargo.getIdTipoCargo()+" ORDER BY idCargoBus DESC";
        try {
            System.err.println(sql);
            list = session.createQuery(sql).list();
            System.err.println("tamaño"+list.size());
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
