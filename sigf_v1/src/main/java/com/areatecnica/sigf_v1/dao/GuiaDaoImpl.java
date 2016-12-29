/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.text.SimpleDateFormat;
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
public class GuiaDaoImpl implements GuiaDao {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public List<Guia> findAll() {
        List<Guia> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia ";
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
    public List<Guia> findByFecha(Date fecha) {
        List<Guia> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE fecha = " + fecha;
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
    public List<Guia> findByFechaAndTerminal(Date fecha, Terminal terminal) {
        List<Guia> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE fecha='" + format.format(fecha) + "' AND bus.terminal =" + terminal.getIdTerminal();
        try {

            list = session.createQuery(sql).list();
            for (Guia g : list) {
                Hibernate.initialize(g.getTrabajador());
                Hibernate.initialize(g.getBus());
                Hibernate.initialize(g.getEstadoGuia());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Guia findByFolio(int folio) {
        Guia guia = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE folio=" + folio;
        try {

            guia = (Guia) session.createQuery(sql).uniqueResult();

            if (guia != null) {
                Hibernate.initialize(guia.getTrabajador());
                Hibernate.initialize(guia.getBus());
                Hibernate.initialize(guia.getEstadoGuia());
            }
            tx.commit();
            
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally{
              HibernateUtil.close(session);
        }
        return guia;
    }

    @Override
    public Guia findByBusAndFecha(Bus bus, Date fecha) {
        Guia guia = null;
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE fecha='" + format.format(fecha) + "' AND bus=" + bus.getIdBus();
        try {
            guia = (Guia) session.createQuery(sql).uniqueResult();
            if (guia != null) {
                Hibernate.initialize(guia.getTrabajador());
                Hibernate.initialize(guia.getBus());
                Hibernate.initialize(guia.getEstadoGuia());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return guia;
    }

    @Override
    public List<Guia> findByBusAndEstado(Bus bus, boolean estado) {
        List<Guia> list = null;
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE bus=" + bus.getIdBus() + " AND recaudada = " + estado;
        try {
            list = session.createQuery(sql).list();

            for (Guia g : list) {
                Hibernate.initialize(g.getTrabajador());
                Hibernate.initialize(g.getBus());
                Hibernate.initialize(g.getEstadoGuia());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<Guia> findByBusBetweenDates(Bus bus, Date from) {
        List<Guia> list = null;
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE bus=" + bus.getIdBus() + " AND fechaRecaudacion BETWEEN '" + format.format(from) + "' AND LAST_DAY('" + format.format(from) + "') ORDER BY fechaGuia";
        try {
            list = session.createQuery(sql).list();

            for (Guia g : list) {
                Hibernate.initialize(g.getTrabajador());
                Hibernate.initialize(g.getBus());
                Hibernate.initialize(g.getEstadoGuia());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<Guia> findByConductorBetweenDates(Trabajador conductor, Date from) {
        List<Guia> list = null;
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE trabajador=" + conductor.getIdTrabajador() + " AND fechaRecaudacion BETWEEN '" + format.format(from) + "' AND LAST_DAY('" + format.format(from) + "')";
        try {
            list = session.createQuery(sql).list();

            for (Guia g : list) {
                Hibernate.initialize(g.getTrabajador());
                Hibernate.initialize(g.getBus());
                Hibernate.initialize(g.getEstadoGuia());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<Guia> findCodigosUnoBetweenDates(Date from) {
        List<Guia> list = null;
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE trabajador=1 AND fechaRecaudacion BETWEEN '" + format.format(from) + "' AND LAST_DAY('" + format.format(from) + "')";
        try {
            list = session.createQuery(sql).list();

            for (Guia g : list) {
                Hibernate.initialize(g.getTrabajador());
                Hibernate.initialize(g.getBus());
                Hibernate.initialize(g.getEstadoGuia());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Guia> findByFechaAndProceso(Date fecha, ProcesoRecaudacion procesoRecaudacion) {
        List<Guia> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Guia WHERE fechaRecaudacion = '" + format.format(fecha) + "' AND procesoRecaudacion=" + procesoRecaudacion.getIdProcesoRecaudacion() + " ORDER BY bus.numeroBus ASC, fechaGuia ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

}
