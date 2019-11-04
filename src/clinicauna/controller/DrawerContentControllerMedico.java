/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import com.jfoenix.controls.JFXButton;
import clinicauna.util.AppContext;
import clinicauna.ClinicaUna;
import clinicauna.controller.Controller;
import clinicauna.model.UsuarioDto;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import clinicauna.util.FlowController;
import clinicauna.util.Idioma;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class DrawerContentControllerMedico extends Controller {

    @FXML
    private ImageView image;
    static public File filePath2;
    @FXML
    private JFXButton btnExit;
    @FXML
    private JFXButton btnPacientes;
    @FXML
    private JFXButton btnMedicos;
    @FXML
    private JFXButton btnAgenda;
    @FXML
    private JFXButton btnExpediente;
    @FXML
    private JFXButton btnReportes;
    @FXML
    private JFXButton btnUsuarios;
    private Idioma idioma;
    private UsuarioDto usuario;

    @FXML
    private void exit(ActionEvent event) {
        FlowController.getInstance().salir();
        AppContext.getInstance().delete("Pact");
        AppContext.getInstance().delete("Expediente");
        AppContext.getInstance().delete("UsuarioActivo");
        AppContext.getInstance().delete("MedicoDto");
        AppContext.getInstance().delete("Med");
        AppContext.getInstance().delete("Medi");
        FlowController.getInstance().goViewInWindowTransparent("LogIn");

    }

    @Override
    public void initialize() {

        idioma = (Idioma) AppContext.getInstance().get("idioma");
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        if (usuario.getIdioma().equals("I")) {
            btnAgenda.setText(idioma.getProperty("Agenda"));
            btnExit.setText(idioma.getProperty("Salir"));
            btnExpediente.setText(idioma.getProperty("MedExp") + " " + idioma.getProperty("Expediente"));
            //btnMedicos.setText(idioma.getProperty("Medicos"));
            btnReportes.setText(idioma.getProperty("Reportes"));
            //btnUsuarios.setText(idioma.getProperty("Usuarios"));
            btnPacientes.setText(idioma.getProperty("Pacientes"));
        }
        Image img;
        try {
            img = new Image("/clinicauna/resources/user.jpg");
            image.setImage(img);
        } catch (Exception e) {
        }
    }

    @FXML
    private void Usuarios(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goView("Usuarios");
    }

    @FXML
    private void btnPacientes(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goView("Pacientes");
    }

    @FXML
    private void btnMedicos(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goView("Medicos");
    }

    @FXML
    private void btnAgenda(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goView("AgendaMedica");
    }

    @FXML
    private void btnExpediente(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goView("ExpedienteMedico");
    }

    @FXML
    private void btnReportes(ActionEvent event) {
        FlowController.getInstance().initialize();
        FlowController.getInstance().goView("Reportes");

    }

}
