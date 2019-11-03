package clinicauna.controller;

import clinicauna.model.ExamenDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.ExamenService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class ExamenesController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private TableView<ExamenDto> table;
    @FXML
    private TableColumn<ExamenDto, String> COL_NOMBRE_EXA;
    @FXML
    private TableColumn<ExamenDto, String> COL_FECHA_EXA;
    @FXML
    private JFXTextArea txtAnotaciones;
    private ArrayList<ExamenDto> examenes;
    private ArrayList<ExamenDto> examenes2;
    private Respuesta resp;
    private ExamenService examenService;
    private Mensaje ms;
    private ExamenDto examenDto;
    private ExpedienteDto expedienteDto;
    private PacienteDto pacienteDto;
    private ObservableList items;
    @FXML
    private JFXDatePicker Fecha;
    @FXML
    private JFXTextField txtNombreExamen;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEliminar;
    private UsuarioDto usuario;
    private Idioma idioma;

    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.Titulo.setText(idioma.getProperty("Examenes"));
            this.btnCancelar.setText(idioma.getProperty("Cancelar"));
            this.btnEditar.setText(idioma.getProperty("Editar"));
            this.btnEliminar.setText(idioma.getProperty("Eliminar"));
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.COL_FECHA_EXA.setText(idioma.getProperty("Fecha"));
            this.COL_NOMBRE_EXA.setText(idioma.getProperty("Examen") + " " + idioma.getProperty("Nombre"));
            this.txtAnotaciones.setPromptText(idioma.getProperty("Anotaciones"));
            this.txtNombreExamen.setPromptText(idioma.getProperty("Examen") + " " + idioma.getProperty("Nombre"));
            this.Fecha.setPromptText(idioma.getProperty("Fecha"));
        }
        examenes2 = new ArrayList();
        expedienteDto = (ExpedienteDto) AppContext.getInstance().get("Expediente");
        pacienteDto = (PacienteDto) AppContext.getInstance().get("Paciente");
        examenService = new ExamenService();
        examenDto = new ExamenDto();
        ms = new Mensaje();
        resp = examenService.getExamenes();
        examenes = ((ArrayList<ExamenDto>) resp.getResultado("Examenes"));
        examenes.stream().filter(x -> x.getExpediente().getExpID().equals(expedienteDto.getExpID())).forEach(x -> {
            examenes2.add(x);
        });
        COL_NOMBRE_EXA.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombreExamen()));
        COL_FECHA_EXA.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFecha().toString()));
        items = FXCollections.observableArrayList(examenes2);
        table.setItems(items);
        Formato();
    }

    @FXML
    private void cancela(ActionEvent event) {
        AppContext.getInstance().set("Paciente", pacienteDto);
        FlowController.getInstance().goView("ExpedienteMedico");
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        limpiarRegistro();
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (RegistroCorrecto()) {
                    txtAnotaciones.getText();
                    String examen = txtNombreExamen.getText();
                    LocalDate fecha = Fecha.getValue();
                    String anotaciones = txtAnotaciones.getText();
                    Long id = table.getSelectionModel().getSelectedItem().getExmID();
                    Long version = table.getSelectionModel().getSelectedItem().getExmVersion() + 1;
                    examenDto = new ExamenDto(id, examen, fecha, anotaciones, version, expedienteDto);
                    try {
                        resp = examenService.guardarExamen(examenDto);
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                        limpiarRegistro();
                        examenes = (ArrayList) examenService.getExamenes().getResultado("Examenes");
                        examenes2.clear();
                        examenes.stream().filter(x -> x.getExpediente().getExpID().equals(expedienteDto.getExpID())).forEach(x -> {
                            examenes2.add(x);
                        });
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(examenes2);
                        table.setItems(items);
                    } catch (Exception e) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the exam");
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el examen");
                        }
                    }
                } else {
                    if (usuario.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "There are erroneous data in the registry, "
                                + "Verify that all data is full");
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                                + "verifica que todos los datos esten llenos.");
                    }
                }
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the exam");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the exam");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
            }
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (RegistroCorrecto()) {

            String anotaciones = txtAnotaciones.getText();
            String nombre = txtNombreExamen.getText();
            LocalDate fecha = Fecha.getValue();
            Long version = new Long(1);
            examenDto = new ExamenDto(null, nombre, fecha, anotaciones, version, expedienteDto);
            try {
                resp = examenService.guardarExamen(examenDto);
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                } else {
                    ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                }
                limpiarRegistro();
                examenes = (ArrayList) examenService.getExamenes().getResultado("Examenes");
                examenes2.clear();
                examenes.stream().filter(x -> x.getExpediente().getExpID().equals(expedienteDto.getExpID())).forEach(x -> {
                    examenes2.add(x);
                });
                table.getItems().clear();
                items = FXCollections.observableArrayList(examenes2);
                table.setItems(items);
            } catch (Exception e) {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the exam");
                } else {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el examen....");
                }
            }

        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "There are erroneous data in the registry, "
                        + "Verify that all data is full");
            } else {
                ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                        + "verifica que todos los datos esten llenos.");
            }
        }
    }

    @FXML
    private void datos(MouseEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                examenDto = table.getSelectionModel().getSelectedItem();
                txtAnotaciones.setText(examenDto.getAnotaciones());
                txtNombreExamen.setText(examenDto.getNombreExamen());
                Fecha.setValue(examenDto.getFecha());
            }
        }
    }

    boolean RegistroCorrecto() {
        return !txtAnotaciones.getText().isEmpty() && !txtNombreExamen.getText().isEmpty() && Fecha.getValue() != null;
    }

    private void limpiarRegistro() {
        txtAnotaciones.clear();
        txtNombreExamen.clear();
        Fecha.setValue(null);
    }

    public void Formato() {
        this.txtAnotaciones.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtNombreExamen.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
    }
}
