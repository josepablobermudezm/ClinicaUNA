/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.UsuarioDto;
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
 * @author Carlos Olivares
 */
public class UsuarioService {
    
    public Respuesta getUsuario(String usuario, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario", usuario);
            parametros.put("clave", clave);
            Request request = new Request("UsuarioController/usuario", "/{usuario}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            UsuarioDto Usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", Usuario);

        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuario(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("UsuarioController/Usuario", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            UsuarioDto Usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", Usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el Usuario [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el Usuario.", "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios(String cedula, String nombre, String pApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            Request request = new Request("UsuarioController/Usuarios", "/{cedula}/{nombre}/{pApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
             List<UsuarioDto> Usuarios = (List<UsuarioDto>) request.readEntity(new GenericType<List<UsuarioDto>>() {
            });
            return new Respuesta(true, "", "", "Usuarios", Usuarios);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo Usuarios.", ex);
            return new Respuesta(false, "Error obteniendo Usuarios.", "getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios() {
        try {
            Request request = new Request("UsuarioController/Usuarios");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<UsuarioDto> Usuarios = (List<UsuarioDto>) request.readEntity(new GenericType<List<UsuarioDto>>() {
            });

            return new Respuesta(true, "", "", "Usuarios", Usuarios);
        } catch (Exception ex) {
            return new Respuesta(false, "", "", "Usuarios","getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta guardarUsuario(UsuarioDto Usuario) {
        /*try {
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            
            return new Respuesta(true, "", "", "Usuario", UsuarioDto);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el Usuario.", ex);
            return new Respuesta(false, "Error guardando el Usuario.", "guardarUsuario " + ex.getMessage());
        }*/
        return null;
    }

    public Respuesta eliminarUsuario(Long id) {
        /*try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("UsuarioController/Usuario","/{id}",parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error eliminando el Usuario.", ex);
            return new Respuesta(false, "Error eliminando el Usuario.", "eliminarUsuario " + ex.getMessage());
        }
    }*/
        return null;
    }
}
