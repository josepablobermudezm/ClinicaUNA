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
    private AnchorPane root;
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
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnAgregarn;
    @FXML
    private JFXHamburger ham3;

    @Override
    public void initialize() {

        Image imgFondo;
        try {
            imgFondo = new Image("/clinicauna/resources/e.jpg");
            imvFondo.setImage(imgFondo);
        } catch (Exception e) {
        }
        Image omg1;
        try {
            omg1 = new Image("/clinicauna/resources/key (1).png");
            imgPassword.setImage(omg1);
        } catch (Exception e) {

        }

        Image omg2;
        try {
            omg2 = new Image("/clinicauna/resources/key (1).png");
            imguser.setImage(omg2);
        } catch (Exception e) {
        }
    }

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

    @FXML
    private void agregarContrasenna(ActionEvent event) {
        cambiarContrasenna();
    }

    @FXML
    private void iniciar(KeyEvent event) {
        cambiarContrasenna();
    }

    @FXML
    private void salir(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goViewInStage("LogIn", this.getStage());
    }

}
