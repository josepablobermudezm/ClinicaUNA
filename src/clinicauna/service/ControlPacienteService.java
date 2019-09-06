/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.ControlPacienteDto;
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
 * @author Jose Pablo Bermudez
 */
public class ControlPacienteService {
    
    public Respuesta getControlPaciente(String ControlPaciente, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ControlPaciente", ControlPaciente);
            parametros.put("clave", clave);
            Request request = new Request("ControlPacienteController/ControlPaciente", "/{ControlPaciente}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ControlPacienteDto controlPaciente = (ControlPacienteDto) request.readEntity(ControlPacienteDto.class);
            return new Respuesta(true, "", "", "ControlPaciente", controlPaciente);

        } catch (Exception ex) {
            Logger.getLogger(ControlPacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el ControlPaciente [" + ControlPaciente + "]", ex);
            return new Respuesta(false, "Error obteniendo el ControlPaciente.", "getControlPaciente " + ex.getMessage());
        }
    }

    public Respuesta getControlPaciente(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ControlPacienteController/ControlPaciente", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ControlPacienteDto ControlPaciente = (ControlPacienteDto) request.readEntity(ControlPacienteDto.class);
            return new Respuesta(true, "", "", "ControlPaciente", ControlPaciente);
        } catch (Exception ex) {
            Logger.getLogger(ControlPacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el ControlPaciente [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el ControlPaciente.", "getControlPaciente " + ex.getMessage());
        }
    }
    /*
    public Respuesta getControlPacientes(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("ControlPacienteController/ControlPacientes", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<ControlPacienteDto> ControlPacientes = (List<ControlPacienteDto>) request.readEntity(new GenericType<List<ControlPacienteDto>>() {
            });
            return new Respuesta(true, "", "", "ControlPacientes", ControlPacientes);
        } catch (Exception ex) {
            Logger.getLogger(ControlPacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo ControlPacientes.", ex);
            return new Respuesta(false, "Error obteniendo ControlPacientes.", "getControlPacientes " + ex.getMessage());
        }
    }*/

    public Respuesta getControlPacientes() {
        try {
            Request request = new Request("ControlPacienteController/ControlPacientes");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<ControlPacienteDto> ControlPacientes = (List<ControlPacienteDto>) request.readEntity(new GenericType<List<ControlPacienteDto>>() {
            });

            return new Respuesta(true, "", "", "ControlPacientes", ControlPacientes);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "ControlPacientes", "getControlPacientes " + ex.getMessage());
        }
    }

    public Respuesta guardarControlPaciente(ControlPacienteDto ControlPaciente) {
        try {

            Request request = new Request("ControlPacienteController/guardar");
            request.post(ControlPaciente);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ControlPaciente = (ControlPacienteDto) request.readEntity(ControlPacienteDto.class);

            return new Respuesta(true, "", "", "ControlPaciente", ControlPaciente);
        } catch (Exception ex) {
            Logger.getLogger(ControlPacienteService.class.getName()).log(Level.SEVERE, "Error guardando el ControlPaciente.", ex);
            return new Respuesta(false, "Error guardando el ControlPaciente.", "guardarControlPaciente " + ex.getMessage());
        }
    }

    public Respuesta eliminarControlPaciente(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ControlPacienteController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ControlPacienteService.class.getName()).log(Level.SEVERE, "Error eliminando el ControlPaciente.", ex);
            return new Respuesta(false, "Error eliminando el ControlPaciente.", "eliminarControlPaciente " + ex.getMessage());
        }
    }

    
}
