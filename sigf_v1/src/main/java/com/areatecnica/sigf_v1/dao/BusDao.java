/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.Terminal;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface BusDao {
    
    public List<Bus> findAll();
    public List<Bus> findAllClean();
    public List<Bus> findByTerminal(Terminal terminal);    
    public List<Bus> findByIdTerminal(int idTerminal);    
    public List<Bus> findByServicio(Servicio servicio);    
    public List<Bus> findByEmpresa(Empresa empresa);    
    public List<Bus> findByFlota(Flota flota);    
    public Bus findByNumero(int numeroBus);
    public Bus findById(int id);
    
}
