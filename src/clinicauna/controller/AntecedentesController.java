/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AntecedenteDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AntecedenteService;
import clinicauna.service.MedicoService;
import clinicauna.service.UsuarioService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class AntecedentesController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private TableColumn<AntecedenteDto, String> COL_PARENTESCO_ANT;
    @FXML
    private TableColumn<AntecedenteDto, String> COL_ENFERMEDAD_ANT;
    @FXML
    private TableView<AntecedenteDto> table;
    private ObservableList items;
    private AntecedenteService antecedenteService;
    private Mensaje ms;
    private Respuesta resp;
    private ArrayList<AntecedenteDto> antecedentesList;
    @FXML
    private JFXTextField txtParentesco;
    @FXML
    private JFXTextField txtEnfermedad;
    private AntecedenteDto antecedenteDto;
    private PacienteDto paciente;
    @Override
    public void initialize() {
        
        paciente = (PacienteDto) AppContext.getInstance().get("Paciente");
        antecedenteService = new AntecedenteService();
        antecedenteDto = new AntecedenteDto();
        ms = new Mensaje();
        resp = antecedenteService.getAntecedentes();
        antecedentesList = ((ArrayList<AntecedenteDto>) resp.getResultado("Antecedentes"));

        COL_PARENTESCO_ANT.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAntParentezco()));
        COL_ENFERMEDAD_ANT.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAntEnfermedad()));
        items = FXCollections.observableArrayList(antecedentesList);
        table.setItems(items);
        

    }

    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().initialize();
        this.getStage().close();
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        txtEnfermedad.clear();
        txtParentesco.clear();
        table.getSelectionModel().clearSelection();
    }

    boolean RegistroCorrecto(){
        return !txtEnfermedad.getText().isEmpty() && !txtParentesco.getText().isEmpty();
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
