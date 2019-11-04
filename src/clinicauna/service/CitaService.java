/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.CitaDto;
import clinicauna.model.UsuarioDto;
import clinicauna.util.AppContext;
import clinicauna.util.Request;
import clinicauna.util.Respuesta;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
public class CitaService {

    private UsuarioDto usuario;

    public Respuesta getCita(String Cita, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Cita", Cita);
            parametros.put("clave", clave);
            Request request = new Request("CitaController/Cita", "/{Cita}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            CitaDto cita = (CitaDto) request.readEntity(CitaDto.class);
            return new Respuesta(true, "", "", "Cita", cita);

        } catch (Exception ex) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, "Error obteniendo el Cita [" + Cita + "]", ex);
            return new Respuesta(false, "Error obteniendo el Cita.", "getCita " + ex.getMessage());
        }
    }

    public Respuesta getCita(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CitaController/Cita", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            CitaDto Cita = (CitaDto) request.readEntity(CitaDto.class);
            return new Respuesta(true, "", "", "Cita", Cita);
        } catch (Exception ex) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, "Error obteniendo el Cita [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Cita.", "getCita " + ex.getMessage());
        }
    }

    /*
    public Respuesta getCitas(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("CitaController/Citas", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<CitaDto> Citas = (List<CitaDto>) request.readEntity(new GenericType<List<CitaDto>>() {
            });
            return new Respuesta(true, "", "", "Citas", Citas);
        } catch (Exception ex) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, "Error obteniendo Citas.", ex);
            return new Respuesta(false, "Error obteniendo Citas.", "getCitas " + ex.getMessage());
        }
    }*/
    public Respuesta getCitas() {
        try {
            Request request = new Request("CitaController/Citas");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<CitaDto> Citas = (List<CitaDto>) request.readEntity(new GenericType<List<CitaDto>>() {
            });

            return new Respuesta(true, "", "", "Citas", Citas);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Citas", "getCitas " + ex.getMessage());
        }
    }

    public Respuesta guardarCita(CitaDto Cita) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Request request = new Request("CitaController/guardar");
            request.post(Cita);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Cita = (CitaDto) request.readEntity(CitaDto.class);
            if (usuario != null) {
                if (usuario.getIdioma().equals("I")) {
                    return new Respuesta(true, "Saved Successfully", request.toString(), "Cita", Cita);
                } else {
                    return new Respuesta(true, "Guardado exitosamente", request.toString(), "Cita", Cita);
                }
            }else {
                return null;
            }

        } catch (Exception ex) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, "Error guardando el Cita.", ex);
            if (usuario != null) {
                if (usuario.getIdioma().equals("I")) {
                    return new Respuesta(false, "There was an error saving the Appointment", "guardarCita " + ex.getMessage());
                } else {
                    return new Respuesta(false, "Error guardando el Cita.", "guardarCita " + ex.getMessage());
                }
            }else{
                return null;
            }
        }
    }

    public Respuesta eliminarCita(Long id) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CitaController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, "Error eliminando el Cita.", ex);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error deleting the Appointment", "eliminarCita " + ex.getMessage());
            } else {
                return new Respuesta(false, "Error eliminando la Cita.", "eliminarCita " + ex.getMessage());
            }
        }
    }

}
