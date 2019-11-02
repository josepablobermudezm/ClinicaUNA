/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.service;

import clinicauna.model.UsuarioDto;
import clinicauna.util.AppContext;
import clinicauna.util.Request;
import clinicauna.util.Respuesta;
import java.net.ConnectException;
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
 * @author Carlos Olivares
 */
public class UsuarioService {

    private UsuarioDto usuario;

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
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "getUsuario " + ex.getMessage());
            }
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
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "getUsuario " + ex.getMessage());
            }
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
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "getUsuario " + ex.getMessage());
            }
            return new Respuesta(false, "Error obteniendo Usuarios.", "getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios() {
        try {
            Request request = new Request("UsuarioController/usuarios");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<UsuarioDto> Usuarios = (List<UsuarioDto>) request.readEntity(new GenericType<List<UsuarioDto>>() {
            });

            return new Respuesta(true, "", "", "Usuarios", Usuarios);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo Usuarios.", ex);
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "getUsuarioS " + ex.getMessage());
            }
            return new Respuesta(false, "", "", "Usuarios", "getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta guardarUsuario(UsuarioDto Usuario) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Request request = new Request("UsuarioController/guardar");
            request.post(Usuario);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "Saved Successfully", request.toString(), "Usuario", Usuario);
            } else {
                return new Respuesta(true, "Guardado exitosamente", request.toString(), "Usuario", Usuario);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el Usuario.", ex);
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "getUsuario " + ex.getMessage());
            }
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error saving the User", "guardarUsuario " + ex.getMessage());

            } else {
                return new Respuesta(false, "Error guardando el Usuario.", "guardarUsuario " + ex.getMessage());

            }
        }
    }

    public Respuesta eliminarUsuario(Long id) {
        try {
            usuario = usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("UsuarioController/eliminar", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(true, "User successfully deleted", "");
            } else {
                return new Respuesta(true, "Usuario eliminado exitosamente", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error eliminando el Usuario.", ex);
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "eliminarUsuario " + ex.getMessage());
            }
            if (usuario.getIdioma().equals("I")) {
                return new Respuesta(false, "There was an error deleting the user", "eliminarUsuario " + ex.getMessage());

            } else {
                return new Respuesta(false, "Error eliminando el Usuario.", "eliminarUsuario " + ex.getMessage());

            }
        }
    }

    public Respuesta activarUsuario(String codigo) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return new Respuesta(true, "http://" + address.getHostAddress() + ":8989/WsClinicaUNA/ws/UsuarioController/activar/" + codigo, "");
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error activando al usuario.", ex);
            return new Respuesta(false, "Error en la direccion IP.", "activarUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuario(String correo) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("correo", correo);
            Request request = new Request("UsuarioController/recuperarContrasenna/", "/{correo}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto Usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", Usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo Usuarios.", ex);
            if (ex.getCause() != null && ex.getCause().getClass() == ConnectException.class) {
                return new Respuesta(false, "No se ha podido conectar con el servidor.", "getUsuarios " + ex.getMessage());
            }
            return new Respuesta(false, "Error obteniendo Usuarios.", "getUsuarios " + ex.getMessage());
        }
    }
}
