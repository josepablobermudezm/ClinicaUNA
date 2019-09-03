/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.ClinicaUna;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.PacienteService;
import clinicauna.service.UsuarioService;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import clinicauna.util.generadorContrasennas;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class PacientesController extends Controller  {

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
    private JFXButton btnEditar1;
    @FXML
    private JFXButton btnEliminar1;
    @FXML
    private JFXButton btnAgregar1;
    @FXML
    private ToggleGroup genero;
    @FXML
    private JFXTextField txtFiltroEmpleado;
    @FXML
    private JFXButton btnBuscar;
    private JFXTextField txtApellido;
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
    
    
    @Override
    public void initialize() {
        btnAgregar1.setCursor(Cursor.HAND);
        btnBuscar.setCursor(Cursor.HAND);
        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);
        
        typeKeys();
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
        COL_FECHANACIMIENTO_PAC.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getFechaNacimiento()!=null)?value.getValue().getFechaNacimiento().toString():"NULO"));
        
        items = FXCollections.observableArrayList(pacientes);
        table.setItems(items);
        //FechaDeNacimiento.getValue().
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
                    String genero1 = (btnHombre.isSelected())?"M":"F";
                    LocalDate fecha = FechaDeNacimiento.getValue();
                    
                    
                    //System.out.println(dateTime);
                    
                    //Integer version = table.getSelectionModel().getSelectedItem().getVersion() + 1;

                    pacienteDto = new PacienteDto(id,nombre, papellido, sapellido,cedula, correo, genero1, fecha);
                    try {
                        resp = pacienteService.guardarPaciente(pacienteDto);
                        ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        limpiarValores();
                        pacientes = (ArrayList) pacienteService.getPacientes().getResultado("Pacientes");
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(pacientes);
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
                pacienteService.eliminarPaciente(table.getSelectionModel().getSelectedItem().getID());
                ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Datos Eliminados correctamente");

                Respuesta respuesta = pacienteService.getPacientes();
                items.clear();
                pacientes = (ArrayList) respuesta.getResultado("Pacientes");
                items = FXCollections.observableArrayList(pacientes);
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

    @FXML
    private void agregar(ActionEvent event) {
        
        
        if (registroCorrecto()) {
        
            String nombre = txtNombre.getText();
            String papellido = txtPApellido.getText();
            String sapellido = txtSApellido.getText();
            String correo = txtCorreo.getText();
            String cedula = txtCedula.getText();
            String genero1 = (btnHombre.isSelected())?"M":"F";
            LocalDate fecha = FechaDeNacimiento.getValue();
            //LocalDateTime final2 = LocalDateTime.of(LocalDate.now(),final1);
            
            pacienteDto = new PacienteDto(null,nombre, papellido, sapellido,cedula, correo, genero1, fecha);
            try {
                resp = pacienteService.guardarPaciente(pacienteDto);
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
                pacientes = (ArrayList) pacienteService.getPacientes().getResultado("Pacientes");
                
                table.getItems().clear();
                items = FXCollections.observableArrayList(pacientes);
                table.setItems(items);
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
            }
        }
        
    }

    @FXML
    private void Filtrar(ActionEvent event) {
    }

    boolean registroCorrecto() {
        return !txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
               && !txtPApellido.getText().isEmpty() && !txtSApellido.getText().isEmpty()
               && !FechaDeNacimiento.getValue().toString().isEmpty()
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
    
    private void typeKeys() {
        txtNombre.setOnKeyTyped(ClinicaUna.aceptaCaracteres);
        txtSApellido.setOnKeyTyped(ClinicaUna.aceptaCaracteres);
        txtPApellido.setOnKeyTyped(ClinicaUna.aceptaCaracteres);
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
                if(pacienteDto.getGenero().equals("M")){
                    btnHombre.setSelected(true);
                }else{
                    btnMujer.setSelected(true);
                }
                FechaDeNacimiento.setValue(pacienteDto.getFechaNacimiento());
            }
        }
    }
    
}
