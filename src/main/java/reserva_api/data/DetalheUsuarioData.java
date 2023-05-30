package reserva_api.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reserva_api.models.PessoaModel;
import reserva_api.models.enums.StatusConta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetalheUsuarioData implements UserDetails {


    private final Optional<PessoaModel> pessoa;

    public DetalheUsuarioData(Optional<PessoaModel> pessoa) {
        this.pessoa = pessoa;
    }

    public Optional<PessoaModel> getPessoa() {
        return this.pessoa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // TODO: colocar as permissões
    }

    @Override
    public String getPassword() {
        return pessoa.orElse(new PessoaModel()).getSenha();
    }

    @Override
    public String getUsername() {
        return pessoa.orElse(new PessoaModel()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.pessoa.orElse(new PessoaModel()).getStatus() == StatusConta.ATIVADA;
    }
}
