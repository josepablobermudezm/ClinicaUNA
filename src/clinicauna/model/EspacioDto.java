/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import java.util.List;
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
    private Long espId;
    @XmlTransient
    private String espHoraFin;
    @XmlTransient
    private String espHoraInicio;
    @XmlTransient
    private Long espVersion;
    @XmlTransient
    private List<CitaPorEspacioDto> citasPorEspacioList;
    @XmlTransient
    private AgendaDto espAgenda;

    public EspacioDto(){
    }

    public EspacioDto(Long espId, String espHoraFin, String espHoraInicio, Long espVersion, AgendaDto espAgenda) {
        this.espId = espId;
        this.espHoraFin = espHoraFin;
        this.espHoraInicio = espHoraInicio;
        this.espVersion = espVersion;
        this.espAgenda = espAgenda;
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


    public Long getEspVersion() {
        return espVersion;
    }

    public void setEspVersion(Long espVersion) {
        this.espVersion = espVersion;
    }

    public Long getEspId() {
        return espId;
    }

    public void setEspId(Long espId) {
        this.espId = espId;
    }

    public List<CitaPorEspacioDto> getCitasPorEspacioList() {
        return citasPorEspacioList;
    }

    public void setCitasPorEspacioList(List<CitaPorEspacioDto> citasPorEspacioList) {
        this.citasPorEspacioList = citasPorEspacioList;
    }

    public AgendaDto getEspAgenda() {
        return espAgenda;
    }

    public void setEspAgenda(AgendaDto espAgenda) {
        this.espAgenda = espAgenda;
    }
    
}
