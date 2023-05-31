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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import reserva_api.dtos.*;
import reserva_api.models.*;
import reserva_api.models.enums.StatusConta;
import reserva_api.models.enums.TipoPerfil;
import reserva_api.models.enums.TipoTelefone;
import reserva_api.repositories.filters.PessoaFilter;
import reserva_api.utils.ApiError;
import reserva_api.utils.ApiSuccess;
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

	@Autowired
	private PasswordEncoder encoder;

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
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiError( "Conta de usuário não encontrada!"));
		}

		var pessoa = pessoaModel.get();

		if(pessoa.getStatus() == StatusConta.DESATIVADA) {
			if(pessoaModel.isEmpty()) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new ApiError( "Conta de usuário desativada!"));
			}
		}

		if (pessoa.getCodigoAtivacao() == null || !pessoa.getCodigoAtivacao().equals(validaCodigoAtivacaoDto.getCodigo())) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError( "Código de ativação inválido!"));
		}


		return ResponseEntity.ok().body(pessoa);
	}

	@PostMapping("/envia-codigo/{opcao}")
	public ResponseEntity<Object> enviaCodigoAtivacao(@PathVariable(value = "opcao") String opcao, @RequestBody @Valid EnviaCodigoDto enviaCodigoDto) throws MessagingException {
		var pessoaModel = pessoaService.buscarPorEmail(enviaCodigoDto.getEmail());

		if(pessoaModel.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiError( "Conta de usuário não encontrada!"));
		}

		var pessoa = pessoaModel.get();

		if(pessoa.getStatus() == StatusConta.DESATIVADA) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError( "Conta de usuário desativada!"));
		}

		String resposta = "";

		var status = HttpStatus.OK;
		boolean comSenha = pessoa.getSenha() != null;

		if (opcao.equals("envio-admin")) {
			if(pessoa.getStatus() != StatusConta.PENDENTE_ATIVACAO_USUARIO) {
				status = HttpStatus.BAD_REQUEST;
				resposta =  "Conta de usuário já ativada!";
			} else {
				resposta = "Código de ativação de conta enviado com sucesso!";

				pessoa.setCodigoAtivacao(geraNumeroAleatorio());
				enviaEmailService.enviar(
						pessoa.getEmail(),
						"Ativação da Conta",
						MensagemEmailUtil.ativacaoUsuario(pessoa)
				);
			}
		} else if (opcao.equals("ativacao-usuario")) {
			if(comSenha && pessoa.getStatus() != StatusConta.PENDENTE_ATIVACAO_USUARIO) {
				status = HttpStatus.BAD_REQUEST;
				resposta =  "Conta de usuário já ativada!";
			} else {
				resposta = "Código de ativação de reenviado com sucesso!";

				pessoa.setCodigoAtivacao(geraNumeroAleatorio());
				enviaEmailService.enviar(
						pessoa.getEmail(),
						"Ativação da Conta",
						MensagemEmailUtil.envioCodigoUsuario(pessoa)
				);
			}
		} else if (opcao.equals("recuperar-conta")) {
			if(pessoa.getStatus() != StatusConta.ATIVADA && !comSenha) {
				status = HttpStatus.BAD_REQUEST;
				resposta =  "Conta pendente de ativação!";
			} else {
				resposta = "Código para recuperação de acesso enviado com sucesso!";

				pessoa.setCodigoAtivacao(geraNumeroAleatorio());
				enviaEmailService.enviar(
						pessoa.getEmail(),
						"Recuperação da conta",
						MensagemEmailUtil.recuperacaoConta(pessoa)
				);
			}
		} else {
			resposta =  "Opcao não encontrada";
			status = HttpStatus.NOT_FOUND;
		}

        pessoaService.salvar(pessoa);

		Object res;
		if (status == HttpStatus.OK) {
			res = new ApiSuccess(resposta);
		} else {
			res = new ApiError(resposta);
		}

        return ResponseEntity.status(status).body(res);
	}

	@PutMapping("/{id}/senha")
	public ResponseEntity criarSenha(@PathVariable Long id, @RequestBody @Valid CriarSenhaDto criarSenhaDto) {
		var pessoa = pessoaService.buscarPorId(id);

		if(pessoa.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiError( "Usuário não encontrado!"));
		}

		var pessoaModel = pessoa.get();

		if (pessoaModel.getCodigoAtivacao() == null || !pessoaModel.getCodigoAtivacao().equals(criarSenhaDto.getCodigo())) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError( "Código de ativação inválido!"));
		}

		pessoaModel.setSenha(encoder.encode(criarSenhaDto.getSenha()));
		pessoaModel.setStatus(StatusConta.ATIVADA);
		pessoaModel.setCodigoAtivacao(null);

		pessoaService.salvar(pessoaModel);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Senha salva com sucesso!"));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
		var pessoaModel = pessoaService.buscarPorId(id);

		if(pessoaModel.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiError( "Pessoa não encontrada!"));
		}

		return ResponseEntity.ok().body(pessoaModel);
	}

	//cadastrando pessoas
	@PostMapping
	//o retorno de ResponseEntity sera um objeto (status e corpo) utilizado para retornar uma resposta ao usuario
	//@Valid pode gerar o badrequest caso o valor informado pelo usuario venha invalido
	public ResponseEntity<Object> salvar(@RequestBody @Valid PessoaDto pessoaDto) {
		pessoaDto.setCpf(pessoaDto.getCpf().replaceAll("[^0-9]", ""));
		pessoaDto.setTelefone(pessoaDto.getTelefone().replaceAll("[^0-9]", ""));
		pessoaDto.setSiape(pessoaDto.getSiape().replaceAll("[^0-9]", ""));

		var erros = new ApiError();
		//---Validações
		if(pessoaService.existsByCpf(pessoaDto.getCpf())){
			erros.setError( "CPF já está em uso!");
		}

		if(pessoaService.existsBySiape(pessoaDto.getSiape())){
			erros.setError( "Siape já está em uso!");
		}

		if(pessoaService.existsByEmail(pessoaDto.getEmail())){
			erros.setError( "Email já está em uso!");
		}

		if(motoristaService.existsByNumeroCnh(pessoaDto.getNumeroCnh())){
			erros.setError( "CNH já está em uso!");
		}

		if(!erros.getErrors().isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
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

		//status é uma resposta
		//body informa retorno do metodo save com os dados ja salvos no banco
		//return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess("Cadastro realizado com sucesso!"));
	}

	@PostMapping(value = "/auto")
	public ResponseEntity<Object> salvarNormal(@RequestBody @Valid CadastroUsuarioDto cadastroUsuarioDto) {
		cadastroUsuarioDto.setCpf(cadastroUsuarioDto.getCpf().replaceAll("[^0-9]", ""));
		cadastroUsuarioDto.setTelefone(cadastroUsuarioDto.getTelefone().replaceAll("[^0-9]", ""));
		cadastroUsuarioDto.setSiape(cadastroUsuarioDto.getSiape().replaceAll("[^0-9]", ""));

		var erros = new ApiError();
		if(pessoaService.existsByCpf(cadastroUsuarioDto.getCpf())){
			erros.setError( "CPF já está em uso!");
		}

		if(pessoaService.existsBySiape(cadastroUsuarioDto.getSiape())){
			erros.setError( "Siape já está em uso!");
		}

		if(pessoaService.existsByEmail(cadastroUsuarioDto.getEmail())){
			erros.setError( "Email já está em uso!");
		}

		if(!erros.getErrors().isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
		}

		var pessoaModel = new PessoaModel();
		BeanUtils.copyProperties(cadastroUsuarioDto, pessoaModel);


		var telefone = new TelefoneModel();
		telefone.setTipo(TipoTelefone.CELULAR);
		telefone.setNumero(cadastroUsuarioDto.getTelefone());
		pessoaModel.setTelefone(telefone);

		var setor = new SetorModel();
		setor.setId(cadastroUsuarioDto.getSetor());
		pessoaModel.setSetor(setor);

		pessoaModel.setStatus(StatusConta.PENDENTE_ATIVACAO_ADMIN);
		pessoaModel.setTipoPerfil(TipoPerfil.NORMAL);
		pessoaModel.setSenha(encoder.encode(cadastroUsuarioDto.getSenha()));

		pessoaService.salvar(pessoaModel);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess("Cadastro realizado com sucesso!"));
	}

	//atualizando pessoas
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
											@RequestBody @Valid PessoaDto pessoaDto) {

		//Verifica se usuário existe a partir do id enviado
		Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
		if(!pessoaModelOptional.isPresent()){
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiError( "Usuário não existe!"));
		}

		//validações para valores nulos

		//Identifica os dados que vao sofrer alterações
        var pessoaModel = pessoaModelOptional.get();

		pessoaDto.setCpf(pessoaDto.getCpf().replaceAll("[^0-9]", ""));
		pessoaDto.setTelefone(pessoaDto.getTelefone().replaceAll("[^0-9]", ""));
		pessoaDto.setSiape(pessoaDto.getSiape().replaceAll("[^0-9]", ""));

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
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ApiSuccess("Atualização realizada com sucesso!"));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		pessoaService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

}
