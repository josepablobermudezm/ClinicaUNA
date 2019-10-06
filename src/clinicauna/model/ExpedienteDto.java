/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import java.util.ArrayList;
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
    private Long expID;
    @XmlTransient
    private Long expVersion;
    @XmlTransient
    private String antecedentesPatologicos;
    @XmlTransient
    private String hospitalizaciones;
    @XmlTransient
    private String operaciones;
    @XmlTransient
    private String alergias;
    @XmlTransient
    private String tratamientos;
    @XmlTransient
    private PacienteDto paciente;
    @XmlTransient
    public ArrayList<AntecedenteDto> antecedentes;
    @XmlTransient
    public ArrayList<ExamenDto> examenes;
    @XmlTransient
    public ArrayList<ControlDto> controles;
    
    public ExpedienteDto(){
        
    }

    public ExpedienteDto(Long expID, Long expVersion, String antecedentesPatologicos, String hospitalizaciones, String operaciones, String alergias, String tratamientos, PacienteDto paciente/*, ArrayList<AntecedenteDto> antecedentes, ArrayList<ExamenDto> examenes, ArrayList<ControlDto> controles*/) {
        this.expID = expID;
        this.expVersion = expVersion;
        this.antecedentesPatologicos = antecedentesPatologicos;
        this.hospitalizaciones = hospitalizaciones;
        this.operaciones = operaciones;
        this.alergias = alergias;
        this.tratamientos = tratamientos;
        this.paciente = paciente;
    }
/*
    public ArrayList<AntecedenteDto> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(ArrayList<AntecedenteDto> antecedentes) {
        this.antecedentes = antecedentes;
    }

    public ArrayList<ExamenDto> getExamenes() {
        return examenes;
    }

    public void setExamenes(ArrayList<ExamenDto> examenes) {
        this.examenes = examenes;
    }

    public ArrayList<ControlDto> getControles() {
        return controles;
    }

    public void setControles(ArrayList<ControlDto> controles) {
        this.controles = controles;
    }*/

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

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }
}
