package reserva_api.resources;

import java.net.URI;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import reserva_api.dto.PessoaDto;
import reserva_api.model.Motorista;
import reserva_api.model.Pessoa;
import reserva_api.repositories.filters.PessoaFilter;
import reserva_api.services.EnviaEmailService;
import reserva_api.services.PessoaService;
import reserva_api.utils.MensagemEmailUtil;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private EnviaEmailService enviaEmailService;

	@GetMapping
	public ResponseEntity<Page<Pessoa>> buscarTodos(Pageable pageable) {
		return ResponseEntity.ok().body(pessoaService.buscarTodos(pageable));
	}

	@GetMapping("/resumo")
	public ResponseEntity<Page<PessoaDto>> filtarPessoa(PessoaFilter pessoaFilter,Pageable pageable) {
		return ResponseEntity.ok().body(pessoaService.filtarTodas(pessoaFilter, pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		return ResponseEntity.ok().body(pessoa);
	}

	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa) throws MessagingException {
		Pessoa pessoaSalvo = pessoaService.salvar(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaSalvo.getId())
				.toUri();

		// enviaEmailService.enviar(
		// 		"josilenevitoriasilva@gmail.com",
		// 		"Teste Spring Boot",
		// 		MensagemEmailUtil.ativacaoUsuario("Josilene", "https://google.com")
		// );

		return ResponseEntity.created(uri).body(pessoaSalvo);
	}

	@PostMapping(value = "/motoristas")
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Motorista pessoa) {
		Pessoa pessoaSalvo = pessoaService.salvar(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(pessoaSalvo);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		pessoaService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalvo = pessoaService.atualizar(id, pessoa);
		return ResponseEntity.ok(pessoaSalvo);
	}

	@PutMapping("/motoristas/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Motorista pessoa) {
		Pessoa pessoaSalvo = pessoaService.atualizar(id, (Motorista) pessoa);
		return ResponseEntity.ok(pessoaSalvo);
	}

}
