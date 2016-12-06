/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.FeriadoLegal;
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
public class FeriadoLegalDaoImpl implements GenericDao<FeriadoLegal>{

    @Override
    public FeriadoLegal findById(int id) {
        FeriadoLegal feriadoLegal = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FeriadoLegal WHERE idFeriadoLegal=" + id;
        try {
            feriadoLegal = (FeriadoLegal) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return feriadoLegal;
    }

    @Override
    public List<FeriadoLegal> findAll() {
        List<FeriadoLegal> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FeriadoLegal";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<FeriadoLegal> findWithLimit() {
        List<FeriadoLegal> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FeriadoLegal ORDER BY fechaDesdeFeriado DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<FeriadoLegal> findBetweenDate(Date fecha) {
        List<FeriadoLegal> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FeriadoLegal WHERE fechaDesdeFeriado BETWEEN '"+format.format(fecha)+"' AND LAST_DAY('"+format.format(fecha)+"') ORDER BY fechaDesdeFeriado ASC";
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
