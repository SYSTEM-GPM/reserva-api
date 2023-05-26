package reserva_api.dtos;

import java.time.LocalDateTime;

import reserva_api.models.enums.StatusSolicitacao;

public class ReservaDto {
	private Long idSolicitacao;
	private Long idRecurso;
	private String recurso;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFinal;
	private String solicitante;
	private String telefone;
	private String externo;
	private String status;

	public ReservaDto() {
	}

	public ReservaDto(Long idSolicitacao, Long idRecurso, String descricaoRecurso, LocalDateTime dataInicio,
					  LocalDateTime dataFinal, String solicitante, String telefone, String externo, StatusSolicitacao status) {
		super();
		this.idSolicitacao = idSolicitacao;
		this.idRecurso = idRecurso;
		this.recurso = descricaoRecurso;
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.solicitante = solicitante;
		this.telefone = telefone;
		this.externo = externo;
		this.status = status.toString();
	}

	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public void setDescricaoRecurso(String recurso) {
		this.recurso = recurso;
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

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getExterno() {
		return externo;
	}

	public void setExterno(String externo) {
		this.externo = externo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
