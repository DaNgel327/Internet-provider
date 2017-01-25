package by.epam.osipov.internet.provider.mail.ssl;


import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.exception.RegistrationException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private Properties props;

    private static final String subject = "You was registered on BelNet.by";
    private static final String senderEmail = "osipov220112@gmail.com";
    private static final String senderEmailPass = "belNetPassword";

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

    /**
     * Sends Access object to user on email.
     * (login and password)
     *
     * @param access Access object to send
     * @param email  receiver email
     */
    public void sendAccess(Access access, String email) throws RegistrationException {
        try {
            trySendAccess(access, email);
        } catch (MessagingException e) {
            throw new RegistrationException("Error while trying to send email to user", e);
        }
    }

    /**
     * Try to send Access object to user
     *
     * @param access Access object to send
     * @param email  receiver email
     */
    private void trySendAccess(Access access, String email) throws MessagingException {
        String aboutAccess = "\nYour Login: " + access.getLogin() + "\n Password: " + access.getPassword();

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderEmailPass);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);
        message.setText(message + aboutAccess);

        Transport.send(message);
    }
}