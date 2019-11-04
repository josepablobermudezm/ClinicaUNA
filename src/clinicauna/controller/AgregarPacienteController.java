/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class AgregarPacienteController extends Controller {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXDatePicker FechaDeNacimiento;
    @FXML
    private Label lblGenero;
    @FXML
    private Label Titulo;
    @FXML
    private JFXRadioButton btnHombre1;
    @FXML
    private ToggleGroup genero2;
    @FXML
    private JFXRadioButton btnMujer1;
    private PacienteDto pacienteDto;
    private PacienteService pacienteService;
    private Mensaje ms;
    private Respuesta resp;
    private Idioma idioma;
    private UsuarioDto usuario;
    @FXML
    private JFXButton btnVolver;
    @FXML
    private JFXButton btnLimpiar;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnVolver.setText(idioma.getProperty("Volver"));
            this.btnLimpiar.setText(idioma.getProperty("Limpiar")+" "+ idioma.getProperty("Registro"));
            this.lblGenero.setText(idioma.getProperty("Genero"));
            this.btnHombre1.setText(idioma.getProperty("Masculino"));
            this.btnMujer1.setText(idioma.getProperty("Femenino"));
            this.txtCedula.setPromptText(idioma.getProperty("Cedula"));
            this.txtCorreo.setPromptText(idioma.getProperty("Correo"));
            this.txtNombre.setPromptText(idioma.getProperty("Nombre"));
            this.txtPApellido.setPromptText(idioma.getProperty("Primero") + " " + idioma.getProperty("Apellido"));
            this.txtSApellido.setPromptText(idioma.getProperty("Segundo") + " " + idioma.getProperty("Apellido"));
            this.Titulo.setText(idioma.getProperty("Agregar") + " " + idioma.getProperty("PacienteB"));
        }
        pacienteDto = new PacienteDto();
        pacienteService = new PacienteService();
        ms = new Mensaje();
        resp = new Respuesta();
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (registroCorrecto()) {
            String nombre = txtNombre.getText();
            String papellido = txtPApellido.getText();
            String sapellido = txtSApellido.getText();
            String correo = txtCorreo.getText();
            String cedula = txtCedula.getText();
            String genero1 = (btnHombre1.isSelected()) ? "M" : "F";
            LocalDate fecha = FechaDeNacimiento.getValue();
            //LocalDateTime final2 = LocalDateTime.of(LocalDate.now(),final1);
            Long version = new Long(1);
            pacienteDto = new PacienteDto(null, nombre, papellido, sapellido, cedula, correo, genero1, fecha, version);
            try {
                resp = pacienteService.guardarPaciente(pacienteDto);
                if (resp.getEstado()) {
                    pacienteDto = (PacienteDto) resp.getResultado("Paciente");
                    if (resp.getEstado()) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                        limpiarValores();
                    } else {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                    }
                } else {
                    if (usuario.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                    }
                }
            } catch (Exception e) {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the patient");
                } else {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "Incomplete Data");
            } else {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Información Incompleta");
            }
        }
    }

    private boolean registroCorrecto() {
        return !txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtPApellido.getText().isEmpty() && !txtSApellido.getText().isEmpty()
                && FechaDeNacimiento.getValue() != null
                && !txtCorreo.getText().isEmpty() && (btnHombre1.isSelected() || btnMujer1.isSelected());
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }

    private void limpiarValores() {
        txtNombre.clear();
        txtPApellido.clear();
        txtSApellido.clear();
        txtCorreo.clear();
        txtCedula.clear();
        btnMujer1.setSelected(false);
        btnHombre1.setSelected(false);
        FechaDeNacimiento.setValue(null);
    }

    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().goViewInStage("AgregarCita", this.stage);
    }

}
