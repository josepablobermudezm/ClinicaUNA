/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.ExamenDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.util.AppContext;
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
public class ExamenService {

    private UsuarioDto usuario;

    public Respuesta getExamen(String Examen, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Examen", Examen);
            parametros.put("clave", clave);
            Request request = new Request("ExamenController/Examen", "/{Examen}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ExamenDto examen = (ExamenDto) request.readEntity(ExamenDto.class);
            return new Respuesta(true, "", "", "Examen", examen);

        } catch (Exception ex) {
            Logger.getLogger(ExamenService.class.getName()).log(Level.SEVERE, "Error obteniendo el Examen [" + Examen + "]", ex);
            return new Respuesta(false, "Error obteniendo el Examen.", "getExamen " + ex.getMessage());
        }
    }

    public Respuesta getExamen(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ExamenController/Examen", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ExamenDto Examen = (ExamenDto) request.readEntity(ExamenDto.class);
            return new Respuesta(true, "", "", "Examen", Examen);
        } catch (Exception ex) {
            Logger.getLogger(ExamenService.class.getName()).log(Level.SEVERE, "Error obteniendo el Examen [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Examen.", "getExamen " + ex.getMessage());
        }
    }

    /*
    public Respuesta getExamens(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("ExamenController/Examens", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<ExamenDto> Examens = (List<ExamenDto>) request.readEntity(new GenericType<List<ExamenDto>>() {
            });
            return new Respuesta(true, "", "", "Examens", Examens);
        } catch (Exception ex) {
            Logger.getLogger(ExamenService.class.getName()).log(Level.SEVERE, "Error obteniendo Examens.", ex);
            return new Respuesta(false, "Error obteniendo Examens.", "getExamens " + ex.getMessage());
        }
    }*/
    public Respuesta getExamenes() {
        try {
            Request request = new Request("ExamenController/Examenes");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<ExamenDto> Examens = (List<ExamenDto>) request.readEntity(new GenericType<List<ExamenDto>>() {
            });

            return new Respuesta(true, "", "", "Examenes", Examens);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Examenes", "getExamenes " + ex.getMessage());
        }
    }

    public Respuesta getExamenes(ExpedienteDto expediente) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("expId", expediente.getExpID());
            Request request = new Request("ExamenController/Examenes", "/{expId}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<ExamenDto> Examens = (List<ExamenDto>) request.readEntity(new GenericType<List<ExamenDto>>() {
            });

            return new Respuesta(true, "", "", "Examenes", Examens);
        } catch (Exception ex) {
            return new Respuesta(false, "getExamens " + ex.getMessage(), "");
        }
    }

    public Respuesta guardarExamen(ExamenDto Examen) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Request request = new Request("ExamenController/guardar");
            request.post(Examen);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Examen = (ExamenDto) request.readEntity(ExamenDto.class);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "Saved Successfully", "The exam has been saved successfully", "Examen", Examen);
            } else {
                return new Respuesta(true, "Guardado Exitosamente", "Se ha guardado el examen exitosamente", "Examen", Examen);
            }
        } catch (Exception ex) {
            Logger.getLogger(ExamenService.class.getName()).log(Level.SEVERE, "Error guardando el Examen.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error saving the exam", "guardarExamen " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error guardando el Examen.", "guardarExamen " + ex.getMessage());
            }
        }
    }

    public Respuesta eliminarExamen(Long id) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ExamenController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ExamenService.class.getName()).log(Level.SEVERE, "Error eliminando el Examen.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error deleting the exam", "eliminarExamen " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error eliminando el Examen.", "eliminarExamen " + ex.getMessage());
            }
        }
    }

}
