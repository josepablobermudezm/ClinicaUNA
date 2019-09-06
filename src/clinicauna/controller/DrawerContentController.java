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
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import clinicauna.util.FlowController;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class DrawerContentController extends Controller {

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

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

   
    @Override
    public void initialize() { 
        /*
        btnAgenda.setCursor(Cursor.HAND);
        btnExit.setCursor(Cursor.HAND);
        btnExpediente.setCursor(Cursor.HAND);
        btnMedicos.setCursor(Cursor.HAND);
        btnReportes.setCursor(Cursor.HAND);
        btnUsuarios.setCursor(Cursor.HAND);
        btnPacientes.setCursor(Cursor.HAND);*/
        //btnUsuarios.setDisable(true);
        
        Image img;
        try {
            img = new Image("/clinicauna/resources/user.jpg");
            image.setImage(img);
        } catch (Exception e) {
        }
/*
        Image img7;
        try {
            img7 = new Image("/horarios/resources/user2.png");
            img1.setImage(img7);
        } catch (Exception e) {

        }
        Image img8;
        try {
            img8 = new Image("/horarios/resources/resume.png");
            img2.setImage(img8);
        } catch (Exception e) {
        }
        Image img9;
        try {
            img9 = new Image("/horarios/resources/portfolio.png");
            img3.setImage(img9);
        } catch (Exception e) {
        }
        Image img10;
        try {
            img10 = new Image("/horarios/resources/hiring.png");
            img4.setImage(img10);
        } catch (Exception e) {
        }
        Image img11;
        try {
            img11 = new Image("/horarios/resources/calendar.png");
            img5.setImage(img11);
        } catch (Exception e) {
        }
        Image img12;
        try {
            img12 = new Image("/horarios/resources/logout.png");
            img6.setImage(img12);
        } catch (Exception e) {
        }*/
    }  
    @FXML
    private void Usuarios(ActionEvent event) {
        FlowController.getInstance().goView("Usuarios");
    }

    @FXML
    private void btnPacientes(ActionEvent event) {
        FlowController.getInstance().goView("Pacientes");
    }

    @FXML
    private void btnMedicos(ActionEvent event) {
        FlowController.getInstance().goView("Medicos");
    }

    @FXML
    private void btnAgenda(ActionEvent event) {
       FlowController.getInstance().goView("Agenda");
    }

    @FXML
    private void btnExpediente(ActionEvent event) {
        FlowController.getInstance().goView("ExpedienteMedico");
        
    }

    @FXML
    private void btnReportes(ActionEvent event) {
        FlowController.getInstance().goView("Reportes");
        
    }

}
