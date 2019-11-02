/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.controller;

import clinicauna.model.ControlDto;
import clinicauna.model.ExpedienteDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.ControlService;
import clinicauna.service.ExpedienteService;
import clinicauna.util.AppContext;
import clinicauna.util.Idioma;
import clinicauna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Bermudez
 */
public class EvolucionHistoricaController extends Controller {

    @FXML
    private LineChart<String, Double> Grafico;
    @FXML
    private NumberAxis NAxis;
    @FXML
    private CategoryAxis CAxis;
    @FXML
    private Label lblPaciente;
    @FXML
    private JFXButton btnVolver;
    private PacienteDto paciente;
    private Respuesta resp = new Respuesta();
    private ArrayList<ControlDto> controles;
    private ControlService ContService;
    private ExpedienteDto expediente;
    private List<ControlDto> cont;
    private int i;
    private UsuarioDto usuario;
    private Idioma idioma;
    @FXML
    private Label lblTitulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        usuario = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
        idioma = (Idioma) AppContext.getInstance().get("idioma");
        if (usuario.getIdioma().equals("I")) {
            this.lblTitulo.setText(idioma.getProperty("Paciente"));
            this.btnVolver.setText(idioma.getProperty("Volver"));
        }
        ContService = new ControlService();
        resp = ContService.getControles();
        controles = new ArrayList<>();
        controles = (ArrayList<ControlDto>) resp.getResultado("controles");
        cont = new ArrayList<>();
        LlenarGrafico();
    }

    public void LlenarGrafico() {
        paciente = (PacienteDto) AppContext.getInstance().get("Paciente");
        lblPaciente.setText(paciente.getNombre() + " " + paciente.getpApellido() + " " + paciente.getsApellido());
        expediente = (ExpedienteDto) AppContext.getInstance().get("Expediente");
        controles.stream().filter(x -> x.getCntExpediente().getExpID().equals(expediente.getExpID())).forEach(s -> {
            cont.add(s);
        });
        Grafico.getData().clear();
        if (usuario.getIdioma().equals("I")) {
            Grafico.setTitle(idioma.getProperty("IMC"));
            NAxis.setLabel(idioma.getProperty("Indices"));
        } else {
            Grafico.setTitle("IMC HISTÓRICO");
            NAxis.setLabel("Indices");
        }

        i = 1;
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (ControlDto con : cont) {
            series.getData().add(new XYChart.Data<>("Control " + String.valueOf(i), con.getCntImc()));
            i++;

        }
        if (usuario.getIdioma().equals("I")) {
            series.setName(idioma.getProperty("IDMC"));
        } else {
            series.setName("Indice de Masa Corporal");
        }
        Grafico.getData().add(series);

    }

    @FXML
    private void Volver(ActionEvent event) {
        this.stage.close();
    }

}
