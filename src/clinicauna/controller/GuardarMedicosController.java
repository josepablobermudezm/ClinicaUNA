/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.MedicoService;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Carlos Olivares
 */
public class GuardarMedicosController implements Initializable {

    @FXML
    private JFXTextField txtFolio;
    @FXML
    private JFXTextField txtCarne;
    @FXML
    private JFXTextField txtNombreUsuario;
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

    /**
     * Initializes the controller class.
     */
    private MedicoService medicoService;
    private Mensaje ms;
    private Respuesta resp;
    private MedicoDto medicoDto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        medicoService = new MedicoService();
        ms = new Mensaje();
        //resp = medicoService.getMedico();
        //medicos = ((ArrayList<MedicoDtoDto>) resp.getResultado("Medicos"));

    }

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
            medicoDto = new MedicoDto(null, codigo, folio,"A" ,carne,inicioJornada,finJornada,espacios,null,version);
            
        }

    }

    boolean registroCorrecto() {
        return !txtCarne.getText().isEmpty() && !txtCodigo.getText().isEmpty()
                && !txtEspacio.getText().isEmpty() && !txtFolio.getText().isEmpty()
                && !txtNombreUsuario.getText().isEmpty();
    }

    @FXML
    private void cancela(ActionEvent event) {

    }

}
