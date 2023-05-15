package reserva_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import reserva_api.models.enums.StatusSolicitacao;

@Entity
@Table(name = "solicitacao")
public class Solicitacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Data inicio é obrigatório")
	@FutureOrPresent(message = "Data início deve ser uma data futura") 
	private LocalDateTime dataInicio;
	@NotNull(message = "Data final é obrigatório")
	@FutureOrPresent(message = "Data final deve ser uma data futura")
	private LocalDateTime dataFinal;
	private String justificativa;
	private LocalDateTime dataSolicitacao;
	private LocalDateTime dataRetirada;
	private LocalDateTime dataDevolucao;

	@Enumerated(EnumType.STRING)
	private StatusSolicitacao status;
	
	@NotNull(message = "Solicitante inicio é obrigatório")
	@JsonIgnoreProperties({"setor","cpf","dataNascimento"})
	@ManyToOne
	@JoinColumn(name = "solicitante_id")
	private PessoaModel solicitante;
	
	@NotNull(message = "Recurso é obrigatório")
	@ManyToMany
	@JoinTable(name = "solicitacao_recurso", 
	joinColumns = @JoinColumn(name = "solicitacao_id"), 
	inverseJoinColumns = @JoinColumn(name = "recurso_id"))
	private Set<Recurso> recursos = new HashSet<>();

	public Solicitacao() {
		super();
	}

	public Solicitacao(Long id) {
		super();
		this.id = id;
	}

	public Solicitacao(Long id, LocalDateTime dataInicio, LocalDateTime dataFinal, String justificativa,
			LocalDateTime dataSolicitacao, LocalDateTime dataRetirada, LocalDateTime dataDevolucao,
			StatusSolicitacao status, PessoaModel solicitante) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.justificativa = justificativa;
		this.dataSolicitacao = dataSolicitacao;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.status = status;
		this.solicitante = solicitante;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public LocalDateTime getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public LocalDateTime getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDateTime dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public StatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacao status) {
		this.status = status;
	}

	public PessoaModel getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(PessoaModel solicitante) {
		this.solicitante = solicitante;
	}

	public Set<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
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
		Solicitacao other = (Solicitacao) obj;
		return Objects.equals(id, other.id);
	}

}
