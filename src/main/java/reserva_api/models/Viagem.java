package reserva_api.models;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "viagem")
public class Viagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String destino;

	@Column(nullable = false, length = 255)
	private String saida;

	@JsonIgnoreProperties({ "setor", "dataNascimento", "cpf", "siape" })
	@ManyToOne
	@JoinColumn(name = "motorista_id")
	private MotoristaModel motorista;

	@JsonIgnoreProperties("recursos")
	@OneToOne
	@JoinColumn(name = "solicitacao_id")
	private Solicitacao solicitacao;

	@JsonIgnoreProperties("viagens")
	@ManyToOne
	@JoinColumn(name = "transporte_id")
	private TransporteModel transporte;


	//Mudar aqui
	@JsonIgnoreProperties("viagem")
	@OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL)
	private Set<PassageirosModel> passageiros = new HashSet<>();

	public Viagem() {

	}

	public Viagem(Long id) {
		super();
		this.id = id;
	}

	public Viagem(Long id, String saida, String destino, MotoristaModel motorista, Solicitacao solicitacao, TransporteModel transporte) {
		super();
		this.id = id;
		this.saida = saida;
		this.destino = destino;
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

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public MotoristaModel getMotorista() {
		return motorista;
	}

	public void setMotorista(MotoristaModel motoristaModel) {
		this.motorista = motoristaModel;
	}

	public TransporteModel getTransporte() {
		return transporte;
	}

	public void setTransporte(TransporteModel transporte) {
		this.transporte = transporte;
	}

	public Set<PassageirosModel> getPassageiros() {
		return passageiros;
	}

	public void setPassageiros(Set<PassageirosModel> passageiros) {
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
		return "Viagem [id=" + id + ", saida=" + saida + ", destino=" + destino
				+ ", motorista=" + motorista + ", solicitacao=" + solicitacao + ", transporte=" + transporte
				+ ", passageiros=" + passageiros + "]";
	}

}
