/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Log;
import com.areatecnica.sigf_v1.entities.Privilegio;
import com.areatecnica.sigf_v1.entities.Usuario;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class LogDaoImpl implements GenericDao<Log>{

    @Override
    public Log findById(int id) {
        Log log = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE idLog=" + id;
        try {
            log = (Log) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return log;
    }

    @Override
    public List<Log> findAll() {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
            
            for(Log l:list){
                Hibernate.initialize(l.getPrivilegio());
                Hibernate.initialize(l.getUsuario());
            }
            
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<Log> findByPrivilegio(Privilegio privilegio) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE privilegio = "+privilegio.getIdPrivilegio();
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Log> findByIdPrivilegio(int privilegio) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE privilegio = "+privilegio;
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Log> findByIdPrivilegioLong(int privilegio) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE privilegio = "+privilegio;
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<Log> findByIdPrivilegioShort(int privilegio) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE privilegio = "+privilegio;
        try {

            list = session.createQuery(sql).setMaxResults(20).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Log> findByUsuario(Usuario usuario) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE usuario = "+usuario.getIdUsuario();
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Log> findByUsuarioLong(Usuario usuario) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE usuario = "+usuario.getIdUsuario();
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Log> findByUsuarioShort(Usuario usuario) {
        List<Log> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Log WHERE usuario = "+usuario.getIdUsuario();
        try {

            list = session.createQuery(sql).setMaxResults(20).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
