/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.Terminal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface GuiaDao {
    
    public List<Guia> findAll();
    
    public List<Guia> findByFecha(Date fecha);
    
    public List<Guia> findByFechaAndTerminal(Date fecha, Terminal terminal);
    
    public Guia findByFolio (int folio);
    
    public Guia findByBusAndFecha(Bus bus, Date fecha);
    
    public List<Guia> findByBusAndEstado(Bus bus, boolean estado);
}
