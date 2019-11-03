/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.AntecedenteDto;
import clinicauna.model.ControlDto;
import clinicauna.model.ExamenDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.util.AppContext;
import clinicauna.util.Request;
import clinicauna.util.Respuesta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Jose Pablo Bermudez
 */
public class ExpedienteService {

    private UsuarioDto usuario;

    public Respuesta getExpediente(String Expediente, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Expediente", Expediente);
            parametros.put("clave", clave);
            Request request = new Request("ExpedienteController/Expediente", "/{Expediente}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ExpedienteDto expediente = (ExpedienteDto) request.readEntity(ExpedienteDto.class);
            return new Respuesta(true, "", "", "Expediente", expediente);

        } catch (Exception ex) {
            Logger.getLogger(ExpedienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el Expediente [" + Expediente + "]", ex);
            return new Respuesta(false, "Error obteniendo el Expediente.", "getExpediente " + ex.getMessage());
        }
    }

    public Respuesta getExpediente(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ExpedienteController/Expediente", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ExpedienteDto Expediente = (ExpedienteDto) request.readEntity(ExpedienteDto.class);
            return new Respuesta(true, "", "", "Expediente", Expediente);
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el Expediente [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Expediente.", "getExpediente " + ex.getMessage());
        }
    }

    /*
    public Respuesta getExpedientes(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("ExpedienteController/Expedientes", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<ExpedienteDto> Expedientes = (List<ExpedienteDto>) request.readEntity(new GenericType<List<ExpedienteDto>>() {
            });
            return new Respuesta(true, "", "", "Expedientes", Expedientes);
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteService.class.getName()).log(Level.SEVERE, "Error obteniendo Expedientes.", ex);
            return new Respuesta(false, "Error obteniendo Expedientes.", "getExpedientes " + ex.getMessage());
        }
    }*/
    public Respuesta getExpedientes() {
        try {
            Request request = new Request("ExpedienteController/Expedientes");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ArrayList<ExpedienteDto> expedientes = (ArrayList<ExpedienteDto>) request.readEntity(new GenericType<ArrayList<ExpedienteDto>>() {
            });

            return new Respuesta(true, "", "", "Expedientes", expedientes);
        } catch (Exception ex) {
            return new Respuesta(false, "getExpedientes " + ex.getMessage(), "");
        }
    }

    public Respuesta guardarExpediente(ExpedienteDto Expediente) {
        try {
            usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Request request = new Request("ExpedienteController/guardar");
            request.post(Expediente);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Expediente = (ExpedienteDto) request.readEntity(ExpedienteDto.class);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "Saved Successfully", request.toString(), "Expediente", Expediente);
            } else {
                return new Respuesta(true, "Guardado exitosamente", request.toString(), "Expediente", Expediente);
            }
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteService.class.getName()).log(Level.SEVERE, "Error guardando el Expediente.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error saving the medical record", "guardarExpediente " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error guardando el Expediente.", "guardarExpediente " + ex.getMessage());
            }
        }
    }

    public Respuesta eliminarExpediente(Long id) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ExpedienteController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteService.class.getName()).log(Level.SEVERE, "Error eliminando el Expediente.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error deleting the medical record", "eliminarExpediente " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error eliminando el Expediente.", "eliminarExpediente " + ex.getMessage());
            }
        }
    }

}
