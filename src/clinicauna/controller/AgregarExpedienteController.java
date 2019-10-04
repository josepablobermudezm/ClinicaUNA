/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ToggleGroup Tratamientos;
    @FXML
    private ToggleGroup Tratamiento;
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
    private JFXTextArea txtHospitalizaciones;
    @FXML
    private JFXTextArea txtAlergias;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextArea txtOperaciones;

    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }

    @Override
    public void initialize() {



    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
}
