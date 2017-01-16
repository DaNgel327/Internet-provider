package by.epam.osipov.internet.provider.mail.ssl;


import by.epam.osipov.internet.provider.entity.impl.Access;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private Properties props;

    private static final String subject = "You was registered on BelNet.by";
    private static final String senderEmail = "osipov220112@gmail.com";
    private static final String senderEmailPass = "id81501135";

    //take from file?
    private static final String message = "Hi! You was registered on BelNet.by";

    public EmailSender() {

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    private void send(String subject, String text, String toEmail) {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderEmailPass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(senderEmail));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //тема сообщения
            message.setSubject(subject);
            //текст
            message.setText(text);

            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendAccess(Access access, String email) {

        String aboutAccess = "\nYour Login: " + access.getLogin() + "\n Password: " + access.getPassword();
        send(subject, message + aboutAccess, email);

        return true;
    }
}