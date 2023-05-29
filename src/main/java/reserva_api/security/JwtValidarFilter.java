package reserva_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

public class JwtValidarFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATRIBUTO = "Authorization";
    public static final String ATRIBUTO_PREFIXO = "Bearer ";

    private final HandlerExceptionResolver handlerExceptionResolver;


    public JwtValidarFilter(AuthenticationManager authenticationManager, HandlerExceptionResolver handlerExceptionResolver) {
        super(authenticationManager);
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String atributo = request.getHeader(HEADER_ATRIBUTO);

        if(atributo == null) {
            chain.doFilter(request, response);
            return;
        }

        if(!atributo.startsWith(ATRIBUTO_PREFIXO)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = atributo.replace(ATRIBUTO_PREFIXO, "");
            UsernamePasswordAuthenticationToken authenticationToken = getAuthorizationToken(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthorizationToken(String token) {
        String usuario = JWT.require(Algorithm.HMAC512(JwtAutentificarFilter.TOKEN_SENHA))
                .build()
                .verify(token)
                .getSubject();

        if (usuario == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(
                usuario,
                null,
                new ArrayList<>() // TODO: adicionar permissões
        );
    }
}
