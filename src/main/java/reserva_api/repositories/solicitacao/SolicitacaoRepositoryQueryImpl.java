package reserva_api.repositories.solicitacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import reserva_api.dtos.ReservaDto;
import reserva_api.models.Equipamento;
import reserva_api.models.Local;
import reserva_api.models.PessoaModel;
import reserva_api.models.Recurso;
import reserva_api.models.Solicitacao;
import reserva_api.models.Transporte;
import reserva_api.repositories.filters.RecursoFilter;

public class SolicitacaoRepositoryQueryImpl implements SolicitacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<ReservaDto> todasReservaPorData(RecursoFilter recursoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ReservaDto> criteria = builder.createQuery(ReservaDto.class);
		Root<Solicitacao> root = criteria.from(Solicitacao.class);
		Join<Solicitacao, Recurso> recursoJoin = root.join("recursos");
		Join<Solicitacao, PessoaModel> pessoaJoin = root.join("solicitante");

		List<Predicate> predicates = criarRestricoes(recursoFilter, builder, root, recursoJoin, null);

		criteria.orderBy(builder.asc(root.get("dataInicio")));
		criteria.distinct(true);

		criteria.select(builder.construct(ReservaDto.class, root.get("id"), recursoJoin.get("id"),
				recursoJoin.get("descricao"), root.get("dataInicio"), root.get("dataFinal"), pessoaJoin.get("nome"),
				pessoaJoin.get("telefone").get("numero"), root.get("status")));

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<ReservaDto> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(recursoFilter, null)); 
	}

	@Override
	public Page<ReservaDto> reservaLocalPorData(RecursoFilter recursoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ReservaDto> criteria = builder.createQuery(ReservaDto.class);
		Root<Solicitacao> root = criteria.from(Solicitacao.class);
		Join<Solicitacao, Recurso> recursoJoin = root.join("recursos");
		Join<Solicitacao, PessoaModel> pessoaJoin = root.join("solicitante");

		List<Predicate> predicates = criarRestricoes(recursoFilter, builder, root, recursoJoin, Local.class);

		criteria.orderBy(builder.asc(root.get("dataInicio")));
		criteria.distinct(true);

		criteria.select(builder.construct(ReservaDto.class, root.get("id"), recursoJoin.get("id"),
				recursoJoin.get("descricao"), root.get("dataInicio"), root.get("dataFinal"), pessoaJoin.get("nome"),
				pessoaJoin.get("telefone").get("numero"), root.get("status")));

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<ReservaDto> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(recursoFilter, Local.class));

	}

	@Override
	public Page<ReservaDto> reservaEquipamentoPorData(RecursoFilter recursoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ReservaDto> criteria = builder.createQuery(ReservaDto.class);
		Root<Solicitacao> root = criteria.from(Solicitacao.class);
		Join<Solicitacao, Recurso> recursoJoin = root.join("recursos");
		Join<Solicitacao, PessoaModel> pessoaJoin = root.join("solicitante");

		List<Predicate> predicates = criarRestricoes(recursoFilter, builder, root, recursoJoin, Equipamento.class);
		

		criteria.orderBy(builder.asc(root.get("dataInicio")));
		criteria.distinct(true);

		criteria.select(builder.construct(ReservaDto.class, root.get("id"), recursoJoin.get("id"),
				recursoJoin.get("descricao"), root.get("dataInicio"), root.get("dataFinal"), pessoaJoin.get("nome"),
				pessoaJoin.get("telefone").get("numero"), root.get("status")));

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<ReservaDto> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(recursoFilter,Equipamento.class));
	}

	@Override
	public Page<ReservaDto> reservaTransportePorData(RecursoFilter recursoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ReservaDto> criteria = builder.createQuery(ReservaDto.class);
		Root<Solicitacao> root = criteria.from(Solicitacao.class);
		Join<Solicitacao, Recurso> recursoJoin = root.join("recursos");
		Join<Solicitacao, PessoaModel> pessoaJoin = root.join("solicitante");

		List<Predicate> predicates = criarRestricoes(recursoFilter, builder, root, recursoJoin, Transporte.class);
		

		criteria.orderBy(builder.asc(root.get("dataInicio")));
		criteria.distinct(true);

		criteria.select(builder.construct(ReservaDto.class, root.get("id"), recursoJoin.get("id"),
				recursoJoin.get("descricao"), root.get("dataInicio"), root.get("dataFinal"), pessoaJoin.get("nome"),
				pessoaJoin.get("telefone").get("numero"), root.get("status")));

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<ReservaDto> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(recursoFilter,Transporte.class));

	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(RecursoFilter recursoFilter, Class c) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Solicitacao> root = criteria.from(Solicitacao.class);
		Join<Solicitacao, Recurso> recursoJoin = root.join("recursos");
		
		List<Predicate> predicates = criarRestricoes(recursoFilter, builder, root, recursoJoin, c);

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private List<Predicate> criarRestricoes(RecursoFilter recursoFilter, CriteriaBuilder builder,
			Root<Solicitacao> root, Join<Solicitacao, Recurso> recursoJoin, Class c) {

		List<Predicate> predicates = new ArrayList<>();

		if (!ObjectUtils.isEmpty(recursoFilter.getDataInicio()) || !ObjectUtils.isEmpty(recursoFilter.getDataFinal())) {
			predicates.add(builder.greaterThan(root.get("dataFinal"), recursoFilter.getDataInicio()));
			predicates.add(builder.lessThan(root.get("dataInicio"), recursoFilter.getDataFinal()));
		}
		if (!ObjectUtils.isEmpty(recursoFilter.getStatus())) {
			predicates.add(builder.equal(root.get("status"), recursoFilter.getStatus()));
		}
		if (!ObjectUtils.isEmpty(recursoFilter.getIdRecurso())) {
			predicates.add(builder.equal(recursoJoin.get("id"), recursoFilter.getIdRecurso()));
		}
		
		if (c!=null) {
			predicates.add(builder.equal(recursoJoin.type(), c));			
		}

		return predicates;
	}

}
