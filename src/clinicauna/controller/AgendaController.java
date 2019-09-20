/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.service.MedicoService;
import clinicauna.util.FlowController;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
public class AgendaController extends Controller {

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
    @FXML
    private JFXComboBox<String> ComboMedico;
    private MedicoDto medicoDto;
    private MedicoService medicoService;
    private Respuesta resp;
    private String mes, year, semana;
    private ArrayList<MedicoDto> lista;

    @Override
    public void initialize() {
        Inicio();

    }

    public void Inicio() {

        medicoService = new MedicoService();
        resp = medicoService.getMedicos();

        int valor = 1;
        for (int i = 0; i < 24; i++) {
            VBox vPane = new VBox();
            vPane.getStyleClass().add("calendar_pane");
            vPane.setMinWidth(125);
            Label label = new Label();
            label.setStyle("-fx-text-fill: gray; -fx-font-size : 15pt; -jfx-focus-color: -fx-secondary;");
            label.setText(valor + ":00");
            valor++;
            vPane.getChildren().add(label);
            GridPane.setVgrow(vPane, Priority.ALWAYS);

            // Add it to the grid
            calendarGrid.add(vPane, 0, i);
        }

        for (int i = 0; i < 24; i++) {
            for (int j = 1; j < 2; j++) {
                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.setOnMouseReleased(citasReleased);
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(125);

                GridPane.setVgrow(vPane, Priority.ALWAYS);

                // Add it to the grid
                calendarGrid.add(vPane, j, i);
            }
        }

        lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
        ObservableList<String> items = FXCollections.observableArrayList(lista.stream().map(x -> x.getUs().getNombre()
                + " " + x.getUs().getpApellido() + " " + x.getUs().getsApellido() + " Ced:" + x.getUs().getCedula())
                .collect(Collectors.toList()));
        ComboMedico.setItems(items);
    }
    
    private EventHandler<MouseEvent> citasReleased = (event)->{
        
        FlowController.getInstance().goViewInWindowModal("AgregarCita", this.stage, false);
        
    };
    
  
    @FXML
    private void Fecha(Event event) {
        mes = (DatePicker.getValue().getMonth() != null) ? DatePicker.getValue().getMonth().toString() : " ";
        year = (String.valueOf(DatePicker.getValue().getYear()) != null) ? String.valueOf(DatePicker.getValue().getYear()) : " ";
        semana = (String.valueOf(DatePicker.getValue().getDayOfMonth()) != null) ? String.valueOf(DatePicker.getValue().getDayOfMonth()) : " ";
        labelmes.setText(mes);
        labelyear.setText(year);
        labelSemana.setText(semana);
        

    }

    private static boolean cedulaEncontrada=false;
    private static String cedulaBuscar="";
    @FXML
    private void seleccionarMedico(ActionEvent event) {
        if(ComboMedico.getSelectionModel()!=null && ComboMedico.getSelectionModel().getSelectedItem()!=null){
            String medico = ComboMedico.getSelectionModel().getSelectedItem();
            medico.chars().forEach(x->{
                if(((char) x)==':'){
                    cedulaEncontrada = true;
                }
                else if(cedulaEncontrada){
                    cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                }
            });
            medicoDto = lista.stream().filter(x->x.getUs().getCedula().equals(cedulaBuscar)).findAny().get();
            cedulaBuscar = "";  
            cedulaEncontrada =false;
        }
    }
}
