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
import clinicauna.service.EspacioService;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import clinicauna.util.vistaCita;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Alert;
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
    private Label lblAnno;
    @FXML
    private Label lblMes;
    @FXML
    private Label lblDia;
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
    private ImageView ArribaScroll2;
    private boolean inicio = true;
    private vistaCita hCita2;
    private vistaCita hCita3;
    private vistaCita hCita;
    @FXML
    private Label lblProgramada;
    @FXML
    private Label lblAtendida;
    @FXML
    private Label lblAusente;
    @FXML
    private Label lblCancelada;
    private Mensaje ms;
    @FXML
    private JFXButton btnBuscar;
    private MedicoDto medicoDtoAux;
    @FXML
    private Label lblSeleccioneMedico;
    @FXML
    private Label lblNombreMedico;

    @Override
    public void initialize() {
        ms = new Mensaje();
        usuarioDto = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (!usuarioDto.getTipoUsuario().equals("M")) {
            if (AppContext.getInstance().get("Med") != null) {
                medicoDto = (MedicoDto) AppContext.getInstance().get("Med");
                lblNombreMedico.setText("     Medico: " + medicoDto.getUs().getNombre() + " "
                        + medicoDto.getUs().getpApellido() + " " + medicoDto.getUs().getsApellido());
            }
            if (this.DatePicker.getValue() != null) {
                this.btnBuscar.setDisable(false);
            } else {
                this.btnBuscar.setDisable(true);
            }
        } else if (usuarioDto.getTipoUsuario().equals("M")) {
            inicio = false;
            //DatePicker.setValue(LocalDate.now());
            btnBuscar.setVisible(false);
            medicoService = new MedicoService();
            resp = medicoService.getMedicos();
            lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
            medicoDto = lista.stream().filter(x -> x.getUs().getID().equals(usuarioDto.getID())).findAny().get();

            inicioJornada = LocalTime.parse(medicoDto.getInicioJornada());
            finJornada = LocalTime.parse(medicoDto.getFinJornada());
            //Creo las conversiones de las horas del medico con formato
            LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicioJornada);
            LocalDateTime fin = LocalDateTime.of(LocalDate.now(), finJornada);
            String inicioS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
            String finS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(fin);
            medicoDto.setInicioJornada(inicioS);
            medicoDto.setFinJornada(finS);
            
            AppContext.getInstance().set("Medi", medicoDto);

            AppContext.getInstance().set("MedicoDto", medicoDto);
        }
        Inicio();
        fecha();
        SeleccionarMedico();
    }

    public void Inicio() {
        medicoService = new MedicoService();
        resp = medicoService.getMedicos();
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            /*
             *   En el caso de que el usuario este entrando y utilice el idioma ingles hacemos la traducción
             */
            //this.DatePicker.setPromptText(idioma.getProperty("Seleccionar") + " " + idioma.getProperty("un") + " " + idioma.getProperty("Fecha"));
            this.btnBuscar.setText(idioma.getProperty("Buscar"));
            this.lblSeleccioneMedico.setText(idioma.getProperty("Seleccione")+" "+idioma.getProperty("un")+" "+idioma.getProperty("Medico"));
            this.lblAnno.setText(idioma.getProperty("Año"));
            this.lblDia.setText(idioma.getProperty("Dia"));
            this.lblMes.setText(idioma.getProperty("Mes"));
            this.Titulo.setText(idioma.getProperty("Agenda"));
            this.lblProgramada.setText(idioma.getProperty("Programada"));
            this.lblAusente.setText(idioma.getProperty("Ausente"));
            this.lblCancelada.setText(idioma.getProperty("Cancelada"));
            this.lblAtendida.setText(idioma.getProperty("Atendida"));
            this.DatePicker.setPromptText(idioma.getProperty("Seleccione")+" "+idioma.getProperty("un")+" "+ idioma.getProperty("Fecha"));
        }
    }

    private EventHandler<MouseEvent> citasReleased = (event) -> {
        /*
         *    Valido que no pueda editar o guardar ninguna cita a menos que sea de un día igual o mayor que el día de hoy,
         *    no se debería de poder guardar citas en días anteriores, no tiene sentido
         */
        if (DatePicker.getValue().isAfter(LocalDate.now()) || DatePicker.getValue().isEqual(LocalDate.now())) {
            if (medicoDto.getEstado().equals("A")) {
                if (AppContext.getInstance().get("Med") != null) {
                    AppContext.getInstance().set("MedicoDto", medicoDto);
                }
                hCita = (vistaCita) event.getSource();
                AppContext.getInstance().set("hBox", hCita);
                AppContext.getInstance().set("Espacio", hCita.getEspacio());
                FlowController.getInstance().goViewInWindowModal("AgregarCita", this.stage, false);
                AppContext.getInstance().delete("Cita");
                //FlowController.getInstance().initialize();
                initialize();
            } else {
                ms.showModal(Alert.AlertType.INFORMATION, "Creación de una Cita", this.getStage(), "El médico se encuentra inactivo");
            }

        } else {
            ms.showModal(Alert.AlertType.INFORMATION, "Creación de una Cita", this.getStage(), "No se puede agregar una cita en esta fecha");
        }
    };

    @FXML
    private void Fecha(Event event) {
        /*
         *   Método de selección de fecha DatePicker, en el caso de que sea un usuario hacemos ciertas validaciones con el booleano que seteamos en el método de SeleccionarMedico
         */
        fecha();
        if (usuarioDto.getTipoUsuario().equals("M")) {
            //SeleccionarMedico();
            calendarGrid.getChildren().clear();
            CambioDeFecha();
        } else {
            inicio = false;

        }
    }

    public void fecha() {
        try {
            /*
             *   Cargamos la fecha en los labels
             */
            mes = (DatePicker.getValue().getMonth() != null) ? DatePicker.getValue().getMonth().toString() : " ";
            year = (String.valueOf(DatePicker.getValue().getYear()) != null) ? String.valueOf(DatePicker.getValue().getYear()) : " ";
            semana = (String.valueOf(DatePicker.getValue().getDayOfMonth()) != null) ? String.valueOf(DatePicker.getValue().getDayOfMonth()) : " ";
            labelmes.setText(mes);
            labelyear.setText(year);
            labelSemana.setText(semana);
        } catch (Exception e) {
        }
    }
    /*private static boolean cedulaEncontrada = false;
    private static String cedulaBuscar = "";*/
    private LocalTime inicioJornada;
    private LocalTime finJornada;

    public void SeleccionarMedico() {
        if (!inicio) {
            calendarGrid.getChildren().clear();
            /*
             *   Es necesario seleccionar un médico si el usuario no es un médico, como lo realizamos mediante una busqueda, realizamos un busqueda a 
             *   partir de la cedula para obtener el medicoDto y utilizarlo posteriormente
             */
            if ((!usuarioDto.getTipoUsuario().equals("M") && medicoDtoAux == null)) {
                medicoDto = (MedicoDto) AppContext.getInstance().get("Med");
                inicioJornada = LocalTime.parse(medicoDto.getInicioJornada());
                finJornada = LocalTime.parse(medicoDto.getFinJornada());
                //Creo las conversiones de las horas del medico con formato
                LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicioJornada);
                LocalDateTime fin = LocalDateTime.of(LocalDate.now(), finJornada);
                String inicioS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
                String finS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(fin);
                medicoDto.setInicioJornada(inicioS);
                medicoDto.setFinJornada(finS);
                medicoDtoAux = (MedicoDto) AppContext.getInstance().get("Med");
            } else if (usuarioDto.getTipoUsuario().equals("M") && AppContext.getInstance().get("Medi") == null) {
                medicoDto = (MedicoDto) AppContext.getInstance().get("MedicoDto");
                inicioJornada = LocalTime.parse(medicoDto.getInicioJornada());
                finJornada = LocalTime.parse(medicoDto.getFinJornada());
                //Creo las conversiones de las horas del medico con formato
                LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicioJornada);
                LocalDateTime fin = LocalDateTime.of(LocalDate.now(), finJornada);
                String inicioS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
                String finS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(fin);
                medicoDto.setInicioJornada(inicioS);
                medicoDto.setFinJornada(finS);
                AppContext.getInstance().set("Medi", medicoDto);
            }
            /*
             *  Intercambio de fechas en la agenda
             */
            CambioDeFecha();
        }

    }

    private void CambioDeFecha() {
        int EspaciosPorHora = medicoDto.getEspacios();//cantidad de espacios que posee un médico por hora
        Integer horas = 0;
        /*
         *   Calcula la cantidad de espacios por hora que tendra la agenda, tomamos en cuenta dos tipos de horarios, puede que la hora inicial sea mayor que 
         *   la hora final y viceversa, ejemplo: HoraInicial:10am HoraFinal:3pm, HoraInicial es antes que HoraFinal entonces hacemos HoraFinal-HoraInicial = 5,
         *   el otro caso es HoraInicial 10am HoraFinal:3am, en este caso hacemos 24-HoraInicial+HoraFinal = 17 horas
         */
        if (inicioJornada.isBefore(finJornada)) {
            horas = finJornada.getHour() - inicioJornada.getHour();
        } else if (inicioJornada.isAfter(finJornada)) {
            horas = (24) - inicioJornada.getHour() + finJornada.getHour();
        }
        int valor = inicioJornada.getHour();
        /*
         *   Creamos los espacios que van dentro de la agenda depediendo de la cantidad de horas que trabaja el médico
         */
        for (int i = 0; i < horas; i++) {
            for (int j = 0; j < EspaciosPorHora; j++) {
                vistaCita hPane = new vistaCita();
                hPane.getStyleClass().add("calendar_pane");
                hPane.setOnMouseReleased(citasReleased);
                /*
                 *   Dependiendo de la cantidad de espacios por hora que se dan entonces hacemos el tamaño del hBox
                 */
                hPane.setMinWidth((EspaciosPorHora == 4) ? 250 : (EspaciosPorHora == 3) ? 333 : (EspaciosPorHora == 2) ? 500 : 1000);
                hPane.setMinHeight(100);
                Label label = new Label();
                //Introduce los valores desde 1 si se ha superado ya las 24 horas
                if (valor >= 24) {
                    valor = 0;
                }
                label.setStyle("-fx-text-fill: gray; -fx-font-size : 12pt; -jfx-focus-color: -fx-secondary;");
                /*
                     *   Básicamente estamos escribiendo el formato de las fechas para que se vean bien dependiendo de la duración de la cita
                 */
                switch (EspaciosPorHora) {
                    case 1:
                        /*
                         *   En este caso solo hay horas completas
                         */
                        if (valor >= 10) {
                            label.setText(valor + ":00");
                        } else {
                            label.setText("0" + valor + ":00");
                        }
                        break;
                    case 2:
                        /*
                         *   En este caso ya tenemos citas cada treinta minutos
                         */
                        if (valor >= 10) {
                            label.setText((j == 0) ? (valor + ":00") : (valor + ":30"));
                        } else {
                            label.setText((j == 0) ? "0" + valor + ":00" : "0" + valor + ":30");
                        }

                        break;
                    case 3:
                        /*
                         *   En este caso ya tenemos citas cada 20
                         */
                        if (valor >= 10) {
                            label.setText((j == 0) ? valor + ":00" : (j == 1) ? valor + ":20" : valor + ":40");
                        } else {
                            label.setText((j == 0) ? "0" + valor + ":00" : (j == 1) ? "0" + valor + ":20" : "0" + valor + ":40");
                        }
                        break;
                    case 4:
                        /*
                         *   En este caso ya tenemos citas cada 15
                         */
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
                    AppContext.getInstance().delete("Cita");
                });

                hPane.setOnDragOver(f -> {
                    f.acceptTransferModes(TransferMode.ANY);
                });

                hPane.setOnDragDropped(e -> {
                    hCita3 = (vistaCita) e.getSource();
                    if (hCita2 != null && hCita3 != hCita2) {
                        hCita2.intercambiarCita(hCita3);
                    }
                });

                hPane.setOnDragDone(e -> {
                    hPane.setCursor(Cursor.OPEN_HAND);
                });
                calendarGrid.add(hPane, j, i);
            }
            valor++;
        }

        AppContext.getInstance().set("Grid", calendarGrid);
        /*
         *   Si la Agenda del medico con el dia seleccionado no se ha creado, entonces la creamos 
         */
        //en el caso de que el datepicker sea null entonces seteamos la fecha de hoy
        resp = new AgendaService().getAgenda((DatePicker.getValue() != null) ? DatePicker.getValue().toString() : LocalDate.now().toString(), medicoDto.getID());
        try {
            if (resp.getEstado()) {
                // System.out.println("no hola");
                agendaDto = (AgendaDto) resp.getResultado("Agenda");
            } else {
                System.out.println("hola");
                //Creo la agenda 
                if (AppContext.getInstance().get("Med") != null) {
                    medicoDto = (MedicoDto) AppContext.getInstance().get("Med");
                }
                //System.out.println("TIME "+ DatePicker.getValue());

                agendaDto = new AgendaDto(null, DatePicker.getValue(), new Long(1), medicoDto);
                System.out.println(agendaDto.getAgeFecha());
                agendaDto = (AgendaDto) new AgendaService().guardarAgenda(agendaDto).getResultado("Agenda");
            }
            //Muestra la agenda del medico
            mostrarAgenda();
            AppContext.getInstance().set("Agenda", agendaDto);
            AppContext.getInstance().set("Med", medicoDto);
        } catch (Exception e) {
            ms.show(Alert.AlertType.INFORMATION, "BRO", " no funca");
        }

    }

    @FXML
    private void Validacion(ActionEvent event) {
        /*
         *   Cuando seleccionamos la fecha, verificamos que el DatePicker no sea null, después iniciamos todo de nuevo para que se cargue un día diferente
         */
        if (medicoDto != null) {
            FlowController.getInstance().initialize();
            initialize();
        } else {
            this.btnBuscar.setDisable(false);
        }
    }

    void mostrarAgenda() {
        //Cargo las citas de la agenda
        /*
         *   Recorremos la lista de espacios de agenda, dentro de esto vamos recorriendo todos los hijos que tiene el calendario y seleccionando el hijo
         *   primero de el children que tiene que es un Objeto de vistaCita que hereda de hBox lo cual viene siendo la hora, y cargamos la Cita de esa hora
         */
        System.out.println("AGENDA " + agendaDto);
        agendaDto.getEspacioList().stream().forEach((espacio) -> {
            if (calendarGrid != null && calendarGrid.getChildren() != null) {
                calendarGrid.getChildren().stream().forEach((vCita) -> {
                    Label hora = (Label) ((vistaCita) vCita).getChildren().get(0);
                    if (hora.getText().equals(espacio.getEspHoraInicio())) {
                        cargarAgenda(((vistaCita) vCita), espacio);
                    }
                });
            }
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
     *  Estos metodos facilitan el movimiento del dia entre las agendas en los eventos del mouse
     */
    @FXML
    private void izquierda(DragEvent event) {
    }

    @FXML
    private void derecha(DragEvent event) {
    }

    @FXML
    private void clickIzquierda(MouseEvent event) {

    }

    @FXML
    private void clickDerecha(MouseEvent event) {
    }

    /*
     *  Facilita el manejo de las citas para bajar el ScrollPane por medio de los eventos del mouse 
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

    @FXML
    private void Buscar(ActionEvent event) {
        AppContext.getInstance().delete("Med");
        FlowController.getInstance().goViewInWindowModal("BuscarMedico", this.getStage(), false);
        if (AppContext.getInstance().get("Med") != null) {
            medicoDto = (MedicoDto) AppContext.getInstance().get("Med");
            medicoDtoAux = null;
            initialize();
            SeleccionarMedico();
        } else {
            AppContext.getInstance().set("Med", medicoDto);
        }
    }
}
