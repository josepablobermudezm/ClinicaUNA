/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AntecedenteDto;
import clinicauna.model.ExpedienteDto;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

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
    private ArrayList<AntecedenteDto> antecedentesList2;
    @FXML
    private JFXTextField txtParentesco;
    @FXML
    private JFXTextField txtEnfermedad;
    private AntecedenteDto antecedenteDto;
    private PacienteDto paciente;
    private ExpedienteDto expediente;

    @Override
    public void initialize() {
        antecedentesList2 = new ArrayList();
        expediente = (ExpedienteDto) AppContext.getInstance().get("Expediente");
        paciente = (PacienteDto) AppContext.getInstance().get("Paciente");
        antecedenteService = new AntecedenteService();
        antecedenteDto = new AntecedenteDto();
        ms = new Mensaje();
        /*resp = antecedenteService.getAntecedentes();
        antecedentesList = ((ArrayList<AntecedenteDto>) resp.getResultado("Antecedentes"));
        antecedentesList.stream().filter(x->x.getAntExpediente().getExpID().equals(expediente.getExpID())).forEach(x->{
            antecedentesList2.add(x);
        });*/
   
        antecedentesList = expediente.getAntecedentes();
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

    boolean RegistroCorrecto() {
        return !txtEnfermedad.getText().isEmpty() && !txtParentesco.getText().isEmpty();
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    private void limpiarValores() {
        txtEnfermedad.clear();
        txtParentesco.clear();
    }

    @FXML
    private void guardar(ActionEvent event) {

        if (RegistroCorrecto()) {
            String parentesco = txtParentesco.getText();
            String enfermedad = txtEnfermedad.getText();
            Long version = new Long(1);
            antecedenteDto = new AntecedenteDto(null, enfermedad, parentesco, version, expediente);
            try {
                resp = antecedenteService.guardarAntecedente(antecedenteDto);
                
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
                antecedentesList = (ArrayList) antecedenteService.getAntecedentes().getResultado("Antecedentes");
                antecedentesList.stream().filter(x->x.getAntExpediente().getExpID().equals(expediente.getExpID())).forEach(x->{
                    antecedentesList2.add(x);
                });
                table.getItems().clear();
                items = FXCollections.observableArrayList(antecedentesList2);
                table.setItems(items);
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
            }
        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                    + "verifica que todos los datos esten llenos.");
        }
    }

    @FXML
    private void datos(MouseEvent event) {
    }

}
