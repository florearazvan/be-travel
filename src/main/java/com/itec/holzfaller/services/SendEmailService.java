package com.itec.holzfaller.services;

import com.itec.holzfaller.entities.User;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by rbu on 4/8/17.
 */
public class SendEmailService {

    private final static String FROM_EMAIL_ADDRESS = "text.email.itec@gmail.com";
    private final static String EMAIL_PASSWORD = "iTec2017";

    private UserService userService = new UserService();

    public void sendStringReportTo(String username){
        User user = userService.findByUsername(username);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL_ADDRESS, EMAIL_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("FROM_EMAIL_ADDRESS"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Report from be-travel");

            Multipart multipart = new MimeMultipart();

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hi,\n\n you can find your report in attachments");
            String filename = System.getProperty("user.dir") + "/" + username + "report.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}
