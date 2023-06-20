package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reserva_api.models.PassageirosModel;

public interface PassageirosRepository extends JpaRepository<PassageirosModel, Long> {
}
