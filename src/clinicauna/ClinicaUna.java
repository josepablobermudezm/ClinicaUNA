
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna;

import clinicauna.util.AppContext;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import java.net.InetAddress;
import javafx.application.Application;
import javafx.event.EventHandler;

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
        /*FlowController.getInstance().goMain();*/
        //Correos.getInstance().linkActivacion("Cjosue13","solomaj12@gmail.com","HI");
        FlowController.getInstance().goViewInWindow("LogIn");

    }

    /**
     */
    public final static EventHandler<KeyEvent> aceptaCaracteres = (KeyEvent event) -> {
        if (Character.isDigit(event.getCharacter().charAt(0))) {
            event.consume();
        }
    };

    public final static EventHandler<KeyEvent> aceptaNumeros = (KeyEvent event) -> {
        if (!Character.isDigit(event.getCharacter().charAt(0))) {
            event.consume();
        }
    };

    public final static EventHandler<KeyEvent> noEscribir = (KeyEvent event) -> {
        event.consume();
    };
    
    public final static EventHandler<KeyEvent> sinEspacios = (KeyEvent event) -> {
        if(event.getCode() == event.getCode().SPACE){
            event.consume();
        }
        
    };

    
    public static void main(String[] args) {
        launch(args);
    }

}
