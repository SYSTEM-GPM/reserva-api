package reserva_api.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import reserva_api.models.enums.TipoTelefone;

@Embeddable
public class TelefoneModel {

	@Enumerated(EnumType.STRING)
	private TipoTelefone tipo;

	private String numero;
	public TelefoneModel() {
	
	}
	
	public TelefoneModel(TipoTelefone tipo, String numero) {
		super();
		this.tipo = tipo;
		this.numero = numero;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	
	

}
