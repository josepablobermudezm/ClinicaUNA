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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public void initialize() {/*
        this.txtConfirmarContrasenna.setOnKeyReleased(clinicauna.ClinicaUna.sinEspacios);
        this.txtXontrasenna.setOnKeyReleased(clinicauna.ClinicaUna.sinEspacios);*/
        
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


    @FXML
    private void agregarContrasenna(ActionEvent event) {
        if (!txtConfirmarContrasenna.getText().isEmpty() && !txtXontrasenna.getText().isEmpty()) {
            if (txtConfirmarContrasenna.getText().equals(txtXontrasenna.getText())) {
                if (!txtConfirmarContrasenna.getText().contains(" ")) {
                    UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
                    usuario.setContrasennaTemp(null);
                    usuario.setContrasenna(txtConfirmarContrasenna.getText());
                    try {
                        UsuarioService usuarioService = new UsuarioService();
                        usuarioService.guardarUsuario(usuario);
                        FlowController.getInstance().initialize();
                        FlowController.getInstance().goMain();
                        this.getStage().close();

                    } catch (Exception e) {
                        System.out.println("Hubo un error al actualizar la contrasenna");
                    }
                } else {
                    System.out.println("La contrasenna no puede tener espacios");
                }
            } else {
                System.out.println("Las contrasennas no coinciden");
            }
        } else {
            System.out.println("Alguno de los campos esta vacio. Por favor corrige tu contrasenna");
        }
    }

    @FXML
    private void iniciar(KeyEvent event) {
    }


    @FXML
    private void salir(ActionEvent event) {
        FlowController.getInstance().goViewInStage("LogIn",this.getStage());
    }



}
