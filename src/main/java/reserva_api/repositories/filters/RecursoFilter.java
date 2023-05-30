package reserva_api.repositories.filters;

import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import reserva_api.models.enums.StatusSolicitacao;

public class RecursoFilter {
	private Long idRecurso;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFinal;
	private String externo;
	@Enumerated(EnumType.STRING)
	private StatusSolicitacao autorizacao;

	public RecursoFilter() {
		super();
	}

	public RecursoFilter(Long idRecurso, LocalDateTime dataInicio, LocalDateTime dataFinal, StatusSolicitacao autorizacao) {
		super();
		this.idRecurso = idRecurso;
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.autorizacao = autorizacao;
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
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

}
