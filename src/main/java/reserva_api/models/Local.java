package reserva_api.models;

import jakarta.persistence.Entity;

@Entity
public class Local extends Recurso {
	private static final long serialVersionUID = 1L;

	private String identificacao;
	private Integer totalDeAssento;

	public Local() {

	}

	public Local(Long id) {
		super(id);
	}

	public Local(Long id, String descricao, String identificacao, Integer totalDeAssento) {
		super(id, descricao);
		this.identificacao = identificacao;
		this.totalDeAssento = totalDeAssento;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Integer getTotalDeAssento() {
		return totalDeAssento;
	}

	public void setTotalDeAssento(Integer totalDeAssento) {
		this.totalDeAssento = totalDeAssento;
	}

}
