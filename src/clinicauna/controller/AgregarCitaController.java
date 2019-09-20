/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.CitaDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.CitaService;
import clinicauna.service.PacienteService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

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
    @FXML
    private JFXTextArea txtmotivo;
    @FXML
    private ToggleGroup estado;
    @FXML
    private Label lblEstado;
    @FXML
    private JFXRadioButton btnProgramada;
    @FXML
    private JFXRadioButton btnAtendida;
    @FXML
    private JFXRadioButton btnCancelada;
    @FXML
    private JFXRadioButton btnAusente;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private JFXButton btnCancelar;
    private Respuesta resp;
    private Respuesta resp1;
    private PacienteService pacienteService;
    private CitaService citaService;
    private PacienteDto pacienteDto;
    private CitaDto citaDto;
    private Mensaje ms;
    private Idioma idioma;
    private UsuarioDto usuario;
    private ArrayList<PacienteDto> lista;

    @Override
    public void initialize() {
        pacienteService = new PacienteService();
        resp = pacienteService.getPacientes();
        ms = new Mensaje();
        citaService = new CitaService();
        resp1 = citaService.getCitas();

        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnGuardar.setText(idioma.getProperty("Guardar"));
            this.btnAtendida.setText(idioma.getProperty("Atendida"));
            this.btnAusente.setText(idioma.getProperty("Ausente"));
            this.btnCancelada.setText(idioma.getProperty("Cancelada"));
            this.btnCancelar.setText(idioma.getProperty("Cancelar"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.btnProgramada.setText(idioma.getProperty("Programada"));
            this.Titulo.setText(idioma.getProperty("Agendar") + " " + idioma.getProperty("ACita"));
            this.ComboPacientes.setPromptText(idioma.getProperty("Pacientes"));
            this.lblEstado.setText(idioma.getProperty("Estado"));
            this.txtCorreo.setPromptText(idioma.getProperty("Correo"));
            this.txtTelefono.setPromptText(idioma.getProperty("Telefono"));
            this.txtmotivo.setPromptText(idioma.getProperty("Motivo"));
        }
        lista = (ArrayList<PacienteDto>) resp.getResultado("Pacientes");
        ObservableList<String> items = FXCollections.observableArrayList(lista.stream().map(x -> x.getNombre()
                + " " + x.getpApellido() + " " + x.getsApellido() + " Ced:" + x.getCedula())
                .collect(Collectors.toList()));
        ComboPacientes.setItems(items);
    }

    @FXML
    private void guardar(ActionEvent event) {

        if (registroCorrecto()) {

            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String motivo = txtmotivo.getText();
            Long version = new Long(1);
            String estado = (btnProgramada.isSelected()) ? "PR" : (btnAtendida.isSelected()) ? "AT" : (btnAusente.isSelected()) ? "AU" : "CA";
            citaDto = new CitaDto(null,version, pacienteDto,motivo,estado);
            try {
                resp1 = citaService.guardarCita(citaDto);
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el paciente...");
            }
        }else{
            System.out.println("no entre");
        }
    }

    private static boolean cedulaEncontrada = false;
    private static String cedulaBuscar = "";

    @FXML
    private void seleccionarPaciente(ActionEvent event) {
        if (ComboPacientes.getSelectionModel() != null && ComboPacientes.getSelectionModel().getSelectedItem() != null) {
            String paciente = ComboPacientes.getSelectionModel().getSelectedItem();
            paciente.chars().forEach(x -> {
                if (((char) x) == ':') {
                    cedulaEncontrada = true;
                } else if (cedulaEncontrada) {
                    cedulaBuscar = cedulaBuscar.concat(Character.toString((char) x));
                }
            });
            pacienteDto = lista.stream().filter(x -> x.getCedula().equals(cedulaBuscar)).findAny().get();
            cedulaBuscar = "";
            cedulaEncontrada = false;
        }
    }

    void limpiarValores() {
        txtCorreo.clear();
        txtTelefono.clear();
        ComboPacientes.getSelectionModel().clearSelection();
    }

    boolean registroCorrecto() {
        return !txtCorreo.getText().isEmpty() && !txtTelefono.getText().isEmpty()
                && ComboPacientes.getSelectionModel() != null;
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
