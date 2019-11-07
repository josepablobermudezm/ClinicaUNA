/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AntecedenteDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AntecedenteService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
    private UsuarioDto usuario;
    private Idioma idioma;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEliminar;

    @Override
    public void initialize() {

        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.txtEnfermedad.setPromptText(idioma.getProperty("Enfermedad"));
            this.txtParentesco.setPromptText(idioma.getProperty("Parentesco"));
            this.btnEditar.setText(idioma.getProperty("Editar"));
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.btnCancelar.setText(idioma.getProperty("Cancelar"));
            this.btnEliminar.setText(idioma.getProperty("Eliminar"));
            this.Titulo.setText(idioma.getProperty("Familiares") + " " + idioma.getProperty("Antecedentes"));
            this.COL_ENFERMEDAD_ANT.setText(idioma.getProperty("Enfermedad"));
            this.COL_PARENTESCO_ANT.setText(idioma.getProperty("Parentesco"));
        }
        antecedentesList2 = new ArrayList();
        expediente = (ExpedienteDto) AppContext.getInstance().get("Expediente");
        paciente = (PacienteDto) AppContext.getInstance().get("Paciente");
        antecedenteService = new AntecedenteService();
        antecedenteDto = new AntecedenteDto();
        ms = new Mensaje();
        resp = antecedenteService.getAntecedentes();
        antecedentesList = ((ArrayList<AntecedenteDto>) resp.getResultado("Antecedentes"));
        antecedentesList.stream().filter(x -> x.getAntExpediente().getExpID().equals(expediente.getExpID())).forEach(x -> {
            antecedentesList2.add(x);
        });
        btnGuardar.setDisable(false);
        //antecedentesList = expediente.getAntecedentes();
        COL_PARENTESCO_ANT.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAntParentesco()));
        COL_ENFERMEDAD_ANT.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAntEnfermedad()));
        items = FXCollections.observableArrayList(antecedentesList2);
        table.setItems(items);

    }

    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().initialize();
        this.getStage().close();
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        limpiarValores();
    }

    boolean RegistroCorrecto() {
        return !txtEnfermedad.getText().isEmpty() && !txtParentesco.getText().isEmpty();
    }

    @FXML
    private void Eliminar(ActionEvent event) {

        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                resp = antecedenteService.eliminarUsuario(antecedenteDto.getAntId());
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                } else {
                    ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                }
                limpiarValores();
                antecedentesList = (ArrayList) antecedenteService.getAntecedentes().getResultado("Antecedentes");
                antecedentesList2.clear();
                antecedentesList.stream().filter(x -> x.getAntExpediente().getExpID().equals(expediente.getExpID())).forEach(x -> {
                    antecedentesList2.add(x);
                });
                table.getItems().clear();
                items = FXCollections.observableArrayList(antecedentesList2);
                table.setItems(items);
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the background");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the background");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
            }
        }

    }

    @FXML
    private void editar(ActionEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (RegistroCorrecto()) {
                    String parentesco = txtParentesco.getText();
                    String enfermedad = txtEnfermedad.getText();
                    Long id = antecedenteDto.getAntId();
                    Long version = antecedenteDto.getAntVersion() + 1;
                    antecedenteDto = new AntecedenteDto(id, enfermedad, parentesco, version, expediente);
                    try {
                        resp = antecedenteService.guardarAntecedente(antecedenteDto);
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                        limpiarValores();
                        antecedentesList = (ArrayList) antecedenteService.getAntecedentes().getResultado("Antecedentes");
                        antecedentesList2.clear();
                        antecedentesList.stream().filter(x -> x.getAntExpediente().getExpID().equals(expediente.getExpID())).forEach(x -> {
                            antecedentesList2.add(x);
                        });
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(antecedentesList2);
                        table.setItems(items);
                    } catch (Exception e) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the background");
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el antecedente");
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
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the background");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the background");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un antecedente");
            }
        }
    }

    private void limpiarValores() {
        txtEnfermedad.clear();
        txtParentesco.clear();
        btnGuardar.setDisable(false);
        table.getSelectionModel().clearSelection();
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

                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                } else {
                    ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                }
                limpiarValores();
                antecedentesList = (ArrayList) antecedenteService.getAntecedentes().getResultado("Antecedentes");
                antecedentesList2.clear();
                antecedentesList.stream().filter(x -> x.getAntExpediente().getExpID().equals(expediente.getExpID())).forEach(x -> {
                    antecedentesList2.add(x);
                });
                table.getItems().clear();
                items = FXCollections.observableArrayList(antecedentesList2);
                table.setItems(items);
            } catch (Exception e) {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the background");
                } else {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el antecedente");
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
                btnGuardar.setDisable(true);
                antecedenteDto = table.getSelectionModel().getSelectedItem();
                txtEnfermedad.setText(antecedenteDto.getAntEnfermedad());
                txtParentesco.setText(antecedenteDto.getAntParentesco());
            }
        }
    }

}
