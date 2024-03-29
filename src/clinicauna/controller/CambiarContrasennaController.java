/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.UsuarioDto;
import clinicauna.service.UsuarioService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class CambiarContrasennaController extends Controller {

    @FXML
    private JFXPasswordField txtXontrasenna;
    @FXML
    private JFXPasswordField txtConfirmarContrasenna;
    @FXML
    private ImageView imvFondo;
    @FXML
    private VBox VboxLOG;
    @FXML
    private ImageView omg;
    @FXML
    private ImageView imguser;
    @FXML
    private ImageView imgPassword;
    @FXML
    private AnchorPane root1;
    @FXML
    private JFXButton button2;

    @Override
    public void initialize() {
        /*
        * para que se seleccione una imagen random para el fondo del inicio de sesión
        */
        int valorEntero = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
        if (valorEntero == 1) {
            Image imgFondo;
            try {
                imgFondo = new Image("/clinicauna/resources/fondo.jpg");
                imvFondo.setImage(imgFondo);
            } catch (Exception e) {
            }
        } else if (valorEntero == 2) {
            Image imgFondo;
            try {
                imgFondo = new Image("/clinicauna/resources/e.jpg");
                imvFondo.setImage(imgFondo);
            } catch (Exception e) {
            }
        } else if (valorEntero == 3) {
            Image imgFondo;
            try {
                imgFondo = new Image("/clinicauna/resources/fondo2.jpg");
                imvFondo.setImage(imgFondo);
            } catch (Exception e) {
            }
        }
        Image omg1;
        try {
            omg1 = new Image("/clinicauna/resources/key (1).png");
            imgPassword.setImage(omg1);
        } catch (Exception e) {

        }

        Image imgLogo;
        try {
            imgLogo = new Image("/clinicauna/resources/logo.png");
            omg.setImage(imgLogo);
        } catch (Exception e) {
        }
        
        Image omg2;
        try {
            omg2 = new Image("/clinicauna/resources/key (1).png");
            imguser.setImage(omg2);
        } catch (Exception e) {
        }
    }

    @FXML
    private void cambiarContrasenna() {
        if (!txtConfirmarContrasenna.getText().isEmpty() && !txtXontrasenna.getText().isEmpty()) {
            if (txtConfirmarContrasenna.getText().equals(txtXontrasenna.getText())) {
                if (!txtConfirmarContrasenna.getText().contains(" ")) {
                    UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
                    usuario.setContrasennaTemp(null);
                    usuario.setContrasenna(txtConfirmarContrasenna.getText());
                    try {
                        UsuarioService usuarioService = new UsuarioService();
                        Respuesta resp = usuarioService.guardarUsuario(usuario);
                        FlowController.getInstance().initialize();
                        FlowController.getInstance().goMain();
                        AppContext.getInstance().set("UsuarioActivo", (UsuarioDto) resp.getResultado("Usuario"));
                        this.getStage().close();

                    } catch (Exception e) {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiando Contraseña", this.getStage(), "Hubo un error al actualizar la contraseña");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.WARNING, "Cambiando Contraseña", this.getStage(), "La contraseña no puede tener espacios");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Cambiando Contraseña", this.getStage(), "Las contraseñas no coinciden");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Cambiando Contraseña", this.getStage(), "Alguno de los campos esta vacio. Por favor corrige tu contraseña");
        }
    }

    private void agregarContrasenna(ActionEvent event) {
        cambiarContrasenna();
    }

    private void iniciar(KeyEvent event) {
        if (event.getCode() == event.getCode().ENTER) {
            cambiarContrasenna();
        }

    }

    @FXML
    private void salir(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goViewInStage("LogIn", this.getStage());
    }

    @FXML
    private void restablecerContrasenna(MouseEvent event) {
    }

}
