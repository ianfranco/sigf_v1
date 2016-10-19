/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.AbonoBus;
import com.areatecnica.sigf_v1.entities.Bus;
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
public class AbonoBusDaoImpl implements GenericDao<AbonoBus>{

    @Override
    public AbonoBus findById(int id) {
        AbonoBus asignacionFamiliar = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AbonoBus WHERE idAbonoBus=" + id;
        try {
            asignacionFamiliar = (AbonoBus) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return asignacionFamiliar;
    }

    @Override
    public List<AbonoBus> findAll() {
        List<AbonoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AbonoBus ORDER BY idAbonoBus DESC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
        
    public List<AbonoBus> findByBus(Bus bus) {
        List<AbonoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AbonoBus WHERE bus = "+bus.getIdBus()+" ORDER BY idAbonoBus DESC";
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
    
    public List<AbonoBus> findGROUP() {
        List<AbonoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AbonoBus GROUP BY idAbon ORDER BY descripcion ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<AbonoBus> findByCargo(int abono) {
        List<AbonoBus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AbonoBus WHERE idAbon ="+abono+"";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<AbonoBus> findByBusAndDate(Bus bus, Date date) {
        List<AbonoBus> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AbonoBus WHERE bus = "+bus.getIdBus()+" AND fechaInicioAbonoBus BETWEEN '"+format.format(date)+"' AND LAST_DAY('"+format.format(date)+"') ORDER BY idAbonoBus DESC";
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
