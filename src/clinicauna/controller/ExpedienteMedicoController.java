/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
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
    private TableView<PacienteDto> table;
    @FXML
    private TableColumn<PacienteDto, String> COL_NOMBRE_PAC;
    @FXML
    private TableColumn<PacienteDto, String> COL_PAPELLIDO_PAC;
    @FXML
    private TableColumn<PacienteDto, String> COL_SAPELLIDO_PAC;
    @FXML
    private TableColumn<PacienteDto, String> COL_CEDULA_PAC;
    @FXML
    private TableColumn<PacienteDto, String> COL_CORREO_PAC;
    @FXML
    private TableColumn<PacienteDto, String> COL_GENERO_PAC;
    @FXML
    private TableColumn<PacienteDto, String> COL_FECHANACIMIENTO_PAC;
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
    private PacienteService pacienteService = new PacienteService();
    private Mensaje ms = new Mensaje();
    private ArrayList<PacienteDto> pacientes;
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
    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.Titulo.setText(idioma.getProperty("MedExp") + " " + idioma.getProperty("Expediente"));
        }

        pacienteService = new PacienteService();
        ms = new Mensaje();
        resp = pacienteService.getPacientes();
        pacientes = ((ArrayList<PacienteDto>) resp.getResultado("Pacientes"));

        COL_NOMBRE_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre()));
        COL_PAPELLIDO_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getpApellido()));
        COL_SAPELLIDO_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getsApellido()));
        COL_CEDULA_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
        COL_CORREO_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCorreo()));
        COL_GENERO_PAC.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getGenero()));
        COL_FECHANACIMIENTO_PAC.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getFechaNacimiento() != null) ? value.getValue().getFechaNacimiento().toString() : "NULO"));

        items = FXCollections.observableArrayList(pacientes);
        table.setItems(items);
        
    }

    @FXML
    private void DatosPaciente(MouseEvent event) {
        //Cargar los datos que se guardaron en la base de datos, en la vista de paciente
        
        
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
