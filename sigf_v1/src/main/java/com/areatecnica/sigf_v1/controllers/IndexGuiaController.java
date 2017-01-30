/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.LogDaoImpl;
import com.areatecnica.sigf_v1.entities.Log;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "indexGuiaController")
@ViewScoped
public class IndexGuiaController implements Serializable {
    
    private List<Log> logItems;
    private LogDaoImpl logDaoImpl;
    
    
    /**
     * Creates a new instance of IndexGuiaController
     */
    public IndexGuiaController() {
    }
    
    @PostConstruct
    public void init(){
        this.logDaoImpl = new LogDaoImpl();
        this.logItems = this.logDaoImpl.findByIdPrivilegioLong(2);
    }

    public List<Log> getLogItems() {
        return logItems;
    }

    public void setLogItems(List<Log> logItems) {
        this.logItems = logItems;
    }
    
}
