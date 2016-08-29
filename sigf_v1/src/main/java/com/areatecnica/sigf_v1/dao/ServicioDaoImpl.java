/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

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
public class ServicioDaoImpl implements ServicioDao{

    @Override
    public List<Servicio> findAll() {
        List<Servicio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Servicio ";
        try {

            list = session.createQuery(sql).list();
            
            for(Servicio a:list){
                Hibernate.initialize(a.getTerminal());
                Hibernate.initialize(a.getUnidadNegocio());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Servicio> findByIdTerminal(int idTerminal) {
        List<Servicio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Servicio s WHERE terminal = "+idTerminal;
        try {

            list = session.createQuery(sql).list();
            for(Servicio a:list){
                Hibernate.initialize(a.getTerminal());
                Hibernate.initialize(a.getUnidadNegocio());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
