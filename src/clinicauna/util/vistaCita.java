/*

* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.AgendaDto;
import clinicauna.model.CitaDto;
import clinicauna.model.EspacioDto;
import clinicauna.model.MedicoDto;
import clinicauna.service.EspacioService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
         *  se hace el intercambio de la cita visualmente
         */

 /*
         *  Tomamos en cuenta varios casos, que ambos sean diferente de null o que solo uno de ellos sea null
         */
        if (vCita.getEspacio() != null && this.getEspacio() != null) {
            EspacioDto espacio = vCita.getEspacio();
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
            vCita.getChildren().add(vCita.getvBox());
            /*
             *  hacemos el intercambio de la base de datos de cita
             */

            AgendaDto agenda;
            agenda = (AgendaDto) AppContext.getInstance().get("Agenda");
            agenda.setAgeMedico((MedicoDto) AppContext.getInstance().get("Med"));

            String horaInicio = this.getEspacio().getEspHoraInicio();
            String horaFinal = this.getEspacio().getEspHoraFin();
            this.getEspacio().setEspHoraFin(vCita.getEspacio().getEspHoraFin());
            this.getEspacio().setEspHoraInicio(vCita.getEspacio().getEspHoraInicio());
            vCita.getEspacio().setEspHoraInicio(horaInicio);
            vCita.getEspacio().setEspHoraFin(horaFinal);

            agenda.setEspacioList(null);

            this.getEspacio().setEspAgenda(agenda);
            vCita.getEspacio().setEspAgenda(agenda);

            EspacioService espacioService = new EspacioService();
            espacioService.guardarEspacio(vCita.getEspacio());
            espacioService.guardarEspacio(this.getEspacio());
        } else if (vCita.getEspacio() != null && this.getEspacio() == null) {
            EspacioDto espacio = vCita.getEspacio();
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
            vCita.getChildren().add(vCita.getvBox());
            /*EspacioDto espacio = vCita.getEspacio();
            EspacioDto espacio2 = this.getEspacio();

            Label label = (Label) this.getChildren().get(0);
            LocalTime inicio = LocalTime.parse(label.getText());
            AgendaDto agenda;
            agenda = (AgendaDto) AppContext.getInstance().get("Agenda");

            /*
             *  Chequemos la cantidad de espacios que tiene el médico
             
            LocalTime final1 = (agenda.getAgeMedico().getEspacios() != 1) ? (inicio.withMinute((agenda.getAgeMedico().getEspacios() == 4) ? inicio.getMinute() + 15
                    : (agenda.getAgeMedico().getEspacios() == 3) ? inicio.getMinute() + 20 : inicio.getMinute() + 30)) : inicio.withHour(inicio.getHour() + 1);

            //Creo las conversiones de las horas del medico con formato
            LocalDateTime inicio12 = LocalDateTime.of(LocalDate.now(), inicio);
            LocalDateTime fin = LocalDateTime.of(LocalDate.now(), final1);
            String inicioS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(inicio12);
            String finS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(fin);
            vCita.getEspacio().setEspHoraInicio(inicioS);
            vCita.getEspacio().setEspHoraFin(finS);

            /*
             *  Está parte es visual   
             
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
            vCita.getChildren().add(vCita.getvBox());
            /*
             *  hacemos el intercambio de la base de datos de cita
             

            agenda = (AgendaDto) AppContext.getInstance().get("Agenda");
            agenda.setAgeMedico((MedicoDto) AppContext.getInstance().get("Med"));

            String horaInicio = this.getEspacio().getEspHoraInicio();
            String horaFinal = this.getEspacio().getEspHoraFin();
            this.getEspacio().setEspHoraFin(vCita.getEspacio().getEspHoraFin());
            this.getEspacio().setEspHoraInicio(vCita.getEspacio().getEspHoraInicio());
            //vCita.getEspacio().setEspHoraInicio(horaInicio);
            //vCita.getEspacio().setEspHoraFin(horaFinal);

            agenda.setEspacioList(null);

            this.getEspacio().setEspAgenda(agenda);
            vCita.getEspacio().setEspAgenda(agenda);

            EspacioService espacioService = new EspacioService();
            espacioService.guardarEspacio(this.getEspacio());
        */
        } else if (vCita.getEspacio() == null && this.getEspacio() != null) {
            EspacioDto espacio = vCita.getEspacio();
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
            vCita.getChildren().add(vCita.getvBox());
            //espacioService.guardarEspacio(vCita.getEspacio());
        }
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
