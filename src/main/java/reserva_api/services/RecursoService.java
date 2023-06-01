package reserva_api.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reserva_api.dtos.LocalDto;
import reserva_api.dtos.TransporteDto;
import reserva_api.models.Equipamento;
import reserva_api.models.LocalModel;
import reserva_api.models.Recurso;
import reserva_api.models.TransporteModel;
import reserva_api.repositories.EquipamentoRepository;
import reserva_api.repositories.LocalRepository;
import reserva_api.repositories.RecursoRepository;
import reserva_api.repositories.TransporteRepository;

import java.util.Optional;

@Service
public class RecursoService {

	@Autowired
	private RecursoRepository recursoRepository;

	@Autowired
	private TransporteRepository transporteRepository;

	@Autowired
	private LocalRepository localRepository;

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	public Page<Recurso> buscarTodos(Pageable pageable) {
		return recursoRepository.findAll(pageable);
	}

	public Recurso buscarPorId(Long id) {
		return recursoRepository.findById(id).orElseThrow();
	}

	public void excluirPorId(Long id) {
		recursoRepository.deleteById(id);
	}

	public Page<TransporteModel> buscarTransportes(Pageable pageable) {
		return transporteRepository.findAll(pageable);
	}

	public Optional<TransporteModel> buscarTransportePorId(Long id) {
		return transporteRepository.findById(id);
	}

	public TransporteModel salvar(TransporteModel transporte) {
		return transporteRepository.save(transporte);
	}

	public TransporteModel atualizar(Long id, TransporteDto transporteDto) {
		TransporteModel transporteSalvo = transporteRepository.findById(id).orElseThrow();
		BeanUtils.copyProperties(transporteDto, transporteSalvo, "id");
		return transporteRepository.save(transporteSalvo);
	}

	public Page<LocalModel> buscarLocais(Pageable pageable) {
		return localRepository.findAll(pageable);
	}
	
	public LocalModel salvar(LocalModel local) {
		return localRepository.save(local);
	}

	public LocalModel atualizar(Long id, LocalDto localDto) {
		LocalModel localSalvo = localRepository.findById(id).orElseThrow();
		BeanUtils.copyProperties(localDto, localSalvo, "id");
		return localRepository.save(localSalvo);
	}

	public Page<Equipamento> buscarEquipamentos(Pageable pageable) {
		return equipamentoRepository.findAll(pageable);
	}
	
	public Equipamento salvar(Equipamento equipamento) {
		return equipamentoRepository.save(equipamento);
	}

	public Equipamento atualizar(Long id, Equipamento equipamento) {
		Equipamento equipamentoSalvo = equipamentoRepository.findById(id).orElseThrow();
		BeanUtils.copyProperties(equipamento, equipamentoSalvo, "id");
		return equipamentoRepository.save(equipamentoSalvo);
	}

	public boolean existsByDescricao(String descricao) {return recursoRepository.existsByDescricao(descricao);
	}
}
