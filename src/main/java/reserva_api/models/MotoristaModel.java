package reserva_api.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import reserva_api.models.enums.TipoPerfil;

@Entity
@Table(name = "motorista")
@SQLDelete(sql = "UPDATE motorista SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class MotoristaModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true, length = 255)
	private String numeroCnh;

	@Id
	private Long id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "motorista")
	private Set<Viagem> viagens = new HashSet<>();

	@Column(columnDefinition = "tinyint(1) default 0")
	@Builder.Default
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean deleted = false;

	public MotoristaModel() {
		super();
	}

	public MotoristaModel(Long id) {
		super();
		this.id = id;
	}

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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
