/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class RecuperarContrasennaController extends Controller {

    @FXML
    private JFXTextField txtNombreUsuario;

    @Override
    public void initialize() {

    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void correo(ActionEvent event) {
        
    }

}
