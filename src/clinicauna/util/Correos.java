/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import clinicauna.model.UsuarioDto;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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

    public void linkActivacion(String Destinatario, String Link) throws MessagingException, IOException {
        // Propiedades necesarias
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.setProperty("mail.smtp.port", "587");
        prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

        Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
        BodyPart link = new MimeBodyPart(); // Aqui se declara lo que será nuestro archivo adjunto
        link.setText("Por favor ingresa a esta dirección web para la activación de tu usuario en el sistema de la Clínica UNA: \n" 
                + Link);
        MimeMultipart m = new MimeMultipart();
        m.addBodyPart(link);
        MimeMessage mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
        mensaje.setSubject("Link de Activación");// Aqui podemos escribir el asunto que necesitemos en el correo
        mensaje.setContent(m); // aqui seteamos nuestro archivo
        // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
        Transport t = session.getTransport("smtp");
        t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
    }
     public void recuperarContrasenna(String Destinatario, String contrasenna) throws MessagingException, IOException {
        // Propiedades necesarias
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.setProperty("mail.smtp.port", "587");
        prop.setProperty("mail.smtp.user", "clinica.una.cr@gmail.com");

        Session session = Session.getDefaultInstance(prop, null); // se inicia sesión con las propiedades
        BodyPart link = new MimeBodyPart(); // Aqui se declara lo que será nuestro archivo adjunto
        link.setText(" Para poder reestablecer la constraseña es necesario la siguiente clave temporal: \n" 
                + contrasenna+" .\n Si no has intentado reestablecer tu contraseña ignora este mensaje." );
        MimeMultipart m = new MimeMultipart();
        m.addBodyPart(link);
        MimeMessage mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress("clinica.una.cr@gmail.com"));// Aqui se define el usuario que enviará el correo
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));// Destinatario
        mensaje.setSubject("Recuperar Contraseña");// Aqui podemos escribir el asunto que necesitemos en el correo
        mensaje.setContent(m); // aqui seteamos nuestro archivo
        // Aqui se conecta con nuestro usuario y contraseña se procede a enviar y se cierra la conexión
        Transport t = session.getTransport("smtp");
        t.connect("clinica.una.cr@gmail.com", "gxowaetyiexzenux");
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
    }
}
