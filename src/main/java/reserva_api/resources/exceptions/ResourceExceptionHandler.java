package reserva_api.resources.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import reserva_api.services.exceptions.AllPropertiesIsRequiredException;
import reserva_api.services.exceptions.NonFreeResourceException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StandardError> noSuchElementException(NoSuchElementException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList(messageSource.getMessage("recurso.indisponivel", null, LocaleContextHolder.getLocale()));
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandardError> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList(messageSource.getMessage("integrade.referencial", null, LocaleContextHolder.getLocale()));
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<StandardError> emptyResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList(messageSource.getMessage("recurso.indisponivel", null, LocaleContextHolder.getLocale()));
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(NonFreeResourceException.class)
	public ResponseEntity<StandardError> nonFreeResourceException(NonFreeResourceException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList(messageSource.getMessage("reserva.indisponivel", null, LocaleContextHolder.getLocale()));
		HttpStatus status = HttpStatus.LOCKED;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AllPropertiesIsRequiredException.class)
	public ResponseEntity<StandardError> allPropertiesIsRequiredException(AllPropertiesIsRequiredException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList(messageSource.getMessage("propriedade.obrigatoria", null, LocaleContextHolder.getLocale()));
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(JWTVerificationException.class)
	public ResponseEntity<StandardError> jWTVerificationException(JWTVerificationException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList("Token inválido");
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<StandardError> authenticationException(AuthenticationException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList(e.getMessage());
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<StandardError> runtimeException(RuntimeException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList("ERRO DO SERVIDOR");
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest resquest) {
		List<String> errors = Arrays
				.asList("Não foi possível excluir");
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				resquest.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
