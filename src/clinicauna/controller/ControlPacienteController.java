/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AntecedenteDto;
import clinicauna.model.ControlDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.PacienteDto;
import clinicauna.service.ControlService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class ControlPacienteController extends Controller {

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
    private JFXTextField txtTratamiento;
    @FXML
    private JFXTextField txtTalla;
    @FXML
    private JFXTextField txtTemperatura;
    @FXML
    private JFXTextField txtIndiceMasaCorporal;
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
    private Respuesta resp;
    private ControlService controlService;
    private Mensaje ms;
    private ControlDto controlDto;
    private ExpedienteDto expedienteDto;
    private PacienteDto pacienteDto;

    @Override
    public void initialize() {

        pacienteDto = (PacienteDto) AppContext.getInstance().get("Paciente");
        expedienteDto = (ExpedienteDto) AppContext.getInstance().get("Expediente");
        controlService = new ControlService();
        resp = controlService.getControles();
        ms = new Mensaje();
        controlDto = new ControlDto();
        controles = ((ArrayList<ControlDto>) resp.getResultado("Controles"));
        COL_FECHA_CONTROL.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCntFecha().toString()));
        COL_HORA_CONTROL.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCntHora()));
        items = FXCollections.observableArrayList(controles);
        table.setItems(items);
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
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {

        if (RegistroCorrecto()) {
            String anotaciones = txtAnotaciones.getText();
            String Examen = txtExamenFisico.getText();
            Double frecuenciaCardiaca = Double.parseDouble(txtFrecuenciaCardiaca.getText());
            Double indiceMasaCoportal = Double.parseDouble(txtIndiceMasaCorporal.getText());
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
            
            LocalDate fecha = Fecha.getValue();
            Long version = new Long(1);
            controlDto = new ControlDto(null, fecha, hora, presion, frecuenciaCardiaca, peso, talla, temperatura, indiceMasaCoportal, anotaciones, razon, planAtencion, observaciones, Examen, tratamiento, version, expedienteDto);
            try {
                resp = controlService.guardarControl(controlDto);

                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarRegistro();
                controles = ((ArrayList<ControlDto>) resp.getResultado("Controles"));
                table.getItems().clear();
                items = FXCollections.observableArrayList(controles);
                table.setItems(items);
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
            }

        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                    + "verifica que todos los datos esten llenos.");
        }

    }

    private void limpiarRegistro() {
        txtAnotaciones.clear();
        txtExamenFisico.clear();
        txtFrecuenciaCardiaca.clear();
        txtIndiceMasaCorporal.clear();
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
                && !txtIndiceMasaCorporal.getText().isEmpty() && !txtObservaciones.getText().isEmpty() && !txtPeso.getText().isEmpty()
                && !txtPlanAtencion.getText().isEmpty() && !txtPresion.getText().isEmpty() && !txtRazonConsulta.getText().isEmpty()
                && !txtTalla.getText().isEmpty() && !txtTemperatura.getText().isEmpty() && !txtTratamiento.getText().isEmpty()
                && Fecha.getValue() != null && Hora.getValue() != null;
    }

}
