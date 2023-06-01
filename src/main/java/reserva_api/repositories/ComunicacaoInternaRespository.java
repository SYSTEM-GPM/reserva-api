package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reserva_api.models.ComunicacaoInternaModel;

public interface ComunicacaoInternaRespository extends JpaRepository<ComunicacaoInternaModel, Long> {

}
