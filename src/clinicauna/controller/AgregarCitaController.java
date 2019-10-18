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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private Label lblEstado;
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
    private Respuesta resp1;
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

    @Override
    public void initialize() {

        hBox = (vistaCita) AppContext.getInstance().get("hBox");
        grid = (GridPane) AppContext.getInstance().get("Grid");
        medicoDto = (MedicoDto) AppContext.getInstance().get("MedicoDto");
        agendaDto = (AgendaDto) AppContext.getInstance().get("Agenda");
        if (AppContext.getInstance().get("Cita") != null) {
            citaDto = (CitaDto) AppContext.getInstance().get("Cita");
            txtCorreo.setText(citaDto.getCorreo());
            txtTelefono.setText(citaDto.getTelefono());
            txtmotivo.setText(citaDto.getMotivo());

        }

        formato();
        pacienteService = new PacienteService();
        resp = pacienteService.getPacientes();
        ms = new Mensaje();
        citaService = new CitaService();
        resp1 = citaService.getCitas();

        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnAtendida.setText(idioma.getProperty("Atendida"));
            this.btnAusente.setText(idioma.getProperty("Ausente"));
            this.btnCancelada.setText(idioma.getProperty("Cancelada"));
            this.btnCancelar.setText(idioma.getProperty("Cancelar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.btnProgramada.setText(idioma.getProperty("Programada"));
            this.Titulo.setText(idioma.getProperty("Agendar") + " " + idioma.getProperty("ACita"));
            this.ComboPacientes.setPromptText(idioma.getProperty("Pacientes"));
            this.lblEstado.setText(idioma.getProperty("Estado"));
            this.txtCorreo.setPromptText(idioma.getProperty("Correo"));
            this.txtTelefono.setPromptText(idioma.getProperty("Telefono"));
            this.txtmotivo.setPromptText(idioma.getProperty("Motivo"));
        }
        lista = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");
        ObservableList<String> items = FXCollections.observableArrayList(lista.stream().map(x -> x.getNombre()
                + " " + x.getpApellido() + " " + x.getsApellido() + " Ced:" + x.getCedula())
                .collect(Collectors.toList()));
        ComboPacientes.setItems(items);
    }

    @FXML
    private void guardar(ActionEvent event) {

        if (registroCorrecto()) {

            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String motivo = txtmotivo.getText();
            Long version = new Long(1);
            String estado = (btnProgramada.isSelected()) ? "PR" : (btnAtendida.isSelected()) ? "AT" : (btnAusente.isSelected()) ? "AU" : "CA";
            //Obtengo el primer el Hbox que contiene el Label con la hora
            citaDto = new CitaDto(null, version, pacienteDto, motivo, estado, telefono, correo, "N");

            try {
                switch (estado) {
                    case "AT": {
                        //hBox.setBackground(Background.EMPTY);
                        String style = "-fx-background-color: #8cff8c; ";
                        // hBox.setStyle(style);
                        ValidarEspacios(style);
                        break;
                    }
                    case "CA": {
                        // hBox.setBackground(Background.EMPTY);
                        String style = "-fx-background-color: #fa7a7a";
                        // hBox.setStyle(style);
                        ValidarEspacios(style);
                        break;
                    }
                    case "PR": {
                        //hBox.setBackground(Background.EMPTY);
                        String style = "-fx-background-color: #fad655";
                        // hBox.setStyle(style);
                        ValidarEspacios(style);
                        break;
                    }
                    case "AU": {
                        //hBox.setBackground(Background.EMPTY);
                        String style = "-fx-background-color: #bdbdbd";
                        // hBox.setStyle(style);
                        ValidarEspacios(style);
                        break;
                    }
                    default:
                        break;
                }

                limpiarValores();
                AppContext.getInstance().set("hBox", null);
                FlowController.getInstance().initialize();
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
        resp1 = citaService.guardarCita(citaDto);
        citaDto = (CitaDto) resp1.getResultado("Cita");
        EspacioService service = new EspacioService();
        //Seteo el medico con el formato del LocalDateTime a la agenda
        agendaDto.setAgeMedico(medicoDto);
        //Recorro la lista de espacios seleccionados para setearles un color definido
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
            service.guardarEspacio(espacioDto);

            vCita.setBackground(Background.EMPTY);
            vCita.setStyle(style);
            vCita.AgregarCita(espacioDto);
            vCita.getChildren().add(((vistaCita) vCita).get((medicoDto.getEspacios() == 2) ? 450 : (medicoDto.getEspacios() == 1) ? 950 : (medicoDto.getEspacios() == 3) ? 280 : 200));
        });
        
        AppContext.getInstance().set("aux", aux);
        AppContext.getInstance().set("CitaDto", citaDto);

        correo = new Correos();
        paciente = (PacienteDto) AppContext.getInstance().get("PacienteDto");
        correo.CorreoCitaHilo(this.txtCorreo.getText());
        FlowController.getInstance().goViewInWindowModalCorreo("VistaCargando", this.getStage(), false);
        
        
        resp = correo.getResp();
        if (resp.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Envío de Correo", this.getStage(), "Correo enviado exitosamente");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Envío de Correo", this.getStage(), "Hubo un error al enviar el correo");
        }
        limpiarValores();

    }

}
