package reserva_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import reserva_api.models.enums.TipoEquipamento;

@Entity
public class Equipamento extends Recurso {
	private static final long serialVersionUID = 1L;

	private String numeroPatrimonio;

	@Enumerated(EnumType.STRING)
	private TipoEquipamento tipo;

	public Equipamento() {

	}

	public Equipamento(Long id) {
		super(id);
	}

	public Equipamento(Long id, String descricao, String numeroPatrimonio, TipoEquipamento tipo) {
		super(id, descricao);
		this.numeroPatrimonio = numeroPatrimonio;
		this.tipo = tipo;
	}

	public String getNumeroPatrimonio() {
		return numeroPatrimonio;
	}

	public void setNumeroPatrimonio(String numeroPatrimonio) {
		this.numeroPatrimonio = numeroPatrimonio;
	}

	public TipoEquipamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}

}
