/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import clinicauna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "ExamenDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ExamenDto {
    @XmlTransient
    private Long exmID;
    @XmlTransient
    private String nombreExamen;
    @XmlTransient
    private LocalDate fecha;
    @XmlTransient
    private String anotaciones;
    @XmlTransient
    private Long exmVersion;
    @XmlTransient
    private ExpedienteDto expediente;

    public ExamenDto() {
    }
    
    public ExpedienteDto getExpediente() {
        return expediente;
    }

    public void setExpediente(ExpedienteDto expediente) {
        this.expediente = expediente;
    }

    public Long getExmID() {
        return exmID;
    }

    public void setExmID(Long exmID) {
        this.exmID = exmID;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFecha() {
        return fecha;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(String anotaciones) {
        this.anotaciones = anotaciones;
    }

    public Long getExmVersion() {
        return exmVersion;
    }

    public void setExmVersion(Long exmVersion) {
        this.exmVersion = exmVersion;
    }
}
