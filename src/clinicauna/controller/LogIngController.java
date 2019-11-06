/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.AgendaDto;
import clinicauna.model.CitaDto;
import clinicauna.model.EspacioDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.AgendaService;
import clinicauna.service.CitaService;
import clinicauna.service.EspacioService;
import clinicauna.service.MedicoService;
import clinicauna.service.UsuarioService;
import clinicauna.util.AppContext;
import clinicauna.util.Correos;
import clinicauna.util.FlowController;
import clinicauna.util.Formato;
import clinicauna.util.Idioma;
import clinicauna.util.Mensaje;
import clinicauna.util.Respuesta;
import clinicauna.util.vistaCita;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class LogIngController extends Controller implements Initializable {

    @FXML
    private ImageView imvFondo;

    @FXML
    private ImageView omg;
    @FXML
    private ImageView imguser;
    @FXML
    private ImageView imgPassword;
    @FXML
    private VBox VboxLOG;
    @FXML
    private JFXTextField txtUsuario1;
    @FXML
    private JFXPasswordField txtClave1;
    private Idioma idioma;
    @FXML
    private JFXButton button2;
    @FXML
    private AnchorPane root1;
    private AgendaService agendaService;
    private Respuesta r;
    private ArrayList<EspacioDto> espacioListAux;//en esta lista voy a guardar todos los espacios que tenga el médico
    private ArrayList<AgendaDto> agendaList;
    private ArrayList<EspacioDto> espacioList;
    private Respuesta respAgenda;
    private Respuesta respEspacio;
    private EspacioService espacioService;
    private ArrayList<CitaDto> citas;
    private CitaService citasService;
    private MedicoService MedicoService;
    private ArrayList<MedicoDto> medicosList;
    private Respuesta resp;
    private MedicoDto medicoDto;
    private static List<vistaCita> aux = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {

    }

    @FXML
    private void ingresar(ActionEvent event) {
        Login();
    }

    private void Login() {
        try {
            if (txtUsuario1.getText() == null || txtUsuario1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Validación de usuario", this.getStage(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave1.getText() == null || txtClave1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Validación de usuario", this.getStage(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {
                UsuarioService UsuarioService = new UsuarioService();
                Respuesta respuesta = UsuarioService.getUsuario(txtUsuario1.getText(), txtClave1.getText());
                if (respuesta.getEstado()) {
                    String contrasena = txtClave1.getText();
                    UsuarioDto usuario = (UsuarioDto) respuesta.getResultado("Usuario");
                    AppContext.getInstance().set("UsuarioActivo", usuario);

                    if ("I".equals(usuario.getIdioma())) {
                        idioma = new Idioma("Inglés");
                    } else {
                        if ("E".equals(usuario.getIdioma())) {
                            idioma = new Idioma("Español");
                        }
                    }
                    AppContext.getInstance().set("idioma", idioma);

                    if (usuario.getEstado().equals("A") && usuario.getContrasennaTemp() != null && contrasena.equals(usuario.getContrasennaTemp())) {
                        FlowController.getInstance().initialize();
                        FlowController.getInstance().goViewInStage("cambiarContrasenna", this.getStage());
                    } else if (usuario.getEstado().equals("A")) {//que el usuario este activo
                        /*
                        * Envío correo de recordatorio en el caso de que la cita sea mañana
                         */
                        FlowController.getInstance().initialize();
                        FlowController.getInstance().goMain();
                        this.getStage().close();
                    } else if (usuario.getEstado().equals("I")) {
                        new Mensaje().showModal(Alert.AlertType.WARNING, "Ingreso", this.getStage(), "El usuario no esta activo, debes activarlo previamente en el correo que ha sido enviado.");
                    }

                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LogIngController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        this.getStage().close();
    }

    private void iniciar(KeyEvent event) {
        if (event.getCode() == event.getCode().ENTER) {
            Login();
        }

    }

    public void Formato() {
        this.txtClave1.setTextFormatter(Formato.getInstance().login(50));
        this.txtUsuario1.setTextFormatter(Formato.getInstance().login(30));
    }

    @FXML
    private void restablecerContrasenna(MouseEvent event) {
        FlowController.getInstance().goViewInStage("RecuperarContrasenna", this.getStage());
    }

    @FXML
    private void LogInKey(KeyEvent event) {
        if (event.getCode() == event.getCode().ENTER) {
            Login();
        }
    }

    private void enviarCorreos() {
        /*
        *   Guardo en la lista de citas todas las citas que pertenezcan a la agenda del día de mañana,luego agrego los datos
        *   que no esten ya repetidos en la lista de citas para evitar enviarle correos a la misma persona por tener varios espacios
         */

        if (agendaList != null && !agendaList.isEmpty()) {
            agendaList.stream().forEach(agenda -> {
                if (agenda.getAgeFecha().isEqual(LocalDate.now().plusDays(1))) {
                    medicoDto = agenda.getAgeMedico();

                    espacioList.stream().forEach(espacio -> {
                        if (!citas.isEmpty()) {

                            //Pregunto si la cita no existe en la lista
                            if (citas.stream().allMatch(x -> !x.getID().equals(espacio.getEspCita().getID()))) {
                                //Pregunto si el correo no ha sido enviado
                                if (espacio.getEspCita().getCorreoEnviado().equals("N")) {
                                    citas.add(espacio.getEspCita());
                                }
                            }
                        } else if (espacio.getEspCita().getCorreoEnviado().equals("N")) {
                            citas.add(espacio.getEspCita());
                        }
                    });
                }
            });

            //Envia los correos por medio de hilos para que no afecten al programa
            if (!citas.isEmpty()) {
                citas.stream().forEach((cita) -> {
                    Stack<EspacioDto> pila = new Stack();
                    espacioList.stream().forEach(espacio -> {
                        if (espacio.getEspCita().getID().equals(cita.getID())) {
                            pila.push(espacio);
                        }
                    });

                    agendaList.stream().forEach((agenda) -> {
                        if (Objects.equals(pila.firstElement().getEspAgenda().getAgeId(), agenda.getAgeId())) {
                            medicoDto = agenda.getAgeMedico();
                        }
                    });

                    pila.sort((t, t1) -> {
                        if (t.getEspId() <= t1.getEspId()) {
                            return 1;
                        } else{
                            return 0;
                        }
                    });
                    
                    Correos correo = new Correos();
                    correo.CorreoCitaHiloRecordatorio(cita.getCorreo(), cita, pila, medicoDto);
                });
            }
        }
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        medicoDto = new MedicoDto();
        MedicoService = new MedicoService();
        resp = MedicoService.getMedicos();
        if (resp.getEstado()) {
            medicosList = (ArrayList) resp.getResultado("Medicos");
        }

        agendaService = new AgendaService();
        espacioService = new EspacioService();
        respAgenda = agendaService.getAgendas();
        respEspacio = espacioService.getEspacios();
        if (respEspacio.getEstado()) {
            espacioList = ((ArrayList) respEspacio.getResultado("Espacios"));
        } else {
        }
        if (respAgenda.getEstado()) {
            agendaList = ((ArrayList) respAgenda.getResultado("Agendas"));
        }

        citasService = new CitaService();
        citas = new ArrayList();
        
        //Envia correos a las citas del día de mañana
        enviarCorreos();

        Formato();
        Image imgLogo;
        try {
            imgLogo = new Image("/clinicauna/resources/logo.png");
            omg.setImage(imgLogo);
        } catch (Exception e) {
        }
        int valorEntero = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
        if (valorEntero == 1) {
            Image imgFondo;
            try {
                imgFondo = new Image("/clinicauna/resources/fondo.jpg");
                imvFondo.setImage(imgFondo);
            } catch (Exception e) {
            }
        } else if (valorEntero == 2) {
            Image imgFondo;
            try {
                imgFondo = new Image("/clinicauna/resources/e.jpg");
                imvFondo.setImage(imgFondo);
            } catch (Exception e) {
            }
        } else if (valorEntero == 3) {
            Image imgFondo;
            try {
                imgFondo = new Image("/clinicauna/resources/fondo2.jpg");
                imvFondo.setImage(imgFondo);
            } catch (Exception e) {
            }
        }

        Image omg1;
        try {
            omg1 = new Image("/clinicauna/resources/key (1).png");
            imgPassword.setImage(omg1);
        } catch (Exception e) {

        }

        Image omg2;
        try {
            omg2 = new Image("/clinicauna/resources/user (3).png");
            imguser.setImage(omg2);
        } catch (Exception e) {
        }
    }
}
