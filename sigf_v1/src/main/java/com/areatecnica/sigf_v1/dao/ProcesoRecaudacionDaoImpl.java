/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class ProcesoRecaudacionDaoImpl implements GenericDao<ProcesoRecaudacion>{

    @Override
    public ProcesoRecaudacion findById(int id) {
        ProcesoRecaudacion procesoRecaudacion = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ProcesoRecaudacion WHERE idProcesoRecaudacion=" + id;
        try {
            procesoRecaudacion = (ProcesoRecaudacion) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return procesoRecaudacion;
    }

    @Override
    public List<ProcesoRecaudacion> findAll() {
        List<ProcesoRecaudacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ProcesoRecaudacion ORDER BY nombreProceso ASC";
        try {

            list = session.createQuery(sql).list();
            
            for(ProcesoRecaudacion l:list){
                Hibernate.initialize(l.getUsuario());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<ProcesoRecaudacion> findAllClean() {
        List<ProcesoRecaudacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ProcesoRecaudacion ORDER BY nombreProceso ASC";
        try {

            list = session.createQuery(sql).list();
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
