package reserva_api.services;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reserva_api.models.ComunicacaoInternaModel;
import reserva_api.models.PessoaModel;
import reserva_api.models.Viagem;
import reserva_api.repositories.ComunicacaoInternaRespository;
import reserva_api.repositories.PessoaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ComunicacaoInternaService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ComunicacaoInternaRespository comunicacaoInternaRespository;

    public Page<ComunicacaoInternaModel> buscarTodos(Pageable pageable) {
        return comunicacaoInternaRespository.findAll(pageable);
    }

    public List<ComunicacaoInternaModel> buscarPorPessoa(PessoaModel pessoaModel, Pageable pageable) {
        return comunicacaoInternaRespository.findAll();
//        var comunicacaoInternaModal = comunicacaoInternaRespository.findByPessoa(pessoaModel);

//        var comunicacaoInterna = comunicacaoInternaModal.get();
//
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//        List<Viagem> pageViagens;
//
//        if (comunicacaoInterna.size() < startItem) {
//            pageViagens = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, comunicacaoInterna.size());
//            pageViagens = comunicacaoInterna.subList(startItem, toIndex);
//        }
//
//        Page<Viagem> page = new PageImpl<>(pageViagens, pageable, solicitacoesViagem.size());
    }

    public ComunicacaoInternaModel salvar(ComunicacaoInternaModel comunicacoInterna) {
        return comunicacaoInternaRespository.save(comunicacoInterna);
    }

}