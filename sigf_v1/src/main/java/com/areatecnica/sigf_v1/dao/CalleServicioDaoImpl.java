/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;
import com.areatecnica.sigf_v1.entities.CalleServicio;
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
public class CalleServicioDaoImpl implements CalleServicioDao{

    @Override
    public List<CalleServicio> findAll() {
        List<CalleServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CalleServicio c ";
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:"+list.size());
            for (CalleServicio c : list) {
                
                Hibernate.initialize(c.getCalle());
                Hibernate.initialize(c.getServicio());
                
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CalleServicio> findByServicio(Servicio servicio) {
        List<CalleServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CalleServicio c WHERE c.servicio = " + servicio.getIdServicio();
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:"+list.size());
            for (CalleServicio c : list) {
                
                Hibernate.initialize(c.getCalle());
                Hibernate.initialize(c.getServicio());
                Hibernate.initialize(c.getCalle().getComuna());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
