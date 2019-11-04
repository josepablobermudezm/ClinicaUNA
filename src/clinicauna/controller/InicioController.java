/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.UsuarioDto;
import clinicauna.util.AppContext;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jose Pablo Bermudez
 */
public class InicioController extends Controller {

    private Label label;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane anchorp;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Label Titulo;
    /**/
    private VBox vBoxCentral;
    @FXML
    private StackPane st;

    @Override
    public void initialize() {

        AppContext.getInstance().set("Border", borderPane);

        VBox box = null;
        try {
            UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            /*
            *   Abrimos un drawer content especifico para cada usuario
            */
            switch (usuario.getTipoUsuario()) {
                case "A": {
                    box = FXMLLoader.load(getClass().getResource("/clinicauna/view/drawerContent.fxml"));
                    break;
                }
                case "M": {
                    box = FXMLLoader.load(getClass().getResource("/clinicauna/view/drawerContent_Medico.fxml"));
                    break;
                }
                case "R": {
                    box = FXMLLoader.load(getClass().getResource("/clinicauna/view/drawerContent_Recepcionista.fxml"));
                    break;
                }
            }
            drawer.setSidePane(box);
            drawer.open();
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
