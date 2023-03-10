package com.application.mypetfx.utils.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    private static final Logger logger = Logger.getLogger(SendMail.class);

    private final String recipient;
    private final String message;
    private final String subject;
    private final SystemEmailConfig systemEmailConfig;

    public SendMail(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        systemEmailConfig = new SystemEmailConfig();
    }

    public void sendMail() {

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(systemEmailConfig.getEmail(), systemEmailConfig.getWatchword());
            }
        });

        //Prepare email message
        MimeMessage mimeMessage = prepareMessage(session);

        //Send mail
        assert mimeMessage != null;
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Messaging Error: ", e);
        }
    }

    private MimeMessage prepareMessage(Session session) {
        try {
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress(systemEmailConfig.getEmail()));
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mm.setSubject(subject);
            mm.setText(message);
            return mm;
        } catch (MessagingException e) {
            logger.error("Messaging Error: ", e);
        }
        return null;
    }

}
