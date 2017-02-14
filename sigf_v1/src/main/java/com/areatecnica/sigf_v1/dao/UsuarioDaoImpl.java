/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Usuario;
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

public class UsuarioDaoImpl implements UsuarioDao {
    
    @Override
    public Usuario findByUsuario(Usuario usuario) {
        Usuario model = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Usuario WHERE rut = '" + usuario.getRut() + "'";
        try {
            model = (Usuario) session.createQuery(sql).uniqueResult();
            Hibernate.initialize(model.getTerminal());
            Hibernate.initialize(model.getRol());
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
            
        }
        return model;
    }

    @Override
    public Usuario login(Usuario usuario) {
        Usuario model = this.findByUsuario(usuario);
        if (model != null) {
            if (!usuario.getPass().equals(model.getPass())) {
                model = null;
            }
        }
        return model;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Usuario";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Usuario findById(int id) {
        Usuario usuario = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Usuario WHERE idUsuario = "+id;
        try {
            usuario = (Usuario) session.createQuery(sql).uniqueResult();
            Hibernate.initialize(usuario.getTerminal());
            Hibernate.initialize(usuario.getRol());
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
            
        }
        return usuario;
    }

}
