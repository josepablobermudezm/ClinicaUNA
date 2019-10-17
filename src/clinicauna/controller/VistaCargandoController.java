/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.util.FlowController;
import clinicauna.util.hiloCorreo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author JORDI RODRIGUEZ
 */
public class VistaCargandoController extends Controller {

    @FXML
    private Label lblCorreos;

    @Override
    public void initialize() {
        //Limpio de nuevo la vista para que se pueda cargar la vista nuevamente
        FlowController.getInstance().initialize();
        if (this.getStage() != null) {
            hiloCorreo hilo = new hiloCorreo(lblCorreos, this.stage);
            hilo.correrHilo();
        }
    }

}
