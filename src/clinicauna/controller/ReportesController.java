package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AgendaService;
import clinicauna.service.MedicoService;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class ReportesController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private Label Titulo1;
    @FXML
    private JFXDatePicker DateFechaInicio;
    @FXML
    private JFXDatePicker DateFechaFin;
    @FXML
    private JFXButton btnGenerarReporteMed;
    @FXML
    private JFXButton btnGenerarReportePac;
    @FXML
    private Label Titulo11;
    @FXML
    private TableColumn<MedicoDto, String> ColFolMed;
    @FXML
    private TableColumn<MedicoDto, String> ColNomMed;
    @FXML
    private TableView<PacienteDto> tvPaciente;
    @FXML
    private TableColumn<PacienteDto, String> colCedPaciente;
    @FXML
    private TableColumn<PacienteDto, String> colPacNombre;
    @FXML
    private TableView<MedicoDto> tvMedico;
    private PacienteService PacService;
    private MedicoService medService;
    private ArrayList<PacienteDto> pacientes;
    private ArrayList<PacienteDto> medicos;
    private ObservableList itemsMedico;
    private ObservableList itemsPaciente;
    private Idioma idioma;
    private UsuarioDto usuario;
    @FXML
    private JFXTextField txtPaciente;
    @FXML
    private JFXTextField txtMedico;

    @Override
    public void initialize() {
        colPacNombre.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre() + " " + value.getValue().getpApellido() + " " + value.getValue().getsApellido()));
        colCedPaciente.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
        ColNomMed.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getUs().getNombre() + " " + value.getValue().getUs().getpApellido() + " " + value.getValue().getUs().getsApellido()));
        ColFolMed.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFolio()));

        PacService = new PacienteService();
        medService = new MedicoService();
        pacientes = (ArrayList) PacService.getPacientes().getResultado("Pacientes");
        medicos = (ArrayList) medService.getMedicos().getResultado("Medicos");
        itemsMedico = FXCollections.observableArrayList(medicos);
        itemsPaciente = FXCollections.observableArrayList(pacientes);
        tvPaciente.setItems(itemsPaciente);
        tvMedico.setItems(itemsMedico);

        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.Titulo.setText(idioma.getProperty("Reportes"));
        }

    }

    @FXML
    private void GenerarReporteMedico(ActionEvent event) {
        if (DateFechaFin.getValue() != null && DateFechaInicio.getValue() != null) {
            if (!DateFechaFin.getValue().isBefore(DateFechaInicio.getValue())) {
                if (tvMedico.getSelectionModel() != null && tvMedico.getSelectionModel().getSelectedItem() != null) {
                    MedicoDto medico = tvMedico.getSelectionModel().getSelectedItem();
                    String FechaInicio = DateFechaInicio.getValue().toString();
                    String FechaFinal = DateFechaFin.getValue().toString();
                    Respuesta resp = new AgendaService().getAgendas(FechaInicio, FechaFinal, medico.getFolio());
                    if (resp.getEstado()) {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Reporte", this.getStage(), resp.getMensaje());
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Reporte", this.getStage(), resp.getMensaje());
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.WARNING, "Generacion de Reporte", this.getStage(), "Debes Seleccionar un medico primero para generar un reporte");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Reporte", this.getStage(), "La fecha fin del reporte no puede estar antes que la de inicio");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Reporte", this.getStage(), "Las fechas del reporte no pueden ir vacias");
        }

    }

    @FXML
    private void GenerarReportePaciente(ActionEvent event) {
        if (tvPaciente.getSelectionModel() != null && tvPaciente.getSelectionModel().getSelectedItem() != null) {
            PacienteDto paciente = tvPaciente.getSelectionModel().getSelectedItem();
           
            Respuesta resp = new PacienteService().getReporteControl(paciente.getCedula());
            if (resp.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Reporte", this.getStage(), resp.getMensaje());
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Reporte", this.getStage(), resp.getMensaje());
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Generacion de Reporte", this.getStage(), "Debes Seleccionar un paciente primero para generar un reporte");
        }
    }

    @FXML
    private void ReleasedNombrePac(KeyEvent event) {
    }

    /*
    Agregar Filtros
     */
    @FXML
    private void filtrarPaciente(ActionEvent event) {
    }

    @FXML
    private void filtrarMedico(ActionEvent event) {
    }

    @FXML
    private void seleccionarPaciente(MouseEvent event) {
        if (tvPaciente.getSelectionModel() != null && tvPaciente.getSelectionModel().getSelectedItem() != null) {
            PacienteDto paciente = tvPaciente.getSelectionModel().getSelectedItem();
            txtPaciente.setText(paciente.getNombre() + " " + paciente.getpApellido() + " " + paciente.getsApellido());
        }
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        tvMedico.getSelectionModel().clearSelection();
        tvPaciente.getSelectionModel().clearSelection();
        txtMedico.clear();
        txtPaciente.clear();
    }

    @FXML
    private void seleccionarMedico(MouseEvent event) {
        if (tvMedico.getSelectionModel() != null && tvMedico.getSelectionModel().getSelectedItem() != null) {
            MedicoDto medico = tvMedico.getSelectionModel().getSelectedItem();
            txtMedico.setText(medico.getUs().getNombre() + " " + medico.getUs().getpApellido() + " " + medico.getUs().getsApellido());
        }
    }

    @FXML
    private void porcentajeCitas(ActionEvent event) {
    }
}
