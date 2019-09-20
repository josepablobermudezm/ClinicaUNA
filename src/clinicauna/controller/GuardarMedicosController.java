/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class GuardarMedicosController extends Controller {
    
    @FXML
    private JFXTextField txtFolio;
    @FXML
    private JFXTextField txtCarne;
    @FXML
    private JFXTimePicker timePickerInicio;
    @FXML
    private JFXTimePicker timePickerfinal;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtEspacio;
    @FXML
    private Label Titulo;
    @FXML
    private JFXButton btnGuardar;
    private Idioma idioma;
    private UsuarioDto usuario;
    /**
     * Initializes the controller class.
     */
    private MedicoService medicoService;
    private Mensaje ms;
    private Respuesta resp;
    private MedicoDto medicoDto;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton BtnCancelar;
    
    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }
    
    @FXML
    private void guardar(ActionEvent event) {
        if (registroCorrecto()) {
            String carne = txtCarne.getText();
            String codigo = txtCodigo.getText();
            String folio = txtFolio.getText();
            Integer espacios = Integer.parseInt(txtEspacio.getText());
            LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), timePickerInicio.getValue());
            LocalDateTime final12 = LocalDateTime.of(LocalDate.now(), timePickerfinal.getValue());
            String inicioJornada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
            String finJornada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(final12);
            //LocalDateTime final2 = LocalDateTime.of(LocalDate.now(),final1);
            Long version = new Long(1);
            medicoDto = new MedicoDto(null, codigo, folio, carne, "A", inicioJornada, finJornada, espacios, null, version);
            AppContext.getInstance().set("Medico", medicoDto);
            this.getStage().close();
        } else {
            new Mensaje().show(Alert.AlertType.WARNING, "Información de Registro", "Existen datos en el registro sin completar.");
        }
        
    }
    
    boolean registroCorrecto() {
        return !txtCarne.getText().isEmpty() && !txtCodigo.getText().isEmpty()
                && !txtEspacio.getText().isEmpty() && !txtFolio.getText().isEmpty()
                && timePickerInicio.getValue() != null && timePickerfinal.getValue() != null;
    }
    
    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().initialize();
        this.getStage().close();
    }
    
    @Override
    public void initialize() {
        medicoService = new MedicoService();
        ms = new Mensaje();
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.BtnCancelar.setText(idioma.getProperty("Cancelar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.Titulo.setText(idioma.getProperty("Agregar") + " " + idioma.getProperty("Medico"));
            this.txtCarne.setPromptText(idioma.getProperty("Carné"));
            this.txtCodigo.setPromptText(idioma.getProperty("Código"));
            this.txtEspacio.setPromptText(idioma.getProperty("Espacio") + " " + idioma.getProperty("por") + " " + idioma.getProperty("Hora"));
            this.timePickerInicio.setPromptText(idioma.getProperty("Inicio") + " " + idioma.getProperty("Jornada"));
            this.timePickerfinal.setPromptText(idioma.getProperty("Final") + " " + idioma.getProperty("Jornada"));
            
        }
    }
    
}
