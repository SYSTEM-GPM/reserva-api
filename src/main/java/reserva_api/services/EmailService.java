package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reserva_api.models.EmailModel;
import reserva_api.models.PessoaModel;
import reserva_api.models.enums.StatusEmail;
import reserva_api.repositories.EmailRespository;
import reserva_api.repositories.PessoaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmailRespository emailRespository;

    public void enviarEmail(Long pessoaId, String assunto, String menssagem) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(pessoaId);
        if(pessoaModelOptional.isPresent()) {
            PessoaModel pessoaModel = pessoaModelOptional.get();

            EmailModel emailModel = new EmailModel();
            emailModel.setPessoa(pessoaModel);
            emailModel.setAssunto(assunto);
            emailModel.setMensagem(menssagem);
            emailModel.setDataEmail(LocalDateTime.now());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("iano.dev.smtp@gmail.com");
            mailMessage.setTo("ianomaciel6385@gmail.com");
            mailMessage.setSubject(assunto);
            mailMessage.setText(menssagem);

            javaMailSender.send(mailMessage);
        } else {
            throw new IllegalArgumentException("Pessoa não encontrada");
        }
    }
}
