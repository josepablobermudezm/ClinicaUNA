/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.ControlDto;
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
public class ControlService {

    private UsuarioDto usuario;

    public Respuesta getControl(String Control, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Control", Control);
            parametros.put("clave", clave);
            Request request = new Request("ControlController/Control", "/{Control}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ControlDto control = (ControlDto) request.readEntity(ControlDto.class);
            return new Respuesta(true, "", "", "Control", control);

        } catch (Exception ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, "Error obteniendo el Control [" + Control + "]", ex);
            return new Respuesta(false, "Error obteniendo el Control.", "getControl " + ex.getMessage());
        }
    }

    public Respuesta getControl(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ControlController/Control", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            ControlDto Control = (ControlDto) request.readEntity(ControlDto.class);
            return new Respuesta(true, "", "", "Control", Control);
        } catch (Exception ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, "Error obteniendo el Control [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Control.", "getControl " + ex.getMessage());
        }
    }

    /*
    public Respuesta getControls(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("ControlController/Controls", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<ControlDto> Controls = (List<ControlDto>) request.readEntity(new GenericType<List<ControlDto>>() {
            });
            return new Respuesta(true, "", "", "Controls", Controls);
        } catch (Exception ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, "Error obteniendo Controls.", ex);
            return new Respuesta(false, "Error obteniendo Controls.", "getControls " + ex.getMessage());
        }
    }*/
    public Respuesta getControles() {
        try {
            Request request = new Request("ControlController/controles");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<ControlDto> Controls = (List<ControlDto>) request.readEntity(new GenericType<List<ControlDto>>() {
            });

            return new Respuesta(true, "", "", "controles", Controls);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "controles", "getControles " + ex.getMessage());
        }
    }

    public Respuesta getControles(ExpedienteDto exp) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", exp.getExpID());
            Request request = new Request("ControlController/Controles", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<ControlDto> Controls = (List<ControlDto>) request.readEntity(new GenericType<List<ControlDto>>() {
            });

            return new Respuesta(true, "", "", "Controles", Controls);
        } catch (Exception ex) {
            return new Respuesta(false, "getControles " + ex.getMessage(), "");
        }
    }

    public Respuesta guardarControl(ControlDto Control) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Request request = new Request("ControlController/guardar");
            request.post(Control);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Control = (ControlDto) request.readEntity(ControlDto.class);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "Saved Successfully", request.toString(), "Control", Control);
            } else {
                return new Respuesta(true, "Guardado Exitosamente", request.toString(), "Control", Control);
            }
        } catch (Exception ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, "Error guardando el Control.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error saving the control.", "guardarControl " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error guardando el Control.", "guardarControl " + ex.getMessage());
            }
        }
    }

    public Respuesta eliminarControl(Long id) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ControlController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, "Error eliminando el Control.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error deleting the control", "eliminarControl " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error eliminando el Control.", "eliminarControl " + ex.getMessage());
            }
        }
    }

}
