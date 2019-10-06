/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.AntecedenteDto;
import clinicauna.util.Request;
import clinicauna.util.Respuesta;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Carlos Olivares
 */
public class AntecedenteService {
    public Respuesta guardarAntecedente(AntecedenteDto antecedente) {
        try {

            Request request = new Request("AntecedenteController/guardar");
            request.post(antecedente);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            antecedente = (AntecedenteDto) request.readEntity(AntecedenteDto.class);

            return new Respuesta(true, "", "", "Antecedente", antecedente);
        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error guardando el Antecedente.", ex);
            return new Respuesta(false, "Error guardando el Antecedente.", "guardarAntecedente " + ex.getMessage());
        }
    }
     public Respuesta eliminarUsuario(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("AntecedenteController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "Antecedente eliminado exitosamente", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error eliminando el Antecedente.", ex);
            if(ex.getCause() != null && ex.getCause().getClass() == ConnectException.class){
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "eliminarAntecedente " + ex.getMessage());
            }
            return new Respuesta(false, "Error eliminando el Antecedente.", "eliminarAntecedente " + ex.getMessage());
        }
    }
     public Respuesta getAntecedentes() {
        try {
            Request request = new Request("AntecedenteController/antecedentes");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<AntecedenteDto> Antecedentes = (List<AntecedenteDto>) request.readEntity(new GenericType<List<AntecedenteDto>>() {
            });

            return new Respuesta(true, "", "", "Antecedentes", Antecedentes);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Antecedentes", "getAntecedentes" + ex.getMessage());
        }
    }
}
