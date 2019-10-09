/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import clinicauna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "AgendaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AgendaDto {
    @XmlTransient
    private Long ageId;
    @XmlTransient
    private LocalDate ageFecha;
    @XmlTransient
    private Long ageVersion;
    @XmlTransient
    private MedicoDto ageMedico;
    @XmlTransient
    private List<EspacioDto> espacioList;
    
    public AgendaDto(){
    }

    public AgendaDto(Long ageId, LocalDate ageFecha, Long ageVersion, MedicoDto ageMedico) {
        this.ageId = ageId;
        this.ageFecha = ageFecha;
        this.ageVersion = ageVersion;
        this.ageMedico = ageMedico;
    }
    
    public Long getAgeId() {
        return ageId;
    }

    public void setAgeId(Long ageId) {
        this.ageId = ageId;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getAgeFecha() {
        return ageFecha;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setAgeFecha(LocalDate ageFecha) {
        this.ageFecha = ageFecha;
    }

    public Long getAgeVersion() {
        return ageVersion;
    }

    public void setAgeVersion(Long ageVersion) {
        this.ageVersion = ageVersion;
    }

    public MedicoDto getAgeMedico() {
        return ageMedico;
    }

    public void setAgeMedico(MedicoDto ageMedico) {
        this.ageMedico = ageMedico;
    }

    public List<EspacioDto> getEspacioList() {
        return espacioList;
    }

    public void setEspacioList(List<EspacioDto> espacioList) {
        this.espacioList = espacioList;
    }

    @Override
    public String toString() {
        return "AgendaDto{" + "ageId=" + ageId + ", ageFecha=" + ageFecha + ", ageVersion=" + ageVersion + ", ageMedico=" + ageMedico + ", espacioList=" + espacioList + '}';
    }
    
}
