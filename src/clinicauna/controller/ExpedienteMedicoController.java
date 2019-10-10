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
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
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
    private Label Titulo;
    private Idioma idioma;
    private UsuarioDto usuario;
    @FXML
    private JFXRadioButton btnSiHospitalizaciones;
    @FXML
    private JFXRadioButton btnNoHospitalizaciones;
    @FXML
    private JFXRadioButton btnSiAlergias;
    @FXML
    private JFXRadioButton btnNoAlergias;
    @FXML
    private JFXRadioButton btnSiOperaciones;
    @FXML
    private JFXRadioButton btnNoOperaciones;
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
    private JFXTextArea txtAlergias;
    @FXML
    private JFXTextArea txtOperaciones;
    @FXML
    private JFXTextArea txtAntecedentesPatologicos;
    private ExpedienteDto expedienteDto;
    private PacienteDto pacienteDto;
    @FXML
    private ToggleGroup Tratamientos;
    @FXML
    private ToggleGroup Operaciones;
    @FXML
    private Label lblAntecedente;
    @FXML
    private JFXRadioButton btnAntecedenteSi;
    @FXML
    private JFXRadioButton btnAntecedentesNo;
    @FXML
    private ToggleGroup Hospitalizacion;
    @FXML
    private JFXButton btnEditar1;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnAntecedentes;
    @FXML
    private JFXButton btnControles;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblTratamiento;
    @FXML
    private Label lblOperaciones;
    @FXML
    private Label lblHospitalizaciones;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private Label lblPaciente;
    @FXML
    private Label lblOperaciones1;
    private PacienteDto paciente;
    @FXML
    private ToggleGroup Antecedentes;
    @FXML
    private ToggleGroup Alergias;

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
        expedientes = (ArrayList<ExpedienteDto>) resp.getResultado("Expedientes");
        DatosPaciente();
    }

    private void DatosPaciente() {
        if (AppContext.getInstance().get("Paciente") != null) {
            paciente = (PacienteDto) AppContext.getInstance().get("Paciente");

            if (expedientes.stream().filter(x -> x.getPaciente().getID() == paciente.getID()).findAny().isPresent()) {
                expedienteDto = expedientes.stream().filter(x -> x.getPaciente().getID() == paciente.getID()).findAny().get();
                txtAlergias.setText((expedienteDto.getAlergias() != null) ? expedienteDto.getAlergias() : "");
                txtAntecedentesPatologicos.setText((expedienteDto.getAntecedentesPatologicos() != null) ? expedienteDto.getAntecedentesPatologicos() : "");
                txtOperaciones.setText((expedienteDto.getOperaciones() != null) ? expedienteDto.getOperaciones() : "");
                txtTratamientos.setText((expedienteDto.getTratamientos() != null) ? expedienteDto.getTratamientos() : "");
                if (expedienteDto.getAlergias().equals(" ")) {
                    btnNoAlergias.setSelected(true);
                } else {
                    btnSiAlergias.setSelected(true);
                }
                if (expedienteDto.getOperaciones().equals(" ")) {
                    btnNoOperaciones.setSelected(true);
                } else {
                    btnSiOperaciones.setSelected(true);
                }
                if (expedienteDto.getHospitalizaciones().equals(" ")) {
                    btnNoHospitalizaciones.setSelected(true);
                } else {
                    btnSiHospitalizaciones.setSelected(true);
                }
                if (expedienteDto.getTratamientos().equals(" ")) {
                    btnNoTratamientos.setSelected(true);
                } else {
                    btnSiTratamientos.setSelected(true);
                }
                if (expedienteDto.getAntecedentesPatologicos().equals(" ")) {
                    btnAntecedentesNo.setSelected(true);
                } else {
                    btnAntecedenteSi.setSelected(true);
                }
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Busqueda de paciente", this.getStage(), "El paciente seleccionado no tiene un expediente");
            }
            lblPaciente.setText(paciente.getNombre() + " " + paciente.getpApellido() + " " + paciente.getsApellido());
        }
    }

    @FXML
    private void editar(ActionEvent event) {

        if (registroCorrecto()) {
            String antecedentes = txtAntecedentesPatologicos.getText();
            String tratamientos = txtTratamientos.getText();
            String hospitalizaciones = (btnSiHospitalizaciones.isSelected() ? "S" : "N");
            String alergias = txtAlergias.getText();
            String operaciones = txtOperaciones.getText();
            Long id = expedienteDto.getExpID();
            Long version = expedienteDto.getExpVersion() + 1;
            PacienteDto paciente = expedienteDto.getPaciente();
            if (btnNoAlergias.isSelected()) {
                alergias = " ";
            }
            if (btnNoHospitalizaciones.isSelected()) {
                hospitalizaciones = " ";
            }
            if (btnNoOperaciones.isSelected()) {
                operaciones = " ";
            }
            if (btnNoTratamientos.isSelected()) {
                tratamientos = " ";
            }
            expedienteDto = new ExpedienteDto(id, version, antecedentes, hospitalizaciones, operaciones, alergias, tratamientos, paciente);
            try {
                resp = expedienteService.guardarExpediente(expedienteDto);
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de Edición", this.getStage(), resp.getMensaje());
                Limpiar();
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de editar el expediente.");
            }
        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Existen datos en el registro sin completar.");
        }
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        Limpiar();
    }

    private void Limpiar() {
        this.txtAlergias.clear();
        this.txtOperaciones.clear();
        this.txtTratamientos.clear();
        this.txtAntecedentesPatologicos.clear();
        this.btnNoAlergias.setSelected(true);
        this.btnNoHospitalizaciones.setSelected(true);
        this.btnNoOperaciones.setSelected(true);
        this.btnNoTratamientos.setSelected(true);
        this.btnAntecedentesNo.setSelected(true);
        this.btnSiAlergias.setSelected(false);
        this.btnSiHospitalizaciones.setSelected(false);
        this.btnSiOperaciones.setSelected(false);
        this.btnSiTratamientos.setSelected(false);
        this.btnAntecedenteSi.setSelected(false);
        this.lblPaciente.setText(null);
        AppContext.getInstance().delete("Paciente");
    }

    boolean registroCorrecto() {
        boolean Value1 = true;
        boolean Value2 = true;
        boolean Value3 = true;
        boolean Value4 = true;
        //va a evuluar que estén seleccionadas todos los radiobutton y además que si un botón está en sí, que tenga que escribir una descripción
        if ((btnNoAlergias.isSelected() || btnSiAlergias.isSelected())
                && (btnNoHospitalizaciones.isSelected() || btnSiHospitalizaciones.isSelected())
                && (btnNoOperaciones.isSelected() || btnSiOperaciones.isSelected())
                && (btnNoTratamientos.isSelected() || btnSiTratamientos.isSelected())
                && (btnAntecedenteSi.isSelected() || btnAntecedentesNo.isSelected())) {
            if (btnSiAlergias.isSelected()) {
                Value1 = false;
                Value1 = !txtAlergias.getText().equals(" ");
            }
            if (btnSiOperaciones.isSelected()) {
                Value2 = false;
                Value2 = !txtOperaciones.getText().equals(" ");
            }
            if (btnSiTratamientos.isSelected()) {
                Value3 = false;
                Value3 = !txtTratamientos.getText().equals(" ");
            }
            if (btnAntecedenteSi.isSelected()) {
                Value4 = false;
                Value4 = !txtAntecedentesPatologicos.getText().equals(" ");
            }
        }
        return (btnNoAlergias.isSelected() || btnSiAlergias.isSelected())
                && (btnNoHospitalizaciones.isSelected() || btnSiHospitalizaciones.isSelected())
                && (btnNoOperaciones.isSelected() || btnSiOperaciones.isSelected())
                && (btnNoTratamientos.isSelected() || btnSiTratamientos.isSelected())
                && Value1 && Value2 && Value3 && Value4 && !txtAntecedentesPatologicos.getText().isEmpty();
    }

    @FXML
    private void Antecedentes(ActionEvent event) {
        if (expedienteDto != null) {
            AppContext.getInstance().set("Expediente", expedienteDto);
            FlowController.getInstance().goViewInWindowModal("Antecedentes", this.getStage(), false);
        } else {
            ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");
        }
    }

    @FXML
    private void controles(ActionEvent event) {
        if (expedienteDto != null) {
            AppContext.getInstance().set("Expediente", expedienteDto);
            FlowController.getInstance().goView("ControlPaciente");
        } else {
            ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un expediente");
        }
    }

    @FXML
    private void AgregarExpediente(ActionEvent event) {

        if (registroCorrecto()) {
            try {
                String alergias = txtAlergias.getText();
                String operaciones = txtOperaciones.getText();
                String tratamientos = txtTratamientos.getText();
                String hospitalizacion = (btnSiHospitalizaciones.isSelected() ? "S" : "N");
                String antecedente = txtAntecedentesPatologicos.getText();
                if (alergias.isEmpty()) {
                    alergias = " ";
                }
                if (operaciones.isEmpty()) {
                    operaciones = " ";
                }
                if (tratamientos.isEmpty()) {
                    tratamientos = " ";
                }
                if (hospitalizacion.isEmpty()) {
                    hospitalizacion = " ";
                }
                if (antecedente.isEmpty()) {
                    antecedente = " ";
                }
                Long version = new Long(1);
                expedienteDto = new ExpedienteDto(null, version, antecedente, hospitalizacion, operaciones, alergias, tratamientos, paciente);
                try {
                    resp = expedienteService.guardarExpediente(expedienteDto);
                    if (resp.getEstado()) {
                        expedienteDto = (ExpedienteDto) resp.getResultado("Expediente");
                        if (resp.getEstado()) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                            Limpiar();
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                    }
                } catch (Exception e) {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
                }
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Información de guardado", this.getStage(), "Error al guardar el expediente");
            }
        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Existen datos en el registro sin completar.");
        }

    }

    @FXML
    private void BuscarPaciente(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("BuscarPaciente", this.getStage(), false);
        initialize();
    }

    @FXML
    private void examenes(ActionEvent event) {
        if (expedienteDto != null) {
            AppContext.getInstance().set("Expediente", expedienteDto);
            FlowController.getInstance().goViewInWindowModal("Examenes", this.getStage(), false);
        } else {
            ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un expediente");
        }
    }

    @FXML
    private void evolucion(ActionEvent event) {
        if (expedienteDto != null) {
            AppContext.getInstance().set("Expediente", expedienteDto);
            FlowController.getInstance().goViewInWindowModal("evolucionHistorica", this.getStage(), false);
        } else {
            ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un expediente");

        }
    }

}
