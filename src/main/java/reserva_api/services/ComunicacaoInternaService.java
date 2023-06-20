package reserva_api.services;

import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reserva_api.models.ComunicacaoInternaModel;
import reserva_api.models.PessoaModel;
import reserva_api.models.Solicitacao;
import reserva_api.models.Viagem;
import reserva_api.repositories.ComunicacaoInternaRespository;
import reserva_api.repositories.PessoaRepository;

import java.util.*;

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

    public Page<ComunicacaoInternaModel> buscarPorPessoa(PessoaModel pessoaModel, Pageable pageable) {
        List<ComunicacaoInternaModel> comunicacaoInterna = comunicacaoInternaRespository.findByPessoa(pessoaModel);

        Collections.sort(comunicacaoInterna, new Comparator<ComunicacaoInternaModel>() {
            @Override
            public int compare(ComunicacaoInternaModel v1, ComunicacaoInternaModel v2) {
                return v2.getId().compareTo(v1.getId());
            }
        });

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ComunicacaoInternaModel> pageViagens;

        if (comunicacaoInterna.size() < startItem) {
            pageViagens = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comunicacaoInterna.size());
            pageViagens = comunicacaoInterna.subList(startItem, toIndex);
        }

        Page<ComunicacaoInternaModel> page = new PageImpl<>(pageViagens, pageable, comunicacaoInterna.size());

        return page;
    }

    public ComunicacaoInternaModel salvar(ComunicacaoInternaModel comunicacoInterna) {
        return comunicacaoInternaRespository.save(comunicacoInterna);
    }

}