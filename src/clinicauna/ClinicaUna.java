
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
       /* FlowController.getInstance().goViewInWindowTransparent("VistaCargando");
        correrHilo();*/
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
        if (event.getCode() == event.getCode().SPACE) {
            event.consume();
        }

    };

    public static void main(String[] args) {
        launch(args);
    }

    private Timer timer = new Timer();
    private int tic = 1;
    private Label label;
    public static boolean finalizado = false;
    private Stage stage;

    /*public hiloCorreo(Label label, Stage stage) {
        this.label = label;
        this.stage = stage;

    }*/
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {
                /*
                switch (tic) {
                    case 1:
                        label.setText("Enviando Correos.");
                        tic++;
                        break;
                    case 2:
                        label.setText("Enviando Correos..");
                        tic++;
                        break;
                    case 3:
                        label.setText("Enviando Correos...");
                        tic = 1;
                        break;
                }*/
                
                Correos.getInstance().run();
                
                hiloCorreo.finalizado = true;
                timer.cancel();
                task.cancel();
                /*if (finalizado) {

                    //stage.close();
                    //finalizado = false;
                }*/
            });

        }
    };

    public void correrHilo() {
        timer.schedule(task, 10, 1000);
    }
}
