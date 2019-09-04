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
 * @author Carlos Olivares
 */
@XmlRootElement(name = "PacienteDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class PacienteDto {
    @XmlTransient
    private Long ID;
    @XmlTransient
    private String nombre;
    @XmlTransient
    private String pApellido;
    @XmlTransient
    private String sApellido;
    @XmlTransient
    private String cedula;
    @XmlTransient
    private String correo;
    @XmlTransient
    private String genero;
    @XmlTransient
    private LocalDate fechaNacimiento;
    @XmlTransient
    private Long pacVersion;

    public PacienteDto() {
    }

    public PacienteDto(Long ID, String nombre, String pApellido, String sApellido, String cedula, String correo, String genero, LocalDate fechaNacimiento, Long pacVersion) {
        this.ID = ID;
        this.nombre = nombre;
        this.pApellido = pApellido;
        this.sApellido = sApellido;
        this.cedula = cedula;
        this.correo = correo;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.pacVersion = pacVersion;
    }

    public Long getPacVersion() {
        return pacVersion;
    }

    public void setPacVersion(Long pacVersion) {
        this.pacVersion = pacVersion;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getpApellido() {
        return pApellido;
    }

    public void setpApellido(String pApellido) {
        this.pApellido = pApellido;
    }

    public String getsApellido() {
        return sApellido;
    }

    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
}
