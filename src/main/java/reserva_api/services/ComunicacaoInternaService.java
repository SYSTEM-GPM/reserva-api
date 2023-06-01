package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reserva_api.models.ComunicacaoInternaModel;
import reserva_api.repositories.ComunicacaoInternaRespository;
import reserva_api.repositories.PessoaRepository;

import java.util.List;

@Service
public class ComunicacaoInternaService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ComunicacaoInternaRespository comunicacaoInternaRespository;

    public List<ComunicacaoInternaModel> buscarTodos() {
        return comunicacaoInternaRespository.findAll();
    }

    public ComunicacaoInternaModel salvar(ComunicacaoInternaModel comunicacoInterna) {
        return comunicacaoInternaRespository.save(comunicacoInterna);
    }

}