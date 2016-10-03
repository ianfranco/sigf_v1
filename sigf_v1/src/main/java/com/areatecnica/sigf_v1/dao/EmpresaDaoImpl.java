/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Empresa;
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
public class EmpresaDaoImpl implements EmpresaDao{

    
    @Override
    public Empresa findById(int id) {
        Empresa empresa = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Empresa WHERE idEmpresa=" + id;
        try {

            empresa = (Empresa) session.createQuery(sql).uniqueResult();
           
            Hibernate.initialize(empresa.getMutual());
            Hibernate.initialize(empresa.getCajaCompensacion());
            
            tx.commit();
        } catch (HibernateException e) {            
            tx.rollback();
            e.printStackTrace();
        }
        return empresa;
    }
    
    @Override
    public List<Empresa> findAll() {
        List<Empresa> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();        
        String sql = "FROM Empresa e ORDER BY e.nombreEmpresa ASC";
        
        try{            
            list = session.createQuery(sql).list();
            
            for(Empresa e:list){
                Hibernate.initialize(e.getCajaCompensacion());
                Hibernate.initialize(e.getMutual());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }

    @Override
    public List<Empresa> findAllClean() {
        List<Empresa> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();        
        String sql = "FROM Empresa e ORDER BY e.nombreEmpresa ASC";
        
        try{            
            list = session.createQuery(sql).list();                        
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }
    
    public List<Empresa> findAllCleanOrderById() {
        List<Empresa> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();        
        String sql = "FROM Empresa e ORDER BY e.idEmpresa ASC";
        
        try{            
            list = session.createQuery(sql).list();                        
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }

    
}
