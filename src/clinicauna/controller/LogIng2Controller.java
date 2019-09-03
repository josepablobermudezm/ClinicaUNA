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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class LogIng2Controller extends Controller {

    @FXML
    private AnchorPane root;
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
    @FXML
    private JFXButton btnSalir1;
    @FXML
    private JFXButton btnIngresar1;

    /**
     * Initializes the controller class.
     */
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
            omg2 = new Image("/clinicauna/resources/user (3).png");
            imguser.setImage(omg2);
        } catch (Exception e) {
        }

    }

    @FXML
    private void ingresar(ActionEvent event) {
        try {

            if (txtUsuario1.getText() == null || txtUsuario1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar1.getScene().getWindow(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave1.getText() == null || txtClave1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar1.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {

                UsuarioService UsuarioService = new UsuarioService();
                Respuesta respuesta = UsuarioService.getUsuario(txtUsuario1.getText(), txtClave1.getText());
                if (respuesta.getEstado()) {
                    AppContext.getInstance().set("Usuario", (UsuarioDto) respuesta.getResultado("Usuario"));
                    FlowController.getInstance().goMain();
                    this.getStage().close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LogIng2Controller.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }

    }

    @FXML
    private void salir(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void iniciar(KeyEvent event) {
        if(event.getCode()== event.getCode().ENTER){
            try {

            if (txtUsuario1.getText() == null || txtUsuario1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar1.getScene().getWindow(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave1.getText() == null || txtClave1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar1.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {

                UsuarioService UsuarioService = new UsuarioService();
                Respuesta respuesta = UsuarioService.getUsuario(txtUsuario1.getText(), txtClave1.getText());
                if (respuesta.getEstado()) {
                    AppContext.getInstance().set("Usuario", (UsuarioDto) respuesta.getResultado("Usuario"));
                    FlowController.getInstance().goMain();
                    this.getStage().close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LogIng2Controller.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
        }
        
    }

}
