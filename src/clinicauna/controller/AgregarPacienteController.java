/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.PacienteDto;
import clinicauna.service.PacienteService;
import clinicauna.util.FlowController;
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
    /**
     * Initializes the controller class.
     */ 

    @Override
    public void initialize() {
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
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                            limpiarValores();
                        }else{
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                    }
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
            }
        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Información Incompleta");
        }
    }

    private boolean registroCorrecto(){
        return !txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtPApellido.getText().isEmpty() && !txtSApellido.getText().isEmpty()
                && FechaDeNacimiento.getValue() != null
                && !txtCorreo.getText().isEmpty() && (btnHombre1.isSelected() || btnMujer1.isSelected());
    }
    
    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }
    
    private void limpiarValores(){
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