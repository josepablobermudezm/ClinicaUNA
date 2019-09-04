/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.ClinicaUna;
import clinicauna.model.MedicoDto;
import clinicauna.service.MedicoService;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.util.ArrayList;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class MedicosController extends Controller {

    @FXML
    private ImageView omg;
    @FXML
    private Label Titulo;
    @FXML
    private TableView<MedicoDto> table;
    @FXML
    private JFXButton btnEditar1;
    @FXML
    private JFXButton btnEliminar1;
    @FXML
    private JFXTextField txtFiltroEmpleado;
    @FXML
    private JFXButton btnBuscar;
    private JFXComboBox<MedicoDto> ComboEstado;
    @FXML
    private TableColumn<MedicoDto, String> COL_CODIGO_MEDICOS;
    @FXML
    private TableColumn<MedicoDto, String> COL_FOLIO_MEDICOS;
    @FXML
    private TableColumn<MedicoDto, String> COL_CARNE_MEDICOS;
    @FXML
    private TableColumn<MedicoDto, String> COL_ESTADO_MEDICOS;
    @FXML
    private TableColumn<MedicoDto, String> COL_INICIO_MEDICOS;
    @FXML
    private TableColumn<MedicoDto, String> COL_FINAL_MEDICOS;
    @FXML
    private TableColumn<MedicoDto, Number> COL_ESPACIOS_MEDICOS;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtFolio;
    @FXML
    private JFXTextField txtCarne;
    @FXML
    private JFXTimePicker timePickerfinal;
    @FXML
    private JFXTimePicker timePickerInicio;
    private Respuesta resp;
    private Mensaje ms;
    private MedicoService medicoService;
    private ArrayList<MedicoDto> medicos;
    private ObservableList items;
    @FXML
    private JFXTextField txtEspacio;
    private MedicoDto medicoDto;
    @FXML
    private JFXTextField txtNombreUsuario;

    @Override
    public void initialize() {
        btnBuscar.setCursor(Cursor.HAND);
        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);
        medicoService = new MedicoService();
        ms = new Mensaje();
        resp = medicoService.getMedicos();
        medicos = ((ArrayList<MedicoDto>) resp.getResultado("Medicos"));

        COL_CODIGO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getCodigo() != null) ? value.getValue().getFolio() : "Sin Asignar"));
        COL_FOLIO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getFolio() != null) ? value.getValue().getFolio() : "Sin Asignar"));
        COL_CARNE_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getCarne() != null) ? value.getValue().getCarne() : "Sin Asignar"));
        COL_ESTADO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEstado()));
        COL_INICIO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getInicioJornada()!=null)?value.getValue().getInicioJornada():"NULO"));
        COL_FINAL_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getFinJornada()!=null)?value.getValue().getFinJornada():"NULO"));
        COL_ESPACIOS_MEDICOS.setCellValueFactory(value -> new SimpleIntegerProperty((value.getValue().getEspacios()!=null)?value.getValue().getEspacios():0));
        items = FXCollections.observableArrayList(medicos);
        table.setItems(items);
    }

    @FXML
    private void DatosEmpleado(MouseEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {

        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (registroCorrecto()) {
                    Long id = medicoDto.getID();
                    String usuario = medicoDto.getUs().getNombreUsuario();
                    String folio = txtFolio.getText();
                    String carne = txtCarne.getText();
                    String codigo = txtCodigo.getText();
                    String inicio2 = timePickerInicio.getValue().toString();
                    LocalTime inicio1 = timePickerInicio.getValue();
                    LocalTime final1 = timePickerfinal.getValue();
                    String final2 = timePickerfinal.getValue().toString();
                    Integer espacios = Integer.parseInt(txtEspacio.getText());
                    LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicio1);
                    LocalDateTime final12 = LocalDateTime.of(LocalDate.now(), final1);
                    UsuarioDto usuariodto = medicoDto.getUs();
                    
                    String inicioJornada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
                    String finJornada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(final12);
                    medicoDto = new MedicoDto(id, usuariodto, codigo, folio, carne, "I", inicioJornada, finJornada, espacios);
                    try {
                        resp = medicoService.guardarMedico(medicoDto);
                        ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        limpiarValores();
                        medicos = (ArrayList) medicoService.getMedicos().getResultado("Medicos");
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(medicos);
                        table.setItems(items);
                    } catch (Exception e) {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
                    }
                } else {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                            + "verifica que todos los datos esten llenos.");
                }
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a editar");
            }
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                medicoService.eliminarMedico(table.getSelectionModel().getSelectedItem().getID());
                ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Datos Eliminados correctamente");

                Respuesta respuesta = medicoService.getMedicos();
                items.clear();
                medicos = (ArrayList) respuesta.getResultado("Medicos");
                items = FXCollections.observableArrayList(medicos);
                table.setItems(items);
                limpiarValores();
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a eliminar");
            }
        }

    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {

    }

    void limpiarValores() {
        txtNombreUsuario.clear();
        txtCarne.clear();
        txtCodigo.clear();
        txtEspacio.clear();
        txtFolio.clear();
        timePickerInicio.setValue(null);
        timePickerfinal.setValue(null);
        table.getSelectionModel().clearSelection();
    }

    boolean registroCorrecto() {
        return !txtNombreUsuario.getText().isEmpty() && !txtCarne.getText().isEmpty() && !txtCodigo.getText().isEmpty()
                && !txtFolio.getText().isEmpty() && !txtEspacio.getText().isEmpty()
                && !timePickerInicio.getValue().toString().isEmpty() && !timePickerfinal.getValue().toString().isEmpty();
    }

    @FXML
    private void Filtrar(ActionEvent event) {
    }

    private void typeKeys() { 
        txtEspacio.setOnKeyTyped(ClinicaUna.aceptaNumeros);
    }

    @FXML
    private void DatosMedico(MouseEvent event) {

        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                medicoDto = table.getSelectionModel().getSelectedItem();
                txtNombreUsuario.setText(medicoDto.getUs().getNombreUsuario());
                if (medicoDto.getCarne() != null) {
                    txtCodigo.setText(medicoDto.getCodigo());
                } else {
                    txtCodigo.setText("Vacío");
                }
                if (medicoDto.getCarne() != null) {
                    txtCarne.setText(medicoDto.getCarne());
                } else {
                    txtCarne.setText("Vacío");
                }
                if (medicoDto.getEspacios() != null) {
                    txtEspacio.setText(String.valueOf(medicoDto.getEspacios()));
                } else {
                    txtEspacio.setText("0");
                }
                if (medicoDto.getFolio() != null) {
                    txtFolio.setText(medicoDto.getFolio());
                } else {
                    txtFolio.setText("Vacío");
                }
                if (medicoDto.getInicioJornada() != null) {
                    LocalTime localTimeObj = LocalTime.parse(medicoDto.getInicioJornada());
                    timePickerInicio.setValue(localTimeObj);
                } else {
                    //00:00
                    timePickerInicio.setValue(LocalTime.MIDNIGHT);
                }
                if (medicoDto.getFinJornada() != null) {
                    LocalTime localTimeObj1 = LocalTime.parse(medicoDto.getFinJornada());
                    timePickerfinal.setValue(localTimeObj1);
                } else {
                    timePickerfinal.setValue(LocalTime.MIDNIGHT);
                }
            }
        }
    }
}
