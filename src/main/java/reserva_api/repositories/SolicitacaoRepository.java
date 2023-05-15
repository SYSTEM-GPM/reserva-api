package reserva_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.PessoaModel;
import reserva_api.models.Solicitacao;
import reserva_api.repositories.solicitacao.SolicitacaoRepositoryQuery;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, SolicitacaoRepositoryQuery {
	public Optional<List<Solicitacao>> findBySolicitante(PessoaModel solicitante);
}