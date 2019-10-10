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
@XmlRootElement(name = "CitaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class CitaDto {
    @XmlTransient
    private Long CtID;
    @XmlTransient
    private Long CtVersion;
    @XmlTransient
    private PacienteDto paciente;
//    EspacioHoraDto espacioHora;
    @XmlTransient
    private String motivo;
    @XmlTransient
    private String estado;
    @XmlTransient
    private String telefono;
    @XmlTransient
    private String correo;

    public CitaDto(){
    }

    public CitaDto(Long CtID, Long CtVersion, PacienteDto paciente, String motivo, String estado, String telefono, String correo) {
        this.CtID = CtID;
        this.CtVersion = CtVersion;
        this.paciente = paciente;
        this.motivo = motivo;
        this.estado = estado;
        this.telefono = telefono;
        this.correo = correo;
    }

    

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public Long getID() {
        return CtID;
    }

    public void setID(Long CtID) {
        this.CtID = CtID;
    }

    public Long getCtVersion() {
        return CtVersion;
    }

    public void setCtVersion(Long CtVersion) {
        this.CtVersion = CtVersion;
    }

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CitaDto{" + "CtID=" + CtID + ", CtVersion=" + CtVersion + ", paciente=" + paciente + ", motivo=" + motivo + ", estado=" + estado + ", telefono=" + telefono + ", correo=" + correo + '}';
    }
    
}
