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
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Carlos
 */
public class LogIngController extends Controller {

    @FXML
    private ImageView imvFondo;

    @FXML
    private ImageView omg;
    @FXML
    private ImageView imguser;
    @FXML
    private ImageView imgPassword;
    @FXML
    private VBox VboxLOG;
    @FXML
    private JFXTextField txtUsuario1;
    @FXML
    private JFXPasswordField txtClave1;
    private Idioma idioma;
    @FXML
    private JFXButton button2;
    @FXML
    private AnchorPane root1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        Formato();
        Image imgLogo;
        try {
            imgLogo = new Image("/clinicauna/resources/logo.png");
            omg.setImage(imgLogo);
        } catch (Exception e) {
        }
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
            omg2 = new Image("/clinicauna/resources/user (3).png");
            imguser.setImage(omg2);
        } catch (Exception e) {
        }

    }

    @FXML
    private void ingresar(ActionEvent event) {
        Login();
    }

    private void Login() {
        try {
            if (txtUsuario1.getText() == null || txtUsuario1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Validación de usuario", this.getStage(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave1.getText() == null || txtClave1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Validación de usuario", this.getStage(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {
                UsuarioService UsuarioService = new UsuarioService();
                Respuesta respuesta = UsuarioService.getUsuario(txtUsuario1.getText(), txtClave1.getText());
                if (respuesta.getEstado()) {
                    String contrasena = txtClave1.getText();
                    UsuarioDto usuario = (UsuarioDto) respuesta.getResultado("Usuario");
                    AppContext.getInstance().set("UsuarioActivo", usuario);

                    if ("I".equals(usuario.getIdioma())) {
                        idioma = new Idioma("Inglés");
                    } else {
                        if ("E".equals(usuario.getIdioma())) {
                            idioma = new Idioma("Español");
                        }
                    }
                    AppContext.getInstance().set("idioma", idioma);

                    if (usuario.getEstado().equals("A") && usuario.getContrasennaTemp() != null && contrasena.equals(usuario.getContrasennaTemp())) {
                        FlowController.getInstance().initialize();
                        FlowController.getInstance().goViewInStage("cambiarContrasenna", this.getStage());
                    } else if (usuario.getEstado().equals("A")) {
                        FlowController.getInstance().initialize();
                        FlowController.getInstance().goMain();
                        this.getStage().close();
                    } else if (usuario.getEstado().equals("I")) {
                        new Mensaje().showModal(Alert.AlertType.WARNING, "Ingreso", this.getStage(), "El usuario no esta activo, debes activarlo previamente en el correo que ha sido enviado.");
                    }

                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LogIngController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        this.getStage().close();
    }

    private void iniciar(KeyEvent event) {
        if (event.getCode() == event.getCode().ENTER) {
            Login();
        }

    }
    public void Formato(){
    this.txtClave1.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
    this.txtUsuario1.setTextFormatter(Formato.getInstance().maxLengthFormat(30));
}
    @FXML
    private void restablecerContrasenna(MouseEvent event) {
        FlowController.getInstance().goViewInStage("RecuperarContrasenna", this.getStage());
    }

    @FXML
    private void LogInKey(KeyEvent event) {
        if (event.getCode() == event.getCode().ENTER) {
            Login();
        }
    }

}