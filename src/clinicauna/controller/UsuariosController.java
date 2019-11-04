/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.MedicoService;
import clinicauna.service.UsuarioService;
import clinicauna.util.AppContext;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    @FXML
    private JFXTextField txtNombreUsuario;
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
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXButton btnLimpiarRegistro;
    @FXML
    private Label lblidioma;
    @FXML
    private Label lblTipo;
    private Respuesta resp;
    private UsuarioService usuarioService;
    private Mensaje ms;
    private ArrayList<UsuarioDto> usuarios;
    private ObservableList items;
    private UsuarioDto usuarioDto;
    private MedicoDto medicoDto;
    private MedicoService medicoService;
    private UsuarioDto usuario;
    private Idioma idioma;
    private UsuarioDto us;
    @FXML
    private JFXButton btnBuscar;

    @Override
    public void initialize() {
        Formato();
        btnAgregar1.setCursor(Cursor.HAND);
        btnEditar1.setCursor(Cursor.HAND);
        btnEliminar1.setCursor(Cursor.HAND);
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            this.btnBuscar.setText(idioma.getProperty("Buscar"));
            this.btnEditar1.setText(idioma.getProperty("Editar"));
            this.btnEliminar1.setText(idioma.getProperty("Eliminar"));
            this.btnAdministrador.setText(idioma.getProperty("Administrador"));
            this.btnAgregar1.setText(idioma.getProperty("Agregar"));
            this.btnMedico.setText(idioma.getProperty("Medico"));
            this.btnIngles.setText(idioma.getProperty("Ingles"));
            this.btnEspanol.setText(idioma.getProperty("Español"));
            this.btnRecepcionista.setText(idioma.getProperty("Recepcionista"));
            this.btnLimpiarRegistro.setText(idioma.getProperty("Limpiar") + " " + idioma.getProperty("Registro"));
            this.COL_NOMBRE_USUARIO.setText(idioma.getProperty("Nombre"));
            this.COL_CEDULA_USUARIO.setText("ID");
            this.COL_CORREO_USUARIO.setText(idioma.getProperty("Correo"));
            this.COL_ESTADO_USUARIO.setText(idioma.getProperty("Estado"));
            this.COL_IDIOMA_USUARIO.setText(idioma.getProperty("Idioma"));
            this.COL_TIPO_USUARIO.setText(idioma.getProperty("Tipo") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Usuario"));
            this.txtCedula.setPromptText("ID");
            this.txtClave.setPromptText(idioma.getProperty("Contraseña"));
            this.txtCorreo.setPromptText(idioma.getProperty("Correo"));

            this.txtNombre.setPromptText(idioma.getProperty("Nombre"));
            this.txtPApellido.setPromptText(idioma.getProperty("Primero") + " " + idioma.getProperty("Apellido"));
            this.txtSApellido.setPromptText(idioma.getProperty("Segundo") + " " + idioma.getProperty("Apellido"));
            this.txtNombreUsuario.setPromptText(idioma.getProperty("Usuario"));
            this.lblTipo.setText(idioma.getProperty("Tipo"));
            this.lblidioma.setText(idioma.getProperty("Idioma"));
            this.Titulo.setText(idioma.getProperty("Mantenimiento") + " " + idioma.getProperty("de") + " " + idioma.getProperty("Usuarios"));
        }

        usuarioService = new UsuarioService();
        ms = new Mensaje();
        resp = usuarioService.getUsuarios();
        usuarios = ((ArrayList<UsuarioDto>) resp.getResultado("Usuarios"));
        medicoService = new MedicoService();
        COL_NOMBRE_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre() + " " + value.getValue().getpApellido() + " " + value.getValue().getsApellido()));
        COL_CEDULA_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
        COL_CORREO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCorreo()));
        COL_TIPO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTipoUsuario().equals("M") ? "Médico" : value.getValue().getTipoUsuario().equals("A") ? "Administrador" : "Recepcionista"));
        COL_IDIOMA_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getIdioma().equals("I") ? "Inglés" : "Español"));
        COL_ESTADO_USUARIO.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEstado().equals("A") ? "Activo" : "Inactivo"));
        items = FXCollections.observableArrayList(usuarios);
        table.setItems(items);

    }

    @FXML
    private void editar(ActionEvent event) {
        if (table.getSelectionModel() != null || AppContext.getInstance().get("Us") != null) {
            if (table.getSelectionModel().getSelectedItem() != null || AppContext.getInstance().get("Us") != null) {
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
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.INFORMATION, "Edtion Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.INFORMATION, "Informacion de Edición", this.getStage(), resp.getMensaje());
                        }
                        limpiarRegistro();
                        usuarios = (ArrayList) usuarioService.getUsuarios().getResultado("Usuarios");
                        table.getItems().clear();
                        items = FXCollections.observableArrayList(usuarios);
                        table.setItems(items);
                    } catch (Exception e) {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), "There was an error editing the user");
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), "Hubo un error al momento de editar el usuario.");
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
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the user to edit");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a editar");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the user to edit");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a editar");
            }
        }

    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (table.getSelectionModel() != null || AppContext.getInstance().get("Us") != null) {
            if (table.getSelectionModel().getSelectedItem() != null || AppContext.getInstance().get("Us") != null) {
                if (AppContext.getInstance().get("Us") != null && table.getSelectionModel().getSelectedItem() == null) {
                    resp = usuarioService.eliminarUsuario(usuarioDto.getID());
                } else {
                    resp = usuarioService.eliminarUsuario(table.getSelectionModel().getSelectedItem().getID());
                }
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.INFORMATION, "Information", this.getStage(), resp.getMensaje());
                } else {
                    ms.showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), resp.getMensaje());
                }
                Respuesta respuesta = usuarioService.getUsuarios();
                items.clear();
                usuarios = (ArrayList) respuesta.getResultado("Usuarios");
                items = FXCollections.observableArrayList(usuarios);
                table.setItems(items);
                limpiarRegistro();
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the user to delete");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar el elemento a eliminar");
                }
            }
        } else {
            if (usuario.getIdioma().equals("I")) {
                ms.showModal(Alert.AlertType.WARNING, "Information", this.getStage(), "You must select the patient");
            } else {
                ms.showModal(Alert.AlertType.WARNING, "Información", this.getStage(), "Debes seleccionar un paciente");
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
            /*
                Si el tipo de usuario es un médico tenemos que abrir la ventana de médicos para que le agregue la información necesaria
            */
            if(tipoUsuario.equals("M")){
                FlowController.getInstance().goViewInWindowModal("GuardarMedicos", this.getStage(), false);
            }
            //validarmos que se haya creado correctamente el médico
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
                    if (resp.getEstado()) {
                        usuarioDto = (UsuarioDto) resp.getResultado("Usuario");

                        if (tipoUsuario.equals("M")) {
                            medicoDto = (MedicoDto) AppContext.getInstance().get("Medico");
                            medicoDto.setUs(usuarioDto);
                            resp = medicoService.guardarMedico(medicoDto);
                            if (!resp.getEstado()) {
                                usuarioService.eliminarUsuario(usuarioDto.getID());
                                if (usuario.getIdioma().equals("I")) {
                                    ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                                } else {
                                    ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                                }
                            } else {
                                AppContext.getInstance().delete("Medico");
                            }
                        }

                        Respuesta resp2 = usuarioService.activarUsuario(usuarioDto.getContrasennaTemp());
                        //Envia correo de activación
                        Correos mail = new Correos();
                        mail.mensajeActivacionHilo(nombreusuario, correo, resp2.getMensaje());
                        FlowController.getInstance().goViewInWindowModalCorreo("VistaCargando", this.getStage(), false);
                        resp = mail.getResp();
                        if (resp.getEstado()) {
                            if (usuario.getIdioma().equals("I")) {
                                ms.showModal(Alert.AlertType.INFORMATION, "Saved Information", this.getStage(), resp.getMensaje());
                            } else {
                                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                            }
                            limpiarRegistro();
                            usuarios = (ArrayList) usuarioService.getUsuarios().getResultado("Usuarios");
                            table.getItems().clear();
                            items = FXCollections.observableArrayList(usuarios);
                            table.setItems(items);
                        } else {
                            usuarioService.eliminarUsuario(usuarioDto.getID());
                            if (usuario.getIdioma().equals("I")) {
                                ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                            } else {
                                ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                            }
                        }
                    } else {
                        if (usuario.getIdioma().equals("I")) {
                            ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                        } else {
                            ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                        }
                    }

                } catch (Exception e) {
                    if (usuario.getIdioma().equals("I")) {
                        ms.showModal(Alert.AlertType.ERROR, "Saved Information", this.getStage(), resp.getMensaje());
                    } else {
                        ms.showModal(Alert.AlertType.ERROR, "Informacion de guardado", this.getStage(), resp.getMensaje());
                    }
                }
            } else {
                if (usuario.getIdioma().equals("I")) {
                    ms.showModal(Alert.AlertType.WARNING, "Saved Information", this.getStage(), "A doctor has not been created for this user, you must create it in order to save.");
                } else {
                    ms.showModal(Alert.AlertType.WARNING, "Informacion de guardado", this.getStage(), "No se ha creado un médico para este usuario, debes crearlo para poder guardar.");
                }
            }
        }
    }

    public void Formato() {
        this.txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(15));
        this.txtClave.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
        this.txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
        this.txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(50));
        this.txtPApellido.setTextFormatter(Formato.getInstance().letrasFormat(50));
        this.txtSApellido.setTextFormatter(Formato.getInstance().letrasFormat(50));
        this.txtNombreUsuario.setTextFormatter(Formato.getInstance().maxLengthFormat(30));
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
    private void crearMedico(ActionEvent event) {
        
    }

    @FXML
    private void BuscarUsuarios(ActionEvent event) {
        AppContext.getInstance().delete("Us");
        FlowController.getInstance().goViewInWindowModal("BuscarUsuario", this.getStage(), false);
        DatosUsuario();
    }

    public void DatosUsuario() {
        if (AppContext.getInstance().get("Us") != null) {
            us = (UsuarioDto) AppContext.getInstance().get("Us");
            usuarioDto = us;
            this.txtCedula.setText(us.getCedula());
            this.txtClave.setText(us.getContrasenna());
            this.txtCorreo.setText(us.getCorreo());
            this.txtNombre.setText(us.getNombre());
            this.txtNombreUsuario.setText(us.getNombreUsuario());
            this.txtPApellido.setText(us.getpApellido());
            this.txtSApellido.setText(us.getsApellido());
        }
    }
}
