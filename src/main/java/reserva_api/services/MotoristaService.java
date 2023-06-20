package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reserva_api.dtos.projection.MotoristaProjection;
import reserva_api.dtos.projection.SolicitacaoLocalProjection;
import reserva_api.models.MotoristaModel;
import reserva_api.repositories.MotoristaRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    public Page<MotoristaModel> buscarTodos(Pageable pageable) {
        return motoristaRepository.findAll(pageable);
    }

    public Page<MotoristaProjection> buscarTodosMotoristas(Pageable pageable) {

        //resgata todas as solicitacoes
        List<MotoristaProjection> motoristas = motoristaRepository.buscarTodosMotoristas();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<MotoristaProjection> pageMotorista;

        if (motoristas.size() < startItem) {
            pageMotorista = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, motoristas.size());
            pageMotorista = motoristas.subList(startItem, toIndex);
        }

        Page<MotoristaProjection> page = new PageImpl<>(pageMotorista, pageable, motoristas.size());

        if (page.isEmpty()) {
            // Não há viagens disponíveis
            // Você pode lançar uma exceção, retornar um ResponseEntity com status adequado ou retornar uma mensagem personalizada
            throw new NoSuchElementException("Sem registros");
        }

        return page;
    }

    public MotoristaModel salvar(MotoristaModel motorista) {
        return motoristaRepository.save(motorista);
    }

    public boolean existsByNumeroCnh(String numeroCnh) {
		return motoristaRepository.existsByNumeroCnh(numeroCnh);
	}

    public Optional<MotoristaModel> findById(Long id) {
        return motoristaRepository.findById(id);
    }

    public void excluirPorId(Long id) {
        motoristaRepository.deleteById(id);
    }

    public MotoristaProjection buscarPorId(Long id) {
        return motoristaRepository.buscaPorId(id).orElseThrow();

    }
}
