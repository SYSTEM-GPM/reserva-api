package reserva_api.resources;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reserva_api.dtos.ComunicacaoInternaDto;
import reserva_api.models.ComunicacaoInternaModel;
import reserva_api.models.enums.StatusEmail;
import reserva_api.models.enums.TipoPerfil;
import reserva_api.services.ComunicacaoInternaService;
import reserva_api.services.EnviaEmailService;
import reserva_api.services.PessoaService;
import reserva_api.utils.ApiError;
import reserva_api.utils.Constantes;
import reserva_api.utils.MensagemEmailUtil;

import java.util.List;

@RestController
@RequestMapping(value = "/comunicacao-interna")
public class ComunicacaoInternaResource {
    @Autowired
    private ComunicacaoInternaService comunicacaoInternaService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnviaEmailService enviaEmailService;

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody ComunicacaoInternaDto comunicacaoInternaDto) throws MessagingException {
        var pessoaModelOptional = pessoaService.findById(comunicacaoInternaDto.getPessoaId());

        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Usuário não encontrado!"));
        }

        var comunicacoInternaModel = new ComunicacaoInternaModel();
        BeanUtils.copyProperties(comunicacaoInternaDto, comunicacoInternaModel);

        comunicacoInternaModel.setPessoa(pessoaModelOptional.get());

        enviaEmailService.enviar(
                Constantes.adminEmail, // e-mail do admin
                comunicacoInternaModel.getAssunto(),
                MensagemEmailUtil.solicitacaoComunicacaoInterna(comunicacoInternaModel)
        );

        comunicacoInternaModel.setStatus(StatusEmail.SENT);

        comunicacaoInternaService.salvar(comunicacoInternaModel);

        return new ResponseEntity<>(comunicacoInternaModel, HttpStatus.CREATED);
    }

    @GetMapping
    public Object buscarTodas(Pageable pageable, HttpServletRequest request) {
        var emailPessoaLogada = request.getUserPrincipal().getName();
        var pessoa = pessoaService.buscarPorEmail(emailPessoaLogada);
        var pessoaModel = pessoa.get();

        System.out.println("Tipo de perfil do usuário: " + pessoaModel.getTipoPerfil());

        Sort sort = Sort.by("solicitacaoId").descending();

        if (pessoaModel.getTipoPerfil() == TipoPerfil.ADMIN) {
            return comunicacaoInternaService.buscarTodos(pageable);
        }

        return comunicacaoInternaService.buscarTodos(pageable);


//        return comunicacaoInternaService.buscarPorPessoa(pessoaModel, pageable);
    }

}
