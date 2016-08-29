/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class AbstractDao<T> {
    
    private Class<T> entityClass;
    private Session session;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public Object find(Object id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Object t = null;
        try {
            t =  session.get(entityClass, (Serializable)id);                    
            tx.commit();
        } catch (HibernateException e) {
            System.err.println("PROBLEMA AL RECIBIR DATOS");
            tx.rollback();
        }
        return t;
    }
    
    
}
