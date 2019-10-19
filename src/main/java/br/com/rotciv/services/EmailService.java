package br.com.rotciv.services;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailService {
    public static void sendmail() throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mymail@gmail.com", "password");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("mymail@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("v.rotciv@hotmail.com"));
        msg.setSubject("Envio de proposta");
        msg.setContent("Portador cadastrado com sucesso!", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        //msg.setContent(multipart);
        Transport.send(msg);
    }
}
