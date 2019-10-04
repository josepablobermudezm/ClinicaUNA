/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.ExpedienteDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.ExpedienteService;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class ExpedienteMedicoController extends Controller {

    @FXML
    private ImageView omg;
    @FXML
    private Label Titulo;
    private Idioma idioma;
    private UsuarioDto usuario;
    @FXML
    private TableView<ExpedienteDto> table;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_NOMBRE_PAC;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_CEDULA_PAC;
    @FXML
    private Label lblGenero;
    @FXML
    private Label lblGenero1;
    @FXML
    private ToggleGroup Hospitalizacion;
    @FXML
    private Label lblGenero11;
    @FXML
    private ToggleGroup Tratamientos;
    @FXML
    private ToggleGroup Tratamiento;
    @FXML
    private JFXRadioButton btnSiHospitalizaciones;
    @FXML
    private JFXRadioButton btnNoHospitalizaciones;
    @FXML
    private JFXRadioButton btnSiAlergias;
    @FXML
    private ToggleGroup Alergias;
    @FXML
    private JFXRadioButton btnNoAlergias;
    @FXML
    private JFXRadioButton btnSiOperaciones;
    @FXML
    private ToggleGroup Operaciones;
    @FXML
    private JFXRadioButton btnNoOperaciones;
    @FXML
    private JFXButton btnEditar1;
    @FXML
    private JFXButton btnEliminar1;
    @FXML
    private JFXButton btnLimpiarRegistro;
    private ExpedienteService expedienteService = new ExpedienteService();
    private Mensaje ms = new Mensaje();
    private ArrayList<ExpedienteDto> expedientes;
    private Respuesta resp;
    private ObservableList items;
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
    private JFXTextArea txtOperaciones;
    @FXML
    private JFXTextArea txtAntecedentesPatologicos;
    private ExpedienteDto expedienteDto;
    private PacienteDto pacienteDto;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_TRATAMIENTO_PAC;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_OPERACIONES_PAC;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_ALERGIAS_PAC;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_HOSPITALIZACIONESPAC;
    @FXML
    private TableColumn<ExpedienteDto, String> COL_ANTECEDENTES_PATOLOGICOS_PAC;
    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.Titulo.setText(idioma.getProperty("MedExp") + " " + idioma.getProperty("Expediente"));
        }

        expedienteService = new ExpedienteService();
        ms = new Mensaje();
        resp = expedienteService.getExpedientes();
        System.out.println(resp.getResultado("Expedientes"));
        expedientes =  (ArrayList<ExpedienteDto>) resp.getResultado("Expedientes");
        
        COL_NOMBRE_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getPaciente().getNombre()+" "+value.getValue().getPaciente().getpApellido() + " " + value.getValue().getPaciente().getsApellido()));
        COL_TRATAMIENTO_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTratamientos()));
        COL_OPERACIONES_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getOperaciones()));
        COL_CEDULA_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getPaciente().getCedula()));
        COL_ALERGIAS_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAlergias()));
        COL_HOSPITALIZACIONESPAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getHospitalizaciones()));
        COL_ANTECEDENTES_PATOLOGICOS_PAC.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getAntecedentesPatologicos())));

        items = FXCollections.observableArrayList(expedientes);
        table.setItems(items);
        
    }

    @FXML
    private void DatosPaciente(MouseEvent event) {
        //Cargar los datos que se guardaron en la base de datos, en la vista de paciente
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                expedienteDto = table.getSelectionModel().getSelectedItem();
                //txtAlergias.setText();
            }
        }
        
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }

}
