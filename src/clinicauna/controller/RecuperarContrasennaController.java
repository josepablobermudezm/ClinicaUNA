/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.UsuarioDto;
import clinicauna.service.UsuarioService;
import clinicauna.util.Correos;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class RecuperarContrasennaController extends Controller {

    @FXML
    private JFXTextField txtCorreoElectronico;

    @Override
    public void initialize() {

    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void correo(ActionEvent event) {
        if (!txtCorreoElectronico.getText().isEmpty()) {
            String correo = txtCorreoElectronico.getText();
            UsuarioService usService = new UsuarioService();
            Respuesta resp = usService.getUsuario(correo);
            if (resp.getEstado()) {
                try {
                    UsuarioDto usuario = (UsuarioDto) resp.getResultado("Usuario");
                    String contrassena = usuario.getContrasennaTemp();
                    Correos.getInstance().recuperarContrasenna(correo, contrassena);
                    
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperando contraseña", this.getStage(),"Se envió una contraseña temporal a este correo");
                } catch (IOException | MessagingException e) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperando contraseña", this.getStage(), e.getMessage());
                }

            }
        }
    }

}
