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
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @Override
    public void initialize() {

    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void agregarContrasenna(ActionEvent event) {
        if (!txtConfirmarContrasenna.getText().isEmpty() && !txtXontrasenna.getText().isEmpty()) {
            if (txtConfirmarContrasenna.getText().equals(txtXontrasenna.getText())) {
                UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
                usuario.setContrasennaTemp(null);
                usuario.setContrasenna(txtConfirmarContrasenna.getText());
                try {
                    UsuarioService usuarioService = new UsuarioService();
                    usuarioService.guardarUsuario(usuario);
                    FlowController.getInstance().initialize();
                    FlowController.getInstance().goMain();
                     ((Stage)AppContext.getInstance().get("stage")).close();
                    
                } catch (Exception e) {
                    
                }

            } else {

            }
        } else {

        }
    }

}
