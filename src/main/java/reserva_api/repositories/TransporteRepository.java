package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.TransporteModel;

@Repository
public interface TransporteRepository extends JpaRepository<TransporteModel, Long>  {

}
