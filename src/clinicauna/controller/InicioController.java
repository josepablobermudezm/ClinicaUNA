/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.util.AppContext;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class InicioController extends Controller  {
    
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
    @FXML
    private ImageView omg;
    private BorderPane Panel = new BorderPane();
    private AnchorPane an;
    /**/
    @FXML
    private StackPane st;
    
    
    
    @Override
    public void initialize() {
        AppContext.getInstance().set("Border", borderPane);       
        Image omg1;
        try {
            omg1 = new Image("/clinicauna/resources/background2.jpg");
            omg.setImage(omg1);
        } catch (Exception e) {
        }
        
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/clinicauna/view/drawerContent.fxml"));
            drawer.setSidePane(box);          
            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask2.setRate(-1);
            drawer.open();
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                burgerTask2.setRate(burgerTask2.getRate()*-1);
                burgerTask2.play();
                
                if(drawer.isShown())
                    drawer.close();
                else{
                    drawer.open();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    
    public void SetBorderPane(BorderPane pane){
        this.Panel = pane;
        Listener();
    }
    public void Listener(){
        Panel.widthProperty().addListener(x->{
           this.drawer.setPrefWidth(Panel.getWidth()-200);
        });
        
        Panel.heightProperty().addListener(v->{
            
        });
    }
    public void initialize(URL url, ResourceBundle rb) {
        omg.fitHeightProperty().bind(st.heightProperty());//  para que la imagen tenga el tamaño de toda la vista
        omg.fitWidthProperty().bind(st.widthProperty());
    } 
}
