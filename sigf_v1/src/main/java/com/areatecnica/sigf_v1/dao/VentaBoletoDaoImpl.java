/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.VentaBoleto;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class VentaBoletoDaoImpl implements VentaBoletoDao{

    @Override
    public List<VentaBoleto> findAll() {
        List<VentaBoleto> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        String sql = "FROM VentaBoleto v";
        
        try{
            list = session.createQuery(sql).list();   
            
            for(VentaBoleto l: list){
                Hibernate.initialize(l.getTerminal());
                //Hibernate.initialize(l.get());
            }
            
            tx.commit();
        } catch (HibernateException e){
            e.printStackTrace();
            tx.rollback();
        } 
        
        return list;
    }

    @Override
    public VentaBoleto findByFecha(Date fechaVenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VentaBoleto findByTerminal(Terminal terminal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
