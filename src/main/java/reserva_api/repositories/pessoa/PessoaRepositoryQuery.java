package reserva_api.repositories.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reserva_api.dtos.PessoaDto;
import reserva_api.repositories.filters.PessoaFilter;

public interface PessoaRepositoryQuery {
	public Page<PessoaDto> filtrarPessoa(PessoaFilter pessoaFilter, Pageable pageable);
}