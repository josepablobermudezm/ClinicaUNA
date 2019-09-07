/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class AgendaController extends Controller{

    @FXML
    private Label Titulo;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private JFXDatePicker DatePicker;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private FlowPane FlowPane;
    @FXML
    private Label lbl;
    @FXML
    private GridPane gridDias;
    @FXML
    private Label labelyear;
    @FXML
    private Label labelmes;

  

    @Override
    public void initialize() {

        int valor = 1;
        for(int i = 0; i < 24; i++){
            VBox vPane = new VBox();
            vPane.getStyleClass().add("calendar_pane");
            vPane.setMinWidth(125);
            Label label = new Label();
            label.setStyle("-fx-text-fill: gray; -fx-font-size : 15pt; -jfx-focus-color: -fx-secondary;");
            label.setText(valor+":00");
            valor++;
            vPane.getChildren().add(label);
            GridPane.setVgrow(vPane, Priority.ALWAYS);
            
            // Add it to the grid
            calendarGrid.add(vPane, 0, i);  
        }
        
        for (int i = 0; i < 24; i++){
            for (int j = 1; j < 8; j++){
                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(125);
                
                GridPane.setVgrow(vPane, Priority.ALWAYS);
                
                // Add it to the grid
                calendarGrid.add(vPane, j, i);  
            }
        }    
        
    }
    
}
