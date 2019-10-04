/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import clinicauna.util.LocalDateAdapter;
import java.time.LocalDate;
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
    Long ageID;
    @XmlTransient
    MedicoDto medicoDto;
    @XmlTransient
    Long agenVersion;
    @XmlTransient
    LocalDate fecha;

    public AgendaDto(){
    }

    public AgendaDto(Long ageID, MedicoDto medicoDto, Long agenVersion, LocalDate fecha) {
        this.ageID = ageID;
        this.medicoDto = medicoDto;
        this.agenVersion = agenVersion;
        this.fecha = fecha;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFecha() {
        return fecha;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getID() {
        return ageID;
    }

    public void setID(Long ID) {
        this.ageID = ID;
    }

    public MedicoDto getMedicoDto() {
        return medicoDto;
    }

    public void setMedicoDto(MedicoDto medicoDto) {
        this.medicoDto = medicoDto;
    }

    public Long getAgenVersion() {
        return agenVersion;
    }

    public void setAgenVersion(Long agenVersion) {
        this.agenVersion = agenVersion;
    }
    
    
}