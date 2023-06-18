package reserva_api.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import reserva_api.models.ComunicacaoInternaModel;
import reserva_api.models.PessoaModel;

import java.util.List;
import java.util.Optional;

public interface ComunicacaoInternaRespository extends JpaRepository<ComunicacaoInternaModel, Long> {

    List<ComunicacaoInternaModel> findByPessoa(PessoaModel pessoaModel);
}
