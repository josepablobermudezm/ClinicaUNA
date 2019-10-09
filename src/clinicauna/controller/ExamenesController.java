/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.ControlDto;
import clinicauna.model.ExamenDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.PacienteDto;
import clinicauna.service.ControlService;
import clinicauna.service.ExamenService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
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
    private JFXTextField txtNombre;
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

    @Override
    public void initialize() {
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
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (RegistroCorrecto()) {
            
            String anotaciones = txtAnotaciones.getText();
            String nombre = txtNombreExamen.getText();
            LocalDate fecha = Fecha.getValue();
            Long version = new Long(1);
            examenDto = new ExamenDto(null,nombre,fecha, anotaciones,version,expedienteDto);
            try {
                resp = examenService.guardarExamen(examenDto);

                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
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
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el examen....");
            }

        } else {
            ms.showModal(Alert.AlertType.ERROR, "Informacion acerca del usuario guardado", this.getStage(), "Existen datos erroneos en el registro, "
                    + "verifica que todos los datos esten llenos.");
        }
    }

    @FXML
    private void datos(MouseEvent event) {
        
        
        
    }
    
    boolean RegistroCorrecto() {
        return !txtAnotaciones.getText().isEmpty() && !txtNombreExamen.getText().isEmpty() && Fecha.getValue() != null;
    }
    
    private void limpiarRegistro(){
        txtAnotaciones.clear();
        txtNombreExamen.clear();
        Fecha.setValue(null);
    }
    
    public void Formato() {
        this.txtAnotaciones.setTextFormatter(Formato.getInstance().maxLengthFormat(150));
        this.txtNombreExamen.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
    }
}
