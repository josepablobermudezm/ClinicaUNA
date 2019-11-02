/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.EspacioDto;
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
public class EspacioService {
    
    
    public Respuesta getEspacio(String Espacio, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Espacio", Espacio);
            parametros.put("clave", clave);
            Request request = new Request("EspacioController/Espacio", "/{Espacio}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EspacioDto espacio = (EspacioDto) request.readEntity(EspacioDto.class);
            return new Respuesta(true, "", "", "Espacio", espacio);

        } catch (Exception ex) {
            Logger.getLogger(EspacioService.class.getName()).log(Level.SEVERE, "Error obteniendo el Espacio [" + Espacio + "]", ex);
            return new Respuesta(false, "Error obteniendo el Espacio.", "getEspacio " + ex.getMessage());
        }
    }

    public Respuesta getEspacio(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("EspacioController/Espacio", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EspacioDto Espacio = (EspacioDto) request.readEntity(EspacioDto.class);
            return new Respuesta(true, "", "", "Espacio", Espacio);
        } catch (Exception ex) {
            Logger.getLogger(EspacioService.class.getName()).log(Level.SEVERE, "Error obteniendo el Espacio [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Espacio.", "getEspacio " + ex.getMessage());
        }
    }
    /*
    public Respuesta getEspacios(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("EspacioController/Espacios", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<EspacioDto> Espacios = (List<EspacioDto>) request.readEntity(new GenericType<List<EspacioDto>>() {
            });
            return new Respuesta(true, "", "", "Espacios", Espacios);
        } catch (Exception ex) {
            Logger.getLogger(EspacioService.class.getName()).log(Level.SEVERE, "Error obteniendo Espacios.", ex);
            return new Respuesta(false, "Error obteniendo Espacios.", "getEspacios " + ex.getMessage());
        }
    }*/

    public Respuesta getEspacios() {
        try {
            Request request = new Request("EspacioController/Espacios");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<EspacioDto> Espacios = (List<EspacioDto>) request.readEntity(new GenericType<List<EspacioDto>>() {
            });

            return new Respuesta(true, "", "", "Espacios", Espacios);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Espacios", "getEspacios " + ex.getMessage());
        }
    }

    public Respuesta guardarEspacio(EspacioDto Espacio) {
        try {

            Request request = new Request("EspacioController/guardar");
            request.post(Espacio);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EspacioDto espacio = (EspacioDto) request.readEntity(EspacioDto.class);

            return new Respuesta(true, "", "", "Espacio", espacio);
        } catch (Exception ex) {
            Logger.getLogger(EspacioService.class.getName()).log(Level.SEVERE, "Error guardando el Espacio.", ex);
            return new Respuesta(false, "Error guardando el Espacio.", "guardarEspacio " + ex.getMessage());
        }
    }

    public Respuesta eliminarEspacio(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("EspacioController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(EspacioService.class.getName()).log(Level.SEVERE, "Error eliminando el Espacio.", ex);
            return new Respuesta(false, "Error eliminando el Espacio.", "eliminarEspacio " + ex.getMessage());
        }
    }

    
}
