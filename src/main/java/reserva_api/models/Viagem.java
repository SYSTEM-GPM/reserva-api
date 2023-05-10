package reserva_api.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "viagem")
public class Viagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Destino é obrigatório")
	private String destino;
	private String numeroApoliceSeguro;
	private String uriApoliceSeguro;
	private Long quilometragemSaida;
	private Long quilometragemChegada;

	@JsonIgnoreProperties({ "setor", "dataNascimento", "cpf", "siape" })
	@ManyToOne
	@JoinColumn(name = "motorista_id")
	private Motorista motorista;

	@JsonIgnoreProperties("recursos")
	@OneToOne
	@JoinColumn(name = "solicitacao_id")
	private Solicitacao solicitacao;

	@JsonIgnoreProperties("viagens")
	@ManyToOne
	@JoinColumn(name = "transporte_id")
	private Transporte transporte;
	
	@JsonIgnoreProperties({"siape","setor","numeroCnh"})
	@ManyToMany
	@JoinTable(name = "passageiro", 
	joinColumns = @JoinColumn(name = "viagem_id"), 
	inverseJoinColumns = @JoinColumn(name = "pessoa_id"))
	private Set<PessoaModel> passageiros = new HashSet<>();

	public Viagem() {

	}

	public Viagem(Long id) {
		super();
		this.id = id;
	}

	public Viagem(Long id, String destino, String numeroApoliceSeguro, String uriApoliceSeguro, Long quilometragemSaida,
			Long quilometragemChegada, Motorista motorista, Solicitacao solicitacao, Transporte transporte) {
		super();
		this.id = id;
		this.destino = destino;
		this.numeroApoliceSeguro = numeroApoliceSeguro;
		this.uriApoliceSeguro = uriApoliceSeguro;
		this.quilometragemSaida = quilometragemSaida;
		this.quilometragemChegada = quilometragemChegada;
		this.motorista = motorista;
		this.solicitacao = solicitacao;
		this.transporte = transporte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getNumeroApoliceSeguro() {
		return numeroApoliceSeguro;
	}

	public void setNumeroApoliceSeguro(String numeroApoliceSeguro) {
		this.numeroApoliceSeguro = numeroApoliceSeguro;
	}

	public String getUriApoliceSeguro() {
		return uriApoliceSeguro;
	}

	public void setUriApoliceSeguro(String uriApoliceSeguro) {
		this.uriApoliceSeguro = uriApoliceSeguro;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Transporte getTransporte() {
		return transporte;
	}

	public void setTransporte(Transporte transporte) {
		this.transporte = transporte;
	}

	public Long getQuilometragemSaida() {
		return quilometragemSaida;
	}

	public void setQuilometragemSaida(Long quilometragemSaida) {
		this.quilometragemSaida = quilometragemSaida;
	}

	public Long getQuilometragemChegada() {
		return quilometragemChegada;
	}

	public void setQuilometragemChegada(Long quilometragemChegada) {
		this.quilometragemChegada = quilometragemChegada;
	}

	public Set<PessoaModel> getPassageiros() {
		return passageiros;
	}

	public void setPassageiros(Set<PessoaModel> passageiros) {
		this.passageiros = passageiros;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Viagem other = (Viagem) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", destino=" + destino + ", uriApoliceSeguro=" + uriApoliceSeguro
				+ ", quilometragemSaida=" + quilometragemSaida + ", quilometragemChegada=" + quilometragemChegada
				+ ", motorista=" + motorista + ", solicitacao=" + solicitacao + ", transporte=" + transporte
				+ ", passageiros=" + passageiros + "]";
	}

}
