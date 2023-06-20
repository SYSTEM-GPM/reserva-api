package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.SetorModel;

@Repository
public interface SetorRepository extends JpaRepository<SetorModel, Long>  {

    boolean existsByNome(String nome);
}
