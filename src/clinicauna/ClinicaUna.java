
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna;

import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.hiloCorreo;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Jose Pablo Bermudez
 */
public class ClinicaUna extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Clinica UNA");
        stage.getIcons().add(new Image("/clinicauna/resources/pharmacy.png"));
        FlowController.getInstance().InitializeFlow(stage, null);
        
        
        
        FlowController.getInstance().goViewInWindowTransparent("LogIn"); 
        //FlowController.getInstance().goViewInWindowTransparent("VistaCargando");
        //Correos.getInstance().start();
        //correrHilo();
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
