/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.report;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos Olivares
 */
@XmlRootElement(name = "ReportManager")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ReportManager {
    //private static ReportManager INSTANCE = null;
    //private final Map parametros;
    @XmlTransient
    private JasperViewer jv;
    @XmlTransient
    private JasperPrint print;
    @XmlTransient
    private JRDataSource dataSource;

    public ReportManager() {
    }

    public JasperViewer getJv() {
        return jv;
    }

    public void setJv(JasperViewer jv) {
        this.jv = jv;
    }

    public JasperPrint getPrint() {
        return print;
    }

    public void setPrint(JasperPrint print) {
        this.print = print;
    }

    public JRDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public String toString() {
        return "ReportManager{" + "jv=" + jv + ", print=" + print + ", dataSource=" + dataSource + '}';
    }
    
}
