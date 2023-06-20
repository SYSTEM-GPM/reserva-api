package reserva_api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import reserva_api.models.MotoristaModel;
import reserva_api.models.Solicitacao;
import reserva_api.models.Viagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long>  {
	public Optional<Viagem> findBySolicitacao(Solicitacao solicitacao);

    boolean existsByMotorista(MotoristaModel motorista);
}
