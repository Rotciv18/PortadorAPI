package br.com.rotciv.services;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailService {

    private static final String EMAIL_FROM = "iluvconductor@gmail.com";
    private static final String EMAIL_TO = "v.rotciv@hotmail.com";
    private static final String PASSWORD = "cdt123456";


    public static void sendmail() throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(EMAIL_FROM, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
        msg.setSubject("Envio de proposta");
        msg.setContent("Portador cadastrado com sucesso!", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(";)", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        //msg.setContent(multipart);
        Transport.send(msg);
    }
}
