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
import clinicauna.util.Correos;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import clinicauna.util.generadorContrasennas;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.SingleSelectionModel;
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
    private JFXComboBox<String> ComboTipoUsuario;
    private JFXComboBox<String> ComboIdioma;
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


    @Override
    public void initialize() {
        btnAgregar1.setCursor(Cursor.HAND);
        btnBuscar.setCursor(Cursor.HAND);
        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);

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
                    String temp = generadorContrasennas.getInstance().getPassword();
                    Long version = usuarioDto.getUsVersion() + 1;
                    String estado = usuarioDto.getEstado();
                    String clave = usuarioDto.getContrasenna();
                    //Integer version = table.getSelectionModel().getSelectedItem().getVersion() + 1;

                    usuarioDto = new UsuarioDto(id, nombre, papellido, estado, sapellido, cedula, correo, nombreusuario, temp, clave, tipoUsuario, idioma, version);
                    try {
                        resp = usuarioService.guardarUsuario(usuarioDto);
                        ms.showModal(Alert.AlertType.INFORMATION, "Informacion de Edición", this.getStage(), resp.getMensaje());
                        limpiarValores();
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
                usuarioService.eliminarUsuario(table.getSelectionModel().getSelectedItem().getID());
                ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Datos Eliminados correctamente");

                Respuesta respuesta = usuarioService.getUsuarios();
                items.clear();
                usuarios = (ArrayList) respuesta.getResultado("Usuarios");
                items = FXCollections.observableArrayList(usuarios);
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
            String tipoUsuario = (btnAdministrador.isSelected()) ? "A" : (btnMedico.isSelected()) ? "M" : "R";
            String idioma = (btnEspanol.isSelected()) ? "E" : "I";
            String nombreusuario = txtNombreUsuario.getText();
            String temp = generadorContrasennas.getInstance().getPassword();
            Long version = new Long(1);
            String clave = txtClave.getText();
            usuarioDto = new UsuarioDto(null, nombre, papellido, "I", sapellido,cedula, correo, nombreusuario, temp, clave, tipoUsuario, idioma, version);
            try {
                resp = usuarioService.guardarUsuario(usuarioDto);
                usuarioDto = (UsuarioDto) resp.getResultado("Usuario");

                if (tipoUsuario.equals("M")) {
                    Long version1 = new Long(1);
                    medicoDto = new MedicoDto(null, null, null, null, "I", null, null, null,usuarioDto, version1 );
                    resp1 = medicoService.guardarMedico(medicoDto);
                    medicoDto = (MedicoDto) resp1.getResultado("Medico");
                }

                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
                usuarios = (ArrayList) usuarioService.getUsuarios().getResultado("Usuarios");

                table.getItems().clear();
                items = FXCollections.observableArrayList(usuarios);
                table.setItems(items);

                //Envia correo de activacion
                resp = usuarioService.activarUsuario(nombreusuario);
                Correos.getInstance().SendMail(correo, resp.getMensaje(), temp);
            } catch (Exception e) {
                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de guardar el usuario...");
            }
        }

    }

    void limpiarValores() {
        txtNombre.clear();
        txtPApellido.clear();
        txtSApellido.clear();
        txtCorreo.clear();
        txtCedula.clear();
        txtNombreUsuario.clear();
        table.getSelectionModel().clearSelection();
        btnAdministrador.setSelected(false);
        btnMedico.setSelected(false);
        btnRecepcionista.setSelected(false);
        btnEspanol.setSelected(false);
        btnIngles.setSelected(false);
        txtClave.clear();
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

}
