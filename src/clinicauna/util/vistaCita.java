/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.CitaDto;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jose Pablo Bermudez
 */
public class vistaCita extends HBox {

    private VBox vBox = new VBox();
    private Label Nombre = new Label();
    private Label Correo = new Label();
    private Label Telefono = new Label();
    private CitaDto cita;

    public vistaCita() {

    }


    public void AgregarCita(CitaDto cita) {
        this.Nombre.setText(cita.getPaciente().getNombre() + " " + cita.getPaciente().getpApellido() + " " + cita.getPaciente().getsApellido());
        this.Correo.setText(cita.getCorreo());
        this.Telefono.setText(cita.getTelefono());
        this.vBox.getChildren().clear();
        this.vBox.getChildren().addAll(Nombre, Correo, Telefono);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(10);
        String style = "-fx-text-fill: #636361;";
        this.Nombre.setStyle(style);
        this.Correo.setStyle(style);
        this.Telefono.setStyle(style);
        this.cita = cita;
    }

    public VBox get(double Width) {
        this.vBox.setPrefWidth(Width);
        
        return this.vBox;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public Label getNombre() {
        return Nombre;
    }

    public void setNombre(Label Nombre) {
        this.Nombre = Nombre;
    }

    public Label getCorreo() {
        return Correo;
    }

    public void setCorreo(Label Correo) {
        this.Correo = Correo;
    }

    public Label getTelefono() {
        return Telefono;
    }

    public void setTelefono(Label Telefono) {
        this.Telefono = Telefono;
    }

    public CitaDto getCita() {
        return cita;
    }

    public void setCita(CitaDto cita) {
        this.cita = cita;
    }
    
}
