/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import clinicauna.util.LocalDateAdapter;
import clinicauna.util.LocalDateTimeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private LocalDate InicioJornada;
    @XmlTransient
    private LocalDate FinJornada;
    @XmlTransient
    private Integer espacios;

    public MedicoDto() {
    }

    public MedicoDto(Long ID, String Codigo, String Folio, String carne, String Estado, LocalDate InicioJornada, LocalDate FinJornada, Integer espacios) {
        this.ID = ID;
        this.Codigo = Codigo;
        this.Folio = Folio;
        this.carne = carne;
        this.Estado = Estado;
        this.InicioJornada = InicioJornada;
        this.FinJornada = FinJornada;
        this.espacios = espacios;
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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getInicioJornada() {
        return InicioJornada;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setInicioJornada(LocalDate InicioJornada) {
        this.InicioJornada = InicioJornada;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFinJornada() {
        return FinJornada;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setFinJornada(LocalDate FinJornada) {
        this.FinJornada = FinJornada;
    }
    
    
}
