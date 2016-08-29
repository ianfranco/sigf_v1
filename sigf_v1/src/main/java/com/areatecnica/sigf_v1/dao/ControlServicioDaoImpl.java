/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.ControlServicio;
import com.areatecnica.sigf_v1.entities.Servicio;
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
public class ControlServicioDaoImpl implements ControlServicioDao {

    @Override
    public List<ControlServicio> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ControlServicio> findByIdServicio(int servicio) {
        List<ControlServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ControlServicio c WHERE servicio = " + servicio;
        try {

            list = session.createQuery(sql).list();

            for (ControlServicio c : list) {
                Hibernate.initialize(c.getServicio());
                Hibernate.initialize(c.getControl());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<ControlServicio> findByServicio(Servicio servicio) {
        List<ControlServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ControlServicio c WHERE servicio = " + servicio.getIdServicio();
        try {

            list = session.createQuery(sql).list();

            for (ControlServicio c : list) {
                Hibernate.initialize(c.getServicio());
                Hibernate.initialize(c.getControl());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int findMaxId() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        int id = 0;
        try{
            id = (Integer) session.createQuery("select max(idControlServicio) from ControlServicio").uniqueResult();
        }catch (HibernateException | NumberFormatException e){
            return 1;
        }        
        
        return id+1;
    }

}
