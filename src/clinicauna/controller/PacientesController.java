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
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class PacientesController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private TableView<PacienteDto> table;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private ToggleGroup genero;
    private JFXTextField txtFiltroEmpleado;
    private JFXButton btnBuscar;
    @FXML
    private JFXDatePicker FechaDeNacimiento;
    private Respuesta resp;
    private PacienteService pacienteService;
    private PacienteDto pacienteDto;
    private Mensaje ms;
    private ArrayList<PacienteDto> pacientes;
    private ObservableList items;
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
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    @FXML
    private JFXRadioButton btnMujer;
    @FXML
    private JFXRadioButton btnHombre;
    private Idioma idioma;
    private UsuarioDto usuario;
    private JFXButton btnLimpiarRegistro;
    @FXML
    private Label lblGenero;
    private ExpedienteDto expedienteDto;
    private ExpedienteService expedienteService;
    @FXML
    private JFXButton btnEditar11;
    @FXML
    private JFXButton btnEliminar11;
    @FXML
    private JFXButton btnLimpiarRegistro1;
    @FXML
    private JFXButton btnAgregar11;

    @Override
    public void initialize() {
        Formato();
        /*btnAgregar1.setCursor(Cursor.HAND);
        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);*/
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnEditar11.setText(idioma.getProperty("Editar"));
            this.btnAgregar11.setText(idioma.getProperty("Agregar"));
            this.btnHombre.setText(idioma.getProperty("Masculino"));
            this.btnMujer.setText(idioma.getProperty("Femenino"));
            this.btnLimpiarRegistro1.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.btnEliminar11.setText(idioma.getProperty("Eliminar"));
            this.COL_CEDULA_PAC.setText("ID");
            this.COL_CORREO_PAC.setText(idioma.getProperty("Correo"));
            this.COL_FECHANACIMIENTO_PAC.setText(idioma.getProperty("Fecha") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Nacimiento"));
            this.COL_GENERO_PAC.setText(idioma.getProperty("Genero"));
            this.COL_PAPELLIDO_PAC.setText(idioma.getProperty("Primero") + " " + idioma.getProperty("Apellido"));
            this.COL_NOMBRE_PAC.setText(idioma.getProperty("Nombre"));
            this.COL_SAPELLIDO_PAC.setText(idioma.getProperty("Segundo") + " " + idioma.getProperty("Apellido"));
            this.txtNombre.setPromptText(idioma.getProperty("Nombre"));
            this.txtPApellido.setPromptText(idioma.getProperty("Primero") + " " + idioma.getProperty("Apellido"));
            this.txtSApellido.setPromptText(idioma.getProperty("Segundo") + " " + idioma.getProperty("Apellido"));
            this.txtCedula.setPromptText("ID");
            this.txtCorreo.setPromptText(idioma.getProperty("Correo"));
            this.lblGenero.setText(idioma.getProperty("Genero"));
            this.FechaDeNacimiento.setPromptText(idioma.getProperty("Fecha") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Nacimiento"));
            this.Titulo.setText(idioma.getProperty("Mantenimiento") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Pacientes"));
        }

        expedienteService = new ExpedienteService();
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
        btnHombre.setSelected(true);
    }

    @FXML
    private void editar(ActionEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (registroCorrecto()) {
                    Long id = pacienteDto.getID();
                    String nombre = txtNombre.getText();
                    String papellido = txtPApellido.getText();
                    String sapellido = txtSApellido.getText();
                    String correo = txtCorreo.getText();
                    String cedula = txtCedula.getText();
                    String genero1 = (btnHombre.isSelected()) ? "M" : "F";
                    LocalDate fecha = FechaDeNacimiento.getValue();
                    Long version = pacienteDto.getPacVersion() + 1;
                    //System.out.println(dateTime);

                    //Integer version = table.getSelectionModel().getSelectedItem().getVersion() + 1;
                    pacienteDto = new PacienteDto(id, nombre, papellido, sapellido, cedula, correo, genero1, fecha, version);
                    try {
                        resp = pacienteService.guardarPaciente(pacienteDto);
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                        limpiarValores();
                        pacientes = (ArrayList) pacienteService.getPacientes().getResultado("Pacientes");
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(pacientes);
                        table.setItems(items);
                    } catch (Exception e) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the patient");
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
                        }
                    }
                } else {
                    if (usuario.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "There are erroneous data in the registry, "
                                + "Verify that all data is full");
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "Existen datos erroneos en el registro, "
                                + "verifica que todos los datos esten llenos.");
                    }
                }
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the patient");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the patient");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");
            }
        }

    }

    @FXML
    private void eliminar(ActionEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                Respuesta r = pacienteService.eliminarPaciente(table.getSelectionModel().getSelectedItem().getID());
                ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), r.getMensaje());

                Respuesta respuesta = pacienteService.getPacientes();
                items.clear();
                pacientes = (ArrayList) respuesta.getResultado("Pacientes");
                items = FXCollections.observableArrayList(pacientes);
                table.setItems(items);
                limpiarValores();
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You muest select the patient to delete");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a eliminar");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You muest select the patient");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el paciente");
            }
        }

    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        this.txtCedula.clear();
        this.txtCorreo.clear();
        this.txtNombre.clear();
        this.txtPApellido.clear();
        this.txtSApellido.clear();
        this.FechaDeNacimiento.setValue(null);
    }

    @FXML
    private void agregar(ActionEvent event) {

        if (registroCorrecto()) {
            String nombre = txtNombre.getText();
            String papellido = txtPApellido.getText();
            String sapellido = txtSApellido.getText();
            String correo = txtCorreo.getText();
            String cedula = txtCedula.getText();
            String genero1 = (btnHombre.isSelected()) ? "M" : "F";
            LocalDate fecha = FechaDeNacimiento.getValue();
            //LocalDateTime final2 = LocalDateTime.of(LocalDate.now(),final1);
            Long version = new Long(1);
            pacienteDto = new PacienteDto(null, nombre, papellido, sapellido, cedula, correo, genero1, fecha, version);
            try {
                resp = pacienteService.guardarPaciente(pacienteDto);
                if (resp.getEstado()) {
                    pacienteDto = (PacienteDto) resp.getResultado("Paciente");
                    if (resp.getEstado()) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                        limpiarValores();
                        pacientes = (ArrayList) pacienteService.getPacientes().getResultado("Pacientes");
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(pacientes);
                        table.setItems(items);
                    } else {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de Guardado", this.getStage(), resp.getMensaje());
                        }
                    }
                } else {
                    if (usuario.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de Guardado", this.getStage(), resp.getMensaje());
                    }
                }
            } catch (Exception e) {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "Hubo un error al momento de guardar el paciente...");
                } else {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion de Guardado", this.getStage(), "There was an error saving the patient");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "Incomplete data");
            } else {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Información Incompleta");
            }
        }

    }

    boolean registroCorrecto() {
        return !txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtPApellido.getText().isEmpty() && !txtSApellido.getText().isEmpty()
                && FechaDeNacimiento.getValue() != null
                && !txtCorreo.getText().isEmpty() && (btnHombre.isSelected() || btnMujer.isSelected());
    }

    void limpiarValores() {
        txtNombre.clear();
        txtPApellido.clear();
        txtSApellido.clear();
        txtCorreo.clear();
        txtCedula.clear();
        btnMujer.setSelected(false);
        btnHombre.setSelected(false);
        FechaDeNacimiento.setValue(null);
        table.getSelectionModel().clearSelection();
    }

    @FXML
    private void DatosPaciente(MouseEvent event) {

        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                pacienteDto = table.getSelectionModel().getSelectedItem();
                txtNombre.setText(pacienteDto.getNombre());
                txtPApellido.setText(pacienteDto.getpApellido());
                txtSApellido.setText(pacienteDto.getsApellido());
                txtCedula.setText(pacienteDto.getCedula());
                txtCorreo.setText(pacienteDto.getCorreo());
                if (pacienteDto.getGenero().equals("M")) {
                    btnHombre.setSelected(true);
                } else {
                    btnMujer.setSelected(true);
                }
                FechaDeNacimiento.setValue(pacienteDto.getFechaNacimiento());
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select a patiente");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select a patiente");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");
            }
        }
    }

    public void Formato() {
        this.txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(20));
        this.txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
        this.txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(50));
        this.txtPApellido.setTextFormatter(Formato.getInstance().letrasFormat(50));
        this.txtSApellido.setTextFormatter(Formato.getInstance().letrasFormat(50));
    }
}
