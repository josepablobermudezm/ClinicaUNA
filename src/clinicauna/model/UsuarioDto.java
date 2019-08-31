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
@XmlRootElement(name = "UsuarioDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class UsuarioDto {
    @XmlTransient
    private Long ID;
    @XmlTransient
    private String nombre;
    @XmlTransient
    private String pApellido;
    @XmlTransient
    private String estado;
    @XmlTransient
    private String sApellido;
    @XmlTransient
    private String cedula;
    @XmlTransient
    private String correo;
    @XmlTransient
    private String nombreUsuario;
    @XmlTransient
    private String contrasennaTemp;
    @XmlTransient
    private String contrasenna;
    @XmlTransient
    private String tipoUsuario;
    @XmlTransient
    private String idioma;
    
    public UsuarioDto() {
    }

    public UsuarioDto(Long ID, String nombre, String pApellido, String sApellido,String estado, String cedula, String correo, String nombreUsuario, String contrasennaTemp, String contrasenna, String tipoUsuario, String idioma) {
        this.ID = ID;
        this.nombre = nombre;
        this.pApellido = pApellido;
        this.estado = estado;
        this.sApellido = sApellido;
        this.cedula = cedula;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasennaTemp = contrasennaTemp;
        this.contrasenna = contrasenna;
        this.tipoUsuario = tipoUsuario;
        this.idioma = idioma;
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

    public String getContrasennaTemp() {
        return contrasennaTemp;
    }

    public void setContrasennaTemp(String contrasennaTemp) {
        this.contrasennaTemp = contrasennaTemp;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    
}
