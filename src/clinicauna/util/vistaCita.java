/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.CitaDto;
import clinicauna.model.EspacioDto;
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
    private EspacioDto espacio;

    public vistaCita() {

    }

    public void AgregarCita(EspacioDto espacio) {
        this.vBox.getChildren().clear();
        if (espacio != null) {
            this.Nombre.setText(espacio.getEspCita().getPaciente().getNombre() + " " + espacio.getEspCita().getPaciente().getpApellido() + " " + espacio.getEspCita().getPaciente().getsApellido());
            this.Correo.setText(espacio.getEspCita().getCorreo());
            this.Telefono.setText(espacio.getEspCita().getTelefono());
            this.vBox.getChildren().addAll(Nombre, Correo, Telefono);
            this.vBox.setAlignment(Pos.CENTER);
            this.vBox.setSpacing(10);
            String style = "-fx-text-fill: #636361;";
            this.Nombre.setStyle(style);
            this.Correo.setStyle(style);
            this.Telefono.setStyle(style);
        }
        this.espacio = espacio;
    }

    public void intercambiarCita(vistaCita vCita) {
        this.getChildren().remove(vBox);
        vCita.getChildren().remove(vCita.getvBox());
        String style = vCita.getStyle();
        vCita.setStyle(getStyle());
        this.setStyle(style);
        
        VBox vAux = vCita.getvBox();
        vCita.setvBox(vBox);
        vBox = vAux;
        this.getChildren().add(vBox);
        vCita.getChildren().add(vCita.getvBox());
       
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

    public EspacioDto getEspacio() {
        return espacio;
    }

    public void setEspacio(EspacioDto espacio) {
        this.espacio = espacio;
    }

}
