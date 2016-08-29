/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Control;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface ControlDao {
    
    public List<Control> findAll();
    
    public Control findById(int id);
    
}
