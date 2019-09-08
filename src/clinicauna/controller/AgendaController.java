/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
    @FXML
    private Label lbl1;
    @FXML
    private Label labelSemana;
    private String mes,year,semana;
    @FXML
    private Label lblDomingo;
    @FXML
    private Label lblLunes;
    @FXML
    private Label lblMartes;
    @FXML
    private Label lblMiercoles;
    @FXML
    private Label lblJueves;
    @FXML
    private Label lblViernes;
    @FXML
    private Label lblSabado;
  

    @Override
    public void initialize() {

        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        DatePicker.setDayCellFactory(dayCellFactory);
        Inicio();
            
    }

    public void Inicio(){
        
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
    
    @FXML
    private void Fecha(Event event) {
        mes = (DatePicker.getValue().getMonth()!=null)?DatePicker.getValue().getMonth().toString():" ";
        year = (String.valueOf(DatePicker.getValue().getYear())!=null)?String.valueOf(DatePicker.getValue().getYear()):" ";
        semana = (String.valueOf(DatePicker.getValue().getDayOfMonth())!=null)?String.valueOf(DatePicker.getValue().getDayOfMonth()):" ";
        labelmes.setText(mes);
        labelyear.setText(year);
        labelSemana.setText(semana);
        fechasDias();
        
    }
    //Este metodo lo que hace es bloquer los días que no sean lunes del datepicker
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.getDayOfWeek() != DayOfWeek.MONDAY ) {
                    setDisable(true);
                    setStyle("-fx-background-color: lightgray;");
                }
            }
        };
        return dayCellFactory;
    }
    
    private void fechasDias(){
        int diaMes = Integer.parseInt(semana);
        lblDomingo.setText(lblDomingo.getText() + (diaMes-1));
        lblLunes.setText(lblLunes.getText() + diaMes);
        lblMartes.setText(lblMartes.getText() + (diaMes+1));
        lblMiercoles.setText(lblMiercoles.getText() + (diaMes+2));
        lblJueves.setText(lblJueves.getText() + (diaMes+3));
        lblViernes.setText(lblViernes.getText() + (diaMes+4));
        lblSabado.setText(lblSabado.getText() + (diaMes+5));
    }
    
}
