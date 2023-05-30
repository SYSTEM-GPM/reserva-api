package reserva_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import reserva_api.data.DetalheUsuarioData;
import reserva_api.models.PessoaModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAutentificarFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRACAO = 86400000; // 1 dia
    public static final String TOKEN_SENHA = "a0de9067-a9af-486e-a05f-6bca4032888d";

    private final AuthenticationManager authenticationManager;

    public JwtAutentificarFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            PessoaModel pessoa = new ObjectMapper().readValue(request.getInputStream(), PessoaModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    pessoa.getEmail(),
                    pessoa.getSenha(),
                    new ArrayList<>() // TODO: adicionar permissões
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autentificar usuário", e);
        }
    }

}
