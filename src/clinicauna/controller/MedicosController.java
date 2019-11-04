/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.ClinicaUna;
import clinicauna.model.AgendaDto;
import clinicauna.model.EspacioDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AgendaService;
import clinicauna.service.EspacioService;
import clinicauna.service.MedicoService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javafx.beans.property.SimpleIntegerProperty;
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
    private Label Titulo;
    @FXML
    private TableView<MedicoDto> table;
    @FXML
    private JFXButton btnEditar1;
    @FXML
    private JFXButton btnEliminar1;
    private JFXTextField txtFiltroEmpleado;
    @FXML
    private JFXButton btnBuscar;
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
    private Idioma idioma;
    private UsuarioDto usuario;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private TableColumn<MedicoDto, String> COL_NOMBRE_MEDICOS;
    private MedicoDto med;
    private ArrayList<EspacioDto> espacioListAux;//en esta lista voy a guardar todos los espacios que tenga el médico
    private ArrayList<AgendaDto> agendaList;
    private ArrayList<EspacioDto> espacioList;
    private Respuesta respAgenda;
    private Respuesta respEspacio;
    private EspacioService espacioService;
    private AgendaService agendaService;

    @Override
    public void initialize() {
        /*
            Lo hago para obtener todos los espacios que existan del médico más adelante
         */
        agendaService = new AgendaService();
        espacioService = new EspacioService();
        respAgenda = agendaService.getAgendas();
        respEspacio = espacioService.getEspacios();
        espacioList = ((ArrayList<EspacioDto>) respEspacio.getResultado("Espacios"));
        agendaList = ((ArrayList<AgendaDto>) respAgenda.getResultado("Agendas"));
        espacioListAux = new ArrayList();

        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnEditar1.setText(idioma.getProperty("Editar"));
            this.btnBuscar.setText(idioma.getProperty("Buscar"));
            this.btnEliminar1.setText(idioma.getProperty("Eliminar"));
            this.COL_CODIGO_MEDICOS.setText(idioma.getProperty("Código"));
            this.COL_CARNE_MEDICOS.setText(idioma.getProperty("Carné"));
            this.COL_ESTADO_MEDICOS.setText(idioma.getProperty("Estado"));
            this.COL_INICIO_MEDICOS.setText(idioma.getProperty("Inicio") + " " + idioma.getProperty("Jornada"));
            this.COL_FINAL_MEDICOS.setText(idioma.getProperty("Final") + " " + idioma.getProperty("Jornada"));
            this.COL_ESPACIOS_MEDICOS.setText(idioma.getProperty("Espacio") + " " + idioma.getProperty("por") + " " + idioma.getProperty("Hora"));
            this.COL_NOMBRE_MEDICOS.setText(idioma.getProperty("Completo") + " " + idioma.getProperty("Nombre"));
            this.timePickerInicio.setPromptText(idioma.getProperty("Inicio") + " " + idioma.getProperty("Jornada"));
            this.timePickerfinal.setPromptText(idioma.getProperty("Final") + " " + idioma.getProperty("Jornada"));
            this.txtCarne.setPromptText(idioma.getProperty("Carné"));
            this.txtCodigo.setPromptText(idioma.getProperty("Código"));
            this.txtEspacio.setPromptText(idioma.getProperty("Espacio") + " " + idioma.getProperty("por") + " " + idioma.getProperty("Hora"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.Titulo.setText(idioma.getProperty("Mantenimiento") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Medicos"));
        }

        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);
        medicoService = new MedicoService();
        ms = new Mensaje();
        resp = medicoService.getMedicos();
        medicos = ((ArrayList<MedicoDto>) resp.getResultado("Medicos"));

        COL_CODIGO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCodigo()));
        COL_FOLIO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFolio()));
        COL_CARNE_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCarne()));
        COL_ESTADO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getEstado().equals("A")) ? "Activo" : "Inactivo"));
        COL_INICIO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getInicioJornada()));
        COL_FINAL_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFinJornada()));
        COL_ESPACIOS_MEDICOS.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getEspacios()));
        this.COL_NOMBRE_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getUs().getNombre() + " " + value.getValue().getUs().getpApellido() + " " + value.getValue().getUs().getsApellido()));
        items = FXCollections.observableArrayList(medicos);
        table.setItems(items);
    }

    @FXML
    private void editar(ActionEvent event) {

        if (table.getSelectionModel() != null || AppContext.getInstance().get("Med") != null) {
            if (table.getSelectionModel().getSelectedItem() != null || AppContext.getInstance().get("Med") != null) {
                if (registroCorrecto()) {
                    Long id = medicoDto.getID();
                    String folio = txtFolio.getText();
                    String carne = txtCarne.getText();
                    String codigo = txtCodigo.getText();
                    LocalTime inicio1 = timePickerInicio.getValue();
                    LocalTime final1 = timePickerfinal.getValue();
                    Integer espacios = Integer.parseInt(txtEspacio.getText());
                    LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicio1);
                    LocalDateTime final12 = LocalDateTime.of(LocalDate.now(), final1);
                    UsuarioDto usuariodto = medicoDto.getUs();
                    Long version = medicoDto.getMedVersion() + 1;
                    String inicioJornada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
                    String finJornada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(final12);
                    medicoDto = new MedicoDto(id, codigo, folio, carne, "I", inicioJornada, finJornada, espacios, usuariodto, version);
                    try {
                        /*
                            Si el valor de espacios es diferente al seleccionado (osea si lo editó) y la lista de espacios que 
                            tiene no está vacía, ya que, estaría editando teniendo conflictos con las agendas ya creadas
                         */
                        if ((txtEspacio.getText() == null ? table.getSelectionModel().getSelectedItem().getEspacios().toString() != null
                                : !txtEspacio.getText().equals(table.getSelectionModel().getSelectedItem().getEspacios().toString())
                                && !espacioListAux.isEmpty() )) {
                            ms.showModal(Alert.AlertType.ERROR, "Espacios por hora", this.getStage(), "Estas tratando de cambiarle la cantidad de espacios por hora al médico pero el médico ya posee citas asignadas");
                            txtEspacio.setText(table.getSelectionModel().getSelectedItem().getEspacios().toString());
                        } else {
                            resp = medicoService.guardarMedico(medicoDto);
                            if (usuario.getIdioma().equals("I")) {
                                ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                            } else {
                                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                            }
                            limpiarValores();
                            medicos = (ArrayList) medicoService.getMedicos().getResultado("Medicos");
                            table.getItems().clear();
                            items = FXCollections.observableArrayList(medicos);
                            table.setItems(items);
                        }
                    } catch (Exception e) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error saving the Doctor");
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
                        }
                    }
                } else {
                    if (usuario.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "User Information", this.getStage(), "There are erroneous data in the registry, "
                                + "Verify that all data is full");
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de usuario", this.getStage(), "Hay datos erroenaos en el sistema, "
                                + "Verifica que todos los datos esten llenos");
                    }
                }
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the doctor to edit");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a editar");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the doctor to edit");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a editar");
            }
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (table.getSelectionModel() != null || AppContext.getInstance().get("Med") != null) {
            if (table.getSelectionModel().getSelectedItem() != null || AppContext.getInstance().get("Med") != null) {
                Respuesta r;
                if (AppContext.getInstance().get("Med") != null && table.getSelectionModel().getSelectedItem() == null) {
                    r = medicoService.eliminarMedico(medicoDto.getID());
                } else {
                    r = medicoService.eliminarMedico(table.getSelectionModel().getSelectedItem().getID());
                }
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.INFORMATION, "Information", this.getStage(), r.getMensaje());
                } else {
                    ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Datos Eliminados correctamente");
                }
                Respuesta respuesta = medicoService.getMedicos();
                items.clear();
                medicos = (ArrayList) respuesta.getResultado("Medicos");
                items = FXCollections.observableArrayList(medicos);
                table.setItems(items);
                limpiarValores();
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the doctor to delete");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a eliminar");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the doctor");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");

            }
        }

    }

    void limpiarValores() {
        txtCarne.clear();
        txtCodigo.clear();
        txtEspacio.clear();
        txtFolio.clear();
        timePickerInicio.setValue(null);
        timePickerfinal.setValue(null);
        table.getSelectionModel().clearSelection();
    }

    boolean registroCorrecto() {
        return !txtCarne.getText().isEmpty() && !txtCodigo.getText().isEmpty()
                && !txtFolio.getText().isEmpty() && !txtEspacio.getText().isEmpty()
                && !timePickerInicio.getValue().toString().isEmpty() && !timePickerfinal.getValue().toString().isEmpty();
    }

    @FXML
    private void DatosMedico(MouseEvent event) {
        /*
        *   Cargo los datos cuando se seleccionan los datos desde el tableview y limpio el AppContext de Med en el caso de que se haya usado en la
        *   vista de buscar para que no genere problemas
        */
        AppContext.getInstance().delete("Med");
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                /*
                    Seteo los datos del médico seleccionado en cada textfield
                */
                medicoDto = table.getSelectionModel().getSelectedItem();
                txtCodigo.setText(medicoDto.getCodigo());
                txtCarne.setText(medicoDto.getCarne());
                txtEspacio.setText(String.valueOf(medicoDto.getEspacios()));
                txtFolio.setText(medicoDto.getFolio());
                LocalTime localTimeObj = LocalTime.parse(medicoDto.getInicioJornada());
                timePickerInicio.setValue(localTimeObj);
                LocalTime localTimeObj1 = LocalTime.parse(medicoDto.getFinJornada());
                timePickerfinal.setValue(localTimeObj1);
                /*
                    Guardo todos los espacios que pertenecen al médico en esta lista
                */
                espacioListAux.clear();
                agendaList.stream().forEach(x -> {
                    if (x.getAgeMedico().getID().equals(medicoDto.getID())) {
                        espacioList.stream().forEach(y -> {
                            if (y.getEspAgenda().getAgeId().equals(x.getAgeId())) {
                                espacioListAux.add(y);
                            }
                        });
                    }
                });
            }
        }
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
        txtCarne.clear();
        txtCodigo.clear();
        txtEspacio.clear();
        txtFolio.clear();
        timePickerInicio.setValue(null);
        timePickerfinal.setValue(null);
    }

    @FXML
    private void BuscarMedico(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        AppContext.getInstance().delete("Med");
        FlowController.getInstance().goViewInWindowModal("BuscarMedico", this.getStage(), false);
        DatosMedico();
    }

    private void DatosMedico() {
        /*
        *   Cargo los datos cuando se seleccionan desde la vista de Buscar medicos
        */
        if (AppContext.getInstance().get("Med") != null) {
            med = (MedicoDto) AppContext.getInstance().get("Med");
            medicoDto = med;
            this.txtCarne.setText(med.getCarne());
            this.txtCodigo.setText(med.getCodigo());
            this.txtEspacio.setText(String.valueOf(med.getEspacios()));
            this.txtFolio.setText(med.getFolio());
            LocalTime localTimeObj = LocalTime.parse(med.getInicioJornada());
            this.timePickerInicio.setValue(localTimeObj);
            LocalTime localTimeObj1 = LocalTime.parse(med.getFinJornada());
            this.timePickerfinal.setValue(localTimeObj1);
        }
    }
}
