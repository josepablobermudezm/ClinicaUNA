/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.util.AppContext;
import clinicauna.util.Request;
import clinicauna.util.Respuesta;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private UsuarioDto usuario;

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
            Request request = new Request("PacienteController/pacientes", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<PacienteDto> Pacientes = (List<PacienteDto>) request.readEntity(new GenericType<List<PacienteDto>>() {
            });
            return new Respuesta(true, "", "", "Pacientes", Pacientes);
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Error obteniendo Pacientes.", ex);
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
            return new Respuesta(false, "", "", "Pacientes", "getPacientes " + ex.getMessage());
        }
    }

    public Respuesta guardarPaciente(PacienteDto Paciente) {
        try {
            usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Request request = new Request("PacienteController/guardar");
            request.post(Paciente);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Paciente = (PacienteDto) request.readEntity(PacienteDto.class);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "Saved Successfully", request.toString(), "Paciente", Paciente);
            } else {
                return new Respuesta(true, "Guardado exitosamente", request.toString(), "Paciente", Paciente);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el Paciente.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error saving the patient", "guardarPaciente " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error guardando el Paciente.", "guardarPaciente " + ex.getMessage());
            }
        }
    }

    public Respuesta eliminarPaciente(Long id) {
        try {
            usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("PacienteController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "Patient successfully removed", "");
            } else {
                return new Respuesta(true, "Paciente eliminado exitosamente", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Error eliminando el Paciente.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error deleting the patient", "eliminarPaciente " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error eliminando el Paciente.", "eliminarPaciente " + ex.getMessage());
            }
        }
    }

    public Respuesta getReporteControl(String cedula) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            Request request = new Request("PacienteController/reporte", "/{cedula}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            File carpeta = new File("C:\\reporte\\");
            carpeta.mkdir();
            //Guardo el pdf en el archivo
            File archivo = new File("C:\\reporte\\ReporteControl.pdf");

            byte[] bytes = (byte[]) request.readEntity(byte[].class);

            Path path = Paths.get(archivo.getAbsolutePath());
            Files.write(path, bytes);

            Runtime.getRuntime().exec("cmd /c start " + archivo);
            return new Respuesta(true, "Reporte Generado Exitosamente", "");
        } catch (IOException ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo Agendas.", ex);
            return new Respuesta(false, "Error obteniendo Agendas.", "getAgendas " + ex.getMessage());
        }
    }
}
