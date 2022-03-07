package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending emails
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email
     * @param to the email address of the recipient.
     * @param subject the subject of the email.
     * @param messageText the message to send.
     */
    public void sendEmail(String to, String subject, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messageText);

        // Uncomment to allow sending mail
        //mailSender.send(message);
        System.out.println(message);
    }
}
