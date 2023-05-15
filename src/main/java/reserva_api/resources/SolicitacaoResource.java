package reserva_api.resources;

import java.net.URI;
import java.util.List;

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
import reserva_api.dtos.ReservaDto;
import reserva_api.models.Solicitacao;
import reserva_api.models.Viagem;
import reserva_api.repositories.filters.RecursoFilter;
import reserva_api.services.SolicitacaoService;
import reserva_api.services.ViagemService;

@RestController
@RequestMapping(value = "/solicitacoes")
public class SolicitacaoResource {

	@Autowired
	private SolicitacaoService solicitacaoService;

	@Autowired
	private ViagemService viagemService;
	
	@GetMapping
	public ResponseEntity<Page<Solicitacao>> buscarTodas(Pageable pageable) {
		return ResponseEntity.ok().body(solicitacaoService.buscarTodas(pageable));
	}
	
	@GetMapping("/estalivre")
	public ResponseEntity<Boolean> isLivre(RecursoFilter recursoFilter) {
		return ResponseEntity.ok().body(solicitacaoService.IsLivre(recursoFilter));
	}

	@GetMapping("/resumo")
	public ResponseEntity<Page<ReservaDto>> buscarReservas(RecursoFilter recursoFilter, Pageable pageable) {
		return ResponseEntity.ok().body(solicitacaoService.todasReservaPorData(recursoFilter, pageable));
	}
	
	@GetMapping("/resumo/locais")
	public ResponseEntity<Page<ReservaDto>> buscarReservasLocal(RecursoFilter recursoFilter, Pageable pageable) {
		return ResponseEntity.ok().body(solicitacaoService.reservaLocalPorData(recursoFilter, pageable));
	}
	
	@GetMapping("/resumo/equipamentos")
	public ResponseEntity<Page<ReservaDto>> buscarReservasEquipamento(RecursoFilter recursoFilter, Pageable pageable) {
		return ResponseEntity.ok().body(solicitacaoService.reservaEquipamentoPorData(recursoFilter, pageable));
	}
	
	@GetMapping("/resumo/transportes")
	public ResponseEntity<Page<ReservaDto>> buscarReservasTransporte(RecursoFilter recursoFilter, Pageable pageable) {
		return ResponseEntity.ok().body(solicitacaoService.reservaTransportePorData(recursoFilter, pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
		Solicitacao solicitacao = solicitacaoService.buscarPorId(id);
		return ResponseEntity.ok().body(solicitacao);
	}

	@GetMapping(value = "/pessoa/{id}")
	public ResponseEntity<List<Solicitacao>> buscarPorPessoa(@PathVariable Long id) {
		return ResponseEntity.ok().body(solicitacaoService.buscarPorPessoa(id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		solicitacaoService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Solicitacao> salvar(@Valid @RequestBody Solicitacao solicitacao) {
		Solicitacao solicitacaoSalva = solicitacaoService.salvar(solicitacao);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(solicitacaoSalva.getId()).toUri();
		return ResponseEntity.created(uri).body(solicitacaoSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Solicitacao> atualizar(@PathVariable Long id, @Valid @RequestBody Solicitacao solicitacao) {
		Solicitacao solicitacaoSalva = solicitacaoService.atualizar(id, solicitacao);
		return ResponseEntity.ok(solicitacaoSalva);
	}

	@GetMapping(value = "/viagens")
	public ResponseEntity<Page<Viagem>> buscarTodasViagens(Pageable pageable) {
		return ResponseEntity.ok().body(viagemService.buscarTodas(pageable));
	}

	@GetMapping(value = "/viagens/{id}")
	public ResponseEntity<Viagem> buscarPorSolicitacao(@PathVariable Long id) {
		return ResponseEntity.ok().body(viagemService.buscarPorSolicitacao(id));
	}

	@PostMapping(value = "/viagens")
	public ResponseEntity<Viagem> salvarViagem(@Valid @RequestBody Viagem viagem) {
		Viagem viagemSalva = viagemService.salvar(viagem);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(viagemSalva.getId())
				.toUri();
		return ResponseEntity.created(uri).body(viagemSalva);
	}

	@DeleteMapping(value = "/viagens/{id}")
	public ResponseEntity<Void> excluirViagemPorId(@PathVariable Long id) {
		viagemService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/viagens/{id}")
	public ResponseEntity<Viagem> atualizar(@PathVariable Long id, @Valid @RequestBody Viagem viagem) {
		Viagem viagemSalva = viagemService.atualizar(id, viagem);
		return ResponseEntity.ok(viagemSalva);
	}

}
