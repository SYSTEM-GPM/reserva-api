package reserva_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reserva_api.models.MotoristaModel;
import reserva_api.models.PessoaModel;
import reserva_api.repositories.MotoristaRepository;
import reserva_api.repositories.PessoaRepository;

@Service
public class MotoristaService {

    //@Autowired
    //private PessoaRepository pessoaRepository;

    @Autowired
    private MotoristaRepository motoristaRepository;

    public MotoristaModel salvar(MotoristaModel motorista) {
        return motoristaRepository.save(motorista);
    }

    /*

    public PessoaModel atualizar(Long id, MotoristaModel motorista) {
		MotoristaModel pessoaSalvo = motoristaRepository.getReferenceById(id);
		copyMotorista(motorista, pessoaSalvo);
		return pessoaRepository.save(pessoaSalvo);
	}

    private void copyMotorista(MotoristaModel pessoa, MotoristaModel pessoaSalvo) {
        if (pessoa.getNome() != null) {
            pessoaSalvo.setNome(pessoa.getNome());
        }
        if (pessoa.getCpf() != null) {
            pessoaSalvo.setCpf(pessoa.getCpf());
        }
        if (pessoa.getSetor() != null) {
            pessoaSalvo.setSetor(pessoa.getSetor());
        }
        if (pessoa.getSiape() != null) {
            pessoaSalvo.setSiape(pessoa.getSiape());

        }
        if (pessoa.getDataNascimento() != null) {
            pessoaSalvo.setDataNascimento(pessoa.getDataNascimento());
        }
        if (pessoa.getNumeroCnh() != null) {
            pessoaSalvo.setNumeroCnh(pessoa.getNumeroCnh());

        }

    }

    */

    public boolean existsByNumeroCnh(String numeroCnh) {
		return motoristaRepository.existsByNumeroCnh(numeroCnh);
	}

}
