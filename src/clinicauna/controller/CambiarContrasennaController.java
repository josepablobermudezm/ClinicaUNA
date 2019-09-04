/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
    
    }
    
}
