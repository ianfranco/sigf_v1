/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ian Franco
 */
@Named(value = "reportBean")
@ViewScoped
public class ReportBean implements Serializable{

    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
    }

    @PostConstruct
    public void init() {

    }

    public void printPDF() {
        String jasperPath = "/resources/reports/INF-Conductores.jasper";
        String fileName = "Listado de conductores.pdf";
        try {
            PDF(null, jasperPath, null, fileName);
        } catch (JRException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PDF(Map<String, Object> params, String jasperPath, List<?> dataSource, String fileName) throws JRException, IOException {
        String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
        
        File file = new File(relativeWebPath);
        //JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource, false);
        Conexion conn = new Conexion();
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, conn.getConnection());
        
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-Disposition", "attachment;filename="+fileName);
        
        ServletOutputStream stream = response.getOutputStream();
        
        JasperExportManager.exportReportToPdfStream(print, stream);
        FacesContext.getCurrentInstance().responseComplete();
        
        conn.closeConexion();
    }

}
