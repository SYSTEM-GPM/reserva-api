package reserva_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
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

	@Column(nullable = false)
	private LocalDateTime dataInicio;

	@Column(nullable = false)
	private LocalDateTime dataFinal;

	@Column(nullable = false, length = 255)
	private String finalidade;

	@Column(nullable = false)
	private LocalDateTime dataSolicitacao;

	@Column
	private LocalDateTime dataRetirada;

	@Column
	private LocalDateTime dataDevolucao;

	@Column(nullable = true, length = 255)
	private String externo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 255)
	private StatusSolicitacao autorizacao;

	@Column(nullable = true, length = 255)
	private String justificativa;

	@NotNull(message = "Solicitante é obrigatório")
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
					   StatusSolicitacao autorizacao, PessoaModel solicitante) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.justificativa = justificativa;
		this.dataSolicitacao = dataSolicitacao;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.autorizacao = autorizacao;
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

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
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

	public String getExterno() {
		return externo;
	}

	public void setExterno(String externo) {
		this.externo = externo;
	}

	public StatusSolicitacao getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(StatusSolicitacao autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
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
