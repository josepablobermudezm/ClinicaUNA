/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.AgendaDto;
import clinicauna.model.CitaDto;
import clinicauna.model.ControlDto;
import clinicauna.model.MedicoDto;
import clinicauna.model.PacienteDto;
import clinicauna.model.UsuarioDto;
import clinicauna.service.CitaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author JORDI RODRIGUEZ
 */
public class Correos extends Thread {

    public Correos() {
        super();
    }
    private UsuarioDto us;
    private String caso;
    private String usuario;
    private String destinatario;
    private String url;
    private String contrasena;
    private Respuesta resp;
    private CitaDto cita;
    private MedicoDto medico;
    private AgendaDto agenda;
    private PacienteDto paciente;
    private List<vistaCita> aux = new ArrayList<>();
    private ControlDto control;

    @Override
    public void run() {
        switch (caso) {
            case "Activacion":
                mensajeActivacion(usuario, destinatario, url);
                break;
            case "Contrasenna":
                recuperarContrasenna(destinatario, contrasena);
                break;
            case "cita":
                CorreoCita(destinatario);
                break;
            case "control":
                CorreoControl(destinatario);
                break;
            case "recordatorio":
                CorreoCitaRecordatorio(destinatario);
                break;
        }
        hiloCorreo.finalizado = true;
    }

    public void mensajeActivacionHilo(String usuario, String Destinatario, String url) {
        this.usuario = usuario;
        this.destinatario = Destinatario;
        this.url = url;
        this.caso = "Activacion";
        start();
    }

    public Respuesta getResp() {
        return resp;
    }

    public void setResp(Respuesta resp) {
        this.resp = resp;
    }

