package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long>  {

}
