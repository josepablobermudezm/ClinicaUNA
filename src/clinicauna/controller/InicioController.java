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
    @FXML
    private JFXHamburger hamburger;
    /**/
    private VBox vBoxCentral;
    @FXML
    private StackPane st;

    @Override
    public void initialize() {
        
        
        AppContext.getInstance().set("Border", borderPane);
        
        borderPane.heightProperty().addListener((observable) -> {
           /* borderPane.set
            System.out.println("HeigProperti "+ borderPane.getHeight());*/
        });
        borderPane.prefHeightProperty().bindBidirectional(((VBox) borderPane.getCenter()).prefHeightProperty());
        borderPane.prefHeightProperty().addListener((observable) -> {
            //System.out.println("PrefHeigProperti "+ borderPane.getHeight());
            //borderPane.
        });
//borderPane.prefWidthProperty().bindBidirectional(other);
        Image omg1;
        try {
/*            omg1 = new Image("/clinicauna/resources/background2.jpg");
            
            omg.setImage(omg1);*/
        } catch (Exception e) {
        }
        /*omg.fitHeightProperty().bind(st.heightProperty());//  para que la imagen tenga el tamaño de toda la vista
        omg.fitWidthProperty().bind(st.widthProperty());*/
        
        st.heightProperty().addListener((observable) -> {
            
//            omg.getImage();
        });
        
        VBox box = null;
        try {
            UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
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
            
            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask2.setRate(-1);
            drawer.open();
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();

                if (drawer.isShown()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    public void SetBorderPane(BorderPane pane) {
        this.Panel = pane;
        Listener();
    }

    public void Listener() {
        Panel.widthProperty().addListener(x -> {
            this.drawer.setPrefWidth(Panel.getWidth() - 200);
        });

        Panel.heightProperty().addListener(v -> {

        });
    }*/
}
