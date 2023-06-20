package reserva_api.services;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import reserva_api.dtos.ReservaDto;
import reserva_api.dtos.SolicitacaoTransporteAtualizarDto;
import reserva_api.dtos.SolicitacaoTransporteDto;
import reserva_api.dtos.projection.SolicitacaoLocalProjection;
import reserva_api.models.*;
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

	public Page<SolicitacaoLocalProjection> buscarTodosLocais(Pageable pageable) {

		//resgata todas as solicitacoes
		List<SolicitacaoLocalProjection> solicitacaoGeral = solicitacaoRepository.buscarTodosLocais();

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<SolicitacaoLocalProjection> pageSolicitacao;

		if (solicitacaoGeral.size() < startItem) {
			pageSolicitacao = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, solicitacaoGeral.size());
			pageSolicitacao = solicitacaoGeral.subList(startItem, toIndex);
		}

		Page<SolicitacaoLocalProjection> page = new PageImpl<>(pageSolicitacao, pageable, solicitacaoGeral.size());

		if (page.isEmpty()) {
			// Não há viagens disponíveis
			// Você pode lançar uma exceção, retornar um ResponseEntity com status adequado ou retornar uma mensagem personalizada
			throw new NoSuchElementException("Sem registros");
		}

		return page;
	}

	public Page<SolicitacaoLocalProjection> buscarTodosLocaisPorPessoa(Long id, Pageable pageable) {

		//resgata todas as solicitacoes
		List<SolicitacaoLocalProjection> solicitacaoGeral = solicitacaoRepository.buscarTodosLocaisPorPessoa(id);

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<SolicitacaoLocalProjection> pageSolicitacao;

		if (solicitacaoGeral.size() < startItem) {
			pageSolicitacao = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, solicitacaoGeral.size());
			pageSolicitacao = solicitacaoGeral.subList(startItem, toIndex);
		}

		Page<SolicitacaoLocalProjection> page = new PageImpl<>(pageSolicitacao, pageable, solicitacaoGeral.size());

		return page;
	}

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

	public Solicitacao salvar(Long idPessoa, Long idLocal, Solicitacao solicitacao) {

		solicitacao.setDataSolicitacao(LocalDateTime.now());
		solicitacao.setAutorizacao(StatusSolicitacao.RESERVADO);
		solicitacao.setSolicitante(new PessoaModel(idPessoa));
		pessoaRepository.findById(idPessoa).orElseThrow();

		solicitacao.getRecursos().add(new LocalModel(idLocal));

		RecursoFilter recursoFilter = new RecursoFilter(null, solicitacao.getDataInicio(),
				solicitacao.getDataFinal(),solicitacao.getAutorizacao());

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
				solicitacao.getDataFinal(),solicitacao.getAutorizacao());
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

	public Solicitacao solicitarTransporte(SolicitacaoTransporteDto solicitacaoTransporteDto, Solicitacao solicitacao) {

		solicitacao.setDataSolicitacao(LocalDateTime.now());
		solicitacao.setAutorizacao(StatusSolicitacao.SOLICITADO);
		solicitacao.setSolicitante(new PessoaModel(solicitacaoTransporteDto.getIdPessoa()));
		pessoaRepository.findById(solicitacaoTransporteDto.getIdPessoa()).orElseThrow();

		solicitacao.getRecursos().add(new TransporteModel(solicitacaoTransporteDto.getIdTransporte()));

		RecursoFilter recursoFilter = new RecursoFilter(null, solicitacao.getDataInicio(),
				solicitacao.getDataFinal(),solicitacao.getAutorizacao());

		for (Recurso r : solicitacao.getRecursos()) {
			recursoRepository.findById(r.getId()).orElseThrow();
			recursoFilter.setIdRecurso(r.getId());
			if (!IsLivre(recursoFilter)) {
				throw new NonFreeResourceException(r.getId());
			}
		}

		return solicitacaoRepository.save(solicitacao);
	}

	public Solicitacao atualizarResposta(Long id, SolicitacaoTransporteAtualizarDto transporteAtualizarDto) {

		Solicitacao solicitacao = solicitacaoRepository.findById(id).orElseThrow();
		solicitacao.setAutorizacao(transporteAtualizarDto.getAutorizacao());

		if (solicitacao.getAutorizacao().equals(StatusSolicitacao.NEGADO)){
			solicitacao.setJustificativa(transporteAtualizarDto.getJustificativa());
		}

		return solicitacaoRepository.save(solicitacao);
	}


	public boolean existeRecurso(Recurso recurso) {
		return solicitacaoRepository.existsByRecursos(recurso);
	}
}
