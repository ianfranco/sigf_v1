/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;

import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import java.util.ArrayList;

/**
 *
 * @author ianfr
 */
public class RegistroGuiaList {

    private Guia guia;
    private ArrayList<EgresoGuia> list;

    public RegistroGuiaList() {
    }

    public RegistroGuiaList(Guia guia) {
    }
    
    public RegistroGuiaList(Guia guia, ArrayList<EgresoGuia> list) {
        this.guia = guia;
        this.list = list;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        this.guia = guia;
    }

    public ArrayList<EgresoGuia> getList() {
        return list;
    }

    public void setList(ArrayList<EgresoGuia> list) {
        this.list = list;
    }
    
    

    
}
