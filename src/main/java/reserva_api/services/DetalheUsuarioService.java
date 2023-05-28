package reserva_api.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reserva_api.data.DetalheUsuarioData;
import reserva_api.models.PessoaModel;
import reserva_api.repositories.PessoaRepository;

import java.util.Optional;

@Service
public class DetalheUsuarioService  implements UserDetailsService {

    private final PessoaRepository repository;

    public DetalheUsuarioService(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PessoaModel> pessoa = repository.findByEmail(username);

        if(pessoa.isEmpty()) {
            throw new UsernameNotFoundException("Usuário ["+username+"] não encontrado");
        }

        return new DetalheUsuarioData(pessoa);
    }
}
