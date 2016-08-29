/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.VentaBoleto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface VentaBoletoDao {

    public List<VentaBoleto> findAll();

    public VentaBoleto findByFecha(Date fechaVenta);

    public VentaBoleto findByTerminal(Terminal terminal);

}
