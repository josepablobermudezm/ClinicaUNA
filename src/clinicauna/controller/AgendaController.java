/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AgendaDto;
import clinicauna.model.CitaDto;
import clinicauna.model.EspacioDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AgendaService;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Respuesta;
import clinicauna.util.vistaCita;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

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
    private ObservableList<String> items;

    @Override
    public void initialize() {
        Inicio();

        if (this.DatePicker.getValue() != null) {
            this.ComboMedico.setDisable(false);
        } else {
            this.ComboMedico.setDisable(true);
        }
    }

    public void Inicio() {
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
        vistaCita hCita = (vistaCita) event.getSource();
        AppContext.getInstance().set("hBox", hCita);
        AppContext.getInstance().set("Cita", hCita.getCita());
        FlowController.getInstance().goViewInWindowModal("AgregarCita", this.stage, false);
        AppContext.getInstance().delete("Cita");
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
        } catch (Exception e) {

        }
    }

    private static boolean cedulaEncontrada = false;
    private static String cedulaBuscar = "";

    @FXML
    private void seleccionarMedico(ActionEvent event) {
        if (ComboMedico.getSelectionModel() != null && ComboMedico.getSelectionModel().getSelectedItem() != null) {
            initialize();
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

            LocalTime inicioJornada = LocalTime.parse(medicoDto.getInicioJornada());
            LocalTime finJornada = LocalTime.parse(medicoDto.getFinJornada());
            //Creo las conversiones de las horas del medico con formato
            LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicioJornada);
            LocalDateTime fin = LocalDateTime.of(LocalDate.now(), finJornada);

            String inicioS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
            String finS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(fin);

            medicoDto.setInicioJornada(inicioS);
            medicoDto.setFinJornada(finS);

            int EspaciosPorHora = medicoDto.getEspacios();
            Integer horas = 0;
            //Calcula la cantidad de espacios por hora que tendra la agenda
            if (inicioJornada.isBefore(finJornada)) {
                horas = -(inicioJornada.getHour() - finJornada.getHour());

            } else {
                horas = ((24 - inicioJornada.getHour()) + inicioJornada.getHour()) - finJornada.getHour();
            }

            int valor = inicioJornada.getHour();

            for (int i = 0; i < horas; i++) {
                for (int j = 0; j < EspaciosPorHora; j++) {
                    vistaCita hPane = new vistaCita();
                    hPane.getStyleClass().add("calendar_pane");
                    hPane.setOnMouseReleased(citasReleased);
                    hPane.setMinWidth((EspaciosPorHora == 4) ? 250 : (EspaciosPorHora == 3) ? 333 : (EspaciosPorHora == 2) ? 500 : 1000);
                    hPane.setMinHeight(100);
                    Label label = new Label();
                    //Introduce los valores desde 1 si se ha superado ya las 24 horas
                    if (valor >= 24) {
                        valor = 0;
                    }

                    label.setStyle("-fx-text-fill: gray; -fx-font-size : 12pt; -jfx-focus-color: -fx-secondary;");
                    switch (EspaciosPorHora) {
                        case 1:
                            if (valor >= 10) {
                                label.setText(valor + ":00");
                            } else {
                                label.setText("0" + valor + ":00");
                            }

                            break;
                        case 2:
                            if (valor >= 10) {
                                label.setText((j == 0) ? (valor + ":00") : (valor + ":30"));
                            } else {
                                label.setText((j == 0) ? "0" + valor + ":00" : "0" + valor + ":30");
                            }

                            break;
                        case 3:

                            if (valor >= 10) {
                                label.setText((j == 0) ? valor + ":00" : (j == 1) ? valor + ":20" : valor + ":40");
                            } else {
                                label.setText((j == 0) ? "0" + valor + ":00" : (j == 1) ? "0" + valor + ":20" : "0" + valor + ":40");
                            }
                            break;
                        case 4:
                            if (valor >= 10) {
                                label.setText((j == 0) ? (valor + ":00") : (j == 1) ? (valor + ":15") : (j == 2) ? (valor + ":30") : (j == 3) ? (valor + ":45") : (valor + ":00"));
                            } else {
                                label.setText((j == 0) ? "0" + valor + ":00" : (j == 1) ? "0" + valor + ":15" : (j == 2) ? "0" + valor + ":30" : (j == 3) ? "0" + valor + ":45" : "0" + valor + ":00");
                            }
                            break;
                    }

                    hPane.getChildren().add(label);
                    hPane.setAlignment(Pos.BASELINE_LEFT);
                    GridPane.setVgrow(hPane, Priority.NEVER);
                    hPane.setStyle("-fx-background-color: #FFFF;");

                    //Metodos de Drag and Drop
                    hPane.setOnDragDetected(e -> {

                        Dragboard db = hPane.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        WritableImage wi = hPane.snapshot(new SnapshotParameters(), null);
                        WritableImage wii = new WritableImage(wi.getPixelReader(), 0, 0, ((int) wi.getWidth()), ((int) wi.getHeight()));

                        content.put(DataFormat.IMAGE, wii);
                        hPane.setCursor(Cursor.CLOSED_HAND);
                        db.setContent(content);
                    });

                    hPane.setOnDragOver(f -> {

                        f.acceptTransferModes(TransferMode.ANY);

                        /* if(node != null && !this.getItem().getActId().equals(node.getItem().getActId())){
                    
                }*/
                    });

                    hPane.setOnDragDropped(e -> {

                    });

                    hPane.setOnDragDone(e -> {
                        hPane.setCursor(Cursor.OPEN_HAND);

                    });

                    calendarGrid.add(hPane, j, i);

                }
                valor++;
            }
            AppContext.getInstance().set("Grid", calendarGrid);

            //Agenda
            /*
            Si la Agenda del medico con el dia seleccionado no se ha creado, entonces la creamos 
             */
            resp = new AgendaService().getAgenda(DatePicker.getValue().toString(), medicoDto.getID());
            if (resp.getEstado()) {
                agendaDto = (AgendaDto) resp.getResultado("Agenda");
            } else {
                //Creo la agenda 
                agendaDto = new AgendaDto(null, DatePicker.getValue(), new Long(1), medicoDto);
                agendaDto = (AgendaDto) new AgendaService().guardarAgenda(agendaDto).getResultado("Agenda");
            }

            //Muestra la agenda del medico
            mostrarAgenda();
            cedulaBuscar = "";
            cedulaEncontrada = false;
            AppContext.getInstance().set("Agenda", agendaDto);
            AppContext.getInstance().set("MedicoDto", medicoDto);
        }
    }

    @FXML
    private void Validacion(ActionEvent event) {
        if (this.DatePicker.getValue() != null) {
            if (ComboMedico.getSelectionModel() != null && ComboMedico.getSelectionModel().getSelectedItem() != null) {
                FlowController.getInstance().initialize();
                initialize();
            } else {
                this.ComboMedico.setDisable(false);
            }
        }
    }

    void mostrarAgenda() {
        //Cargo las citas de la agenda
        agendaDto.getEspacioList().stream().forEach((espacio) -> {
            calendarGrid.getChildren().stream().forEach((vCita) -> {
                Label hora = (Label) ((vistaCita) vCita).getChildren().get(0);
                if (hora.getText().equals(espacio.getEspHoraInicio())) {
                    cargarAgenda(((vistaCita) vCita), espacio);
                }
            });
        });
    }

    private void cargarAgenda(vistaCita vCita, EspacioDto espacio) {
        //Pregunto el estado de la cita
        switch (espacio.getEspCita().getEstado()) {
            //Elijo el estilo de cada cita para cargar los datos en la vistaCita
            case "AT": {
                String style = "-fx-background-color: #8cff8c; ";
                cargarVistaCita(vCita, style, espacio.getEspCita());
                break;
            }
            case "CA": {
                String style = "-fx-background-color: #fa7a7a";
                cargarVistaCita(vCita, style, espacio.getEspCita());
                break;
            }
            case "PR": {
                String style = "-fx-background-color: #fad655";
                cargarVistaCita(vCita, style, espacio.getEspCita());
                break;
            }
            case "AU": {
                String style = "-fx-background-color: #bdbdbd";
                cargarVistaCita(vCita, style, espacio.getEspCita());
                break;
            }
            default:
                break;
        }
    }

    private void cargarVistaCita(vistaCita vCita, String style, CitaDto cita) {
        //Carga la vista de las citas 
        vCita.setBackground(Background.EMPTY);
        vCita.setStyle(style);
        vCita.AgregarCita(cita);
        vCita.getChildren().add(vCita.get((medicoDto.getEspacios() == 2) ? 450 : (medicoDto.getEspacios() == 1) ? 950 : (medicoDto.getEspacios() == 3) ? 280 : 200));
    }
}
