package clinicauna.controller;

import clinicauna.model.AgendaDto;
import clinicauna.model.CitaDto;
import clinicauna.model.EspacioDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.CitaService;
import clinicauna.service.EspacioService;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import clinicauna.util.vistaCita;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class AgregarCitaController extends Controller {

    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXComboBox<String> ComboPacientes;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label Titulo;
    @FXML
    private JFXTextArea txtmotivo;
    @FXML
    private ToggleGroup estado;
    @FXML
    private JFXRadioButton btnProgramada;
    @FXML
    private JFXRadioButton btnAtendida;
    @FXML
    private JFXRadioButton btnCancelada;
    @FXML
    private JFXRadioButton btnAusente;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField txtEspacios;
    private GridPane grid;
    private vistaCita hBox;
    private Respuesta resp;
    private PacienteService pacienteService;
    private CitaService citaService;
    private PacienteDto pacienteDto;
    private CitaDto citaDto;
    private EspacioDto espacioDto;
    private Mensaje ms;
    private Idioma idioma;
    private UsuarioDto usuario;
    private ArrayList<PacienteDto> lista;
    private MedicoDto medicoDto;
    private AgendaDto agendaDto;
    private Correos correo;
    private PacienteDto paciente;
    private String estado1;
    private static boolean valor1 = false;
    private static String valor = "";
    @FXML
    private Label lblEst;

    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.txtEspacios.setPromptText(idioma.getProperty("Agenda") + " " + idioma.getProperty("Espacios"));
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnAtendida.setText(idioma.getProperty("Atendida"));
            this.btnAusente.setText(idioma.getProperty("Ausente"));
            this.btnCancelada.setText(idioma.getProperty("Cancelada"));
            this.btnCancelar.setText(idioma.getProperty("Cancelar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.btnProgramada.setText(idioma.getProperty("Programada"));
            this.Titulo.setText(idioma.getProperty("Agendar") + " " + idioma.getProperty("ACita"));
            this.ComboPacientes.setPromptText(idioma.getProperty("Pacientes"));
            this.lblEst.setText(idioma.getProperty("Estado"));
            this.txtCorreo.setPromptText(idioma.getProperty("Correo"));
            this.txtTelefono.setPromptText(idioma.getProperty("Telefono"));
            this.txtmotivo.setPromptText(idioma.getProperty("Motivo"));
        }
        formato();
        pacienteService = new PacienteService();
        resp = pacienteService.getPacientes();
        ms = new Mensaje();
        citaService = new CitaService();

        hBox = (vistaCita) AppContext.getInstance().get("hBox");
        grid = (GridPane) AppContext.getInstance().get("Grid");
        medicoDto = (MedicoDto) AppContext.getInstance().get("MedicoDto");
        agendaDto = (AgendaDto) AppContext.getInstance().get("Agenda");

        lista = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");

        ObservableList<String> items = FXCollections.observableArrayList(lista.stream().map(x -> x.getNombre()
                + " " + x.getpApellido() + " " + x.getsApellido() + " Ced:" + x.getCedula())
                .collect(Collectors.toList()));
        ComboPacientes.setItems(items);
        //si el appcontext de espacio no está vacío, de prepara para la edición
        if (AppContext.getInstance().get("Espacio") != null) {
            btnAtendida.setDisable(false);
            btnAusente.setDisable(false);
            btnCancelada.setDisable(false);
            espacioDto = (EspacioDto) AppContext.getInstance().get("Espacio");
            citaDto = espacioDto.getEspCita();
            txtCorreo.setText(espacioDto.getEspCita().getCorreo());
            txtTelefono.setText(espacioDto.getEspCita().getTelefono());
            txtmotivo.setText(espacioDto.getEspCita().getMotivo());
            txtEspacios.setText(String.valueOf(espacioDto.getEspCita().getEspacios().size()));
            ComboPacientes.setValue(espacioDto.getEspCita().getPaciente().getNombre() + " " + espacioDto.getEspCita().getPaciente().getpApellido() + " " + espacioDto.getEspCita().getPaciente().getsApellido() + " Ced:" + espacioDto.getEspCita().getPaciente().getCedula());
            String paciente = espacioDto.getEspCita().getPaciente().getNombre() + " " + espacioDto.getEspCita().getPaciente().getpApellido() + " " + espacioDto.getEspCita().getPaciente().getsApellido() + " Ced:" + espacioDto.getEspCita().getPaciente().getCedula();
            paciente.chars().forEach(x -> {
                if (((char) x) == ':') {
                    cedulaEncontrada = true;
                } else if (cedulaEncontrada) {
                    cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                }
            });
            pacienteDto = lista.stream().filter(x -> x.getCedula().equals(cedulaBuscar)).findAny().get();
            AppContext.getInstance().set("PacienteDto", pacienteDto);
            cedulaBuscar = "";
            cedulaEncontrada = false;
            //LocalDateTime inicioJornada = LocalDateTime.parse(espacioDto.getEspHoraInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            //Date date = Date.from(inicioJornada.atZone(ZoneId.systemDefault()).toInstant());
            txtEspacios.setText(String.valueOf(agendaDto.getEspacioList().stream().filter(x -> x.getEspCita().getID().equals(espacioDto.getEspCita().getID())).count()));
            //if(espacioDto.getEspCita().getEstado().equals("CA") /*&& (espacioDto.getEspAgenda().getAgeFecha().isBefore(LocalDate.now()))*/ /*&& date.before(Dat)*/){
            if (espacioDto.getEspCita().getEstado().equals("PR")) {
                btnProgramada.setSelected(true);
            } else {
                btnProgramada.setSelected(false);
            }
            if (espacioDto.getEspCita().getEstado().equals("AT")) {
                btnAtendida.setSelected(true);
            } else {
                btnAtendida.setSelected(false);
            }
            if (espacioDto.getEspCita().getEstado().equals("AU")) {
                btnAusente.setSelected(true);
            } else {
                btnAusente.setSelected(false);
            }
            //obtengo el valor de la hora del padre HBOX
            Label label = (Label) hBox.getvBox().getParent().getChildrenUnmodifiable().get(0);
            //conversión de string a localTime
            LocalTime isoTime = LocalTime.parse(label.getText() + ":00",
                    DateTimeFormatter.ISO_LOCAL_TIME);
            System.out.println(isoTime);
            if (espacioDto.getEspCita().getEstado().equals("CA")) {
                if (((agendaDto.getAgeFecha().isEqual(LocalDate.now()) && isoTime.isAfter(LocalTime.now()))
                        || (agendaDto.getAgeFecha().isAfter(LocalDate.now()) && isoTime.isAfter(LocalTime.now())))) {
                    btnCancelada.setSelected(true);
                    btnProgramada.setDisable(false);
                    btnAtendida.setDisable(true);
                    btnCancelada.setDisable(false);
                    btnAusente.setDisable(true);
                    System.out.println("hora legal");
                } else {
                    btnCancelada.setSelected(true);
                    btnProgramada.setDisable(true);
                    btnAtendida.setDisable(true);
                    btnCancelada.setDisable(false);
                    btnAusente.setDisable(true);
                    System.out.println("hora ilegal");
                }
                System.out.println("está cancelada");
            }
        } else {
            //es la primera vez que la selecciona por lo tanto no debe de poder elegir otro tipo de estado
            estado1 = "PR";
            btnAtendida.setSelected(false);
            btnAusente.setSelected(false);
            btnCancelada.setSelected(false);
            btnProgramada.setSelected(true);
            btnAtendida.setDisable(true);
            btnAusente.setDisable(true);
            btnCancelada.setDisable(true);
        }

    }

    @FXML
    private void guardar(ActionEvent event) {

        if (registroCorrecto()) {

            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String motivo = txtmotivo.getText();
            Long version = new Long(1);
            if (AppContext.getInstance().get("Espacio") != null) {
                estado1 = (btnProgramada.isSelected()) ? "PR" : (btnAtendida.isSelected()) ? "AT" : (btnAusente.isSelected()) ? "AU" : "CA";
            } else {
                estado1 = "PR";
            }
            //Obtengo el primer el Hbox que contiene el Label con la hora
            citaDto = new CitaDto(null, version, pacienteDto, motivo, estado1, telefono, correo, "N");

            try {
                switch (estado1) {
                    case "AT": {
                        String style = "-fx-background-color: #fad655; ";
                        ValidarEspacios(style);
                        break;
                    }
                    case "CA": {
                        String style = "-fx-background-color: #fa7a7a;";
                        ValidarEspacios(style);
                        break;
                    }
                    case "PR": {
                        String style = "-fx-background-color: #8cff8c;";
                        ValidarEspacios(style);
                        break;
                    }
                    case "AU": {
                        String style = "-fx-background-color: #bdbdbd;";
                        ValidarEspacios(style);
                        break;
                    }
                    default:
                        break;
                }
                limpiarValores();
                AppContext.getInstance().set("hBox", null);
                this.getStage().close();
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar la cita..." + e.getMessage());
            }
        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Faltan datos por ingresar");
        }
    }

    private static boolean cedulaEncontrada = false;
    private static String cedulaBuscar = "";

    @FXML
    private void seleccionarPaciente(ActionEvent event) {
        //este método lo que hace es buscar el nombre a partir de la cédula
        if (ComboPacientes.getSelectionModel() != null && ComboPacientes.getSelectionModel().getSelectedItem() != null) {
            String paciente = ComboPacientes.getSelectionModel().getSelectedItem();
            paciente.chars().forEach(x -> {
                if (((char) x) == ':') {
                    cedulaEncontrada = true;
                } else if (cedulaEncontrada) {
                    cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                }
            });
            pacienteDto = lista.stream().filter(x -> x.getCedula().equals(cedulaBuscar)).findAny().get();
            AppContext.getInstance().set("PacienteDto", pacienteDto);
            cedulaBuscar = "";
            cedulaEncontrada = false;
        }
    }

    void limpiarValores() {
        txtCorreo.clear();
        txtTelefono.clear();
        txtmotivo.clear();
        ComboPacientes.getSelectionModel().clearSelection();
        val = false;
        bandera = false;
        aux.clear();
        i = 0;
        j = 0;
    }

    boolean registroCorrecto() {
        return !txtCorreo.getText().isEmpty() && !txtTelefono.getText().isEmpty()
                && ComboPacientes.getSelectionModel() != null;
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        this.txtCorreo.clear();
        this.txtTelefono.clear();
        this.txtmotivo.clear();
    }

    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().initialize();
        limpiarValores();
        AppContext.getInstance().delete("Espacio");
        this.getStage().close();
    }

    private void formato() {
        this.txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
        this.txtTelefono.setTextFormatter(Formato.getInstance().integerFormat(15));
        this.txtmotivo.setTextFormatter(Formato.getInstance().maxLengthFormat(100));
        this.txtEspacios.setTextFormatter(Formato.getInstance().integerFormat(1));
    }
    private static int i, j = 0;
    private boolean val = false;
    private boolean bandera = false;
    private static List<vistaCita> aux = new ArrayList<>();

    private void ValidarEspacios(String style) {
        //revizo que si hayan espacio vacíos más adelante de la cita, en el caso que no hayan le voy ell valor de espacios al usuario y le pregunto si quiere continuar
        int x = grid.getChildren().indexOf(hBox);
        int espacio = Integer.parseInt(txtEspacios.getText());
        grid.getChildren().stream().forEach(l -> {
            if (grid.getChildren().indexOf(l) >= x) {
                i++;
                if (i <= espacio && !bandera) {
                    if (((vistaCita) l).getEspacio() == null) {
                        aux.add((vistaCita) l);
                        j++;
                    } else {
                        val = true;
                        bandera = true;
                    }
                }
            }
        });
        if (val) {
            if (new Mensaje().showConfirmation("Espacios de Cita", this.getStage(), "Hay disponibles " + String.valueOf(j) + " Espacios ¿Deseas agregarlos?")) {
                AgregarCita(style);

            } else {
                limpiarValores();
            }
        } else {
            if (j == espacio) {
                AgregarCita(style);

            } else {
                if (!val) {
                    if (new Mensaje().showConfirmation("Espacios de Cita", this.getStage(), "Hay disponibles " + String.valueOf(j) + " Espacios ¿Deseas agregarlos?")) {
                        AgregarCita(style);
                    } else {
                        limpiarValores();
                    }
                }
            }
        }
    }

    public void AgregarCita(String style) {
        //Guardo la cita en base de datos
        resp = citaService.guardarCita(citaDto);
        citaDto = (CitaDto) resp.getResultado("Cita");
        EspacioService service = new EspacioService();
        //Seteo el medico con el formato del LocalDateTime a la agenda
        agendaDto.setAgeMedico(medicoDto);
        //Recorro la lista de espacios seleccionados para setearles un color definido
        System.out.println(aux.size());
        aux.stream().forEach(vCita -> {
            Label hora = (Label) vCita.getChildren().get(0);
            LocalTime localTimeObj = LocalTime.parse(hora.getText());
            String horaInicio = " ";
            String horaFin = " ";
            //Las horas de salida según cantidad de espacios por hora
            if (medicoDto.getEspacios() == 1) {
                LocalDateTime horaCitaLocal = LocalDateTime.of(LocalDate.now(), localTimeObj);
                horaInicio = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal);
                LocalTime localTimeObje;
                if (localTimeObj.getHour() == 23) {
                    localTimeObje = localTimeObj.withHour(0);
                } else {
                    localTimeObje = localTimeObj.withHour(localTimeObj.getHour() + 1);
                }

                LocalDateTime horaCitaLocal1 = LocalDateTime.of(LocalDate.now(), localTimeObje);
                horaFin = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal1);
            } else if (medicoDto.getEspacios() == 2) {
                LocalDateTime horaCitaLocal = LocalDateTime.of(LocalDate.now(), localTimeObj);
                horaInicio = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal);
                LocalTime localTimeObje;
                if (localTimeObj.getMinute() == 30) {
                    if (localTimeObj.getHour() == 23) {
                        localTimeObje = localTimeObj.withHour(0).withMinute(0);
                    } else {
                        localTimeObje = localTimeObj.withHour(localTimeObj.getHour() + 1).withMinute(0);
                    }

                } else {
                    localTimeObje = localTimeObj.withMinute(localTimeObj.getMinute() + 30);
                }
                LocalDateTime horaCitaLocal1 = LocalDateTime.of(LocalDate.now(), localTimeObje);
                horaFin = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal1);
            } else if (medicoDto.getEspacios() == 3) {
                LocalDateTime horaCitaLocal = LocalDateTime.of(LocalDate.now(), localTimeObj);
                horaInicio = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal);
                LocalTime localTimeObje;
                if (localTimeObj.getMinute() == 40) {
                    if (localTimeObj.getHour() == 23) {
                        localTimeObje = localTimeObj.withHour(0).withMinute(0);
                    } else {
                        localTimeObje = localTimeObj.withHour(localTimeObj.getHour() + 1).withMinute(0);
                    }

                } else {
                    localTimeObje = localTimeObj.withMinute(localTimeObj.getMinute() + 20);
                }
                LocalDateTime horaCitaLocal1 = LocalDateTime.of(LocalDate.now(), localTimeObje);
                horaFin = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal1);
            } else if (medicoDto.getEspacios() == 4) {
                LocalDateTime horaCitaLocal = LocalDateTime.of(LocalDate.now(), localTimeObj);
                horaInicio = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal);
                LocalTime localTimeObje;
                if (localTimeObj.getMinute() == 45) {
                    if (localTimeObj.getHour() == 23) {
                        localTimeObje = localTimeObj.withHour(0).withMinute(0);
                    } else {
                        localTimeObje = localTimeObj.withHour(localTimeObj.getHour() + 1).withMinute(0);
                    }
                } else {
                    localTimeObje = localTimeObj.withMinute(localTimeObj.getMinute() + 15);
                }
                LocalDateTime horaCitaLocal1 = LocalDateTime.of(LocalDate.now(), localTimeObje);
                horaFin = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaCitaLocal1);
            }

            Long version = new Long(1);
            espacioDto = new EspacioDto(null, horaFin, horaInicio, version, citaDto, agendaDto);
            resp = service.guardarEspacio(espacioDto);
            System.out.println("Respuesta " + resp.getMensaje());
            //agendaDto.getEspacioList().add(espacioDto); GENERA UN LOOP INFINITO; NI IDEA DE PORQUE, HAY QUE VER OTRA FORMA DE ACTUALIZAR
            vCita.setBackground(Background.EMPTY);
            vCita.setStyle(style);
            vCita.AgregarCita((EspacioDto) resp.getResultado("Espacio"));
            vCita.getChildren().add(((vistaCita) vCita).get((medicoDto.getEspacios() == 2) ? 450 : (medicoDto.getEspacios() == 1) ? 950 : (medicoDto.getEspacios() == 3) ? 280 : 200));
        });

        AppContext.getInstance().set("aux", aux);
        AppContext.getInstance().set("CitaDto", citaDto);

        correo = new Correos();
        paciente = (PacienteDto) AppContext.getInstance().get("PacienteDto");
        correo.CorreoCitaHilo(this.txtCorreo.getText());
        FlowController.getInstance().goViewInWindowModalCorreo("VistaCargando", this.getStage(), false);
        AppContext.getInstance().delete("aux");
        resp = correo.getResp();
        if (resp.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Envío de Correo", this.getStage(), "Correo enviado exitosamente");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Envío de Correo", this.getStage(), "Hubo un error al enviar el correo");
        }
        limpiarValores();
        FlowController.getInstance().initialize();
        //initialize();
    }

    @FXML
    private void editar(ActionEvent event) {

        if (registroCorrecto()) {
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String motivo = txtmotivo.getText();
            Long version = new Long(1) + 1;
            if (AppContext.getInstance().get("Espacio") != null) {
                estado1 = (btnProgramada.isSelected()) ? "PR" : (btnAtendida.isSelected()) ? "AT" : (btnAusente.isSelected()) ? "AU" : "CA";
            } else {
                estado1 = "PR";
            }
            String correoEnviado = espacioDto.getEspCita().getCorreoEnviado();
            //Obtengo el primer el Hbox que contiene el Label con la hora

            citaDto = new CitaDto(espacioDto.getEspCita().getID(), version, pacienteDto, motivo, estado1, telefono, correo, correoEnviado);
            resp = citaService.guardarCita(citaDto);
            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de Edición", this.getStage(), resp.getMensaje());
            limpiarValores();
            this.getStage().close();
            /*try {
                if (estado1 != "CA") {
                    resp = citaService.guardarCita(citaDto);
                }else{
                    //resp = new EspacioService().eliminarEspacio(espacioDto.getEspId());
                    resp = citaService.eliminarCita(espacioDto.getEspCita().getID());
                }
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de Edición", this.getStage(), resp.getMensaje());
                limpiarValores();
                this.getStage().close();
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de editar el usuario.");
            }*/
//            try {
//                switch (estado) {
//                    case "AT": {
//                        String style = "-fx-background-color: #8cff8c; ";
//                        ValidarEspacios(style);
//                        break;
//                    }
//                    case "CA": {
//                        String style = "-fx-background-color: #fa7a7a";
//                        ValidarEspacios(style);
//                        break;
//                    }
//                    case "PR": {
//                        String style = "-fx-background-color: #fad655";
//                        ValidarEspacios(style);
//                        break;
//                    }
//                    case "AU": {
//                        String style = "-fx-background-color: #bdbdbd";
//                        ValidarEspacios(style);
//                        break;
//                    }
//                    default:
//                        break;
//                }
//
//                limpiarValores();
//                AppContext.getInstance().set("hBox", null);
//                FlowController.getInstance().initialize();
//                this.getStage().close();
//            } catch (Exception e) {
//                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar la cita..." + e.getMessage());
//            }
        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Faltan datos por ingresar");
        }

    }

    @FXML
    private void agregarPaciente(ActionEvent event) {
        FlowController.getInstance().goViewInStage("AgregarPaciente", this.getStage());
    }

}
