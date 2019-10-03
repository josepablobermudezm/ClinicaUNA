/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.CitaDto;
import clinicauna.model.PacienteDto;
import static java.util.Collections.list;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jose Pablo Bermudez
 */

public class vistaCita{
    
    private VBox vBox = new VBox();
    private Label Nombre =  new Label(); 
    private Label Correo =  new Label(); 
    private Label Telefono =  new Label(); 
    private CitaDto cita;
    
    public vistaCita(){
        
    }
    public vistaCita(CitaDto cita){
        super();
        this.cita = cita;
        this.Nombre.setText(cita.getPaciente().getNombre() +" " +cita.getPaciente().getpApellido() +" " +cita.getPaciente().getsApellido());
        this.Correo.setText(cita.getCorreo());
        this.Telefono.setText(cita.getTelefono());
        this.vBox.getChildren().addAll(Nombre,Correo,Telefono);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(10);
        this.vBox.setOnMouseReleased(x->{
            System.out.println(this.cita.toString());
        });
        
        //this.getChildren().addAll(vBox);
        //System.out.println(this.getChildren().size());
    }
    
    
    public VBox get(double Width){
    
        this.vBox.setPrefWidth(Width);
      
        return this.vBox;
    }
    

}
