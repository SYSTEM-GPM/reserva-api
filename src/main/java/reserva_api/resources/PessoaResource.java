package reserva_api.resources;

import java.net.URI;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import reserva_api.dtos.PessoaDto;
import reserva_api.models.*;
import reserva_api.models.enums.TipoTelefone;
import reserva_api.repositories.filters.PessoaFilter;
import reserva_api.services.EnviaEmailService;
import reserva_api.services.PessoaService;
import reserva_api.services.UsuarioService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EnviaEmailService enviaEmailService;

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
    /*
	@PostMapping
	public ResponseEntity<PessoaModel> salvar(@Valid @RequestBody PessoaModel pessoaModel) {
		PessoaModel pessoaModelSalvo = pessoaService.salvar(pessoaModel);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaModelSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(pessoaModelSalvo);
	}
	*/

	@PostMapping
	//o retorno de ResponseEntity sera um objeto (status e corpo) utilizado para retornar uma resposta ao usuario
	//@Valid pode gerar o badrequest caso o valor informado pelo usuario venha invalido
	public ResponseEntity<PessoaModel> salvar(@RequestBody @Valid PessoaDto pessoaDto) throws MessagingException {

		//---Validações
/*		if(pessoaService.existsByLicensePlateCar(pessoaDto.getLicensePlateCar())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate is alredy in use!");
		}

		if(pessoaService.existsByParkingSpotNumber(pessoaDto.getParkingSpotNumber())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is alredy in use!");
		}

		if(pessoaService.existsByApartmentAndBlock(pessoaDto.getApartment(), pessoaDto.getBlock())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot alredy registered for this apartment/block!");
		}

		//validar setor e data com valores null
*/
		//---

		//so executa caso o usuario tenha enviado todos os dados corretamente
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

		//Cadastro na tabela usuario
		//var usuarioModel = new UsuarioModel();
		//usuarioModel.setPessoaId(pessoaModel.getId());
		//usuarioModel.setEmail(pessoaDto.getEmail());
		//usuarioModel.setSenha(pessoaDto.getSenha());
		//usuarioService.salvar(usuarioModel);

		// enviaEmailService.enviar(
		// 		"josilenevitoriasilva@gmail.com",
		// 		"Teste Spring Boot",
		// 		MensagemEmailUtil.ativacaoUsuario("Josilene", "https://google.com")
		// );

		//status é uma resposta
		//body informa retorno do metodo save com os dados ja salvos no banco
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel);
	}

	@PostMapping(value = "/motoristas")
	public ResponseEntity<PessoaModel> salvar(@Valid @RequestBody Motorista pessoa) {
		PessoaModel pessoaModelSalvo = pessoaService.salvar(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaModelSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(pessoaModelSalvo);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		pessoaService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<PessoaModel> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaModel pessoaModel) {
		PessoaModel pessoaModelSalvo = pessoaService.atualizar(id, pessoaModel);
		return ResponseEntity.ok(pessoaModelSalvo);
	}

	@PutMapping("/motoristas/{id}")
	public ResponseEntity<PessoaModel> atualizar(@PathVariable Long id, @Valid @RequestBody Motorista pessoa) {
		PessoaModel pessoaModelSalvo = pessoaService.atualizar(id, (Motorista) pessoa);
		return ResponseEntity.ok(pessoaModelSalvo);
	}

}
