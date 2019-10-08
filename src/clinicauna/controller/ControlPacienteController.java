/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> COL_FECHA_CONTROL;
    @FXML
    private TableColumn<?, ?> COL_HORA_CONTROL;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnGuardar;


    @Override
    public void initialize() {
        
    }

    @FXML
    private void cancela(ActionEvent event) {
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
}
