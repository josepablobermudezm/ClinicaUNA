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
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class MedicosController extends Controller  {

    @FXML
    private ImageView omg;
    @FXML
    private Label Titulo;
    @FXML
    private TableView<?> table;
    @FXML
    private JFXButton btnEditar1;
    @FXML
    private JFXButton btnEliminar1;
    @FXML
    private JFXButton btnAgregar1;
    @FXML
    private JFXTextField txtFiltroEmpleado;
    @FXML
    private JFXButton btnBuscar;
    @FXML
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
   
    @Override
    public void initialize() {

        /*medicoService = new MedicoService();
        ms = new Mensaje();
        resp = medicoService.getMedicos();
        medicos = ((ArrayList<MedicoDto>) resp.getResultado("Medicos"));
        
        COL_CODIGO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCodigo()));
        COL_FOLIO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFolio()));
        COL_CARNE_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCarne()));
        COL_ESTADO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEstado()));
        COL_INICIO_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getInicioJornada()!=null)?value.getValue().getInicioJornada().toString():"NULO"));
        COL_FINAL_MEDICOS.setCellValueFactory(value -> new SimpleStringProperty((value.getValue().getFinJornada()!=null)?value.getValue().getFinJornada().toString():"NULO"));
        COL_ESPACIOS_MEDICOS.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getEspacios()));
        
        items = FXCollections.observableArrayList(medicos);
        table.setItems(items);*/
        
    }
    
    
    @FXML
    private void DatosEmpleado(MouseEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }

    @FXML
    private void agregar(ActionEvent event) {
        
        if (registroCorrecto()) {
        
            String codigo = txtCodigo.getText();
            String folio = txtFolio.getText();
            String carne = txtCarne.getText();
            Integer espacio = Integer.parseInt(txtEspacio.getText());
            LocalTime inicio = timePickerInicio.getValue();
            LocalTime final1 = timePickerfinal.getValue();
            //String estado = ComboEstado.getValue().toString();
            
            LocalDateTime inicio1 = LocalDateTime.of(LocalDate.now(),inicio);
            LocalDateTime final2 = LocalDateTime.of(LocalDate.now(),final1);
            
            medicoDto = new MedicoDto(null,codigo, folio, carne,/*estado*/"A", inicio1, final2, espacio);
            try {
                resp = medicoService.guardarMedico(medicoDto);
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
                medicos = (ArrayList) medicoService.getMedicos().getResultado("Medicos");
                table.getItems().clear();
                items = FXCollections.observableArrayList(medicos);
                table.setItems(items);
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el medico...");
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
        ComboEstado.getSelectionModel().clearSelection();
        table.getSelectionModel().clearSelection();
    }
    
    boolean registroCorrecto() {
        return !txtCarne.getText().isEmpty() && !txtCodigo.getText().isEmpty()
               && !txtFolio.getText().isEmpty() && !txtEspacio.getText().isEmpty()
               && !timePickerInicio.getValue().equals(null) && !timePickerfinal.getValue().equals(null)
               && ComboEstado.getSelectionModel().isEmpty();
    }
    
    @FXML
    private void Filtrar(ActionEvent event) {
    }
    
    private void typeKeys() {
        txtEspacio.setOnKeyTyped(ClinicaUna.aceptaNumeros);
    }
}
