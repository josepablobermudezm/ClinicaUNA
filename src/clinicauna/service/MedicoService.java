/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.MedicoDto;
import clinicauna.util.Request;
import clinicauna.util.Respuesta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author JORDI RODRIGUEZ
 */
public class MedicoService {

    public Respuesta getMedico(String Medico, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Medico", Medico);
            parametros.put("clave", clave);
            Request request = new Request("MedicoController/Medico", "/{Medico}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            MedicoDto medico = (MedicoDto) request.readEntity(MedicoDto.class);
            return new Respuesta(true, "", "", "Medico", medico);

        } catch (Exception ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, "Error obteniendo el Medico [" + Medico + "]", ex);
            return new Respuesta(false, "Error obteniendo el medico.", "getMedico " + ex.getMessage());
        }
    }

    public Respuesta getMedico(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("MedicoController/Medico", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            MedicoDto medico = (MedicoDto) request.readEntity(MedicoDto.class);
            return new Respuesta(true, "", "", "Medico", medico);
        } catch (Exception ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, "Error obteniendo el Medico [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Medico.", "getMedico " + ex.getMessage());
        }
    }
    /*
    public Respuesta getMedicos(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("MedicoController/Medicos", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<MedicoDto> Medicos = (List<MedicoDto>) request.readEntity(new GenericType<List<MedicoDto>>() {
            });
            return new Respuesta(true, "", "", "Medicos", Medicos);
        } catch (Exception ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, "Error obteniendo Medicos.", ex);
            return new Respuesta(false, "Error obteniendo Medicos.", "getMedicos " + ex.getMessage());
        }
    }*/

    public Respuesta getMedicos() {
        try {
            Request request = new Request("MedicoController/medicos");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<MedicoDto> Medicos = (List<MedicoDto>) request.readEntity(new GenericType<List<MedicoDto>>() {
            });

            return new Respuesta(true, "", "", "Medicos", Medicos);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Medicos", "getMedicos " + ex.getMessage());
        }
    }

    public Respuesta guardarMedico(MedicoDto Medico) {
        try {

            Request request = new Request("MedicoController/guardar");
            request.post(Medico);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Medico = (MedicoDto) request.readEntity(MedicoDto.class);

            return new Respuesta(true, "Medico Actualizado Correctamente", "", "Medico", Medico);
        } catch (Exception ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, "Error guardando el Medico.", ex);
            return new Respuesta(false, "Error guardando el Medico.", "guardarMedico " + ex.getMessage());
        }
    }

    public Respuesta eliminarMedico(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("MedicoController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, "Error eliminando el Medico.", ex);
            return new Respuesta(false, "Error eliminando el Medico.", "eliminarMedico " + ex.getMessage());
        }
    }

    
}
