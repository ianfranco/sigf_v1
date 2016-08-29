/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class BoletoDaoImpl implements BoletoDao{

    @Override
    public List<Boleto> selectedItems() {
        List<Boleto> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();        
        String sql = "FROM Boleto b ";
        
        try{            
            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }
    
    @Override
    public Boleto findById(String id){
        Boleto boleto = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();        
        String sql = "FROM Boleto b WHERE b.idBoleto = "+id;
        
        try{            
            boleto = (Boleto) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        
        return boleto;
    }
    
    
}
