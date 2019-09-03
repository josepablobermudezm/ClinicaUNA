/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.PacienteDto;
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
public class PacienteService {
    
     public Respuesta getPaciente(String usuario, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario", usuario);
            parametros.put("clave", clave);
            Request request = new Request("PacienteController/usuario", "/{usuario}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            PacienteDto Paciente = (PacienteDto) request.readEntity(PacienteDto.class);
            return new Respuesta(true, "", "", "Paciente", Paciente);

        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el Paciente.", "getPaciente " + ex.getMessage());
        }
    }

    public Respuesta getPaciente(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("PacienteController/Paciente", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            PacienteDto Paciente = (PacienteDto) request.readEntity(PacienteDto.class);
            return new Respuesta(true, "", "", "Paciente", Paciente);
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el Usuario [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Paciente.", "getPaciente " + ex.getMessage());
        }
    }

    public Respuesta getPacientes(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("PacienteController/Pacientes", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
             List<PacienteDto> Pacientes = (List<PacienteDto>) request.readEntity(new GenericType<List<PacienteDto>>() {
            });
            return new Respuesta(true, "", "", "Pacientes", Pacientes);
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo Usuarios.", ex);
            return new Respuesta(false, "Error obteniendo Pacientes.", "getPacientes " + ex.getMessage());
        }
    }

    public Respuesta getPacientes() {
        try {
            Request request = new Request("PacienteController/pacientes");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<PacienteDto> Pacientes = (List<PacienteDto>) request.readEntity(new GenericType<List<PacienteDto>>() {
            });

            return new Respuesta(true, "", "", "Pacientes", Pacientes);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Pacientes","getPacientes " + ex.getMessage());
        }
    }

    public Respuesta guardarPaciente(PacienteDto Paciente) {
        try {
            
            Request request = new Request("PacienteController/guardar");
            request.post(Paciente);
            
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            
            Paciente = (PacienteDto) request.readEntity(PacienteDto.class);
            
            return new Respuesta(true, "", "", "Paciente", Paciente);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el Paciente.", ex);
            return new Respuesta(false, "Error guardando el Paciente.", "guardarPaciente " + ex.getMessage());
        }
    }

    public Respuesta eliminarPaciente(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("PacienteController/eliminar","/{id}",parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Error eliminando el Paciente.", ex);
            return new Respuesta(false, "Error eliminando el Paciente.", "eliminarPaciente " + ex.getMessage());
        }
    }
}
