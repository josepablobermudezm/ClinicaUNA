/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "ControlDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ControlDto {
    
    @XmlTransient
    Long ctrPacID;
    @XmlTransient
    Long ctrPacVersion;
    @XmlTransient
    LocalDate fecha;
    @XmlTransient
    String hora;
    @XmlTransient
    float presion;
    @XmlTransient
    float frecuenciaCardiaca;
    @XmlTransient
    float peso;
    @XmlTransient
    float talla;
    @XmlTransient
    float temperatura;
    @XmlTransient
    float imc;
    @XmlTransient
    String anotacionEnfermeria;
    @XmlTransient
    String razonConsulta;
    @XmlTransient
    String PlanAtencion;
    @XmlTransient
    String Observaciones;
    @XmlTransient
    String examenFisico;
    @XmlTransient
    String tratamiento;
    @XmlTransient
    PacienteDto paciente;
    @XmlTransient
    ExamenDto examen;

    public ControlDto(Long ctrPacID, Long ctrPacVersion, LocalDate fecha, String hora, float presion, float frecuenciaCardiaca, float peso, float talla, float temperatura, float imc, String anotacionEnfermeria, String razonConsulta, String PlanAtencion, String Observaciones, String examenFisico, String tratamiento, PacienteDto paciente, ExamenDto examen) {
        this.ctrPacID = ctrPacID;
        this.ctrPacVersion = ctrPacVersion;
        this.fecha = fecha;
        this.hora = hora;
        this.presion = presion;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.peso = peso;
        this.talla = talla;
        this.temperatura = temperatura;
        this.imc = imc;
        this.anotacionEnfermeria = anotacionEnfermeria;
        this.razonConsulta = razonConsulta;
        this.PlanAtencion = PlanAtencion;
        this.Observaciones = Observaciones;
        this.examenFisico = examenFisico;
        this.tratamiento = tratamiento;
        this.paciente = paciente;
        this.examen = examen;
    }
    public Long getCtrPacID() {
        return ctrPacID;
    }

    public void setCtrPacID(Long ctrPacID) {
        this.ctrPacID = ctrPacID;
    }

    public Long getCtrPacVersion() {
        return ctrPacVersion;
    }

    public void setCtrPacVersion(Long ctrPacVersion) {
        this.ctrPacVersion = ctrPacVersion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getPresion() {
        return presion;
    }

    public void setPresion(float presion) {
        this.presion = presion;
    }

    public float getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(float frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getTalla() {
        return talla;
    }

    public void setTalla(float talla) {
        this.talla = talla;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public String getAnotacionEnfermeria() {
        return anotacionEnfermeria;
    }

    public void setAnotacionEnfermeria(String anotacionEnfermeria) {
        this.anotacionEnfermeria = anotacionEnfermeria;
    }

    public String getRazonConsulta() {
        return razonConsulta;
    }

    public void setRazonConsulta(String razonConsulta) {
        this.razonConsulta = razonConsulta;
    }

    public String getPlanAtencion() {
        return PlanAtencion;
    }

    public void setPlanAtencion(String PlanAtencion) {
        this.PlanAtencion = PlanAtencion;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public String getExamenFisico() {
        return examenFisico;
    }

    public void setExamenFisico(String examenFisico) {
        this.examenFisico = examenFisico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }

    public ExamenDto getExamen() {
        return examen;
    }

    public void setExamen(ExamenDto examen) {
        this.examen = examen;
    }
}
