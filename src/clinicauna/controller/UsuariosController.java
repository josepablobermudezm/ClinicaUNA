/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.ClinicaUna;
import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.MedicoService;
import clinicauna.service.UsuarioService;
import clinicauna.util.AppContext;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class UsuariosController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private ImageView omg;
    @FXML
    private TableView<UsuarioDto> table;
    @FXML
    private TableColumn<UsuarioDto, String> COL_NOMBRE_USUARIO;
    @FXML
    private TableColumn<UsuarioDto, String> COL_CEDULA_USUARIO;
    @FXML
    private TableColumn<UsuarioDto, String> COL_CORREO_USUARIO;
    @FXML
    private TableColumn<UsuarioDto, String> COL_TIPO_USUARIO;
    @FXML
    private TableColumn<UsuarioDto, String> COL_IDIOMA_USUARIO;
    @FXML
    private TableColumn<UsuarioDto, String> COL_ESTADO_USUARIO;
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
    private JFXButton btnBuscar;
    @FXML
    private TableColumn<UsuarioDto, String> COL_PAPELLIDO_USUARIO;
    @FXML
    private TableColumn<UsuarioDto, String> COL_SAPELLIDO_USUARIO;
    @FXML
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    private UsuarioDto usuarioDto;
    @FXML
    private JFXTextField txtNombreUsuario;
    private Respuesta resp;
    private Respuesta resp1;
    private UsuarioService usuarioService;
    private Mensaje ms;
    private ArrayList<UsuarioDto> usuarios;
    private ObservableList items;
    @FXML
    private JFXTextField txtFiltroUsuario;
    @FXML
    private ToggleGroup idiomagroup;
    @FXML
    private JFXRadioButton btnEspanol;
    @FXML
    private JFXRadioButton btnIngles;
    @FXML
    private JFXRadioButton btnAdministrador;
    @FXML
    private ToggleGroup puesto;
    @FXML
    private JFXRadioButton btnRecepcionista;
    @FXML
    private JFXRadioButton btnMedico;
    private MedicoDto medicoDto;
    private MedicoService medicoService;
    @FXML
    private JFXPasswordField txtClave;
    private ArrayList<MedicoDto> medicos;
    private UsuarioDto usuario;
    private Idioma idioma;

    @Override
    public void initialize() {
        btnAgregar1.setCursor(Cursor.HAND);
        btnBuscar.setCursor(Cursor.HAND);
        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnEditar1.setText(idioma.getProperty("Editar"));
            this.btnBuscar.setText(idioma.getProperty("Buscar"));
            this.btnEliminar1.setText(idioma.getProperty("Eliminar"));
            this.COL_CEDULA_USUARIO.setText("ID");
            this.COL_CORREO_USUARIO.setText(idioma.getProperty("Correo"));
            this.COL_ESTADO_USUARIO.setText(idioma.getProperty("Estado"));
            this.COL_IDIOMA_USUARIO.setText(idioma.getProperty("Idioma"));
            this.Titulo.setText(idioma.getProperty("Mantenimiento") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Usuarios"));
        }
        typeKeys();
        usuarioService = new UsuarioService();
        ms = new Mensaje();
        resp = usuarioService.getUsuarios();
        usuarios = ((ArrayList<UsuarioDto>) resp.getResultado("Usuarios"));

        medicoService = new MedicoService();
        resp1 = medicoService.getMedicos();
        medicos = ((ArrayList<MedicoDto>) resp1.getResultado("Medicos"));

        COL_NOMBRE_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre()));
        COL_CEDULA_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
        COL_CORREO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCorreo()));
        COL_TIPO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTipoUsuario()));
        COL_IDIOMA_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getIdioma()));
        COL_ESTADO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEstado()));
        COL_PAPELLIDO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getpApellido()));
        COL_SAPELLIDO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getsApellido()));
        //System.out.println(usuarios.size());
        items = FXCollections.observableArrayList(usuarios);
        table.setItems(items);

    }

    @FXML
    private void editar(ActionEvent event) {

        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (registroCorrecto()) {
                    Long id = usuarioDto.getID();
                    String nombre = txtNombre.getText();
                    String papellido = txtPApellido.getText();
                    String sapellido = txtSApellido.getText();
                    String correo = txtCorreo.getText();
                    String cedula = txtCedula.getText();
                    String tipoUsuario = (btnAdministrador.isSelected()) ? "A" : (btnMedico.isSelected()) ? "M" : "R";
                    String idioma = (btnEspanol.isSelected()) ? "E" : "I";
                    String nombreusuario = txtNombreUsuario.getText();
                    Long version = usuarioDto.getUsVersion() + 1;
                    String estado = usuarioDto.getEstado();
                    String clave = usuarioDto.getContrasenna();

                    usuarioDto = new UsuarioDto(id, nombre, papellido, estado, sapellido, cedula, correo, nombreusuario, null, clave, tipoUsuario, idioma, version);
                    try {
                        resp = usuarioService.guardarUsuario(usuarioDto);
                        ms.showModal(Alert.AlertType.INFORMATION, "Informacion de Edición", this.getStage(), resp.getMensaje());
                        limpiarRegistro();
                        usuarios = (ArrayList) usuarioService.getUsuarios().getResultado("Usuarios");
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(usuarios);
                        table.setItems(items);
                    } catch (Exception e) {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de editar el usuario.");
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
                resp = usuarioService.eliminarUsuario(table.getSelectionModel().getSelectedItem().getID());
                ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), resp.getMensaje());

                Respuesta respuesta = usuarioService.getUsuarios();
                items.clear();
                usuarios = (ArrayList) respuesta.getResultado("Usuarios");
                items = FXCollections.observableArrayList(usuarios);
                table.setItems(items);
                limpiarRegistro();
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a eliminar");
            }
        }
    }

    @FXML
    private void limpiarRegistro() {
        txtNombre.clear();
        txtPApellido.clear();
        txtSApellido.clear();
        txtCorreo.clear();
        txtCedula.clear();
        txtNombreUsuario.clear();
        table.getSelectionModel().clearSelection();
        btnAdministrador.setSelected(true);
        btnEspanol.setSelected(true);
        txtClave.clear();
    }

    @FXML
    private void agregar(ActionEvent event) {

        if (registroCorrecto()) {

            String tipoUsuario = (btnAdministrador.isSelected()) ? "A" : (btnMedico.isSelected()) ? "M" : "R";
            if ((tipoUsuario.equals("M") && AppContext.getInstance().get("Medico") != null) || !tipoUsuario.equals("M")) {
                String idioma = (btnEspanol.isSelected()) ? "E" : "I";

                String nombre = txtNombre.getText();
                String papellido = txtPApellido.getText();
                String sapellido = txtSApellido.getText();
                String correo = txtCorreo.getText();
                String cedula = txtCedula.getText();

                String nombreusuario = txtNombreUsuario.getText();
                Long version = new Long(1);
                String clave = txtClave.getText();
                usuarioDto = new UsuarioDto(null, nombre, papellido, "I", sapellido, cedula, correo, nombreusuario, null, clave, tipoUsuario, idioma, version);
                try {
                    resp = usuarioService.guardarUsuario(usuarioDto);
                    usuarioDto = (UsuarioDto) resp.getResultado("Usuario");
                    Respuesta resp2 = usuarioService.activarUsuario(usuarioDto.getContrasennaTemp());
                    //Envia correo de activacion
                    Correos.getInstance().mensajeActivacion(nombreusuario, correo, resp2.getMensaje());

                    if (tipoUsuario.equals("M")) {

                        medicoDto = (MedicoDto) AppContext.getInstance().get("Medico");
                        medicoDto.setUs(usuarioDto);
                        medicoService.guardarMedico(medicoDto);
                        AppContext.getInstance().delete("Medico");
                    }

                    ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                    limpiarRegistro();
                    usuarios = (ArrayList) usuarioService.getUsuarios().getResultado("Usuarios");

                    table.getItems().clear();
                    items = FXCollections.observableArrayList(usuarios);
                    table.setItems(items);

                } catch (IOException | MessagingException e) {
                    ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), e.getMessage());
                }
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Informacion de guardado", this.getStage(), "No se ha creado un médico para este usuario, debes crearlo para poder guardar.");
            }
        }
    }

    private void typeKeys() {
        txtNombre.setOnKeyTyped(ClinicaUna.aceptaCaracteres);
        txtPApellido.setOnKeyTyped(ClinicaUna.aceptaCaracteres);
        txtSApellido.setOnKeyTyped(ClinicaUna.aceptaCaracteres);
        txtFiltroUsuario.setOnKeyTyped(ClinicaUna.aceptaNumeros);
    }

    boolean registroCorrecto() {
        return !txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtPApellido.getText().isEmpty() && !txtSApellido.getText().isEmpty()
                && !txtNombreUsuario.getText().isEmpty()
                && !txtCorreo.getText().isEmpty() && (btnIngles.isSelected() || btnEspanol.isSelected()) && (btnAdministrador.isSelected() || btnRecepcionista.isSelected() || btnMedico.isSelected());
    }

    @FXML
    private void DatosUsuario(MouseEvent event) {
        if (table.getSelectionModel() != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                usuarioDto = table.getSelectionModel().getSelectedItem();
                txtNombre.setText(usuarioDto.getNombre());
                txtPApellido.setText(usuarioDto.getpApellido());
                txtSApellido.setText(usuarioDto.getsApellido());
                txtNombreUsuario.setText(usuarioDto.getNombreUsuario());
                txtCorreo.setText(usuarioDto.getCorreo());
                txtCedula.setText(usuarioDto.getCedula());
                txtClave.setText(usuarioDto.getContrasenna());
                if (usuarioDto.getIdioma().equals("E")) {
                    btnEspanol.setSelected(true);
                } else {
                    btnIngles.setSelected(true);
                }
                if (usuarioDto.getTipoUsuario().equals("A")) {
                    btnAdministrador.setSelected(true);
                } else if (usuarioDto.getTipoUsuario().equals("M")) {
                    btnMedico.setSelected(true);
                } else {
                    btnRecepcionista.setSelected(true);
                }
            }
        }
    }

    @FXML
    private void Filtrar(ActionEvent event) {
    }

    @FXML
    private void crearMedico(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("GuardarMedicos", this.getStage(), false);
    }

}
