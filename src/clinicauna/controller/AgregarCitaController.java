/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.CitaDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.service.CitaService;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class AgregarCitaController extends Controller {

    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXComboBox<String> ComboPacientes;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label Titulo;
    private Respuesta resp;
    private Respuesta resp1;
    private PacienteService pacienteService;
    private CitaService citaService;
    private PacienteDto pacienteDto;
    private CitaDto citaDto;
    private Mensaje ms;
    @FXML
    private JFXTextArea txtmotivo;
    private ArrayList<PacienteDto> lista;

    @Override
    public void initialize() {
        pacienteService = new PacienteService();
        resp = pacienteService.getPacientes();
        citaService = new CitaService();
        resp1 = citaService.getCitas();
        
        lista = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");
        ObservableList<String> items = FXCollections.observableArrayList(lista.stream().map(x->x.getNombre() 
                + " " + x.getpApellido() + " " + x.getsApellido()+ " Ced:" + x.getCedula())
                .collect(Collectors.toList()));
        ComboPacientes.setItems(items);
    }

    @FXML
    private void guardar(ActionEvent event) {
        
        MedicoDto Medico = (MedicoDto) AppContext.getInstance().get("");
        
        String info = ComboPacientes.getValue().toString();
        System.out.println(info);
        if (registroCorrecto()) {
        
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String motivo = txtmotivo.getText();
            Long version = new Long(1);
            //citaDto = new CitaDto(null,version, pacienteDto, /*espacio Por hora */,motivo,);
            try {
                resp = citaService.guardarCita(citaDto);
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
            }
        }
    }
    
    private static boolean cedulaEncontrada=false;
    private static String cedulaBuscar="";
    @FXML
    private void seleccionarPaciente(ActionEvent event) {
        if(ComboPacientes.getSelectionModel()!=null && ComboPacientes.getSelectionModel().getSelectedItem()!=null){
            String paciente = ComboPacientes.getSelectionModel().getSelectedItem();
            paciente.chars().forEach(x->{
                if(((char) x)==':'){
                    cedulaEncontrada = true;
                }
                else if(cedulaEncontrada){
                    cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                }
            });
            pacienteDto = lista.stream().filter(x->x.getCedula().equals(cedulaBuscar)).findAny().get();
            cedulaBuscar = "";  
            cedulaEncontrada =false;
        }
    }
    
    void limpiarValores() {
        txtCorreo.clear();
        txtTelefono.clear();
        ComboPacientes.getSelectionModel().clearSelection();
    }
    
    boolean registroCorrecto() {
        return !txtCorreo.getText().isEmpty() && !txtTelefono.getText().isEmpty()
                && ComboPacientes.getItems().isEmpty();
    }

    @FXML
    private void limpiarRegistro(ActionEvent event) {
    }

    @FXML
    private void cancela(ActionEvent event) {
        FlowController.getInstance().initialize();
        this.getStage().close();
    }
    
}
