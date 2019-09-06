/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.AgendaDto;
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
public class AgendaService {
    
    
    public Respuesta getAgenda(String Agenda, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Agenda", Agenda);
            parametros.put("clave", clave);
            Request request = new Request("AgendaController/Agenda", "/{Agenda}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            AgendaDto agenda = (AgendaDto) request.readEntity(AgendaDto.class);
            return new Respuesta(true, "", "", "Agenda", agenda);

        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo el Agenda [" + Agenda + "]", ex);
            return new Respuesta(false, "Error obteniendo el Agenda.", "getAgenda " + ex.getMessage());
        }
    }

    public Respuesta getAgenda(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("AgendaController/Agenda", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            AgendaDto Agenda = (AgendaDto) request.readEntity(AgendaDto.class);
            return new Respuesta(true, "", "", "Agenda", Agenda);
        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo el Agenda [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Agenda.", "getAgenda " + ex.getMessage());
        }
    }
    /*
    public Respuesta getAgendas(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("AgendaController/Agendas", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<AgendaDto> Agendas = (List<AgendaDto>) request.readEntity(new GenericType<List<AgendaDto>>() {
            });
            return new Respuesta(true, "", "", "Agendas", Agendas);
        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo Agendas.", ex);
            return new Respuesta(false, "Error obteniendo Agendas.", "getAgendas " + ex.getMessage());
        }
    }*/

    public Respuesta getAgendas() {
        try {
            Request request = new Request("AgendaController/Agendas");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<AgendaDto> Agendas = (List<AgendaDto>) request.readEntity(new GenericType<List<AgendaDto>>() {
            });

            return new Respuesta(true, "", "", "Agendas", Agendas);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Agendas", "getAgendas " + ex.getMessage());
        }
    }

    public Respuesta guardarAgenda(AgendaDto Agenda) {
        try {

            Request request = new Request("AgendaController/guardar");
            request.post(Agenda);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Agenda = (AgendaDto) request.readEntity(AgendaDto.class);

            return new Respuesta(true, "", "", "Agenda", Agenda);
        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error guardando el Agenda.", ex);
            return new Respuesta(false, "Error guardando el Agenda.", "guardarAgenda " + ex.getMessage());
        }
    }

    /*public Respuesta eliminarAgenda(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("AgendaController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error eliminando el Agenda.", ex);
            return new Respuesta(false, "Error eliminando el Agenda.", "eliminarAgenda " + ex.getMessage());
        }
    }*/
    
}
