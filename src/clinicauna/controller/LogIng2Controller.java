/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/*import unaplanilla2.model.EmpleadoDto;
import unaplanilla2.service.EmpleadoService;
import unaplanilla2.util.AppContext;
import unaplanilla2.util.FlowController;
import unaplanilla2.util.Mensaje;
import unaplanilla2.util.Respuesta;*/

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class LogIng2Controller extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imvFondo;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtClave;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnIngresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imvFondo.fitHeightProperty().bind(root.heightProperty());//  para que la imagen tenga el tamaño de toda la vista
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }    

    @Override
    public void initialize() {
        
    }
    
    @FXML
    private void ingresar(ActionEvent event) {
       /* try {

            if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar.getScene().getWindow(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave.getText() == null || txtClave.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.getUsuario(txtUsuario.getText(), txtClave.getText());
                respuesta.getEstado();
                if (respuesta.getEstado()) {
                    AppContext.getInstance().set("Usuario", (EmpleadoDto)respuesta.getResultado("Empleado"));
                    FlowController.getInstance().goMain();
                        ((Stage) btnIngresar.getScene().getWindow()).close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LogIng2Controller.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }*/
    }
    
    @FXML
    private void salir(ActionEvent event) {
        ((Stage) btnSalir.getScene().getWindow()).close();
    }
    
}
