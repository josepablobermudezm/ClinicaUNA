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
    private JFXDatePicker DateFechaInicio;
    @FXML
    private JFXDatePicker DateFechaFin;
    @FXML
    private JFXButton btnGenerarReporteMed;
    @FXML
    private JFXButton btnGenerarReportePac;
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
    private Label lblTituloPaciente;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private Label lblTituloMedico;
    @FXML
    private JFXButton btnPorcentajeCitas;
    @FXML
    private JFXButton btnLimpiar;
    @FXML
    private JFXTextField txtFolio;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtCarne;
    private MedicoService service;
    private ArrayList<MedicoDto> meds;
    private ObservableList items;
    private PacienteService PService;
    @FXML
    private TableColumn<MedicoDto, String> col_codigo;
    @FXML
    private TableColumn<MedicoDto, String> Col_Carne;

    @Override
    public void initialize() {
        colPacNombre.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre() + " " + value.getValue().getpApellido() + " " + value.getValue().getsApellido()));
        colCedPaciente.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));

        ColNomMed.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getUs().getNombre() + " " + value.getValue().getUs().getpApellido() + " " + value.getValue().getUs().getsApellido()));
        ColFolMed.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFolio()));
        col_codigo.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCodigo()));
        Col_Carne.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCarne()));

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
            this.lblTituloMedico.setText(idioma.getProperty("Medico")+" "+idioma.getProperty("Reporte"));
            this.lblTituloPaciente.setText(idioma.getProperty("PacienteB")+" "+idioma.getProperty("Reporte"));
            this.txtApellido.setPromptText(idioma.getProperty("Apellido"));
            this.txtCarne.setPromptText(idioma.getProperty("Carné"));
            this.txtCodigo.setPromptText(idioma.getProperty("Código"));
            this.txtCedula.setPromptText(idioma.getProperty("Cedula"));
            this.txtNombre.setPromptText(idioma.getProperty("Nombre"));
            this.ColNomMed.setText(idioma.getProperty("Nombre"));
            this.Col_Carne.setText(idioma.getProperty("Carné"));
            this.colCedPaciente.setText(idioma.getProperty("Cedula"));
            this.colPacNombre.setText(idioma.getProperty("Nombre"));
            this.btnGenerarReporteMed.setText(idioma.getProperty("Agenda"));
            this.btnGenerarReportePac.setText(idioma.getProperty("Generar"));
            this.btnPorcentajeCitas.setText(idioma.getProperty("RPCitas"));
            this.btnLimpiar.setText("Limpiar"+" "+idioma.getProperty("Registro"));
            this.DateFechaInicio.setPromptText(idioma.getProperty("Inicio")+" "+idioma.getProperty("Fecha"));
            this.DateFechaFin.setPromptText(idioma.getProperty("Final")+" "+idioma.getProperty("Fecha"));
            
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
    private void seleccionarPaciente(MouseEvent event) {
        if (tvPaciente.getSelectionModel() != null && tvPaciente.getSelectionModel().getSelectedItem() != null) {
            PacienteDto paciente = tvPaciente.getSelectionModel().getSelectedItem();
            txtNombre.setText(paciente.getNombre());
            txtCedula.setText(paciente.getCedula());
            txtApellido.setText(paciente.getpApellido()+" "+paciente.getsApellido());
        }
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        tvMedico.getSelectionModel().clearSelection();
        tvPaciente.getSelectionModel().clearSelection();
        txtFolio.clear();
        txtCodigo.clear();
        txtCarne.clear();
        txtApellido.clear();
        txtCedula.clear();
        txtNombre.clear();
    }

    @FXML
    private void seleccionarMedico(MouseEvent event) {
        if (tvMedico.getSelectionModel() != null && tvMedico.getSelectionModel().getSelectedItem() != null) {
            MedicoDto medico = tvMedico.getSelectionModel().getSelectedItem();
            txtFolio.setText(medico.getFolio());
            txtCodigo.setText(medico.getCodigo());
            txtCarne.setText(medico.getCarne());
        }
    }

    @FXML
    private void porcentajeCitas(ActionEvent event) {
    }

    @FXML
    private void ReleasedNombre(KeyEvent event) {
        BuscarPacientes();
    }

    @FXML
    private void ReleasedCedula(KeyEvent event) {
        BuscarPacientes();
    }

    @FXML
    private void ReleasedApellido(KeyEvent event) {
        BuscarPacientes();
    }

    @FXML
    private void ReleasedFolio(KeyEvent event) {
        BuscarMedicos();
    }

    @FXML
    private void ReleasedCodigo(KeyEvent event) {
        BuscarMedicos();
    }

    @FXML
    private void ReleasedCarne(KeyEvent event) {
        BuscarMedicos();
    }

    public void BuscarPacientes() {
        String ced = (!txtCedula.getText().isEmpty()) ? "%" + txtCedula.getText().toUpperCase() + "%" : "%";
        String nom = (!txtNombre.getText().isEmpty()) ? "%" + txtNombre.getText().toUpperCase() + "%" : "%";
        String apellido = (!txtApellido.getText().isEmpty()) ? "%" + txtApellido.getText().toUpperCase() + "%" : "%";

        PService = new PacienteService();
        Respuesta resp = PService.getPacientes(ced, nom, apellido);

        if (resp.getEstado()) {
            pacientes = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");
            items = FXCollections.observableArrayList(pacientes);
            tvPaciente.setItems(items);
        } else {
            System.out.println(resp.getMensaje());
        }
    }

    public void BuscarMedicos() {
        String cod = (!txtCodigo.getText().isEmpty()) ? "%" + txtCodigo.getText().toUpperCase() + "%" : "%";
        String carne = (!txtCarne.getText().isEmpty()) ? "%" + txtCarne.getText().toUpperCase() + "%" : "%";
        String folio = (!txtFolio.getText().isEmpty()) ? "%" + txtFolio.getText().toUpperCase() + "%" : "%";

        service = new MedicoService();
        Respuesta resp = service.getMedicos(cod, carne, folio);

        if (resp.getEstado()) {
            meds = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
            items = FXCollections.observableArrayList(meds);
            this.tvMedico.setItems(items);
        } else {
            System.out.println(resp.getMensaje());
        }
    }

}