    public void mensajeActivacion(String usuario, String Destinatario, String url) {
        try {
            us = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart(); // Aqui se declara lo que será nuestro archivo adjunto
            if (us.getIdioma().equals("I")) {
                link.setContent(activarUsuarioIngles(usuario, url), "text/html");
            } else {
                link.setContent(activarUsuario(usuario, url), "text/html");
            }
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(link);
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
            if (us.getIdioma().equals("I")) {
                mensaje.setSubject("Activation Message");
            } else {
                mensaje.setSubject("Mensaje de Activación");
            }
            mensaje.setContent(m); // aqui seteamos nuestro archivo
            // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
            Transport t = session.getTransport("smtp");
            t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();

            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(true, "Activation Message Sent Successfully", "");
            } else {
                resp = new Respuesta(true, "Mensaje de Activación enviado exitosamente", "");
            }
        } catch (MessagingException e) {
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(false, "There was an error sending the activation message", "");
            } else {
                resp = new Respuesta(false, "Hubo un error al enviar el correo de activación al usuario.", "");
            }
        }

    }

    public void recuperarContrasennaHilo(String Destinatario, String contrasenna) {
        this.destinatario = Destinatario;
        this.contrasena = contrasenna;
        this.caso = "Contrasenna";
        start();

    }

    public void CorreoCitaHilo(String Destinatario) {
        this.destinatario = Destinatario;
        this.caso = "cita";
        cita = (CitaDto) AppContext.getInstance().get("CitaDto");
        medico = (MedicoDto) AppContext.getInstance().get("MedicoDto");
        aux = (List) AppContext.getInstance().get("aux");
        paciente = (PacienteDto) AppContext.getInstance().get("PacienteDto");
        agenda = (AgendaDto) AppContext.getInstance().get("Agenda");
        start();
    }

    public void CorreoCitaHiloRecordatorio(String Destinatario, CitaDto cita) {
        this.destinatario = Destinatario;
        this.caso = "recordatorio";
        this.cita = cita;
        medico = (MedicoDto) AppContext.getInstance().get("MedicoDto");
        aux = (List) AppContext.getInstance().get("aux");
        paciente = (PacienteDto) AppContext.getInstance().get("PacienteDto");
        agenda = (AgendaDto) AppContext.getInstance().get("Agenda");
        start();
    }

    public void CorreoControlHilo(String Destinatario) {
        this.destinatario = Destinatario;
        this.caso = "control";
        control = (ControlDto) AppContext.getInstance().get("Control");
        medico = (MedicoDto) AppContext.getInstance().get("MedicoDto");
        start();
    }

    public void CorreoControl(String Destinatario) {
        try {
            us = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            // Propiedades necesarias
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart();
            if (us.getIdioma().equals("I")) {
                link.setContent(htmlCorreoControlIngles(medico), "text/html");
            } else {
                link.setContent(htmlCorreoControl(medico), "text/html");
            }

            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(link);
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
            if (us.getIdioma().equals("I")) {
                mensaje.setSubject("Appointment Information");
            } else {
                mensaje.setSubject("Información de Cita");
            }
            mensaje.setContent(m); // aqui seteamos nuestro archivo
            // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
            Transport t = session.getTransport("smtp");
            t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(true, "Mail sent successfully", "");
            } else {
                resp = new Respuesta(true, "Correo enviado exitosamente.", "");
            }

        } catch (MessagingException e) {
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(false, "Mail was not sent due to a network problem", e.getLocalizedMessage());
            } else {
                resp = new Respuesta(false, "No se ha enviado un correo de información de cita debido a un problema de red.", e.getLocalizedMessage());
            }
        }

    }

    public void CorreoCita(String Destinatario) {
        try {
            us = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            // Propiedades necesarias
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart();
            if (us.getIdioma().equals("I")) {
                link.setContent(htmlCorreoCitaIngles(cita), "text/html");
            } else {
                link.setContent(htmlCorreoCita(cita), "text/html");
            }
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(link);
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
            if (us.getIdioma().equals("I")) {
                mensaje.setSubject("Appointment Information");
            } else {
                mensaje.setSubject("Información de Cita");
            }
            mensaje.setContent(m); // aqui seteamos nuestro archivo
            // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
            Transport t = session.getTransport("smtp");
            t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(true, "Mail sent successfully", "");
            } else {
                resp = new Respuesta(true, "Correo enviado exitosamente.", "");
            }
        } catch (MessagingException e) {
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(false, "Mail was not sent due to a network problem", e.getLocalizedMessage());
            } else {
                resp = new Respuesta(false, "No se ha enviado un correo de información de cita debido a un problema de red.", e.getLocalizedMessage());
            }
        }
    }

    public void CorreoCitaRecordatorio(String Destinatario) {
        try {
            us = (UsuarioDto) AppContext.getInstance().get("UsuarioActivo");
            // Propiedades necesarias
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart();
            link.setContent(htmlCorreoCitaRecordatorio(cita), "text/html");
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(link);
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
            mensaje.setSubject("Información de Cita");// Aqui podemos escribir el asunto que necesitemos en el correo
            mensaje.setContent(m); // aqui seteamos nuestro archivo
            // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
            Transport t = session.getTransport("smtp");
            t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            System.out.println("Correo Enviado");
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(true, "Mail sent successfully", "");
            } else {
                resp = new Respuesta(true, "Correo enviado exitosamente.", "");
            }
            /*
            Si el correo se envio, guardo el estado de la cita a correo Enviado
             */
            cita.setCorreoEnviado("S");
            new CitaService().guardarCita(cita);
        } catch (MessagingException e) {
            System.out.println("Correo Fallido");
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(false, "Mail was not sent due to a network problem", e.getLocalizedMessage());
            } else {
                resp = new Respuesta(false, "No se ha enviado un correo de información de cita debido a un problema de red.", e.getLocalizedMessage());
            }
        }
    }

    public void recuperarContrasenna(String Destinatario, String contrasenna) {
        try {
            // Propiedades necesarias
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart();
            link.setContent(htmlRecuperarContrasenna(contrasenna), "text/html");
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(link);
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
            mensaje.setSubject("Recuperación de Contraseña");// Aqui podemos escribir el asunto que necesitemos en el correo
            mensaje.setContent(m); // aqui seteamos nuestro archivo
            // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
            Transport t = session.getTransport("smtp");
            t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(true, "Mail sent successfully", "");
            } else {
                resp = new Respuesta(true, "Correo enviado exitosamente.", "");
            }
        } catch (MessagingException e) {
            if (us.getIdioma().equals("I")) {
                resp = new Respuesta(false, "Mail was not sent due to a network problem", e.getLocalizedMessage());
            } else {
                resp = new Respuesta(false, "No se ha podido enviar el correo debido a un problema de red", e.getLocalizedMessage());
            }
        }
    }

    public String htmlCorreoCitaRecordatorio(CitaDto cita) {

        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px\">Información de Cita</h2>\n"
                + "				<p style=\"margin: 2px; font-size: 15px\">\n"
                + "					Este correo tiene como propósito recordarle que mañana tiene una cita en la clinica: " + "  " + "<br>" + " Información sobre cita: " + "<br>"
                + "Médico: " + medico.getUs().getNombre() + " " + medico.getUs().getpApellido() + "<br>" + "Fecha: " + agenda.getAgeFecha().toString() + "<br>" + "Hora de inicio: " + aux.get(0).getEspacio().getEspHoraInicio()
                + "<br>" + "Hora Final:" + aux.get(aux.size() - 1).getEspacio().getEspHoraFin() + "<br>" + "Paciente: " + paciente.getNombre() + " " + paciente.getpApellido() + " " + paciente.getsApellido() + "<br>" + "Cédula: " + paciente.getCedula()
                + "                             <p style=\"margin: 2px; font-size: 15px\">\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    public String htmlCorreoCita(CitaDto cita) {

        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px\">Información de Cita</h2>\n"
                + "				<p style=\"margin: 2px; font-size: 15px\">\n"
                + "					Su cita ha sido registrada exitosamente: " + "  " + "<br>" + " Información sobre cita: " + "<br>"
                + "Médico: " + medico.getUs().getNombre() + " " + medico.getUs().getpApellido() + "<br>" + "Fecha: " + agenda.getAgeFecha().toString() + "<br>" + "Hora de inicio: " + aux.get(0).getEspacio().getEspHoraInicio()
                + "<br>" + "Hora Final:" + aux.get(aux.size() - 1).getEspacio().getEspHoraFin() + "<br>" + "Paciente: " + paciente.getNombre() + " " + paciente.getpApellido() + " " + paciente.getsApellido() + "<br>" + "Cédula: " + paciente.getCedula()
                + "                             <p style=\"margin: 2px; font-size: 15px\">\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    public String htmlCorreoControl(MedicoDto medico) {

        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px\">Información de Cita</h2>\n"
                + "				<p style=\"margin: 2px; font-size: 15px\">\n"
                + "					Control de Cita: " + "  " + "<br>" + " Información sobre control: " + "<br>"
                + "Médico: " + medico.getUs().getNombre() + " " + medico.getUs().getpApellido() + "<br>" + "Fecha: " + control.getCntFecha().toString() + "<br>" + "Hora de inicio: " + control.getCntHora()
                + "<br>" + "Razón de la consulta: " + control.getCntRazonConsulta() + "<br>" + "Plan de atención: " + control.getCntPlanAtencion()
                + "<br>" + "Observaciones: " + control.getCntObservaciones() + "<br>" + "Examen físico: " + control.getCntExamenFisico()
                + "<br>" + "Tratamiento: " + control.getCntTratamiento()
                + "                             <p style=\"margin: 2px; font-size: 15px\">\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    public String htmlRecuperarContrasenna(String contrasenna) {

        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px\">Recuperación de contraseña</h2>\n"
                + "				<p style=\"margin: 2px; font-size: 15px\">\n"
                + "					Para poder reestablecer la constraseña es necesario la siguiente clave temporal: " + contrasenna + "</p>\n"
                + "                             <p style=\"margin: 2px; font-size: 15px\">\n"
                + "                             Si no has intentado reestablecer tu contraseña ignora este mensaje.</p>\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    public String activarUsuario(String usuario, String url) {
        usuario = usuario.toUpperCase();
        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px; text-align: center\">Mensaje de Activación</h2>\n"
                + "				<p style=\"color: #500050; margin: 2px; font-size: 20px; font-weight: bold\">\n"
                + "					" + usuario + ": </p>\n"
                + "                             <p style=\"margin: 2px; font-size: 15px\">"
                + "                                     Para completar su registro en Clínica UNA, se necesita activar el usuario perteneciente a este correo. Si no eres tú, ignora este mensaje.</p>"
                + "				<ul style=\"font-size: 15px;  margin: 10px 0\">\n"
                + "					<li>Una vez que hayas activado tu usuario, podrás utilizar todas las características que permite el uso de la aplicación.</li>\n"
                + "				</ul>\n"
                + "				<div style=\"width: 100%;margin:20px 0; display: inline-block;text-align: center\">\n"
                + "				</div>\n"
                + "				<div style=\"width: 100%; text-align: center\">\n"
                + "					<a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db\" href=\"" + url + "\">Activar Usuario</a>\n"
                + "				</div>\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    private String activarUsuarioIngles(String usuario, String url) {
        usuario = usuario.toUpperCase();
        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px; text-align: center\">Activation Message</h2>\n"
                + "				<p style=\"color: #500050; margin: 2px; font-size: 20px; font-weight: bold\">\n"
                + "					" + usuario + ": </p>\n"
                + "                             <p style=\"margin: 2px; font-size: 15px\">"
                + "                                     To complete your registration at Clínica UNA, you need to activate the user belonging to this email. If it's not you, ignore this message.</p>"
                + "				<ul style=\"font-size: 15px;  margin: 10px 0\">\n"
                + "					<li>If you have activated your user, you can use all the features that allow the use of the application.</li>\n"
                + "				</ul>\n"
                + "				<div style=\"width: 100%;margin:20px 0; display: inline-block;text-align: center\">\n"
                + "				</div>\n"
                + "				<div style=\"width: 100%; text-align: center\">\n"
                + "					<a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db\" href=\"" + url + "\">Activar Usuario</a>\n"
                + "				</div>\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    private String htmlCorreoControlIngles(MedicoDto medico) {

        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px\">Appointment Information</h2>\n"
                + "				<p style=\"margin: 2px; font-size: 15px\">\n"
                + "					Appointment Control: " + "  " + "<br>" + " Control Information: " + "<br>"
                + "Doctor: " + medico.getUs().getNombre() + " " + medico.getUs().getpApellido() + "<br>" + "Date: " + control.getCntFecha().toString() + "<br>" + "Start time: " + control.getCntHora()
                + "<br>" + "Reason for the appointment: " + control.getCntRazonConsulta() + "<br>" + "Care plan: " + control.getCntPlanAtencion()
                + "<br>" + "Observations: " + control.getCntObservaciones() + "<br>" + "Physical exam: " + control.getCntExamenFisico()
                + "<br>" + "Treatment: " + control.getCntTratamiento()
                + "                             <p style=\"margin: 2px; font-size: 15px\">\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    private String htmlCorreoCitaIngles(CitaDto cita) {
        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<title>holi</title>\n"
                + "</head>\n"
                + "<body style=\"background-color: white \">\n"
                + "\n"
                + "<!--Copia desde aquí-->\n"
                + "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "	<tr>\n"
                + "		<td style=\"background-color: white\">\n"
                + " <div id=\"imagenLogo\" style=\"width: 150px; heigth: 150px;  padding: 11px 23px\">"
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/P5p8msw5/logo.png\" heigth=\"150px\"></div>\n"
                + "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n"
                + "				<h2 style=\"color: #3e3e7d; margin: 0 0 7px\">Appointment Information</h2>\n"
                + "				<p style=\"margin: 2px; font-size: 15px\">\n"
                + "					Your appointment has been registered successfully: " + "  " + "<br>" + "Appointment Information: " + "<br>"
                + "Doctor: " + medico.getUs().getNombre() + " " + medico.getUs().getpApellido() + "<br>" + "Date: " + agenda.getAgeFecha().toString() + "<br>" + "Start time: " + aux.get(0).getEspacio().getEspHoraInicio()
                + "<br>" + "End Time:" + aux.get(aux.size() - 1).getEspacio().getEspHoraFin() + "<br>" + "Patient: " + paciente.getNombre() + " " + paciente.getpApellido() + " " + paciente.getsApellido() + "<br>" + "ID: " + paciente.getCedula()
                + "                             <p style=\"margin: 2px; font-size: 15px\">\n"
                + "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">ClínicaUNA 2019</p>\n"
                + "			</div>\n"
                + "		</td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!--hasta aquí-->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

}
