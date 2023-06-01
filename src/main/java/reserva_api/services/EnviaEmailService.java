package reserva_api.services;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EnviaEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String destinatario, String titulo, String conteudo) throws MessagingException {
        log.info("Enviando email...");
        var mail = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);

        helper.setTo(destinatario);
        helper.setSubject(titulo);
        helper.setText(conteudo, true);

        mailSender.send(mail);
        log.info("Email enviado");
    }
}
