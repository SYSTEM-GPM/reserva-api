package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.MotoristaModel;

@Repository
public interface MotoristaRepository extends JpaRepository<MotoristaModel, Long>  {

    boolean existsByNumeroCnh(String numeroCnh);

}
