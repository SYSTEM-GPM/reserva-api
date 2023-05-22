package reserva_api.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reserva_api.dtos.SetorDto;
import reserva_api.models.SetorModel;
import reserva_api.models.TransporteModel;
import reserva_api.repositories.SetorRepository;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;

	public Page<SetorModel> buscarTodos(Pageable pageable) {
		return setorRepository.findAll(pageable);
	}

	public SetorModel buscarPorId(Long id) {
		return setorRepository.findById(id).orElseThrow();
	}

	public SetorModel salvar(SetorModel setorModel) {
		return setorRepository.save(setorModel);
	}

	public void excluirPorId(Long id) {
		setorRepository.deleteById(id);
	}

	public SetorModel atualizar(Long id, SetorDto setorDto) {
		SetorModel setorModelSalvo = setorRepository.findById(id).orElseThrow();
		BeanUtils.copyProperties(setorDto, setorModelSalvo, "id");
		return setorRepository.save(setorModelSalvo);
	}

	public boolean existsByNome(String nome) { return setorRepository.existsByNome(nome); }
}
