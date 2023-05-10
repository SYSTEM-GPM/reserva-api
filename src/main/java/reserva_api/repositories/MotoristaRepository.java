package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.Motorista;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long>  {

}
