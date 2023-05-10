package reserva_api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import reserva_api.dtos.ReservaDto;
import reserva_api.models.PessoaModel;
import reserva_api.models.Recurso;
import reserva_api.models.Solicitacao;
import reserva_api.models.enums.StatusSolicitacao;
import reserva_api.repositories.PessoaRepository;
import reserva_api.repositories.RecursoRepository;
import reserva_api.repositories.SolicitacaoRepository;
import reserva_api.repositories.filters.RecursoFilter;
import reserva_api.services.exceptions.AllPropertiesIsRequiredException;
import reserva_api.services.exceptions.NonFreeResourceException;

@Service
public class SolicitacaoService {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;

	public Page<Solicitacao> buscarTodas(Pageable pageable) {
		return solicitacaoRepository.findAll(pageable);
	}

	public Page<ReservaDto> todasReservaPorData(RecursoFilter recursoFilter, Pageable pageable) {
		return solicitacaoRepository.todasReservaPorData(recursoFilter, pageable);
	}

	public Page<ReservaDto> reservaTransportePorData(RecursoFilter recursoFilter, Pageable pageable) {
		return solicitacaoRepository.reservaTransportePorData(recursoFilter, pageable);
	}

	public Page<ReservaDto> reservaEquipamentoPorData(RecursoFilter recursoFilter, Pageable pageable) {
		return solicitacaoRepository.reservaEquipamentoPorData(recursoFilter, pageable);
	}

	public Page<ReservaDto> reservaLocalPorData(RecursoFilter recursoFilter, Pageable pageable) {
		return solicitacaoRepository.reservaLocalPorData(recursoFilter, pageable);
	}

	public Solicitacao buscarPorId(Long id) {
		return solicitacaoRepository.findById(id).orElseThrow();
	}

	public List<Solicitacao> buscarPorPessoa(Long id) {
		PessoaModel solicitante = pessoaRepository.findById(id).orElseThrow();
		return solicitacaoRepository.findBySolicitante(solicitante).orElseThrow();
	}

	public Boolean IsLivre(RecursoFilter recursoFilter) {
		Pageable pageable = PageRequest.of(0, 20);
		if(ObjectUtils.isEmpty(recursoFilter.getIdRecurso()) || 
				ObjectUtils.isEmpty(recursoFilter.getDataInicio()) ||
				ObjectUtils.isEmpty(recursoFilter.getDataFinal())) {
			throw new AllPropertiesIsRequiredException();
		}
		Page<ReservaDto> reservas = solicitacaoRepository.todasReservaPorData(recursoFilter, pageable);
		return reservas.isEmpty();
	}

	public Solicitacao salvar(Solicitacao solicitacao) {
		solicitacao.setDataSolicitacao(LocalDateTime.now());
		solicitacao.setStatus(StatusSolicitacao.SOLICITADO);
		pessoaRepository.findById(solicitacao.getSolicitante().getId()).orElseThrow();
		RecursoFilter recursoFilter = new RecursoFilter(null, solicitacao.getDataInicio(), 
				solicitacao.getDataFinal(),solicitacao.getStatus());
		for (Recurso r : solicitacao.getRecursos()) {
			recursoRepository.findById(r.getId()).orElseThrow();
			recursoFilter.setIdRecurso(r.getId());
			if (!IsLivre(recursoFilter)) {
				throw new NonFreeResourceException(r.getId());
			}
		}
		return solicitacaoRepository.save(solicitacao);
	}

	public void excluirPorId(Long id) {
		solicitacaoRepository.deleteById(id);
	}

	public Solicitacao atualizar(Long id, Solicitacao solicitacao) {
		Solicitacao solicitacaoSalva = solicitacaoRepository.findById(id).orElseThrow();
		
		pessoaRepository.findById(solicitacao.getSolicitante().getId()).orElseThrow();
		RecursoFilter recursoFilter = new RecursoFilter(null, solicitacao.getDataInicio(), 
				solicitacao.getDataFinal(),solicitacao.getStatus());
		for (Recurso r : solicitacao.getRecursos()) {
			recursoRepository.findById(r.getId()).orElseThrow();
			recursoFilter.setIdRecurso(r.getId());
			if (!IsLivre(recursoFilter)) {
				throw new NonFreeResourceException(r.getId());
			}
		}
		BeanUtils.copyProperties(solicitacao, solicitacaoSalva, new String[] { "id", "dataSolicitacao" });
		return solicitacaoRepository.save(solicitacaoSalva);
	}

}
