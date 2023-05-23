package reserva_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "local")
public class LocalModel extends Recurso {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 255)
	private String identificacao;
	@Column
	private Integer totalDeAssento;

	public LocalModel() {

	}

	public LocalModel(Long id) {
		super(id);
	}

	public LocalModel(Long id, String descricao, String identificacao, Integer totalDeAssento) {
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
