/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class UnidadNegocioDaoImpl implements UnidadNegocioDao{

    @Override
    public List<UnidadNegocio> findAll() {
        List<UnidadNegocio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM UnidadNegocio ORDER BY numeroUnidadNegocio ASC";
        try {

            list = session.createQuery(sql).list();
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<UnidadNegocio> findVinabus() {
        List<UnidadNegocio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM UnidadNegocio WHERE idUnidadNegocio<>3 ORDER BY numeroUnidadNegocio ASC";
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
    public UnidadNegocio findById(int id) {
        UnidadNegocio unidad = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM UnidadNegocio WHERE idUnidadNegocio=" + id;
        try {

            unidad = (UnidadNegocio) session.createQuery(sql).uniqueResult();
            
            tx.commit();
        } catch (HibernateException e) {            
            tx.rollback();
            e.printStackTrace();
        }
        return unidad;
    }
    
}
