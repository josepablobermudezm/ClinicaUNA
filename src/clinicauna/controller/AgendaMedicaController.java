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
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AgendaService;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Respuesta;
import clinicauna.util.vistaCita;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class AgendaMedicaController extends Controller implements Initializable {

    @FXML
    private GridPane calendarGrid;
    @FXML
    private JFXDatePicker DatePicker;
    @FXML
    private ScrollPane ScrollPane;
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
    private UsuarioDto usuarioDto = new UsuarioDto();
    @FXML
    private Label Titulo;
    @FXML
    private ImageView ArribaScroll;
    @FXML
    private ImageView ArribaScroll1;
    @FXML
    private ImageView ArribaScroll2;
    @FXML
    private ImageView ArribaScroll3;
    private boolean inicio = true;
    private vistaCita hCita2;
    private vistaCita hCita3;
    private vistaCita hCita;

    @Override
    public void initialize() {
        usuarioDto = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (!usuarioDto.getTipoUsuario().equals("M")) {
            if (this.DatePicker.getValue() != null) {
                AppContext.getInstance().delete("MedicoDto");
                this.ComboMedico.setDisable(false);
            } else {
                this.ComboMedico.setDisable(true);
            }
            //AppContext.getInstance().delete("MedicoDto");
        } else if (usuarioDto.getTipoUsuario().equals("M")) {
            inicio = false;
            DatePicker.setValue(LocalDate.now());
            ComboMedico.setVisible(false);
            medicoService = new MedicoService();
            resp = medicoService.getMedicos();
            lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
            medicoDto = lista.stream().filter(x -> x.getUs().getID().equals(usuarioDto.getID())).findAny().get();
            AppContext.getInstance().set("MedicoDto", medicoDto);
        }

        Inicio();
        SeleccionarMedico();
        fecha();
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
        hCita = (vistaCita) event.getSource();
        AppContext.getInstance().set("hBox", hCita);
        AppContext.getInstance().set("Espacio", hCita.getEspacio());
        FlowController.getInstance().goViewInWindowModal("AgregarCita", this.stage, false);
        AppContext.getInstance().delete("Cita");
        Inicio();
    };

    @FXML
    private void Fecha(Event event) {
        fecha();
        if (usuarioDto.getTipoUsuario().equals("M")) {
            SeleccionarMedico();
        } else {
            inicio = false;
        }
    }

    public void fecha() {
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
    private LocalTime inicioJornada;
    private LocalTime finJornada;

    @FXML
    private void seleccionarMedico(ActionEvent event) {
        if (ComboMedico.getSelectionModel() != null && ComboMedico.getSelectionModel().getSelectedItem() != null) {
            initialize();
            SeleccionarMedico();
        }
    }

    public void SeleccionarMedico() {
        if (!inicio) {
            calendarGrid.getChildren().clear();
            //si el usuario es médico no es necesario buscarlo con los datos del combo box.
            if (!usuarioDto.getTipoUsuario().equals("M") && AppContext.getInstance().get("MedicoDto") == null) {
                String medico = ComboMedico.getSelectionModel().getSelectedItem();
                medico.chars().forEach(x -> {
                    if (((char) x) == ':') {
                        cedulaEncontrada = true;
                    } else if (cedulaEncontrada) {
                        cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                    }
                });
                //Revisar condicion
                medicoDto = lista.stream().filter(x -> x.getUs().getCedula().equals(cedulaBuscar)).findAny().get();
                inicioJornada = LocalTime.parse(medicoDto.getInicioJornada());
                finJornada = LocalTime.parse(medicoDto.getFinJornada());
                //Creo las conversiones de las horas del medico con formato
                LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicioJornada);
                LocalDateTime fin = LocalDateTime.of(LocalDate.now(), finJornada);

                String inicioS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
                String finS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(fin);

                medicoDto.setInicioJornada(inicioS);
                medicoDto.setFinJornada(finS);
            }

            /*     LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicioJornada);
        LocalDateTime fin = LocalDateTime.of(LocalDate.now(), finJornada);*/
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
                    GridPane.setHgrow(hPane, Priority.ALWAYS);
                    //GridPane.setVgrow(hPane, Priority.ALWAYS);
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
                        //cuando se detecta un drag entonces guardo los datos de ese hBox en appcontext
                        hCita2 = (vistaCita) e.getSource();
                        AppContext.getInstance().set("hBox", hCita2);
                        AppContext.getInstance().set("Espacio", hCita2.getEspacio());
                        //FlowController.getInstance().goViewInWindowModal("AgregarCita", this.stage, false);
                        AppContext.getInstance().delete("Cita");
                        //guardo la cita que agarra
                    });

                    hPane.setOnDragOver(f -> {
                        f.acceptTransferModes(TransferMode.ANY);
                        /* if(node != null && !this.getItem().getActId().equals(node.getItem().getActId())){
                }*/

                    });

                    hPane.setOnDragDropped(e -> {
                        String style = "";
                        System.out.println(hCita2.getCorreo());
                        EspacioDto espacio = (EspacioDto) AppContext.getInstance().get("Espacio");
                        hCita3 = (vistaCita) e.getSource();
                        hCita3.setStyle(hCita2.getStyle());
                        hCita3.setvBox(hCita2.getvBox());
                        hCita3.setCorreo(hCita2.getCorreo());
                        hCita3.setEspacio(hCita2.getEspacio());
                        hCita3.setNombre(hCita2.getNombre());
                        hCita3.setTelefono(hCita2.getTelefono());
                        hCita3.AgregarCita(espacio);
                        //hCita3.AgregarCita(espacio);
                        VBox vBox = new VBox();
                        vBox.getChildren().addAll(hCita2.getNombre(), hCita2.getCorreo(), hCita2.getTelefono());
                        vBox.setAlignment(Pos.CENTER);
                        vBox.setSpacing(10);
                        String style1 = "-fx-text-fill: #636361;";
                        hCita2.getNombre().setStyle(style1);
                        hCita2.getCorreo().setStyle(style1);
                        hCita2.getTelefono().setStyle(style1);
                        hCita3.getChildren().addAll(vBox);
                        //hCita.setStyle("-fx-text-fill: #636361;");
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
                String style = "-fx-background-color: #fad655;";
                cargarVistaCita(vCita, style, espacio);
                break;
            }
            case "CA": {
                String style = "-fx-background-color: #fa7a7a";
                cargarVistaCita(vCita, style, espacio);
                break;
            }
            case "PR": {
                String style = "-fx-background-color: #8cff8c;";
                cargarVistaCita(vCita, style, espacio);
                break;
            }
            case "AU": {
                String style = "-fx-background-color: #bdbdbd";
                cargarVistaCita(vCita, style, espacio);
                break;
            }
            default:
                break;
        }
    }

    private void cargarVistaCita(vistaCita vCita, String style, EspacioDto espacio) {
        //Carga la vista de las citas 
        vCita.setBackground(Background.EMPTY);
        vCita.setStyle(style);
        vCita.AgregarCita(espacio);
        vCita.getChildren().add(vCita.get((medicoDto.getEspacios() == 2) ? 450 : (medicoDto.getEspacios() == 1) ? 950 : (medicoDto.getEspacios() == 3) ? 280 : 200));
    }

    /*
    Estos metodos facilitan el movimiento del dia entre las agendas en los eventos del mouse
     */
    @FXML
    private void izquierda(DragEvent event) {
        if (ComboMedico.getSelectionModel().getSelectedItem() != null && DatePicker.getValue() != null || DatePicker.getValue() != null && usuarioDto.getTipoUsuario().equals("M")) {
            if (DatePicker.getValue().getDayOfMonth() == 1 && DatePicker.getValue().getMonth().getValue() == 1) {
                Integer ano = DatePicker.getValue().getYear() - 1;
                LocalDate fecha = DatePicker.getValue().withYear(ano).withMonth(12).withDayOfMonth(31);
                DatePicker.setValue(fecha);
            } else {
                DatePicker.setValue(DatePicker.getValue().withDayOfYear(DatePicker.getValue().getDayOfYear() - 1));
            }
            AppContext.getInstance().delete("MedicoDto");
            fecha();
            Inicio();
            SeleccionarMedico();
        } else {
            DatePicker.setValue(DatePicker.getValue().withDayOfYear(DatePicker.getValue().getDayOfYear() - 1));
        }
    }

    @FXML
    private void derecha(DragEvent event) {
        if (ComboMedico.getSelectionModel().getSelectedItem() != null && DatePicker.getValue() != null || DatePicker.getValue() != null && usuarioDto.getTipoUsuario().equals("M")) {
            if (DatePicker.getValue().getDayOfMonth() == 31 && DatePicker.getValue().getMonth().getValue() == 12) {
                Integer ano = DatePicker.getValue().getYear() + 1;
                LocalDate fecha = DatePicker.getValue().withYear(ano).withMonth(1).withDayOfMonth(1);
                DatePicker.setValue(fecha);
            } else {
                DatePicker.setValue(DatePicker.getValue().withDayOfYear(DatePicker.getValue().getDayOfYear() + 1));
            }
            AppContext.getInstance().delete("MedicoDto");
            fecha();
            Inicio();
            SeleccionarMedico();
        }
    }

    @FXML
    private void clickIzquierda(MouseEvent event) {
        if (ComboMedico.getSelectionModel().getSelectedItem() != null && DatePicker.getValue() != null || DatePicker.getValue() != null && usuarioDto.getTipoUsuario().equals("M")) {
            if (DatePicker.getValue().getDayOfMonth() == 1 && DatePicker.getValue().getMonth().getValue() == 1) {
                Integer ano = DatePicker.getValue().getYear() - 1;
                LocalDate fecha = DatePicker.getValue().withYear(ano).withMonth(12).withDayOfMonth(31);
                DatePicker.setValue(fecha);
            } else {
                System.out.println(DatePicker.getValue());
                DatePicker.setValue(DatePicker.getValue().withDayOfYear(DatePicker.getValue().getDayOfYear() - 1));
                System.out.println(DatePicker.getValue());
            }

            AppContext.getInstance().delete("MedicoDto");
            fecha();
            Inicio();
            SeleccionarMedico();
        }

    }

    @FXML
    private void clickDerecha(MouseEvent event) {
        if (ComboMedico.getSelectionModel().getSelectedItem() != null && DatePicker.getValue() != null || DatePicker.getValue() != null && usuarioDto.getTipoUsuario().equals("M")) {
            System.out.println("Derecha");
            if (DatePicker.getValue().getDayOfMonth() == 31 && DatePicker.getValue().getMonth().getValue() == 12) {
                Integer ano = DatePicker.getValue().getYear() + 1;
                LocalDate fecha = DatePicker.getValue().withYear(ano).withMonth(1).withDayOfMonth(1);
                DatePicker.setValue(fecha);
            } else {
                DatePicker.setValue(DatePicker.getValue().withDayOfYear(DatePicker.getValue().getDayOfYear() + 1));
            }
            AppContext.getInstance().delete("MedicoDto");
            fecha();
            Inicio();
            SeleccionarMedico();
        }
    }

    /*
        Facilita el manejo de las citas para bajar el ScrollPane por medio de los eventos del mouse 
     */
    @FXML
    private void clickAbajo(MouseEvent event) {
        if (ScrollPane.getVvalue() < 1) {
            ScrollPane.setVvalue(ScrollPane.getVvalue() + 0.05);
        }
    }

    @FXML
    private void clickArriba(MouseEvent event) {
        if (ScrollPane.getVvalue() > 0) {
            ScrollPane.setVvalue(ScrollPane.getVvalue() - 0.05);
        }
    }

    @FXML
    private void arriba(DragEvent event) {
        if (ScrollPane.getVvalue() > 0) {
            ScrollPane.setVvalue(ScrollPane.getVvalue() - 0.01);
        }
    }

    @FXML
    private void abajo(DragEvent event) {
        if (ScrollPane.getVvalue() < 1) {
            ScrollPane.setVvalue(ScrollPane.getVvalue() + 0.01);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
