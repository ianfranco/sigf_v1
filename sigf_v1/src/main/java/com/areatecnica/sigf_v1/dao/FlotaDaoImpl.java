/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class FlotaDaoImpl implements FlotaDao{

    @Override
    public List<Flota> findAll() {
        List<Flota> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Flota f ORDER BY nombreFlota ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Flota findById(int id) {
        Flota flota = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Flota WHERE idFlota=" + id;
        try {

            flota = (Flota) session.createQuery(sql).uniqueResult();
            
            tx.commit();
        } catch (HibernateException e) {            
            tx.rollback();
            e.printStackTrace();
        }
        return flota;
    }
    
}
