/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.ExpedienteDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.service.ExpedienteService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
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
public class AgregarExpedienteController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private Label lblGenero11;
    @FXML
    private Label lblGenero;
    @FXML
    private JFXRadioButton btnSiHospitalizaciones;
    @FXML
    private ToggleGroup Hospitalizacion;
    @FXML
    private JFXRadioButton btnNoHospitalizaciones;
    @FXML
    private JFXRadioButton btnSiAlergias;
    @FXML
    private ToggleGroup Alergias;
    @FXML
    private JFXRadioButton btnNoAlergias;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private Label lblGenero1;
    @FXML
    private JFXRadioButton btnSiOperaciones;
    @FXML
    private ToggleGroup Operaciones;
    @FXML
    private JFXRadioButton btnNoOperaciones;
    @FXML
    private JFXRadioButton btnSiTratamientos;
    @FXML
    private JFXRadioButton btnNoTratamientos;
    @FXML
    private JFXTextArea txtTratamientos;
    @FXML
    private JFXTextArea txtAlergias;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextArea txtOperaciones;
    private Mensaje ms;
    private ExpedienteDto expedienteDto;
    private ExpedienteService expedienteService;
    private PacienteDto paciente;
    @FXML
    private JFXTextArea txtAntedecentes;
    private Respuesta resp = new Respuesta();
    @FXML
    private ToggleGroup TratamientosToggle;
    
    
    @FXML
    private void limpiarRegistro(ActionEvent event) {
        Limpiar();
    }
    
    private void Limpiar(){
        this.txtAlergias.clear();
        this.txtOperaciones.clear();
        this.txtTratamientos.clear();
        this.btnNoAlergias.setSelected(false);
        this.btnNoHospitalizaciones.setSelected(false);
        this.btnNoOperaciones.setSelected(false);
        this.btnNoTratamientos.setSelected(false);
        this.btnSiAlergias.setSelected(false);
        this.btnSiHospitalizaciones.setSelected(false);
        this.btnSiOperaciones.setSelected(false);
        this.btnSiTratamientos.setSelected(false);
    }

    @Override
    public void initialize() {
        expedienteDto = new ExpedienteDto();
        expedienteService = new ExpedienteService();
        paciente = (PacienteDto) AppContext.getInstance().get("Paciente");
        ms = new Mensaje();

        btnNoAlergias.setSelected(true);
        btnNoHospitalizaciones.setSelected(true);
        btnNoOperaciones.setSelected(true);
        btnNoTratamientos.setSelected(true);
    }

    @FXML
    private void guardar(ActionEvent event) {
        if(registroCorrecto()){
            try{
            String alergias = txtAlergias.getText();
            String operaciones = txtOperaciones.getText();
            String tratamientos = txtTratamientos.getText();
            String hospitalizacion = (btnSiHospitalizaciones.isSelected()?"S":"N");
            String antecedente = txtAntedecentes.getText();
            Long version = new Long(1);
            expedienteDto = new ExpedienteDto(null, version,antecedente, hospitalizacion, operaciones, alergias, tratamientos, null);
            
            AppContext.getInstance().set("Expediente", expedienteDto);
            //resp = expedienteService.guardarExpediente(expedienteDto);
            Limpiar();
            this.getStage().close();
            }catch(Exception e){
                ms.showModal(Alert.AlertType.ERROR, "Información de guardado", this.getStage(), "Error al guardar el expediente");
            }
        }else{
            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Existen datos en el registro sin completar.");
        }
    }
    
    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().initialize();
        this.getStage().close();
    }
    
    boolean registroCorrecto() {
        boolean Value1 = true;
        boolean Value2 = true;
        boolean Value3 = true;
        //va a evuluar que estén seleccionadas todos los radiobutton y además que si un botón está en sí, que tenga que escribir una descripción
        if( (btnNoAlergias.isSelected() || btnSiAlergias.isSelected())
                && (btnNoHospitalizaciones.isSelected() || btnSiHospitalizaciones.isSelected()) 
                && (btnNoOperaciones.isSelected() || btnSiOperaciones.isSelected())
                && (btnNoTratamientos.isSelected() || btnSiTratamientos.isSelected())){
            if(btnSiAlergias.isSelected()){
                Value1 = false;
                Value1 = !txtAlergias.getText().isEmpty();
            }
            if(btnSiOperaciones.isSelected()){
                Value2 = false;
                Value2 = !txtOperaciones.getText().isEmpty();
            }
            if(btnSiTratamientos.isSelected()){
                Value3 = false;
                Value3 = !txtTratamientos.getText().isEmpty();
            }
        }
        return (btnNoAlergias.isSelected() || btnSiAlergias.isSelected())
                && (btnNoHospitalizaciones.isSelected() || btnSiHospitalizaciones.isSelected()) 
                && (btnNoOperaciones.isSelected() || btnSiOperaciones.isSelected())
                && (btnNoTratamientos.isSelected() || btnSiTratamientos.isSelected())
                && Value1 && Value2 && Value3 && !txtAntedecentes.getText().isEmpty();
    }
    
}
