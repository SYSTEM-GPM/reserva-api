package reserva_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import reserva_api.dtos.projection.MotoristaProjection;
import reserva_api.dtos.projection.SolicitacaoLocalProjection;
import reserva_api.models.PessoaModel;
import reserva_api.models.Solicitacao;
import reserva_api.repositories.solicitacao.SolicitacaoRepositoryQuery;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, SolicitacaoRepositoryQuery {
	public Optional<List<Solicitacao>> findBySolicitante(PessoaModel solicitante);

	@Query(value = "SELECT s.id AS solicitacao_id, s.data_final, s.data_inicio, s.data_solicitacao, s.finalidade, s.externo, s.autorizacao, r.id AS local_id, r.descricao AS local, l.identificacao, l.total_de_assento, p.id AS solicitante_id, p.nome AS solicitante, p.cpf, p.siape, p.numero AS telefone FROM solicitacao s JOIN pessoa p ON s.solicitante_id = p.id JOIN solicitacao_recurso sr ON s.id = sr.solicitacao_id JOIN recurso r ON sr.recurso_id = r.id JOIN local l ON r.id = l.id ORDER BY s.data_solicitacao DESC", nativeQuery = true)
	Page<SolicitacaoLocalProjection> buscarTodosLocais(Pageable pageable);

	@Query(value = "SELECT s.id AS solicitacao_id, s.data_final, s.data_inicio, s.data_solicitacao, s.finalidade, s.externo, s.autorizacao, r.id AS local_id, r.descricao AS local, l.identificacao, l.total_de_assento, p.id AS solicitante_id, p.nome AS solicitante, p.cpf, p.siape, p.numero AS telefone FROM solicitacao s JOIN pessoa p ON s.solicitante_id = p.id JOIN solicitacao_recurso sr ON s.id = sr.solicitacao_id JOIN recurso r ON sr.recurso_id = r.id JOIN local l ON r.id = l.id WHERE p.id = ?1 ORDER BY s.data_solicitacao DESC", nativeQuery = true)
	Page<SolicitacaoLocalProjection> buscarTodosLocaisPorPessoa(Long id, Pageable pageable);
}