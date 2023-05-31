package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reserva_api.dtos.projection.MotoristaProjection;
import reserva_api.models.MotoristaModel;
import reserva_api.repositories.MotoristaRepository;

import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    public Page<MotoristaModel> buscarTodos(Pageable pageable) {
        return motoristaRepository.findAll(pageable);
    }

    public Page<MotoristaProjection> buscarTodosMotoristas(Pageable pageable) {
        return motoristaRepository.buscarTodosMotoristas(pageable);
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

}
