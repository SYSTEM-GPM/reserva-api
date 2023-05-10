package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reserva_api.dtos.PessoaDto;
import reserva_api.models.Motorista;
import reserva_api.models.PessoaModel;
import reserva_api.repositories.MotoristaRepository;
import reserva_api.repositories.PessoaRepository;
import reserva_api.repositories.filters.PessoaFilter;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private MotoristaRepository motoristaRepository;

	public Page<PessoaModel> buscarTodos(Pageable pageable) {
		return pessoaRepository.findAll(pageable);
	}
	
	public Page<PessoaDto> filtarTodas(PessoaFilter pessoaFilter,Pageable pageable) {
		return pessoaRepository.filtrarPessoa(pessoaFilter, pageable);
	}

	public PessoaModel buscarPorId(Long id) {
		return pessoaRepository.findById(id).orElseThrow();
	}

	public PessoaModel salvar(PessoaModel pessoaModel) {
		return pessoaRepository.save(pessoaModel);
	}

	public PessoaModel salvar(Motorista motorista) {
		return pessoaRepository.save(motorista);
	}

	public void excluirPorId(Long id) {
		pessoaRepository.deleteById(id);
	}

	public PessoaModel atualizar(Long id, PessoaModel pessoaModel) {
		PessoaModel pessoaModelSalvo = pessoaRepository.getReferenceById(id);
		copyPessoa(pessoaModel, pessoaModelSalvo);
		return pessoaRepository.save(pessoaModelSalvo);
	}

	public PessoaModel atualizar(Long id, Motorista motorista) {
		Motorista pessoaSalvo = motoristaRepository.getReferenceById(id);
		copyMotorista(motorista, pessoaSalvo);
		return pessoaRepository.save(pessoaSalvo);
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

	private void copyMotorista(Motorista pessoa, Motorista pessoaSalvo) {
		if (pessoa.getNome() != null) {
			pessoaSalvo.setNome(pessoa.getNome());
		}
		if (pessoa.getCpf() != null) {
			pessoaSalvo.setCpf(pessoa.getCpf());
		}
		if (pessoa.getSetor() != null) {
			pessoaSalvo.setSetor(pessoa.getSetor());
		}
		if (pessoa.getSiape() != null) {
			pessoaSalvo.setSiape(pessoa.getSiape());

		}
		if (pessoa.getDataNascimento() != null) {
			pessoaSalvo.setDataNascimento(pessoa.getDataNascimento());
		}
		if (pessoa.getNumeroCnh() != null) {
			pessoaSalvo.setNumeroCnh(pessoa.getNumeroCnh());

		}

	}

}
