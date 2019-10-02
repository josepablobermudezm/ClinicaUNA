/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.UsuarioDto;
import clinicauna.service.UsuarioService;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class RecuperarContrasennaController extends Controller {

    @FXML
    private JFXTextField txtCorreoElectronico;
    @FXML
    private Label Titulo;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imvFondo;
    @FXML
    private VBox VboxLOG;
    @FXML
    private ImageView omg;
    @FXML
    private ImageView imgPassword;
    @FXML
    private ImageView imguser;
    @FXML
    private JFXButton button2;

    @Override
    public void initialize() {

        Image imgFondo;
        try {
            imgFondo = new Image("/clinicauna/resources/e.jpg");
            imvFondo.setImage(imgFondo);
        } catch (Exception e) {
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        FlowController.getInstance().goViewInStage("LogIn", this.getStage());
    }

    public void Formato() {
        this.txtCorreoElectronico.setTextFormatter(Formato.getInstance().maxLengthFormat(30));
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
                    if (usuario.getEstado().equals("A")) {
                        String contrassena = usuario.getContrasennaTemp();
                        resp = Correos.getInstance().recuperarContrasenna(correo, contrassena);
                        if (resp.getEstado()) {
                            txtCorreoElectronico.clear();
                            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Recuperando contraseña", this.getStage(), "Se envió una contraseña temporal a este correo");
                        } else {
                            usuario.setContrasennaTemp(null);
                            usService.guardarUsuario(usuario);
                            new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperando contraseña", this.getStage(),resp.getMensaje());
                        }

                    } else {
                        new Mensaje().showModal(Alert.AlertType.WARNING, "Recuperando contraseña", this.getStage(), "Este usuario no ha sido activado");
                    }
                } catch (Exception e) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperando contraseña", this.getStage(), e.getMessage());
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperando contraseña", this.getStage(), resp.getMensaje());
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Recuperando contraseña", this.getStage(), "No se ha digitado algún correo electrónico");
        }
    }
}
