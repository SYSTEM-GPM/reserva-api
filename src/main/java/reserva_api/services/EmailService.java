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
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmailRespository emailRespository;

    public EmailModel enviarEmail(EmailModel emailModel) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(emailModel.getPessoa().getId());
        if(pessoaModelOptional.isPresent()) {
            PessoaModel pessoaModel = pessoaModelOptional.get();
            try {
                emailModel.setPessoa(pessoaModel);
                emailModel.setAssunto(emailModel.getAssunto());
                emailModel.setMensagem(emailModel.getMensagem());
                emailModel.setDataEmail(LocalDateTime.now());

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("iano.dev.smtp@gmail.com"); // trocar para -> emailModel.getPessoa().getNome()
                mailMessage.setTo("ianomaciel6385@gmail.com"); // trocar para o email da ADM :)
                mailMessage.setSubject(emailModel.getAssunto());
                mailMessage.setText(emailModel.getMensagem());


                javaMailSender.send(mailMessage);

                emailModel.setStatusEmail(StatusEmail.SENT);
            } catch (MailException e) {
                emailModel.setStatusEmail(StatusEmail.ERROR);
            } finally {
                return emailRespository.save(emailModel);
            }
        } else {
            throw new IllegalArgumentException("Pessoa não encontrada");
        }
    }

    public List<EmailModel> listarTodosEmails() {
        return emailRespository.findAll();
    }
}
