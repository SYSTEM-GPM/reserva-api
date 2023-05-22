package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long>  {

    boolean existsByDescricao(String descricao);
}
