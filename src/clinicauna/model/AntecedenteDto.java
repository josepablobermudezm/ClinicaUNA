/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Olivares
 */
@XmlRootElement(name = "AntecedenteDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AntecedenteDto {
    @XmlTransient
    private Long antId;
    @XmlTransient
    private String antEnfermedad;
    @XmlTransient
    private String antParentezco;
    @XmlTransient
    private Long antVersion;
    @XmlTransient
    private ExpedienteDto antExpediente;

    public AntecedenteDto() {
    }

    public AntecedenteDto(Long antId, String antEnfermedad, String antParentezco, Long antVersion, ExpedienteDto antExpediente) {
        this.antId = antId;
        this.antEnfermedad = antEnfermedad;
        this.antParentezco = antParentezco;
        this.antVersion = antVersion;
        this.antExpediente = antExpediente;
    }

    public Long getAntId() {
        return antId;
    }

    public void setAntId(Long antId) {
        this.antId = antId;
    }

    public String getAntEnfermedad() {
        return antEnfermedad;
    }

    public void setAntEnfermedad(String antEnfermedad) {
        this.antEnfermedad = antEnfermedad;
    }

    public String getAntParentezco() {
        return antParentezco;
    }

    public void setAntParentezco(String antParentezco) {
        this.antParentezco = antParentezco;
    }

    public Long getAntVersion() {
        return antVersion;
    }

    public void setAntVersion(Long antVersion) {
        this.antVersion = antVersion;
    }

    public ExpedienteDto getAntExpediente() {
        return antExpediente;
    }

    public void setAntExpediente(ExpedienteDto antExpediente) {
        this.antExpediente = antExpediente;
    }
    
}