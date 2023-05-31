package reserva_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;
import reserva_api.services.DetalheUsuarioService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguracao {

    @Autowired
    DetalheUsuarioService detalheUsuarioService;


    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(detalheUsuarioService);
        var authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.POST,
                        "/login", "/pessoas/envia-codigo/**", "/pessoas/valida-codigo","/pessoas/auto"
                ).permitAll()
                .requestMatchers(HttpMethod.PUT, "/pessoas/{id}/senha").permitAll()
                .requestMatchers(HttpMethod.GET,
                        "/swagger-ui/**", "/configuration/ui",  "/swagger-resources/**",
                        "/configuration/security", "/swagger-ui.html", "/v3/api-docs/swagger-config/**",
                        "/webjars/**", "/v3/api-docs"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/setores").permitAll()
                .anyRequest().authenticated().and()
                .authenticationManager(authenticationManager)
                .addFilterBefore(new JwtValidarFilter(authenticationManager, handlerExceptionResolver), JwtAutentificarFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
