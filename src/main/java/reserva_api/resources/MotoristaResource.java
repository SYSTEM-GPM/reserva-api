package reserva_api.resources;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reserva_api.dtos.MotoristaDto;
import reserva_api.dtos.PessoaDto;
import reserva_api.models.MotoristaModel;
import reserva_api.models.PessoaModel;
import reserva_api.models.SetorModel;
import reserva_api.models.TelefoneModel;
import reserva_api.models.enums.TipoTelefone;
import reserva_api.services.MotoristaService;
import reserva_api.services.PessoaService;
import reserva_api.utils.MensagemEmailUtil;

import java.util.Optional;

@RestController
@RequestMapping(value = "/pessoas/motoristas")
public class MotoristaResource {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private MotoristaService motoristaService;

    //cadastrando motorista
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid MotoristaDto motoristaDto) {

        if(motoristaService.existsByNumeroCnh(motoristaDto.getNumeroCnh())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: CNH já está em uso!");
        }

        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(motoristaDto, pessoaModel);
        pessoaModel = pessoaService.salvar(pessoaModel);

        var motoristaModel = new MotoristaModel();
        motoristaModel.setId(pessoaModel.getId());
        motoristaModel.setNumeroCnh(motoristaDto.getNumeroCnh());
        motoristaService.salvar(motoristaModel);

        //return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel.getNome() +"\n"+ motoristaModel.getNumeroCnh());
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
                                            @RequestBody @Valid MotoristaDto motoristaDto) {

        //verifica se motorista existe
        Optional<MotoristaModel> motoristaModelOptional = motoristaService.findById(id);
        if(!motoristaModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Motorista não existe!");
        }

        //pega dados da pessoa
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);

        var motoristaModel = motoristaModelOptional.get();
        motoristaModel.setNumeroCnh(motoristaDto.getNumeroCnh());
        motoristaService.salvar(motoristaModel);

        var pessoaModel = pessoaModelOptional.get();
        pessoaModel.setNome(motoristaDto.getNome());
        pessoaService.salvar(pessoaModel);

        //return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel.getNome() +"\n"+ motoristaModel.getNumeroCnh());
        return ResponseEntity.status(HttpStatus.OK).body("Atualização realizada com sucesso!");
    }

}
