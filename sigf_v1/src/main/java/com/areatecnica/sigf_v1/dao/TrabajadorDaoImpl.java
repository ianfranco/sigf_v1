/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.Trabajador;
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
public class TrabajadorDaoImpl implements TrabajadorDao {

    @Override
    public List<Trabajador> findAll() {
        List<Trabajador> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador ORDER BY codigoTrabajador";
        try {

            list = session.createQuery(sql).list();

            for (Trabajador t : list) {
                Hibernate.initialize(t.getAsignacionFamiliar());
                Hibernate.initialize(t.getInstitucionPrevision());
                Hibernate.initialize(t.getInstitucionApv());
                Hibernate.initialize(t.getInstitucionSalud());
                Hibernate.initialize(t.getTipoCotizacionTrabajador());
                Hibernate.initialize(t.getComuna());
                Hibernate.initialize(t.getMonedaPactadaInstitucionSalud());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    @Override
    public List<Trabajador> findAllClean() {
        List<Trabajador> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador ORDER BY codigoTrabajador";
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
    public List<Trabajador> findByTerminal(Terminal terminal) {
        List<Trabajador> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador WHERE  terminal=" + terminal.getIdTerminal() + " ORDER BY apellidoPaternoTrabajador ASC";
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
    public void deactivated(Trabajador trabajador) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        //trabajador.set
        try {

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public int maxId() {
        int maxId = 0;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            maxId = (Integer) session.createQuery("SELECT MAX(codigoTrabajador) FROM Trabajador").uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } catch (NullPointerException f) {
            return 1;
        }
        return maxId + 1;
    }

    @Override
    public Trabajador findByRut(String rut) {
        Trabajador trabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador WHERE rutTrabajador ='" + rut+"'";
        try {
            trabajador = (Trabajador) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } catch (NullPointerException f) {

        }
        return trabajador;
    }

    @Override
    public boolean existeTrabajador(String rut) {
        Trabajador trabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador WHERE rutTrabajador ='" + rut + "'";
        try {
            trabajador = (Trabajador) session.createQuery(sql).uniqueResult();
            tx.commit();

            if (trabajador == null) {
                return false;
            }

        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Trabajador findByCodigo(int codigo) {
        Trabajador trabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador WHERE codigoTrabajador=" + codigo;
        try {
            trabajador = (Trabajador) session.createQuery(sql).uniqueResult();

            Hibernate.initialize(trabajador.getAsignacionFamiliar());
            Hibernate.initialize(trabajador.getInstitucionPrevision());
            Hibernate.initialize(trabajador.getInstitucionApv());
            Hibernate.initialize(trabajador.getInstitucionSalud());
            Hibernate.initialize(trabajador.getTipoCotizacionTrabajador());
            Hibernate.initialize(trabajador.getComuna());
            Hibernate.initialize(trabajador.getMonedaPactadaInstitucionSalud());

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } catch (NullPointerException f) {

        }
        return trabajador;
    }

    @Override
    public Trabajador findById(int id) {
        Trabajador trabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Trabajador WHERE idTrabajador=" + id;
        try {
            trabajador = (Trabajador) session.createQuery(sql).uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } catch (NullPointerException f) {

        }
        return trabajador;
    }

}
