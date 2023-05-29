package reserva_api.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reserva_api.data.DetalheUsuarioData;
import reserva_api.dtos.LoginDto;
import reserva_api.models.PessoaModel;
import reserva_api.models.enums.StatusConta;
import reserva_api.security.JwtAutentificarFilter;
import reserva_api.services.PessoaService;
import reserva_api.utils.ApiError;
import reserva_api.utils.ApiSuccess;
import reserva_api.utils.LoginResposta;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class LoginResource {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getSenha()
        );

        var authenticate =  this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var usuario = (DetalheUsuarioData) authenticate.getPrincipal();

        String token =  JWT.create()
                .withSubject(usuario.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtAutentificarFilter.TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(JwtAutentificarFilter.TOKEN_SENHA));

        return ResponseEntity.ok()
                .body(new LoginResposta(usuario.getPessoa().orElse(new PessoaModel()), token));
    }


}
