package reserva_api.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import reserva_api.models.enums.TipoPerfil;

@Entity
@Table(name = "motorista")
public class MotoristaModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true, length = 255)
	private String numeroCnh;

	@Id
	private Long id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "motorista")
	private Set<Viagem> viagens = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCnh() {
		return numeroCnh;
	}

	public void setNumeroCnh(String numeroCnh) {
		this.numeroCnh = numeroCnh;
	}

	public Set<Viagem> getViagens() {
		return viagens;
	}

	public void setViagens(Set<Viagem> viagens) {
		this.viagens = viagens;
	}
}
