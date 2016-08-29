/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;



import com.areatecnica.sigf_v1.entities.CompraBoleto;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class CompraBoletoDaoImpl implements CompraBoletoDao {

    @Override
    public CompraBoleto findByFolio(int folio) {
        CompraBoleto compraBoleto = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CompraBoleto c WHERE b.folio = " + folio;

        try {
            compraBoleto = (CompraBoleto) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }

        return compraBoleto;
    }

    @Override
    public List<CompraBoleto> findAll() {
        List<CompraBoleto> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CompraBoleto c";

        try {
            list = session.createQuery(sql).list();
            
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }

        return list;
    }

    @Override
    public List<CompraBoleto> findByFechas(Date desde, Date hasta) {
        List<CompraBoleto> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM CompraBoleto c WHERE Fecha BETWEEN '"+desde+"' AND '"+hasta+"'";

        try {
            list = session.createQuery(sql).list();
            
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }

        return list;
    }

}
