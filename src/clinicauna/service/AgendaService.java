/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.AgendaDto;
import clinicauna.report.ReportManager;
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
 * @author Jose Pablo Bermudez
 */
public class AgendaService {

    public Respuesta getAgenda(String fecha, Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fecha", fecha);
            parametros.put("id", id);
            Request request = new Request("AgendaController/agenda", "/{fecha}/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            AgendaDto agenda = (AgendaDto) request.readEntity(AgendaDto.class);
            return new Respuesta(true, "Agenda Existente", "", "Agenda", agenda);
        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo la Agenda [" + fecha + "]", ex);
            return new Respuesta(false, "Error obteniendo el Agenda.", "getAgenda " + ex.getMessage());
        }
    }

    public Respuesta getAgendas(String FechaInicio, String FechaFinal, String folio) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fechaInicio", FechaInicio);
            parametros.put("fechaFinal", FechaFinal);
            parametros.put("folio", folio);
            Request request = new Request("AgendaController/agendas", "/{fechaInicio}/{fechaFinal}/{folio}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            byte[] byteArray;
            
            File carpeta = new File("C:\\reporte\\");
            carpeta.mkdir();
            //Guardo el pdf en el archivo
            File archivo = new File("C:\\reporte\\ReporteAgenda.pdf");
            
            byte[] bytes = (byte[]) request.readEntity(byte[].class);
            
            System.out.println(archivo.getAbsolutePath());
            
            Path path = Paths.get(archivo.getAbsolutePath());
            Files.write(path, bytes);
            
            Runtime.getRuntime().exec("cmd /c start " +archivo);
            return new Respuesta(true, "", "");
        } catch (IOException ex) {
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo Agendas.", ex);
            return new Respuesta(false, "Error obteniendo Agendas.", "getAgendas " + ex.getMessage());
        }
    }

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

            Agenda = (AgendaDto) request.readEntity(AgendaDto.class
            );

            return new Respuesta(true, "", "", "Agenda", Agenda);

        } catch (Exception ex) {
            Logger.getLogger(AgendaService.class
                    .getName()).log(Level.SEVERE, "Error guardando el Agenda.", ex);
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
