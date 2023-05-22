package reserva_api.resources;

import java.net.URI;

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
import reserva_api.dtos.LocalDto;
import reserva_api.dtos.TransporteDto;
import reserva_api.models.*;
import reserva_api.services.RecursoService;

@RestController
@RequestMapping(value = "/recursos")
public class RecursoResource {

	@Autowired
	private RecursoService recursoService;

	@GetMapping
	public ResponseEntity<Page<Recurso>> buscarTodos(Pageable pageable) {
		return ResponseEntity.ok().body(recursoService.buscarTodos(pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Recurso> buscarPorId(@PathVariable Long id) {
		Recurso recurso = recursoService.buscarPorId(id);
		return ResponseEntity.ok().body(recurso);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		recursoService.excluirPorId(id);
		return ResponseEntity.noContent().build();
	}

	//Buscar todos os transportes
	@GetMapping(value = "/transportes")
	public ResponseEntity<Page<TransporteModel>> buscarTransportes(Pageable pageable) {
		return ResponseEntity.ok().body(recursoService.buscarTransportes(pageable));
	}

	//Cadastrar transportes
	@PostMapping(value = "/transportes")
	public ResponseEntity<Object> salvar(@RequestBody @Valid TransporteDto transporteDto) {

		if(recursoService.existsByDescricao(transporteDto.getDescricao())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Transporte já existe!");
		}

		var transporteModel = new TransporteModel();
		BeanUtils.copyProperties(transporteDto, transporteModel);
		recursoService.salvar(transporteModel);

		//Recurso recursoSalvo = recursoService.salvar(transporteModel);
		//return ResponseEntity.status(HttpStatus.CREATED).body(recursoSalvo);

		return ResponseEntity.status(HttpStatus.CREATED).body("Transporte cadastrado com sucesso");
	}

	//Atualizar transportes
	@PutMapping("/transportes/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
											@RequestBody @Valid TransporteDto transporteDto) {

		//Recurso recursoSalvo = recursoService.atualizar(id, transporteDto);
		//return ResponseEntity.ok(recursoSalvo);

		recursoService.atualizar(id, transporteDto);
		return ResponseEntity.status(HttpStatus.OK).body("Atualização realizada com sucesso!");
	}

	//Buscar todos os locais
	@GetMapping(value = "/locais")
	public ResponseEntity<Page<LocalModel>> buscarLocais(Pageable pageable) {
		return ResponseEntity.ok().body(recursoService.buscarLocais(pageable));
	}

	//Cadastrar local
	@PostMapping(value = "/locais")
	public ResponseEntity<Object> salvar(@RequestBody @Valid LocalDto localDto) {

		if(recursoService.existsByDescricao(localDto.getDescricao())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local já existe!");
		}

		var localModel = new LocalModel();
		BeanUtils.copyProperties(localDto, localModel);
		recursoService.salvar(localModel);

		//Recurso recursoSalvo = recursoService.salvar(localModel);
		//return ResponseEntity.status(HttpStatus.CREATED).body(recursoSalvo);

		return ResponseEntity.status(HttpStatus.CREATED).body("Local cadastrado com sucesso");
	}

	//Atualizar Local
	@PutMapping("/locais/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
											@RequestBody @Valid LocalDto localDto) {

		//Recurso recursoSalvo = recursoService.atualizar(id, localDto);
		//return ResponseEntity.ok(recursoSalvo);

		recursoService.atualizar(id, localDto);
		return ResponseEntity.status(HttpStatus.OK).body("Atualização realizada com sucesso!");
	}

	/*
	@GetMapping(value = "/equipamentos")
	public ResponseEntity<Page<Equipamento>> buscarEquipamentos(Pageable pageable) {
		return ResponseEntity.ok().body(recursoService.buscarEquipamentos(pageable));
	}

	@PostMapping(value = "/equipamentos")
	public ResponseEntity<Recurso> salvar(@Valid @RequestBody Equipamento equipamento) {
		Recurso recursoSalvo = recursoService.salvar(equipamento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(recursoSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(recursoSalvo);
	}

	@PutMapping("/equipamentos/{id}")
	public ResponseEntity<Recurso> atualizar(@PathVariable Long id, @Valid @RequestBody Equipamento equipamento) {
		Recurso recursoSalvo = recursoService.atualizar(id, equipamento);
		return ResponseEntity.ok(recursoSalvo);
	}
	*/

}