package clinicauna.controller;

import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AgendaService;
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

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class ReportesController extends Controller {

    @FXML
    private Label Titulo;
    private Idioma idioma;
    private UsuarioDto usuario;
    @FXML
    private JFXTextField txtCedulaMedico;
    @FXML
    private TableView<?> TvMedico;
    @FXML
    private Label Titulo1;
    @FXML
    private JFXComboBox<?> CmbTipoReoprte;
    @FXML
    private JFXTextField txtApellidoMedico;
    @FXML
    private JFXTextField txtNombreMedico;
    @FXML
    private JFXDatePicker DateDiaEspecífico;
    @FXML
    private Label lblDía;
    @FXML
    private Label lblDel;
    @FXML
    private JFXDatePicker DateFechaInicio;
    @FXML
    private Label lblFechaFin;
    @FXML
    private JFXDatePicker DateFechaFin;
    @FXML
    private JFXButton btnGenerarReporteMed;
    @FXML
    private TableView<PacienteDto> TvPaciente;
    @FXML
    private JFXTextField txtCedulaPaciente;
    @FXML
    private JFXTextField txtApellidoPaciente;
    @FXML
    private JFXTextField txtNombrePaciente;
    @FXML
    private JFXButton btnGenerarReportePac;
    @FXML
    private Label Titulo11;
    @FXML
    private TableColumn<PacienteDto, String> ColCedPac;
    @FXML
    private TableColumn<PacienteDto, String> ColNomPac;
    private PacienteService PacService;
    private ArrayList<PacienteDto> pacientes;
    private ObservableList items;

    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.Titulo.setText(idioma.getProperty("Reportes"));
        }
        ColNomPac.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre() + " " + value.getValue().getpApellido() + " " + value.getValue().getsApellido()));
        ColCedPac.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
    }

    @FXML
    private void GenerarReporteMedico(ActionEvent event) {
        if (DateFechaFin.getValue() != null && DateFechaInicio.getValue() != null) {
            if (!DateFechaFin.getValue().isBefore(DateFechaInicio.getValue())) {
                String FechaInicio = DateFechaInicio.getValue().toString();
                String FechaFinal = DateFechaFin.getValue().toString();
                Respuesta resp = new AgendaService().getAgendas(FechaInicio, FechaFinal,"123");
                if(resp.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Reporte", this.getStage(),resp.getMensaje());
                }else{
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Reporte", this.getStage(),resp.getMensaje());
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
    }

    @FXML
    private void ReleasedCedulaPac(KeyEvent event) {
        BuscarPaciente();
    }

    @FXML
    private void ReleasedApellidoPac(KeyEvent event) {
        BuscarPaciente();
    }

    @FXML
    private void ReleasedNombrePac(KeyEvent event) {
        BuscarPaciente();
    }

    public void BuscarPaciente() {
        String ced = (!txtCedulaPaciente.getText().isEmpty()) ? "%" + txtCedulaPaciente.getText().toUpperCase() + "%" : "%";
        String nom = (!txtNombrePaciente.getText().isEmpty()) ? "%" + txtNombrePaciente.getText().toUpperCase() + "%" : "%";
        String apellido = (!txtApellidoPaciente.getText().isEmpty()) ? "%" + txtApellidoPaciente.getText().toUpperCase() + "%" : "%";

        PacService = new PacienteService();
        Respuesta resp = PacService.getPacientes(ced, nom, apellido);

        if (resp.getEstado()) {
            pacientes = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");
            items = FXCollections.observableArrayList(pacientes);
            TvPaciente.setItems(items);
        } else {
            System.out.println(resp.getMensaje());
        }
    }
}
