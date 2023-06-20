package reserva_api.repositories.pessoa;

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
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import reserva_api.dtos.PessoaDto;
import reserva_api.models.PessoaModel;
import reserva_api.repositories.filters.PessoaFilter;

public class PessoaRepositoryQueryImpl implements PessoaRepositoryQuery {
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<PessoaDto> filtrarPessoa(PessoaFilter pessoaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaDto> criteria = builder.createQuery(PessoaDto.class);
		Root<PessoaModel> root = criteria.from(PessoaModel.class);
		
		List<Predicate> predicates = criarRestricoes(pessoaFilter, builder, root);	

		criteria.orderBy(builder.asc(root.get("nome")));
		criteria.distinct(true);

		criteria.select(builder.construct(PessoaDto.class, root.get("nome"), root.get("cpf"),
				root.get("siape"), root.get("dataNascimento"), root.get("tipoPerfil"),
				root.get("telefone").get("numero")));

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<PessoaDto> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(PessoaFilter  pessoaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<PessoaModel> root = criteria.from(PessoaModel.class);
		
		List<Predicate> predicates = criarRestricoes(pessoaFilter, builder, root);

		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private List<Predicate> criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder,
			Root<PessoaModel> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (!ObjectUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")),
					"%" + pessoaFilter.getNome().toLowerCase() + "%"));
		}
		
		if (!ObjectUtils.isEmpty(pessoaFilter.getCpf())) {
			predicates.add(builder.like(root.get("cpf"),
					"%" + pessoaFilter.getCpf() + "%"));
		}
		
		if (!ObjectUtils.isEmpty(pessoaFilter.getSiape())) {
			predicates.add(builder.like(root.get("siape"),
					"%" + pessoaFilter.getSiape() + "%"));
		}
		
		if (pessoaFilter.getDataNascimento() != null) {
			predicates.add(builder.equal(root.get("dataNascimento"), 
					pessoaFilter.getDataNascimento()));
		}
		
		if (!ObjectUtils.isEmpty(pessoaFilter.getTipoPerfil())) {
			predicates.add(builder.equal(root.get("tipoPerfil"), pessoaFilter.getTipoPerfil()));
		}

		return predicates;
	}
}