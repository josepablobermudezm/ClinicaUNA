/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.AgendaDto;
import clinicauna.model.CitaDto;
import clinicauna.model.EspacioDto;
import clinicauna.service.EspacioService;
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
        /*
            se hace el intercambio de la cita visualmente
        */
        /*EspacioDto espacio = vCita.getEspacio();
        EspacioDto espacio2 = this.getEspacio();
        this.setEspacio(espacio);
        vCita.setEspacio(espacio2);
        this.getChildren().remove(vBox);
        vCita.getChildren().remove(vCita.getvBox());
        String style = vCita.getStyle();
        vCita.setStyle(getStyle());
        this.setStyle(style);

        
        VBox vAux = vCita.getvBox();
        vCita.setvBox(vBox);
        vBox = vAux;
        this.getChildren().add(vBox);
        vCita.getChildren().add(vCita.getvBox());*/
        /*
            hacemos el intercambio de la base de datos de cita
        */
        
        
        
        /*AgendaDto agenda;
        agenda = (AgendaDto) AppContext.getInstance().get("Agenda");
        String horaInicio = this.getEspacio().getEspHoraInicio();
        String horaFinal = this.getEspacio().getEspHoraFin();
        this.getEspacio().setEspHoraFin(vCita.getEspacio().getEspHoraFin());
        this.getEspacio().setEspHoraInicio(vCita.getEspacio().getEspHoraInicio());
        vCita.getEspacio().setEspHoraInicio(horaInicio);
        vCita.getEspacio().setEspHoraFin(horaFinal);
        this.getEspacio().setEspAgenda(agenda);
        vCita.getEspacio().setEspAgenda(agenda);
        
        EspacioService espacioService = new EspacioService();
        
        espacioService.guardarEspacio(this.getEspacio());*/
        
        
        
        
        //espacioService.guardarEspacio(vCita.getEspacio());
        /*if (vCita.getEspacio() != null && this.getEspacio() != null) {

            EspacioDto espacioDto3 = new EspacioDto(vCita.getEspacio().getEspId(), vCita.getEspacio().getEspHoraInicio(), vCita.getEspacio().getEspHoraFin(),
                    vCita.getEspacio().getEspVersion() + 1, this.getEspacio().getEspCita(), vCita.getEspacio().getEspAgenda());

            espacioService.guardarEspacio(espacioDto3);

            EspacioDto espacioDto2 = new EspacioDto(this.getEspacio().getEspId(), this.getEspacio().getEspHoraInicio(), this.getEspacio().getEspHoraFin(),
                    this.getEspacio().getEspVersion() + 1, vCita.getEspacio().getEspCita(), this.getEspacio().getEspAgenda());

            espacioService.guardarEspacio(espacioDto2);
        }*/
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
