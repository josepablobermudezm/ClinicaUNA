/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AgendaDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private Label labelyear;
    @FXML
    private Label labelmes;
    @FXML
    private Label labelSemana;
    @FXML
    private JFXComboBox<String> ComboMedico;
    @FXML
    private Label lblAnno;
    @FXML
    private Label lblMes;
    @FXML
    private Label lblDia;
    @FXML
    private Label lblHora;
    private MedicoDto medicoDto;
    private MedicoService medicoService;
    private Respuesta resp;
    private String mes, year, semana;
    private ArrayList<MedicoDto> lista;
    private UsuarioDto usuario;
    private Idioma idioma;
    private AgendaDto agendaDto;
    ObservableList<String> items;

    @Override
    public void initialize() {
        Inicio();

    }

    public void Inicio() {
        VBox vbox = new VBox();
        //calendarGrid.addRow(0,vbox);
        medicoService = new MedicoService();
        resp = medicoService.getMedicos();
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.ComboMedico.setPromptText(idioma.getProperty("Seleccionar") + " " + idioma.getProperty("un") + " " + idioma.getProperty("Medico"));
            this.DatePicker.setPromptText(idioma.getProperty("Seleccionar") + " " + idioma.getProperty("un") + " " + idioma.getProperty("Fecha"));
            this.lblAnno.setText(idioma.getProperty("Año"));
            this.lblDia.setText(idioma.getProperty("Dia"));
            this.lblHora.setText(idioma.getProperty("Hora"));
            this.lblMes.setText(idioma.getProperty("Mes"));
        }

        lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
        items = FXCollections.observableArrayList(lista.stream().map(x -> x.getUs().getNombre()
                + " " + x.getUs().getpApellido() + " " + x.getUs().getsApellido() + " Ced:" + x.getUs().getCedula())
                .collect(Collectors.toList()));
        ComboMedico.setItems(items);
    }

    private EventHandler<MouseEvent> citasReleased = (event) -> {
        AppContext.getInstance().set("hBox", (HBox) event.getSource());
        FlowController.getInstance().goViewInWindowModal("AgregarCita", this.stage, false);

    };

    @FXML
    private void Fecha(Event event) {
        try {
            mes = (DatePicker.getValue().getMonth() != null) ? DatePicker.getValue().getMonth().toString() : " ";
            year = (String.valueOf(DatePicker.getValue().getYear()) != null) ? String.valueOf(DatePicker.getValue().getYear()) : " ";
            semana = (String.valueOf(DatePicker.getValue().getDayOfMonth()) != null) ? String.valueOf(DatePicker.getValue().getDayOfMonth()) : " ";
            labelmes.setText(mes);
            labelyear.setText(year);
            labelSemana.setText(semana);

            agendaDto = new AgendaDto();
        } catch (Exception e) {

        }
    }

    private static boolean cedulaEncontrada = false;
    private static String cedulaBuscar = "";

    @FXML
    private void seleccionarMedico(ActionEvent event) {
        if (ComboMedico.getSelectionModel() != null && ComboMedico.getSelectionModel().getSelectedItem() != null) {
            calendarGrid.getChildren().clear();
            String medico = ComboMedico.getSelectionModel().getSelectedItem();
            medico.chars().forEach(x -> {
                if (((char) x) == ':') {
                    cedulaEncontrada = true;
                } else if (cedulaEncontrada) {
                    cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                }
            });
            medicoDto = lista.stream().filter(x -> x.getUs().getCedula().equals(cedulaBuscar)).findAny().get();
            AppContext.getInstance().set("MedicoDto", medicoDto);
            cedulaBuscar = "";
            cedulaEncontrada = false;
            LocalTime localTimeObj = LocalTime.parse(medicoDto.getInicioJornada());
            LocalTime localTimeObj2 = LocalTime.parse(medicoDto.getFinJornada());
            int EspaciosPorHora = medicoDto.getEspacios();
            Integer horas = 0;
            //Calcula la cantidad de espacios por hora que tendra la agenda
            if (localTimeObj.isBefore(localTimeObj2)) {
                horas = -(localTimeObj.getHour() - localTimeObj2.getHour());

            } else {
                horas = ((24 - localTimeObj.getHour()) + localTimeObj.getHour()) - localTimeObj2.getHour();
            }

            int valor = localTimeObj.getHour();

            for (int i = 0; i < horas; i++) {
                for (int j = 0; j < EspaciosPorHora; j++) {
                    HBox hPane = new HBox();
                    hPane.getStyleClass().add("calendar_pane");
                    hPane.setOnMouseReleased(citasReleased);
                    hPane.setMinWidth((EspaciosPorHora == 4) ? 250 : (EspaciosPorHora == 3) ? 333 : (EspaciosPorHora == 2) ? 500 : 1000);
                    hPane.setMinHeight(100);
                    Label label = new Label();
                    //Introduce los valores desde 1 si se ha superado ya las 24 horas
                    if (valor > 24) {
                        valor = 1;
                    }
                    label.setStyle("-fx-text-fill: gray; -fx-font-size : 12pt; -jfx-focus-color: -fx-secondary;");
                    switch (EspaciosPorHora) {
                        case 1:
                            label.setText((valor + ":00"));
                            break;
                        case 2:
                            label.setText((j == 0) ? (valor + ":00") : (valor + ":30") );
                            break;
                        case 3:
                            label.setText((j == 0) ? (valor + ":00") : (j == 1) ? (valor + ":20") : (valor + ":40") );
                            break;
                        case 4:
                            label.setText((j == 0) ? (valor + ":00") : (j == 1) ? (valor + ":15") : (j == 2) ? (valor + ":30") : (j == 3) ? (valor + ":45") : (valor + ":00"));
                            break;
                    }
                    hPane.getChildren().add(label);
                    hPane.setAlignment(Pos.BASELINE_LEFT);
                    GridPane.setVgrow(hPane, Priority.NEVER);
                    // Add it to the grid
                    calendarGrid.add(hPane, j, i);
                }
                valor++;
            }

            AppContext.getInstance().set("Medico", medicoDto);

        }
    }
}
