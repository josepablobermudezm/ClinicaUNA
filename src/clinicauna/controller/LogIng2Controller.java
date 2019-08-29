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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*import unaplanilla2.model.UsuarioDto;
import unaplanilla2.service.UsuarioService;
import unaplanilla2.util.AppContext;
import unaplanilla2.util.FlowController;
import unaplanilla2.util.Mensaje;
import unaplanilla2.util.Respuesta;*/

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
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnIngresar;
    @FXML
    private ImageView omg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {

        /*Image omg1;
        try {
            omg1 = new Image("/clinicauna/resources/e.png");
            omg.setImage(omg1);
        } catch (Exception e) {
        }*/
    }

    @FXML
    private void ingresar(ActionEvent event) {
        try {

            if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar.getScene().getWindow(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave.getText() == null || txtClave.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {

                UsuarioService UsuarioService = new UsuarioService();
                Respuesta respuesta = UsuarioService.getUsuario(txtUsuario.getText(), txtClave.getText());
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
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

}
