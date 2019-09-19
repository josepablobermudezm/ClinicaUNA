/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.service.MedicoService;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Label labelyear;
    @FXML
    private Label labelmes;
    @FXML
    private Label lbl1;
    @FXML
    private Label labelSemana;
    private String mes,year,semana;
    @FXML
    private Label lblMedico;
    @FXML
    private JFXComboBox<String> ComboMedico;
    private MedicoDto medicoDto;
    private MedicoService medicoService;
    private Respuesta resp;
    @Override
    public void initialize() {

        //Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        //DatePicker.setDayCellFactory(dayCellFactory);
        Inicio();
            
    }

    public void Inicio(){
        
        medicoService = new MedicoService();
        resp = medicoService.getMedicos();
        
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
            for (int j = 1; j < 2; j++){
                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(125);
                
                GridPane.setVgrow(vPane, Priority.ALWAYS);
                
                // Add it to the grid
                calendarGrid.add(vPane, j, i);  
            }
        }
        
        ArrayList<MedicoDto> lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
        ObservableList<String> items = FXCollections.observableArrayList(lista.stream().map(x->x.getUs().getNombre() 
                + " " + x.getUs().getpApellido() + " " + x.getUs().getsApellido() + " Ced:" + x.getUs().getCedula())
                .collect(Collectors.toList()));
        ComboMedico.setItems(items);
    }
    
    private void BuscarMedico(){
    
        
    
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
    /*private Callback<DatePicker, DateCell> getDayCellFactory() {
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
    }*/
    
    private void fechasDias(){
    }
    
}
