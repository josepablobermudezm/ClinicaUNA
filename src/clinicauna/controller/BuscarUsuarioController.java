/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.UsuarioDto;
import clinicauna.service.UsuarioService;
import clinicauna.util.AppContext;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author JORDI RODRIGUEZ
 */
public class BuscarUsuarioController extends Controller{

    @FXML
    private Label Titulo;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private TableView<UsuarioDto> Table_Buscar;
    @FXML
    private TableColumn<UsuarioDto, String> Col_Cedula;
    @FXML
    private TableColumn<UsuarioDto, String> Col_Nombre;
    private UsuarioDto usuario;
    private Idioma idioma;
    private UsuarioService service; 
    private ArrayList<UsuarioDto> usuarios;
    private ObservableList items;
    @FXML
    private void ReleasedCedula(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void ReleasedNombre(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void ReleasedApellido(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void MostrarDatos(MouseEvent event) {
        if(Table_Buscar.getSelectionModel()!= null && Table_Buscar.getSelectionModel().getSelectedItem()!=null){
            AppContext.getInstance().set("Us", Table_Buscar.getSelectionModel().getSelectedItem());
            FlowController.getInstance().initialize();
            this.getStage().close();
        }
    }

    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if(usuario.getIdioma().equals("I")){
            this.txtNombre.setPromptText(idioma.getProperty("Nombre"));
            this.txtCedula.setPromptText(idioma.getProperty("Cedula"));
            this.txtApellido.setPromptText(idioma.getProperty("Apellido"));
            this.Col_Cedula.setText(idioma.getProperty("Cedula"));
            this.Col_Nombre.setText(idioma.getProperty("Nombre"));
            this.Titulo.setText(idioma.getProperty("Ch")+" "+idioma.getProperty("un")+" "+idioma.getProperty("Usuario"));
        }
        Col_Nombre.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre() + " " + value.getValue().getpApellido() + " " + value.getValue().getsApellido()));
        Col_Cedula.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCedula()));
        Buscar();
    }
     public void Buscar(){
         String ced = (!txtCedula.getText().isEmpty()) ? "%" + txtCedula.getText().toUpperCase() + "%" : "%";
        String nom = (!txtNombre.getText().isEmpty()) ? "%" + txtNombre.getText().toUpperCase() + "%" : "%";
        String apellido = (!txtApellido.getText().isEmpty()) ? "%" + txtApellido.getText().toUpperCase() + "%" : "%";

        service = new UsuarioService();
        Respuesta resp = service.getUsuarios(ced, nom, apellido);

        if (resp.getEstado()) {
            usuarios = (ArrayList<UsuarioDto>) resp.getResultado("Usuarios");
            items = FXCollections.observableArrayList(usuarios);
            Table_Buscar.setItems(items);
        }else{
            System.out.println(resp.getMensaje());
        }
     }
}
