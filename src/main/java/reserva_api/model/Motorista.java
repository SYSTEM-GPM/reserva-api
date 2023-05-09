package reserva_api.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import reserva_api.model.enums.TipoPerfil;

@Entity
public class Motorista extends Pessoa {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Número da CNH é obrigatório")
	private String numeroCnh;
	
	@JsonIgnore
	@OneToMany(mappedBy = "motorista")
	private Set<Viagem> viagens = new HashSet<>();

	public Motorista() {

	}

	public Motorista(Long id) {
		super(id);
	}

	public Motorista(Long id, String nome, String cpf, String siape,
					 LocalDate dataNascimento, Setor setor, TipoPerfil tipoPerfil, Telefone telefone, String numeroCnh) {
		super(id, nome, cpf, siape, dataNascimento, setor, tipoPerfil,telefone);
		this.numeroCnh = numeroCnh;
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

}
