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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Olivares
 */
public class UsuarioService {
    
    public Respuesta getUsuario(String usuario, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario",usuario);
            parametros.put("clave", clave);
            Request request = new Request("UsuarioController/usuario","/{usuario}/{clave}",parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(),"");
            }
            
            UsuarioDto Usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true,"","Usuario obtenido exitosamente","Usuario",Usuario);
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
}
