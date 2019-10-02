/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import java.io.IOException;
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
public class Correos {

    private static Correos INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (Correos.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Correos();
                }
            }
        }
    }

    public static Correos getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public Respuesta mensajeActivacion(String usuario, String Destinatario, String url) {
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart(); // Aqui se declara lo que será nuestro archivo adjunto
            link.setContent(activarUsuario(usuario, url), "text/html");
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(link);
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
            mensaje.setSubject("Mensaje de Activación");// Aqui podemos escribir el asunto que necesitemos en el correo
            mensaje.setContent(m); // aqui seteamos nuestro archivo
            // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
            Transport t = session.getTransport("smtp");
            t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            return new Respuesta(true,"Mensaje de Activación enviado exitosamente","");
        } catch (MessagingException e) {
            return new Respuesta(false,"Hubo un error al enviar el correo de activación al usuario.","");
        }

    }

    public Respuesta recuperarContrasenna(String Destinatario, String contrasenna) {
        try {
            // Propiedades necesarias
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

            Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
            BodyPart link = new MimeBodyPart(); // Aqui se declara lo que será nuestro archivo adjunto
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
            return new Respuesta(true,"","");
        } catch (MessagingException e) {
            return new Respuesta(false,"No se ha enviado un correo de recuperación debido a un problema de red.",e.getLocalizedMessage());
        }

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
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/5yGvbDXK/logo-Correo.jpg\" heigth=\"150px\"></div>\n"
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
                + "<img width=\"150px\" style=\"display:block; margin: 1.5% 3%;\" \" src=\"https://i.postimg.cc/5yGvbDXK/logo-Correo.jpg\" heigth=\"150px\"></div>\n"
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
}
