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

	public PessoaModel atualizar(Long id, PessoaModel pessoaModel) {
		PessoaModel pessoaModelSalvo = pessoaRepository.getReferenceById(id);
		copyPessoa(pessoaModel, pessoaModelSalvo);
		return pessoaRepository.save(pessoaModelSalvo);
	}

	private void copyPessoa(PessoaModel pessoaModel, PessoaModel pessoaModelSalvo) {
		if (pessoaModel.getNome() != null) {
			pessoaModelSalvo.setNome(pessoaModel.getNome());
		}
		if (pessoaModel.getCpf() != null) {
			pessoaModelSalvo.setCpf(pessoaModel.getCpf());
		}
		if (pessoaModel.getSetor() != null) {
			pessoaModelSalvo.setSetor(pessoaModel.getSetor());
		}
		if (pessoaModel.getSiape() != null) {
			pessoaModelSalvo.setSiape(pessoaModel.getSiape());

		}
		if (pessoaModel.getDataNascimento() != null) {
			pessoaModelSalvo.setDataNascimento(pessoaModel.getDataNascimento());
		}

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

}
