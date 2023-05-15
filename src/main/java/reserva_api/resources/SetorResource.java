package reserva_api.resources;

import java.net.URI;

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
import reserva_api.models.SetorModel;
import reserva_api.services.SetorService;

@RestController
@RequestMapping(value = "/setores")
public class SetorResource {
	
	@Autowired
	private SetorService setorService;
	
	@GetMapping
	public ResponseEntity<Page<SetorModel>> buscarTodos(Pageable pageable) {
		return ResponseEntity.ok().body(setorService.buscarTodos(pageable));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SetorModel> buscarPorId(@PathVariable Long id) {
		SetorModel setorModel = setorService.buscarPorId(id);
		return ResponseEntity.ok().body(setorModel);
	}
	
	@PostMapping
	public ResponseEntity<SetorModel> salvar(@Valid @RequestBody SetorModel setorModel) {
		SetorModel setorModelSalvo = setorService.salvar(setorModel);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(setorModelSalvo.getId()).toUri();
		return ResponseEntity.created(uri).body(setorModelSalvo);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		setorService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SetorModel> atualizar(@PathVariable Long id, @Valid @RequestBody SetorModel setorModel) {
		SetorModel setorModelSalvo = setorService.atualizar(id, setorModel);
		return ResponseEntity.ok(setorModelSalvo);
	}
	
	
	
	

}
