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
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "EspacioDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class EspacioDto {
    @XmlTransient
    private Long espID;
    @XmlTransient
    private Long espVersion;
    @XmlTransient
    private AgendaDto agenda;
    @XmlTransient
    private String espHoraFin;
    @XmlTransient
    private String espHoraInicio;

    public EspacioDto(Long espID, Long espVersion, AgendaDto agenda, String espHoraFin, String espHoraInicio) {
        this.espID = espID;
        this.espVersion = espVersion;
        this.agenda = agenda;
        this.espHoraFin = espHoraFin;
        this.espHoraInicio = espHoraInicio;
    }

    public String getEspHoraFin() {
        return espHoraFin;
    }

    public void setEspHoraFin(String espHoraFin) {
        this.espHoraFin = espHoraFin;
    }

    public String getEspHoraInicio() {
        return espHoraInicio;
    }

    public void setEspHoraInicio(String espHoraInicio) {
        this.espHoraInicio = espHoraInicio;
    }
    
    public Long getEspID() {
        return espID;
    }

    public void setEspID(Long espID) {
        this.espID = espID;
    }

    public Long getEspVersion() {
        return espVersion;
    }

    public void setEspVersion(Long espVersion) {
        this.espVersion = espVersion;
    }

    public AgendaDto getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaDto agenda) {
        this.agenda = agenda;
    }
    
    
}
