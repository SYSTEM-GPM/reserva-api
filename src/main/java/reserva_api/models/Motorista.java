package reserva_api.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import reserva_api.models.enums.TipoPerfil;

@Entity
public class Motorista extends PessoaModel {
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
					 LocalDate dataNascimento, SetorModel setorModel, TipoPerfil tipoPerfil, TelefoneModel telefoneModel, String numeroCnh) {
		super(id, nome, cpf, siape, dataNascimento, setorModel, tipoPerfil, telefoneModel);
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
