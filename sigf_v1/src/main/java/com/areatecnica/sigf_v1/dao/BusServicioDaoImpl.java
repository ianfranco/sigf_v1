/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.BusServicio;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian
 */
public class BusServicioDaoImpl implements BusServicioDao{

    @Override
    public List<BusServicio> findAll() {
        List<BusServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM BusServicio b ";
        try {

            list = session.createQuery(sql).list();
            
            for (BusServicio b : list) {
                
                Hibernate.initialize(b.getBus());
                Hibernate.initialize(b.getServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BusServicio> findByBus(Bus bus) {
        List<BusServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM BusServicio b WHERE b.bus = " + bus.getIdBus();
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:"+list.size());
            for (BusServicio c : list) {
                Hibernate.initialize(c.getServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
