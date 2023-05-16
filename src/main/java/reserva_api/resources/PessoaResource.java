package reserva_api.resources;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import reserva_api.dtos.PessoaDto;
import reserva_api.models.*;
import reserva_api.models.enums.TipoTelefone;
import reserva_api.repositories.filters.PessoaFilter;
import reserva_api.services.EnviaEmailService;
import reserva_api.services.MotoristaService;
import reserva_api.services.PessoaService;
import reserva_api.utils.MensagemEmailUtil;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private MotoristaService motoristaService;

	@Autowired
	private EnviaEmailService enviaEmailService;

	//visualizar pessoas
	@GetMapping
	public ResponseEntity<Page<PessoaModel>> buscarTodos(Pageable pageable) {
		return ResponseEntity.ok().body(pessoaService.buscarTodos(pageable));
	}

	@GetMapping("/resumo")
	public ResponseEntity<Page<PessoaDto>> filtarPessoa(PessoaFilter pessoaFilter,Pageable pageable) {
		return ResponseEntity.ok().body(pessoaService.filtarTodas(pessoaFilter, pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PessoaModel> buscarPorId(@PathVariable Long id) {
		PessoaModel pessoaModel = pessoaService.buscarPorId(id);
		return ResponseEntity.ok().body(pessoaModel);
	}

	//cadastrando pessoas
	@PostMapping
	//o retorno de ResponseEntity sera um objeto (status e corpo) utilizado para retornar uma resposta ao usuario
	//@Valid pode gerar o badrequest caso o valor informado pelo usuario venha invalido
	public ResponseEntity<Object> salvar(@RequestBody @Valid PessoaDto pessoaDto) throws MessagingException {

		//---Validações
		if(pessoaService.existsByCpf(pessoaDto.getCpf())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: CPF já está em uso!");
		}

		if(pessoaService.existsBySiape(pessoaDto.getSiape())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Siape já está em uso!");
		}

		if(pessoaService.existsByEmail(pessoaDto.getEmail())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Email já está em uso!");
		}

		if(motoristaService.existsByNumeroCnh(pessoaDto.getNumeroCnh())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: CNH já está em uso!");
		}

		//validar setor e data com valores null

		//---

		//so executa caso a pessoa tenha enviado todos os dados corretamente
		var pessoaModel = new PessoaModel();
		//Converte o valor dto para valores model
		BeanUtils.copyProperties(pessoaDto, pessoaModel);

		//como cliente nao tem acesso a esse dado, ele é cadastrado automaticamente
		//Definindo pelo codigo o padrao de CELULAR para novos cadastros
		var telefone = new TelefoneModel();
		telefone.setTipo(TipoTelefone.CELULAR);
		telefone.setNumero(pessoaDto.getTelefone());
		pessoaModel.setTelefone(telefone);

		//cadastra o setor
		var setor = new SetorModel();
		setor.setId(pessoaDto.getSetor());
		pessoaModel.setSetor(setor);

		//Cadastro na tabela pessoa
		pessoaModel = pessoaService.salvar(pessoaModel);

		//Cadastro na tabela motorista
		if(pessoaDto.getNumeroCnh() != null &&
			!pessoaDto.getNumeroCnh().isEmpty() &&
			!pessoaDto.getNumeroCnh().isBlank()){

			var motoristaModel = new MotoristaModel();
			motoristaModel.setId(pessoaModel.getId());
			motoristaModel.setNumeroCnh(pessoaDto.getNumeroCnh());
			motoristaService.salvar(motoristaModel);
		}

		enviaEmailService.enviar(
		 		pessoaDto.getEmail(),
		 		"Ativação da Conta",
				MensagemEmailUtil.ativacaoUsuario(pessoaDto.getNome(), "https://google.com")
		);

		//status é uma resposta
		//body informa retorno do metodo save com os dados ja salvos no banco
		//return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel);
		return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
	}

	//atualizando pessoas
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
											@RequestBody @Valid PessoaDto pessoaDto) {

		//Verifica se usuário existe a partir do id enviado
		Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
		if(!pessoaModelOptional.isPresent()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Usuário não existe!");
		}

		//validações para valores nulos

		//Identifica os dados que vao sofrer alterações
        var pessoaModel = pessoaModelOptional.get();

		pessoaModel.setNome(pessoaDto.getNome());
		pessoaModel.setCpf(pessoaDto.getCpf());
		pessoaModel.setSiape(pessoaDto.getSiape());
		pessoaModel.setDataNascimento(pessoaDto.getDataNascimento());
		pessoaModel.setTipoPerfil(pessoaDto.getTipoPerfil());

		var telefone = new TelefoneModel();
		telefone.setTipo(TipoTelefone.CELULAR);
		telefone.setNumero(pessoaDto.getTelefone());
		pessoaModel.setTelefone(telefone);

		var setor = new SetorModel();
		setor.setId(pessoaDto.getSetor());
		pessoaModel.setSetor(setor);

		pessoaModel.setEmail(pessoaDto.getEmail());
		//pessoaModel.setSenha(pessoaDto.getSenha());

		pessoaModel = pessoaService.salvar(pessoaModel);

		//remover validação para caso a pessoa queria retirar cnh
		if(pessoaDto.getNumeroCnh() != null &&
			!pessoaDto.getNumeroCnh().isEmpty() &&
			!pessoaDto.getNumeroCnh().isBlank()){

			var motoristaModel = new MotoristaModel();
			motoristaModel.setId(pessoaModel.getId());
			motoristaModel.setNumeroCnh(pessoaDto.getNumeroCnh());
			motoristaService.salvar(motoristaModel);
		}

		//return ResponseEntity.status(HttpStatus.OK).body(pessoaModel);
		return ResponseEntity.status(HttpStatus.OK).body("Atualização realizada com sucesso!");
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		pessoaService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

//	@PostMapping(value = "/motoristas")
//	public ResponseEntity<PessoaModel> salvar(@Valid @RequestBody MotoristaModel pessoa) {
//		PessoaModel pessoaModelSalvo = pessoaService.salvar(pessoa);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaModelSalvo.getId())
//				.toUri();
//		return ResponseEntity.created(uri).body(pessoaModelSalvo);
//	}

//	@PutMapping("/motoristas/{id}")
//	public ResponseEntity<PessoaModel> atualizar(@PathVariable Long id, @Valid @RequestBody MotoristaModel pessoa) {
//		PessoaModel pessoaModelSalvo = pessoaService.atualizar(id, (MotoristaModel) pessoa);
//		return ResponseEntity.ok(pessoaModelSalvo);
//	}

}
