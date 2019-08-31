/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.ClinicaUna;
import clinicauna.model.UsuarioDto;
import clinicauna.service.UsuarioService;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import clinicauna.util.generadorContrasennas;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
public class UsuariosController extends Controller  {

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
    private JFXComboBox<String> ComboTipoUsuario;
    @FXML
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
    private UsuarioService usuarioService;
    private Mensaje ms;
    private ArrayList<UsuarioDto> usuarios;
    private ObservableList items;
    @FXML
    private JFXTextField txtFiltroUsuario;
    
    @Override
    public void initialize() {
        
        typeKeys();
        usuarioService = new UsuarioService();
        ms = new Mensaje();
        resp = usuarioService.getUsuarios();
        usuarios = ((ArrayList<UsuarioDto>) resp.getResultado("Usuarios"));
        
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
        
        ObservableList<String> idioma = FXCollections.observableArrayList("Ingles","Español");
        ObservableList<String> tipo = FXCollections.observableArrayList("Administrador","Medico","Recepcionista");
        ComboIdioma.setItems(idioma);
        ComboTipoUsuario.setItems(tipo);
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
        
            String nombre = txtNombre.getText();
            String papellido = txtPApellido.getText();
            String sapellido = txtSApellido.getText();
            String correo = txtCorreo.getText();
            String cedula = txtCedula.getText();
            String tipoUsuario = (ComboTipoUsuario.getSelectionModel().getSelectedItem().equals("Administrador"))?"A":(ComboTipoUsuario.getSelectionModel().equals("Medico"))?"M":"R";
            String idioma = (ComboIdioma.getSelectionModel().getSelectedItem().equals("Español"))?"E":"I";
            String nombreusuario = txtNombreUsuario.getText();
            String temp = generadorContrasennas.getInstance().getPassword();
            
            usuarioDto = new UsuarioDto(null,nombre, papellido, sapellido,"I",cedula, correo,nombreusuario, temp,null, tipoUsuario, idioma );
            try {
                resp = usuarioService.guardarUsuario(usuarioDto);
                ms.showModal(Alert.AlertType.INFORMATION, "Informacion de guardado", this.getStage(), resp.getMensaje());
                limpiarValores();
                usuarios = (ArrayList) usuarioService.getUsuarios().getResultado("Usuarios");
                table.getItems().clear();
                items = FXCollections.observableArrayList(usuarios);
                table.setItems(items);
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
        ComboIdioma.getSelectionModel().clearSelection();
        ComboTipoUsuario.getSelectionModel().clearSelection();
        table.getSelectionModel().clearSelection();
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
               && !ComboIdioma.getSelectionModel().isEmpty() && !txtNombreUsuario.getText().isEmpty()
               && !ComboTipoUsuario.getSelectionModel().isEmpty() && !txtCorreo.getText().isEmpty();
    }

    @FXML
    private void Filtrar(ActionEvent event) {
    }
    
}
