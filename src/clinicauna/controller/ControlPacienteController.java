/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.ControlDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.ControlService;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class ControlPacienteController extends Controller implements Initializable {

    @FXML
    private Label Titulo;
    @FXML
    private JFXTextField txtPresion;
    @FXML
    private JFXTextField txtFrecuenciaCardiaca;
    @FXML
    private JFXTextField txtPeso;
    @FXML
    private JFXDatePicker Fecha;
    @FXML
    private JFXTimePicker Hora;
    @FXML
    private JFXTextArea txtTratamiento;
    @FXML
    private JFXTextField txtTalla;
    @FXML
    private JFXTextField txtTemperatura;
    @FXML
    private JFXTextArea txtAnotaciones;
    @FXML
    private JFXTextArea txtRazonConsulta;
    @FXML
    private JFXTextArea txtPlanAtencion;
    @FXML
    private JFXTextArea txtObservaciones;
    @FXML
    private JFXTextArea txtExamenFisico;
    @FXML
    private TableView<ControlDto> table;
    @FXML
    private TableColumn<ControlDto, String> COL_FECHA_CONTROL;
    @FXML
    private TableColumn<ControlDto, String> COL_HORA_CONTROL;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnGuardar;
    private ObservableList items;
    private ArrayList<ControlDto> controles;
    private ArrayList<ControlDto> controles2;
    private Respuesta resp;
    private ControlService controlService;
    private Mensaje ms;
    private ControlDto controlDto;
    private ExpedienteDto expedienteDto;
    private PacienteDto pacienteDto;
    @FXML
    private Label lblPaciente;
    private UsuarioDto usuario;
    private MedicoDto medicoDto;
    private MedicoService medicoService;
    private ArrayList<MedicoDto> lista;
    @FXML
    private JFXComboBox<String> ComboMedico;
    @FXML
    private Label lblMedico;
    private UsuarioDto usuarioActivo;
    private Idioma idioma;
    @FXML
    private Label lblPacienteA;
    @FXML
    private JFXButton btnVolver;

    @Override
    public void initialize() {
        usuarioActivo = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        if (usuarioActivo.getIdioma().equals("I")) {
            this.COL_FECHA_CONTROL.setText(idioma.getProperty("Fecha"));
            this.COL_HORA_CONTROL.setText(idioma.getProperty("Hora"));
            this.lblMedico.setText(idioma.getProperty("MedicoA"));
            this.lblPacienteA.setText(idioma.getProperty("Paciente"));
            this.ComboMedico.setPromptText(idioma.getProperty("Seleccione") + " " + idioma.getProperty("un") + " " + idioma.getProperty("Medico"));
            this.Titulo.setText(idioma.getProperty("PacienteA") + " " + "Control");
            this.txtPeso.setPromptText(idioma.getProperty("Peso"));
            this.txtPresion.setPromptText(idioma.getProperty("Presion"));
            this.txtTalla.setPromptText(idioma.getProperty("Altura"));
            this.txtAnotaciones.setPromptText(idioma.getProperty("Anotaciones"));
            this.txtObservaciones.setPromptText(idioma.getProperty("Observaciones"));
            this.txtTemperatura.setPromptText(idioma.getProperty("Temperatura"));
            this.txtTratamiento.setPromptText(idioma.getProperty("Tratamiento"));
            this.txtRazonConsulta.setPromptText(idioma.getProperty("Razon") + " " + idioma.getProperty("de") + " " + idioma.getProperty("la") + " " + idioma.getProperty("Consulta"));
            this.txtExamenFisico.setPromptText(idioma.getProperty("Fisico") + " " + idioma.getProperty("CExamen"));
            this.txtPlanAtencion.setPromptText(idioma.getProperty("Atencion") + " " + "Plan");
            this.txtFrecuenciaCardiaca.setPromptText(idioma.getProperty("Frecuencia") + " " + idioma.getProperty("Cardiaca"));
            this.Fecha.setPromptText(idioma.getProperty("Fecha"));
            this.Hora.setPromptText(idioma.getProperty("Hora"));
            this.btnEditar.setText(idioma.getProperty("Editar"));
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.btnVolver.setText(idioma.getProperty("Volver"));

        }
        ms = new Mensaje();
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        controles2 = new ArrayList();
        expedienteDto = (ExpedienteDto) AppContext.getInstance().get("Expediente");
        pacienteDto = (PacienteDto) AppContext.getInstance().get("Pact");
        controlService = new ControlService();
        controlDto = new ControlDto();
        ms = new Mensaje();
        resp = controlService.getControles();
        controles = ((ArrayList<ControlDto>) resp.getResultado("controles"));
        controles.stream().filter(x -> x.getCntExpediente().getExpID().equals(expedienteDto.getExpID())).forEach(x -> {
            controles2.add(x);
        });
        COL_FECHA_CONTROL.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCntFecha().toString()));
        COL_HORA_CONTROL.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCntHora()));
        items = FXCollections.observableArrayList(controles2);
        table.setItems(items);
        Formato();
        lblPaciente.setText(expedienteDto.getPaciente().getNombre() + " " + expedienteDto.getPaciente().getpApellido() + " " + expedienteDto.getPaciente().getsApellido());
        if (usuario.getTipoUsuario().equals("M")) {
            lblMedico.setVisible(false);
            ComboMedico.setVisible(false);
            medicoService = new MedicoService();
            resp = medicoService.getMedicos();
            lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
            medicoDto = lista.stream().filter(x -> x.getUs().getID().equals(usuario.getID())).findAny().get();
            AppContext.getInstance().set("MedicoDto", medicoDto);
        } else if (usuario.getTipoUsuario().equals("A")) {
            txtAnotaciones.setDisable(true);
            txtExamenFisico.setDisable(true);
            txtFrecuenciaCardiaca.setDisable(true);
            txtObservaciones.setDisable(true);
            txtPeso.setDisable(true);
            txtPlanAtencion.setDisable(true);
            txtPresion.setDisable(true);
            txtRazonConsulta.setDisable(true);
            txtTalla.setDisable(true);
            txtTemperatura.setDisable(true);
            txtTratamiento.setDisable(true);
            Fecha.setDisable(true);
            Hora.setDisable(true);
            medicoService = new MedicoService();
            resp = medicoService.getMedicos();
            lista = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
            items = FXCollections.observableArrayList(lista.stream().map(x -> x.getUs().getNombre()
                    + " " + x.getUs().getpApellido() + " " + x.getUs().getsApellido() + " Ced:" + x.getUs().getCedula())
                    .collect(Collectors.toList()));
            ComboMedico.setItems(items);
            //medicoDto = lista.stream().filter(x -> x.getUs().getID().equals(usuario.getID())).findAny().get();
            //AppContext.getInstance().set("MedicoDto", medicoDto);
            //ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Debe de seleccinar un médico para poder continuar");
        }
    }

    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().goView("ExpedienteMedico");
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        limpiarRegistro();
    }

    @FXML
    private void editar(ActionEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (RegistroCorrecto()) {
                    Double presion = Double.parseDouble(txtPresion.getText());
                    Double frecueciaCardiaca = Double.parseDouble(txtFrecuenciaCardiaca.getText());
                    Double peso = Double.parseDouble(txtPeso.getText());
                    Double talla = Double.parseDouble(txtTalla.getText());
                    Double temperatura = Double.parseDouble(txtTemperatura.getText());
                    String tratamiento = txtTratamiento.getText();
                    String Anotaciones = txtAnotaciones.getText();
                    String razon = txtRazonConsulta.getText();
                    String plan = txtPlanAtencion.getText();
                    String observacion = txtObservaciones.getText();
                    String examen = txtExamenFisico.getText();
                    LocalTime hora1 = Hora.getValue();
                    LocalDateTime hora = LocalDateTime.of(LocalDate.now(), hora1);
                    String horaC = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(hora);
                    LocalDate fecha = Fecha.getValue();
                    Long id = table.getSelectionModel().getSelectedItem().getCntId();
                    Long version = table.getSelectionModel().getSelectedItem().getCntVersion() + 1;
                    Double imc = peso / Math.pow(talla, 2);

                    controlDto = new ControlDto(id, fecha, horaC, presion, frecueciaCardiaca, peso, talla, temperatura, imc, Anotaciones,
                            razon, plan, observacion, examen, tratamiento, version, expedienteDto);
                    try {
                        resp = controlService.guardarControl(controlDto);
                        if (usuarioActivo.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                        limpiarRegistro();
                        controles = (ArrayList) controlService.getControles().getResultado("controles");
                        controles2.clear();
                        controles.stream().filter(x -> x.getCntExpediente().getExpID().equals(expedienteDto.getExpID())).forEach(x -> {
                            controles2.add(x);
                        });
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(controles2);
                        table.setItems(items);
                    } catch (Exception e) {
                        if (usuarioActivo.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the control");
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el control");
                        }
                    }
                } else {
                    if (usuarioActivo.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "There are erroneous data in the registry, "
                                + "Verify that all data is full");
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                                + "verifica que todos los datos esten llenos.");
                    }
                }
            } else {
                if (usuarioActivo.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the control");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
                }
            }
        } else {
            if (usuarioActivo.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the control");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
            }
        }
    }

    @FXML
    private void guardar(ActionEvent event) {

        if (RegistroCorrecto()) {
            String anotaciones = txtAnotaciones.getText();
            String Examen = txtExamenFisico.getText();
            Double frecuenciaCardiaca = Double.parseDouble(txtFrecuenciaCardiaca.getText());
            String observaciones = txtObservaciones.getText();
            Double peso = Double.parseDouble(txtPeso.getText());
            String planAtencion = txtPlanAtencion.getText();
            Double presion = Double.parseDouble(txtPresion.getText());
            String razon = txtRazonConsulta.getText();
            Double talla = Double.parseDouble(txtTalla.getText());
            Double temperatura = Double.parseDouble(txtTemperatura.getText());
            String tratamiento = txtTratamiento.getText();
            LocalDateTime horaLocal = LocalDateTime.of(LocalDate.now(), Hora.getValue());
            String hora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(horaLocal);
            Double imc = peso / Math.pow(talla, 2);
            LocalDate fecha = Fecha.getValue();
            Long version = new Long(1);
            controlDto = new ControlDto(null, fecha, hora, presion, frecuenciaCardiaca, peso, talla, temperatura, imc, anotaciones, razon, planAtencion, observaciones, Examen, tratamiento, version, expedienteDto);
            AppContext.getInstance().set("Control", controlDto);
            Correos correo = new Correos();

            correo.CorreoControlHilo(pacienteDto.getCorreo());
            FlowController.getInstance().goViewInWindowModalCorreo("VistaCargando", this.getStage(), false);
            resp = correo.getResp();
            if (resp.getEstado()) {
                if (usuarioActivo.getIdioma().equals("I")) {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Send Mail", this.getStage(), "Mail sent successfully");
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Envío de Correo", this.getStage(), "Correo enviado exitosamente");
                }
            } else {
                if (usuarioActivo.getIdioma().equals("I")) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Send Mail", this.getStage(), "There was an error sending the mail");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Envío de Correo", this.getStage(), "Hubo un error al enviar el correo");
                }
            }
            resp = controlService.guardarControl(controlDto);
            if (resp.getEstado()) {
                if (usuarioActivo.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                } else {
                    ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                }
                limpiarRegistro();
                controles = (ArrayList) controlService.getControles().getResultado("controles");
                controles2.clear();
                controles.stream().filter(x -> x.getCntExpediente().getExpID().equals(expedienteDto.getExpID())).forEach(x -> {
                    controles2.add(x);
                });
                table.getItems().clear();
                items = FXCollections.observableArrayList(controles2);
                table.setItems(items);
            } else {
                if (usuarioActivo.getIdioma().equals("I")) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Send Mail", this.getStage(), "There was an error sending the mail");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Envío de Correo", this.getStage(), "Hubo un error al enviar el correo");
                }
            }
        } else {
            if (usuarioActivo.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "There are erroneous data in the registry, "
                        + "Verify that all data is full");
            } else {
                ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                        + "verifica que todos los datos esten llenos.");
            }
        }

    }

    private void limpiarRegistro() {
        btnGuardar.setDisable(false);
        txtAnotaciones.clear();
        txtExamenFisico.clear();
        txtFrecuenciaCardiaca.clear();
        txtObservaciones.clear();
        txtPeso.clear();
        txtPlanAtencion.clear();
        txtPresion.clear();
        txtRazonConsulta.clear();
        txtTalla.clear();
        txtTemperatura.clear();
        txtTratamiento.clear();
        Fecha.setValue(null);
        Hora.setValue(null);
    }

    boolean RegistroCorrecto() {
        return !txtAnotaciones.getText().isEmpty() && !txtExamenFisico.getText().isEmpty() && !txtFrecuenciaCardiaca.getText().isEmpty()
                && !txtObservaciones.getText().isEmpty() && !txtPeso.getText().isEmpty()
                && !txtPlanAtencion.getText().isEmpty() && !txtPresion.getText().isEmpty() && !txtRazonConsulta.getText().isEmpty()
                && !txtTalla.getText().isEmpty() && !txtTemperatura.getText().isEmpty() && !txtTratamiento.getText().isEmpty()
                && Fecha.getValue() != null && Hora.getValue() != null;
    }

    public void Formato() {
        this.txtAnotaciones.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtExamenFisico.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtFrecuenciaCardiaca.setTextFormatter(Formato.getInstance().twoDecimalFormat(5));
        this.txtObservaciones.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtPeso.setTextFormatter(Formato.getInstance().twoDecimalFormat(5));
        this.txtPlanAtencion.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtPresion.setTextFormatter(Formato.getInstance().twoDecimalFormat(5));
        this.txtRazonConsulta.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtTalla.setTextFormatter(Formato.getInstance().twoDecimalFormat(5));
        this.txtTemperatura.setTextFormatter(Formato.getInstance().twoDecimalFormat(5));
        this.txtTratamiento.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
    }

    @FXML
    private void DatosControl(MouseEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (ComboMedico.getValue() != null) {
                    btnGuardar.setDisable(true);
                    controlDto = table.getSelectionModel().getSelectedItem();
                    txtAnotaciones.setText(controlDto.getCntAnotacionEnfermeria());
                    txtExamenFisico.setText(controlDto.getCntExamenFisico());
                    txtFrecuenciaCardiaca.setText(controlDto.getCntFrecuenciaCardiaca().toString());
                    txtObservaciones.setText(controlDto.getCntObservaciones());
                    txtPeso.setText(controlDto.getCntPeso().toString());
                    txtPlanAtencion.setText(controlDto.getCntPlanAtencion());
                    txtPresion.setText(controlDto.getCntPresion().toString());
                    txtRazonConsulta.setText(controlDto.getCntRazonConsulta());
                    txtTalla.setText(controlDto.getCntTalla().toString());
                    txtTemperatura.setText(controlDto.getCntTemperatura().toString());
                    txtTratamiento.setText(controlDto.getCntTratamiento());
                    Fecha.setValue(controlDto.getCntFecha());
                    LocalTime localTimeObj1 = LocalTime.parse(controlDto.getCntHora());
                    Hora.setValue(localTimeObj1);
                }else{
                    ms.showModal(Alert.AlertType.ERROR, "Error cargando el controls", this.getStage(), "Debe de seleccionar un médico");
                }
            }
        }
    }
    private static boolean cedulaEncontrada = false;
    private static String cedulaBuscar = "";

    @FXML
    private void seleccionarMedico(ActionEvent event) {
        SeleccionarMedico();
    }

    public void SeleccionarMedico() {
        if (ComboMedico.getSelectionModel() != null && ComboMedico.getSelectionModel().getSelectedItem() != null) {
            //buscamos el médico a partir de su cédula
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
            txtAnotaciones.setDisable(false);
            txtExamenFisico.setDisable(false);
            txtFrecuenciaCardiaca.setDisable(false);
            txtObservaciones.setDisable(false);
            txtPeso.setDisable(false);
            txtPlanAtencion.setDisable(false);
            txtPresion.setDisable(false);
            txtRazonConsulta.setDisable(false);
            txtTalla.setDisable(false);
            txtTemperatura.setDisable(false);
            txtTratamiento.setDisable(false);
            Fecha.setDisable(false);
            Hora.setDisable(false);
            cedulaBuscar = "";
            cedulaEncontrada = false;
            System.out.println(medicoDto);
            AppContext.getInstance().set("MedicoDto", medicoDto);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
