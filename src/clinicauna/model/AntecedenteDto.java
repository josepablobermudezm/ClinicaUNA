/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Olivares
 */
@XmlRootElement(name = "ControlDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AntecedenteDto {
    private Long antId;
    private String antEnfermedad;
    private String antParentezco;
    private Long antVersion;
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