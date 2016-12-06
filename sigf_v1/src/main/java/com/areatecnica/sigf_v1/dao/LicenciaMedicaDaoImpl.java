/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.LicenciaMedica;
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
public class LicenciaMedicaDaoImpl implements GenericDao<LicenciaMedica>{

    @Override
    public LicenciaMedica findById(int id) {
        LicenciaMedica licenciaMedica = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM LicenciaMedica WHERE idLicenciaMedica=" + id;
        try {
            licenciaMedica = (LicenciaMedica) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return licenciaMedica;
    }

    @Override
    public List<LicenciaMedica> findAll() {
        List<LicenciaMedica> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM LicenciaMedica";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<LicenciaMedica> findWithLimit() {
        List<LicenciaMedica> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM LicenciaMedica ORDER BY fechaDesdeReposo DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<LicenciaMedica> findBetweenDate(Date fecha) {
        List<LicenciaMedica> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM LicenciaMedica WHERE fechaDesdeReposo BETWEEN '"+format.format(fecha)+"' AND LAST_DAY('"+format.format(fecha)+"') ORDER BY fechaDesdeReposo ASC";
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
