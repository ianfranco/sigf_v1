/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.FiniquitoRelacionLaboral;
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
public class FiniquitoDaoImpl implements GenericDao<FiniquitoRelacionLaboral>{

    @Override
    public FiniquitoRelacionLaboral findById(int id) {
        FiniquitoRelacionLaboral relacionLaboral = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FiniquitoRelacionLaboral WHERE idFinitquitoRelacionLaboral=" + id;
        try {
            relacionLaboral = (FiniquitoRelacionLaboral) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return relacionLaboral;
    }

    @Override
    public List<FiniquitoRelacionLaboral> findAll() {
        List<FiniquitoRelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FiniquitoRelacionLaboral WHERE estado = 1 ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<FiniquitoRelacionLaboral> findWithLimit() {
        List<FiniquitoRelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FiniquitoRelacionLaboral ORDER BY fechaFiniquito DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<FiniquitoRelacionLaboral> findByEmpresa(Empresa empresa) {
        List<FiniquitoRelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FiniquitoRelacionLaboral WHERE estado = 1 AND empresa="+empresa.getIdEmpresa()+" ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<FiniquitoRelacionLaboral> findAllByEmpresa(Empresa empresa) {
        List<FiniquitoRelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FiniquitoRelacionLaboral WHERE relacionLaboral.empresa="+empresa.getIdEmpresa()+" ORDER BY relacionLaboral.trabajador.apellidoPaternoTrabajador ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<FiniquitoRelacionLaboral> findByDate(Date date) {
        List<FiniquitoRelacionLaboral> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FiniquitoRelacionLaboral WHERE fechaFiniquito BETWEEN '"+format.format(date)+"' AND LAST_DAY('"+format.format(date)+"')  ORDER BY fechaFiniquito ASC";
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
