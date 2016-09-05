/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.ModeloMarcaBus;
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
public class ModeloMarcaBusDaoImpl implements ModeloMarcaBusDao{

    @Override
    public List<ModeloMarcaBus> findAll() {
        List<ModeloMarcaBus> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ModeloMarcaBus ";
        try {
            

            list = session.createQuery(sql).list();
            
            for(ModeloMarcaBus l:list){
                Hibernate.initialize(l.getMarcaBus());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ModeloMarcaBus findById(int id) {
        ModeloMarcaBus modelo = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ModeloMarcaBus WHERE idModeloMarcaBus=" + id;
        try {

            modelo = (ModeloMarcaBus) session.createQuery(sql).uniqueResult();
            
            tx.commit();
        } catch (HibernateException e) {            
            tx.rollback();
            e.printStackTrace();
        }
        return modelo;
    }
    
}
