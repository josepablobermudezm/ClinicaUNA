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
@XmlRootElement(name = "EspacioDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class CitaPorEspacioDto {
    @XmlTransient
    private Long ctxespId;
    @XmlTransient
    private Long ctxespTiempoCita;
    @XmlTransient
    private Long ctxespVersion;
    @XmlTransient
    private CitaDto ctxespCita;
    @XmlTransient
    private EspacioDto ctxespEspacio;

    public CitaPorEspacioDto() {
    }

    public CitaPorEspacioDto(Long ctxespId, Long ctxespTiempoCita, Long ctxespVersion, CitaDto ctxespCita, EspacioDto ctxespEspacio) {
        this.ctxespId = ctxespId;
        this.ctxespTiempoCita = ctxespTiempoCita;
        this.ctxespVersion = ctxespVersion;
        this.ctxespCita = ctxespCita;
        this.ctxespEspacio = ctxespEspacio;
    }
    
    
    public Long getCtxespId() {
        return ctxespId;
    }

    public void setCtxespId(Long ctxespId) {
        this.ctxespId = ctxespId;
    }

    public Long getCtxespTiempoCita() {
        return ctxespTiempoCita;
    }

    public void setCtxespTiempoCita(Long ctxespTiempoCita) {
        this.ctxespTiempoCita = ctxespTiempoCita;
    }

    public Long getCtxespVersion() {
        return ctxespVersion;
    }

    public void setCtxespVersion(Long ctxespVersion) {
        this.ctxespVersion = ctxespVersion;
    }

    public CitaDto getCtxespCita() {
        return ctxespCita;
    }

    public void setCtxespCita(CitaDto ctxespCita) {
        this.ctxespCita = ctxespCita;
    }

    public EspacioDto getCtxespEspacio() {
        return ctxespEspacio;
    }

    public void setCtxespEspacio(EspacioDto ctxespEspacio) {
        this.ctxespEspacio = ctxespEspacio;
    }
    
}
