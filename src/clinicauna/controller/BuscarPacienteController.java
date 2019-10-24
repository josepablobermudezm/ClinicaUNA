/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.PacienteDto;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author JORDI RODRIGUEZ
 */
public class BuscarPacienteController extends Controller {

    @FXML
    private Label lblTitulo;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private TableView<PacienteDto> Table_Buscar;
    @FXML
    private TableColumn<PacienteDto, String> Col_Cedula;
    @FXML
    private TableColumn<PacienteDto, String> Col_Nombre;
    private PacienteService service;
    private ArrayList<PacienteDto> pacientes;
    private ObservableList items;

    @FXML
    private void ReleasedCedula(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void ReleasedApellido(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void ReleasedNombre(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void MostrarDatos(MouseEvent event) {
        if(Table_Buscar.getSelectionModel()!= null && Table_Buscar.getSelectionModel().getSelectedItem()!=null){
            AppContext.getInstance().set("Paciente", Table_Buscar.getSelectionModel().getSelectedItem());
            //PacienteDto paciente = (PacienteDto) AppContext.getInstance().get("Paciente");
            FlowController.getInstance().initialize();
            this.getStage().close();
        }
    }

    @Override
    public void initialize() {
        Col_Nombre.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre() + " " + value.getValue().getpApellido() + " " + value.getValue().getsApellido()));
        Col_Cedula.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
        Buscar();
        //FlowController.getInstance().getController("ExpedienteMedicoController").initialize();
    }

    private void Buscar() {
        String ced = (!txtCedula.getText().isEmpty()) ? "%" + txtCedula.getText().toUpperCase() + "%" : "%";
        String nom = (!txtNombre.getText().isEmpty()) ? "%" + txtNombre.getText().toUpperCase() + "%" : "%";
        String apellido = (!txtApellido.getText().isEmpty()) ? "%" + txtApellido.getText().toUpperCase() + "%" : "%";

        service = new PacienteService();
        Respuesta resp = service.getPacientes(ced, nom, apellido);

        if (resp.getEstado()) {
            pacientes = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");
            items = FXCollections.observableArrayList(pacientes);
            Table_Buscar.setItems(items);
        }else{
            System.out.println(resp.getMensaje());
        }
    }

}
