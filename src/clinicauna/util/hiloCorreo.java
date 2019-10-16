/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author JORDI RODRIGUEZ
 */
public class hiloCorreo {

    private Timer timer = new Timer();
    private int tic = 1;
    private Label label;
    public static boolean finalizado = false; 
    private Stage stage;
    public hiloCorreo(Label label, Stage stage) {
        this.label = label;
        this.stage = stage;
        
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {

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
                }

            });

            if (finalizado) {
                timer.cancel();
                task.cancel();
                stage.close();
                finalizado = false;
            }
        }
    };

    public void correrHilo() {
        timer.schedule(task, 10, 1000);
    }
}
