package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reserva_api.models.EmailModel;
import reserva_api.models.enums.StatusEmail;
import reserva_api.repositories.EmailRespository;

import java.time.LocalDateTime;

@Service
public class EmailService {
    @Autowired
    EmailRespository emailRespository;

    @Autowired
    private JavaMailSender emailSender;
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setText(emailModel.getText());
            message.setSubject(emailModel.getSubject());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRespository.save(emailModel);
        }
    }
}
