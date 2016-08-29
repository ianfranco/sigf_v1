/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.EstadoGuia;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class EstadoGuiaDaoImpl implements EstadoGuiaDao{

    @Override
    public List<EstadoGuia> findAll() {
        List<EstadoGuia> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();        
        String sql = "FROM EstadoGuia e ";
        
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
