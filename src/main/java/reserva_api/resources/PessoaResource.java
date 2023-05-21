package reserva_api.resources;

import java.util.Optional;
import java.util.Random;

import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import reserva_api.dtos.CriarSenhaDto;
import reserva_api.dtos.LoginDto;
import reserva_api.dtos.PessoaDto;
import reserva_api.dtos.ValidaCodigoAtivacaoDto;
import reserva_api.models.*;
import reserva_api.models.enums.StatusConta;
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

	private BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private String geraNumeroAleatorio() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		return String.format("%06d", number);
	}

	//visualizar pessoas
	@GetMapping
	public ResponseEntity<Page<PessoaModel>> buscarTodos(Pageable pageable) {
		return ResponseEntity.ok().body(pessoaService.buscarTodos(pageable));
	}

	@GetMapping("/resumo")
	public ResponseEntity<Page<PessoaDto>> filtarPessoa(PessoaFilter pessoaFilter,Pageable pageable) {
		return ResponseEntity.ok().body(pessoaService.filtarTodas(pessoaFilter, pageable));
	}

	@PostMapping("/valida-codigo")
	public ResponseEntity<Object> validaCodigoAtivacao(@RequestBody @Valid ValidaCodigoAtivacaoDto validaCodigoAtivacaoDto) {
		var pessoaModel = pessoaService.buscarPorEmail(validaCodigoAtivacaoDto.getEmail());

		if(pessoaModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Conta de usuário não encontrada!");
		}

		var pessoa = pessoaModel.get();

		if (pessoa.getCodigoAtivacao() == null || !pessoa.getCodigoAtivacao().equals(validaCodigoAtivacaoDto.getCodigo())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Código de ativação inválido!");
		}

		return ResponseEntity.ok().body(pessoa);
	}

	@PostMapping("/enviar-codigo")
	public ResponseEntity<Object> enviarCodigoAtivacao(@RequestBody @Valid ValidaCodigoAtivacaoDto validaCodigoAtivacaoDto) {
		var pessoaModel = pessoaService.buscarPorEmail(validaCodigoAtivacaoDto.getEmail());

		if(pessoaModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Conta de usuário não encontrada!");
		}

		var pessoa = pessoaModel.get();

		if (pessoa.getCodigoAtivacao() == null || !pessoa.getCodigoAtivacao().equals(validaCodigoAtivacaoDto.getCodigo())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Código de ativação inválido!");
		}

		return ResponseEntity.ok().body(pessoa);
	}

	@PutMapping("/{id}/senha")
	public ResponseEntity<Object> criarSenha(@PathVariable Long id, @RequestBody @Valid CriarSenhaDto criarSenhaDto) {
		var pessoa = pessoaService.buscarPorId(id);

		if(pessoa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Usuário não encontrado!");
		}

		var pessoaModel = pessoa.get();

		if (pessoaModel.getCodigoAtivacao() == null || !pessoaModel.getCodigoAtivacao().equals(criarSenhaDto.getCodigo())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Código de ativação inválido!");
		}

		if(pessoaModel.getStatus().equals(StatusConta.ATIVADA)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Conta de usuário já ativada!");
		}

		pessoaModel.setSenha(passwordEncoder().encode(criarSenhaDto.getSenha()));
		pessoaModel.setStatus(StatusConta.ATIVADA);
		pessoaModel.setCodigoAtivacao(null);

		pessoaService.salvar(pessoaModel);

		return ResponseEntity.ok().body("Usuário ativado com sucesso!");
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
		var pessoa = pessoaService.buscarPorEmail(loginDto.getEmail());

		if(pessoa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: E-mail não encontrado!");
		}

		return ResponseEntity.ok().body(pessoa);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
		var pessoaModel = pessoaService.buscarPorId(id);

		if(pessoaModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Pessoa não encontrada!");
		}

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


		// Define status da conta
		pessoaModel.setStatus(StatusConta.PENDENTE_ATIVACAO_USUARIO);
		// Define codigo de ativacao para enviar
		pessoaModel.setCodigoAtivacao(geraNumeroAleatorio());

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
				MensagemEmailUtil.ativacaoUsuario(pessoaModel)
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

}
