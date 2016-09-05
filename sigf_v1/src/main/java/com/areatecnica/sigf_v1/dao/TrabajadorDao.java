/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.Trabajador;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface TrabajadorDao {
    
    public List<Trabajador> findAll();
    public List<Trabajador> findByTerminal(Terminal terminal);
    public Trabajador findByRut(String rut);
    public Trabajador findByCodigo(int codigo);
    public boolean existeTrabajador(String rut);
    public void deactivated(Trabajador trabajador);
    public int maxId();
}
