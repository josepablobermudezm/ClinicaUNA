
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna;

import clinicauna.util.FlowController;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.image.Image;
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
        
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
