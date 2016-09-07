/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class EgresoRecaudacionDaoImpl implements EgresoRecaudacionDao {

    @Override
    public List<EgresoRecaudacion> findAll() {
        List<EgresoRecaudacion> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        String sql = "FROM EgresoRecaudacion ";
        
        try{
            list = session.createQuery(sql).list();   
            
            tx.commit();
        } catch (HibernateException e){
            e.printStackTrace();
            tx.rollback();
        } 
        
        return list;
    }

    @Override
    public List<EgresoRecaudacion> findByProceso(ProcesoRecaudacion procesoRecaudacion) {
        List<EgresoRecaudacion> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        String sql = "FROM EgresoRecaudacion WHERE procesoRecaudacion="+procesoRecaudacion.getIdProcesoRecaudacion();
        
        try{
            list = session.createQuery(sql).list();   
            
            tx.commit();
        } catch (HibernateException e){
            e.printStackTrace();
            tx.rollback();
        } 
        
        return list;
    }
    
}
