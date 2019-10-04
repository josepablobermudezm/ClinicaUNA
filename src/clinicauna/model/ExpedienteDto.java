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
@XmlRootElement(name = "ExpedienteDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ExpedienteDto {
    @XmlTransient
    Long expID;
    @XmlTransient
    Long expVersion;
    @XmlTransient
    String antecedentesPatologicos;
    @XmlTransient
    String hospitalizaciones;
    @XmlTransient
    String operaciones;
    @XmlTransient
    String alergias;
    @XmlTransient
    String tratamientos;
    @XmlTransient
    PacienteDto paciente;

    public ExpedienteDto(){
        
    }
    
    public ExpedienteDto(Long expID, Long expVersion, String antecedentesPatologicos, String hospitalizaciones, String operaciones, String alergias, String tratamientos,/* String antecedentesFamiliares,*/ PacienteDto paciente) {
        this.expID = expID;
        this.expVersion = expVersion;
        this.antecedentesPatologicos = antecedentesPatologicos;
        this.hospitalizaciones = hospitalizaciones;
        this.operaciones = operaciones;
        this.alergias = alergias;
        this.tratamientos = tratamientos;
        //this.antecedentesFamiliares = antecedentesFamiliares;
        this.paciente = paciente;
    }

    public Long getExpID() {
        return expID;
    }

    public void setExpID(Long expID) {
        this.expID = expID;
    }

    public Long getExpVersion() {
        return expVersion;
    }

    public void setExpVersion(Long expVersion) {
        this.expVersion = expVersion;
    }

    public String getAntecedentesPatologicos() {
        return antecedentesPatologicos;
    }

    public void setAntecedentesPatologicos(String antecedentesPatologicos) {
        this.antecedentesPatologicos = antecedentesPatologicos;
    }

    public String getHospitalizaciones() {
        return hospitalizaciones;
    }

    public void setHospitalizaciones(String hospitalizaciones) {
        this.hospitalizaciones = hospitalizaciones;
    }

    public String getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(String operaciones) {
        this.operaciones = operaciones;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(String tratamientos) {
        this.tratamientos = tratamientos;
    }
/*
    public String getAntecedentesFamiliares() {
        return antecedentesFamiliares;
    }

    public void setAntecedentesFamiliares(String antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
    }
*/
    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }
}
