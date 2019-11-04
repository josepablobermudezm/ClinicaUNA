/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.MedicoService;
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
public class BuscarMedicoController extends Controller {

    @FXML
    private Label Titulo;
    @FXML
    private TableView<MedicoDto> Table_Buscar;
    @FXML
    private TableColumn<MedicoDto, String> Col_Nom;
    @FXML
    private TableColumn<MedicoDto, String> Col_Folio;
    @FXML
    private TableColumn<MedicoDto, String> Col_Codigo;
    @FXML
    private TableColumn<MedicoDto, String> Col_Carne;
    private UsuarioDto usuario;
    private Idioma idioma;
    @FXML
    private JFXTextField txtFolio;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtCarne;
    private MedicoService service;
    private ArrayList<MedicoDto> medicos;
    private ObservableList items;
    
    @FXML
    private void ReleasedFolio(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void ReleasedCodigo(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void ReleasedCarne(KeyEvent event) {
        Buscar();
    }

    @FXML
    private void MostrarDatos(MouseEvent event) {
        if(Table_Buscar.getSelectionModel()!= null && Table_Buscar.getSelectionModel().getSelectedItem()!=null){
            AppContext.getInstance().set("Med", Table_Buscar.getSelectionModel().getSelectedItem());
            FlowController.getInstance().initialize();
            this.getStage().close();
        }
    }

    @Override
    public void initialize() {
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if(usuario.getIdioma().equals("I")){
            this.Titulo.setText(idioma.getProperty("Seleccione")+" "+idioma.getProperty("un")+" "+idioma.getProperty("Medico"));
            this.Col_Codigo.setText(idioma.getProperty("Código"));
            this.Col_Carne.setText(idioma.getProperty("Carné"));
            this.Col_Nom.setText(idioma.getProperty("Nombre"));
            this.txtCarne.setPromptText(idioma.getProperty("Carné"));
            this.txtCodigo.setPromptText(idioma.getProperty("Código"));
        }
        this.Col_Nom.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getUs().getNombre()+" "+value.getValue().getUs().getpApellido()+" "+ value.getValue().getUs().getsApellido()));
        this.Col_Carne.setCellValueFactory(value-> new SimpleStringProperty(value.getValue().getCarne()));
        this.Col_Codigo.setCellValueFactory(value-> new SimpleStringProperty(value.getValue().getCodigo()));
        this.Col_Folio.setCellValueFactory(value-> new SimpleStringProperty(value.getValue().getFolio()));
        Buscar();
    }
     public void Buscar(){
        String cod = (!txtCodigo.getText().isEmpty()) ? "%" + txtCodigo.getText().toUpperCase() + "%" : "%";
        String carne = (!txtCarne.getText().isEmpty()) ? "%" + txtCarne.getText().toUpperCase() + "%" : "%";
        String folio = (!txtFolio.getText().isEmpty()) ? "%" + txtFolio.getText().toUpperCase() + "%" : "%";

        service = new MedicoService();
        Respuesta resp = service.getMedicos(cod, carne, folio);

        if (resp.getEstado()) {
            medicos = (ArrayList<MedicoDto>) resp.getResultado("Medicos");
            items = FXCollections.observableArrayList(medicos);
            Table_Buscar.setItems(items);
        }else{
            System.out.println(resp.getMensaje());
        }
     }
}
