/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;


import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class TerminalDaoImpl implements TerminalDao{

    @Override
    public List<Terminal> findAll() {
        List<Terminal> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        String sql = "FROM Terminal t";
        
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
    public Terminal findById(int idTerminal) {
        Terminal terminal = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Terminal WHERE idTerminal=" + idTerminal;
        try {

            terminal = (Terminal) session.createQuery(sql).uniqueResult();
            
            tx.commit();
        } catch (HibernateException e) {
            
            tx.rollback();
            e.printStackTrace();
        }
        return terminal;
    }
    
    
    
}
