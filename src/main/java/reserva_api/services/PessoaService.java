package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reserva_api.dtos.PessoaDto;
import reserva_api.models.MotoristaModel;
import reserva_api.models.PessoaModel;
import reserva_api.repositories.MotoristaRepository;
import reserva_api.repositories.PessoaRepository;
import reserva_api.repositories.filters.PessoaFilter;

import java.util.Optional;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Page<PessoaModel> buscarTodos(Pageable pageable) {
		return pessoaRepository.findAll(pageable);
	}
	
	public Page<PessoaDto> filtarTodas(PessoaFilter pessoaFilter,Pageable pageable) {
		return pessoaRepository.filtrarPessoa(pessoaFilter, pageable);
	}

	public PessoaModel buscarPorId(Long id) {
		return pessoaRepository.findById(id).orElseThrow();
	}

	public PessoaModel salvar(PessoaModel pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public void excluirPorId(Long id) {
		pessoaRepository.deleteById(id);
	}

	//funcoes de validações
	public boolean existsByCpf(String cpf) {
		return pessoaRepository.existsByCpf(cpf);
	}

	public boolean existsBySiape(String siape) {
		return pessoaRepository.existsBySiape(siape);
	}

	public boolean existsByEmail(String email) {
		return pessoaRepository.existsByEmail(email);
	}

	public Optional<PessoaModel> findById(Long id) {
		return pessoaRepository.findById(id);
	}

}
