/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.model;

import clinicauna.util.LocalDateAdapter;
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
    private Long cntId;
    @XmlTransient
    private LocalDate cntFecha;
    @XmlTransient
    private String cntHora;
    @XmlTransient
    private Double cntPresion;
    @XmlTransient
    private Double cntFrecuenciaCardiaca;
    @XmlTransient
    private Double cntPeso;
    @XmlTransient
    private Double cntTalla;
    @XmlTransient
    private Double cntTemperatura;
    @XmlTransient
    private Double cntImc;
    @XmlTransient
    private String cntAnotacionEnfermeria;
    @XmlTransient
    private String cntRazonConsulta;
    @XmlTransient
    private String cntPlanAtencion;
    @XmlTransient
    private String cntObservaciones;
    @XmlTransient
    private String cntExamenFisico;
    @XmlTransient
    private String cntTratamiento;
    @XmlTransient
    private Long cntVersion;
    @XmlTransient
    private ExpedienteDto cntExpediente;

    public ControlDto(Long cntId, LocalDate cntFecha, String cntHora, Double cntPresion, Double cntFrecuenciaCardiaca, Double cntPeso, Double cntTalla, Double cntTemperatura, Double cntImc, String cntAnotacionEnfermeria, String cntRazonConsulta, String cntPlanAtencion, String cntObservaciones, String cntExamenFisico, String cntTratamiento, Long cntVersion, ExpedienteDto cntExpediente) {
        this.cntId = cntId;
        this.cntFecha = cntFecha;
        this.cntHora = cntHora;
        this.cntPresion = cntPresion;
        this.cntFrecuenciaCardiaca = cntFrecuenciaCardiaca;
        this.cntPeso = cntPeso;
        this.cntTalla = cntTalla;
        this.cntTemperatura = cntTemperatura;
        this.cntImc = cntImc;
        this.cntAnotacionEnfermeria = cntAnotacionEnfermeria;
        this.cntRazonConsulta = cntRazonConsulta;
        this.cntPlanAtencion = cntPlanAtencion;
        this.cntObservaciones = cntObservaciones;
        this.cntExamenFisico = cntExamenFisico;
        this.cntTratamiento = cntTratamiento;
        this.cntVersion = cntVersion;
        this.cntExpediente = cntExpediente;
    }
    

    public Long getCntId() {
        return cntId;
    }

    public void setCntId(Long cntId) {
        this.cntId = cntId;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getCntFecha() {
        return cntFecha;
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setCntFecha(LocalDate cntFecha) {
        this.cntFecha = cntFecha;
    }

    public String getCntHora() {
        return cntHora;
    }

    public void setCntHora(String cntHora) {
        this.cntHora = cntHora;
    }

    public Double getCntPresion() {
        return cntPresion;
    }

    public void setCntPresion(Double cntPresion) {
        this.cntPresion = cntPresion;
    }

    public Double getCntFrecuenciaCardiaca() {
        return cntFrecuenciaCardiaca;
    }

    public void setCntFrecuenciaCardiaca(Double cntFrecuenciaCardiaca) {
        this.cntFrecuenciaCardiaca = cntFrecuenciaCardiaca;
    }

    public Double getCntPeso() {
        return cntPeso;
    }

    public void setCntPeso(Double cntPeso) {
        this.cntPeso = cntPeso;
    }

    public Double getCntTalla() {
        return cntTalla;
    }

    public void setCntTalla(Double cntTalla) {
        this.cntTalla = cntTalla;
    }

    public Double getCntTemperatura() {
        return cntTemperatura;
    }

    public void setCntTemperatura(Double cntTemperatura) {
        this.cntTemperatura = cntTemperatura;
    }

    public Double getCntImc() {
        return cntImc;
    }

    public void setCntImc(Double cntImc) {
        this.cntImc = cntImc;
    }

    public String getCntAnotacionEnfermeria() {
        return cntAnotacionEnfermeria;
    }

    public void setCntAnotacionEnfermeria(String cntAnotacionEnfermeria) {
        this.cntAnotacionEnfermeria = cntAnotacionEnfermeria;
    }

    public String getCntRazonConsulta() {
        return cntRazonConsulta;
    }

    public void setCntRazonConsulta(String cntRazonConsulta) {
        this.cntRazonConsulta = cntRazonConsulta;
    }

    public String getCntPlanAtencion() {
        return cntPlanAtencion;
    }

    public void setCntPlanAtencion(String cntPlanAtencion) {
        this.cntPlanAtencion = cntPlanAtencion;
    }

    public String getCntObservaciones() {
        return cntObservaciones;
    }

    public void setCntObservaciones(String cntObservaciones) {
        this.cntObservaciones = cntObservaciones;
    }

    public String getCntExamenFisico() {
        return cntExamenFisico;
    }

    public void setCntExamenFisico(String cntExamenFisico) {
        this.cntExamenFisico = cntExamenFisico;
    }

    public String getCntTratamiento() {
        return cntTratamiento;
    }

    public void setCntTratamiento(String cntTratamiento) {
        this.cntTratamiento = cntTratamiento;
    }

    public Long getCntVersion() {
        return cntVersion;
    }

    public void setCntVersion(Long cntVersion) {
        this.cntVersion = cntVersion;
    }

    public ExpedienteDto getCntExpediente() {
        return cntExpediente;
    }

    public void setCntExpediente(ExpedienteDto cntExpediente) {
        this.cntExpediente = cntExpediente;
    }
   

}
