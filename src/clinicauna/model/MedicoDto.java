/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import clinicauna.util.LocalDateTimeAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Carlos Olivares
 */

@XmlRootElement(name = "MedicoDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class MedicoDto {
    @XmlTransient
    private Long ID;
    @XmlTransient
    private String Codigo;
    @XmlTransient
    private String Folio;
    @XmlTransient
    private String carne;
    @XmlTransient
    private String Estado;
    @XmlTransient
    private String InicioJornada;
    @XmlTransient
    private String FinJornada;
    @XmlTransient
    private Integer espacios;
    @XmlTransient
    private UsuarioDto us;
    @XmlTransient
    private Long medVersion;

    public MedicoDto() {
    }

    public MedicoDto(Long ID, String Codigo, String Folio, String carne, String Estado, String InicioJornada, String FinJornada, Integer espacios, UsuarioDto us, Long medVersion) {
        this.ID = ID;
        this.Codigo = Codigo;
        this.Folio = Folio;
        this.carne = carne;
        this.Estado = Estado;
        this.InicioJornada = InicioJornada;
        this.FinJornada = FinJornada;
        this.espacios = espacios;
        this.us = us;
        this.medVersion = medVersion;
    }

    public Long getMedVersion() {
        return medVersion;
    }

    public void setMedVersion(Long medVersion) {
        this.medVersion = medVersion;
    }
    
    public UsuarioDto getUs() {
        return us;
    }

    public void setUs(UsuarioDto us) {
        this.us = us;
    }

    public Integer getEspacios() {
        return espacios;
    }

    public void setEspacios(Integer espacios) {
        this.espacios = espacios;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String Folio) {
        this.Folio = Folio;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
    public String getInicioJornada() {
        return InicioJornada;
    }
    public void setInicioJornada(String InicioJornada) {
        this.InicioJornada = InicioJornada;
    }
    public String getFinJornada() {
        return FinJornada;
    }
    public void setFinJornada(String FinJornada) {
        this.FinJornada = FinJornada;
    }
    
    
}
