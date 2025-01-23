package utils;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

import static jakarta.mail.Transport.send;

public class Comunicaciones {
    private static final String host = "smtp.gmail.com";
    private static final String user = "fernanshop.wiwi@gmail.com";
    private static final String pass = "xzgx nnzp dmgt lsur";


    public static boolean enviarCorreoVerificacion(String correoDestino) {
        String contenido;

        //Creamos nuestra variable de propiedades con los datos de nuestro servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Obtenemos la sesión en nuestro servidor de correo
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(user, pass);
            }
        });

        try {
            //Creamos un mensaje de correo por defecto
            Message message = new MimeMessage(session);

            // En el mensaje, establecemos el receptor
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correoDestino));

            // Establecemos el Asunto
            message.setSubject("Correo de verificación de uso en Fernanshop");

            // Añadimos el contenido del mensaje
            message.setText("""
                    
                    
                    
                    Debe introducir el siguiente código: X67K94""");

            // Intentamos mandar el mensaje
            send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}