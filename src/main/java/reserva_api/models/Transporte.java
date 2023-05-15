package reserva_api.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Transporte extends Recurso {
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "Placa é obrigatório")
	private String placa;
	private Integer totalDeAssentos;

	@JsonIgnore
	@OneToMany(mappedBy = "transporte")
	private Set<Viagem> viagens = new HashSet<>();

	public Transporte() {
	}

	public Transporte(Long id) {
		super(id);
	}

	public Transporte(Long id, String descricao, String placa, Integer totalDeAssentos) {
		super(id, descricao);
		this.placa = placa;
		this.totalDeAssentos = totalDeAssentos;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getTotalDeAssentos() {
		return totalDeAssentos;
	}

	public void setTotalDeAssentos(Integer totalDeAssentos) {
		this.totalDeAssentos = totalDeAssentos;
	}

	public Set<Viagem> getViagens() {
		return viagens;
	}

}
