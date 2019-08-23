/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class PacientesController extends Controller  {

    @FXML
    private ImageView omg;
    @FXML
    private Label Titulo;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, String> COL_NOMBRE_USUARIO;
    @FXML
    private TableColumn<?, String> COL_CEDULA_USUARIO;
    @FXML
    private TableColumn<?, String> COL_CORREO_USUARIO;
    @FXML
    private TableColumn<?, String> COL_TIPO_USUARIO;
    @FXML
    private TableColumn<?, String> COL_IDIOMA_USUARIO;
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
    private JFXTextField txtFiltroEmpleado;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXComboBox<?> ComboTipoUsuario;
    @FXML
    private JFXComboBox<?> ComboIdioma;
    @FXML
    private JFXComboBox<?> ComboEstado;  

    
    
    @Override
    public void initialize() {
        
        Image omg1;
        try {
            omg1 = new Image("/clinicauna/resources/background.jpg");
            omg.setImage(omg1);
            
        } catch (Exception e) {}

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
    }

    @FXML
    private void Filtrar(ActionEvent event) {
    }

    
}
