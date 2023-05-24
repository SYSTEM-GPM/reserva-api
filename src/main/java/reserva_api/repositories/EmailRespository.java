package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reserva_api.models.EmailModel;

public interface EmailRespository extends JpaRepository<EmailModel, Long> {

}
