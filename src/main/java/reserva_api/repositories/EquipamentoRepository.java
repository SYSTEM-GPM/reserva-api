package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>  {

}
