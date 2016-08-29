/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.InventarioTerminal;
import com.areatecnica.sigf_v1.entities.Terminal;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface InventarioTerminalDao {

    public List<InventarioTerminal> findAll();
    
    public List<InventarioTerminal> findByIdTerminal(int idTerminal);

    public List<InventarioTerminal> findByEstado(boolean estado);

    public List<InventarioTerminal> findByBoleto(Boleto boleto);
    
    public List<InventarioTerminal> findByBoletoANDTerminal(Boleto boleto, Terminal terminal);
    
    public List<InventarioTerminal> findByBoletoAndIdTerminal(Boleto boleto, int idTerminal);
    
    public InventarioTerminal findByIdentificador(String identificador, String serie);
}
