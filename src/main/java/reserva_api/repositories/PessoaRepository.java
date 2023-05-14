package reserva_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reserva_api.models.PessoaModel;
import reserva_api.repositories.pessoa.PessoaRepositoryQuery;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long>, PessoaRepositoryQuery  {

    boolean existsByCpf(String cpf);

    boolean existsBySiape(String siape);

    boolean existsByEmail(String email);

    //boolean existsByCnh(String cnh);

}
