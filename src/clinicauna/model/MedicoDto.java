/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    private LocalDateTime InicioJornada;
    @XmlTransient
    private LocalDateTime FinJornada;

    public MedicoDto() {
    }

    public MedicoDto(Long ID, String Codigo, String Folio, String carne, String Estado, LocalDateTime InicioJornada, LocalDateTime FinJornada) {
        this.ID = ID;
        this.Codigo = Codigo;
        this.Folio = Folio;
        this.carne = carne;
        this.Estado = Estado;
        this.InicioJornada = InicioJornada;
        this.FinJornada = FinJornada;
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

    public LocalDateTime getInicioJornada() {
        return InicioJornada;
    }

    public void setInicioJornada(LocalDateTime InicioJornada) {
        this.InicioJornada = InicioJornada;
    }

    public LocalDateTime getFinJornada() {
        return FinJornada;
    }

    public void setFinJornada(LocalDateTime FinJornada) {
        this.FinJornada = FinJornada;
    }
    
    
}
