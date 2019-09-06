/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.EspacioHoraDto;
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
public class EspacioHoraService {
    
    
    public Respuesta getEspacioHora(String EspacioHora, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("EspacioHora", EspacioHora);
            parametros.put("clave", clave);
            Request request = new Request("EspacioHoraController/EspacioHora", "/{EspacioHora}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EspacioHoraDto espacioHora = (EspacioHoraDto) request.readEntity(EspacioHoraDto.class);
            return new Respuesta(true, "", "", "EspacioHora", espacioHora);

        } catch (Exception ex) {
            Logger.getLogger(EspacioHoraService.class.getName()).log(Level.SEVERE, "Error obteniendo el EspacioHora [" + EspacioHora + "]", ex);
            return new Respuesta(false, "Error obteniendo el EspacioHora.", "getEspacioHora " + ex.getMessage());
        }
    }

    public Respuesta getEspacioHora(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("EspacioHoraController/EspacioHora", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EspacioHoraDto EspacioHora = (EspacioHoraDto) request.readEntity(EspacioHoraDto.class);
            return new Respuesta(true, "", "", "EspacioHora", EspacioHora);
        } catch (Exception ex) {
            Logger.getLogger(EspacioHoraService.class.getName()).log(Level.SEVERE, "Error obteniendo el EspacioHora [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el EspacioHora.", "getEspacioHora " + ex.getMessage());
        }
    }
    /*
    public Respuesta getEspacioHoras(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("EspacioHoraController/EspacioHoras", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<EspacioHoraDto> EspacioHoras = (List<EspacioHoraDto>) request.readEntity(new GenericType<List<EspacioHoraDto>>() {
            });
            return new Respuesta(true, "", "", "EspacioHoras", EspacioHoras);
        } catch (Exception ex) {
            Logger.getLogger(EspacioHoraService.class.getName()).log(Level.SEVERE, "Error obteniendo EspacioHoras.", ex);
            return new Respuesta(false, "Error obteniendo EspacioHoras.", "getEspacioHoras " + ex.getMessage());
        }
    }*/

    public Respuesta getEspacioHoras() {
        try {
            Request request = new Request("EspacioHoraController/EspacioHoras");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<EspacioHoraDto> EspacioHoras = (List<EspacioHoraDto>) request.readEntity(new GenericType<List<EspacioHoraDto>>() {
            });

            return new Respuesta(true, "", "", "EspacioHoras", EspacioHoras);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "EspacioHoras", "getEspacioHoras " + ex.getMessage());
        }
    }

    public Respuesta guardarEspacioHora(EspacioHoraDto EspacioHora) {
        try {

            Request request = new Request("EspacioHoraController/guardar");
            request.post(EspacioHora);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EspacioHora = (EspacioHoraDto) request.readEntity(EspacioHoraDto.class);

            return new Respuesta(true, "", "", "EspacioHora", EspacioHora);
        } catch (Exception ex) {
            Logger.getLogger(EspacioHoraService.class.getName()).log(Level.SEVERE, "Error guardando el EspacioHora.", ex);
            return new Respuesta(false, "Error guardando el EspacioHora.", "guardarEspacioHora " + ex.getMessage());
        }
    }

    public Respuesta eliminarEspacioHora(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("EspacioHoraController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(EspacioHoraService.class.getName()).log(Level.SEVERE, "Error eliminando el EspacioHora.", ex);
            return new Respuesta(false, "Error eliminando el EspacioHora.", "eliminarEspacioHora " + ex.getMessage());
        }
    }

    
}
