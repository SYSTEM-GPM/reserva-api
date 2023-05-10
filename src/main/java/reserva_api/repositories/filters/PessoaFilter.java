package reserva_api.repositories.filters;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import reserva_api.model.enums.TipoPerfil;

public class PessoaFilter {
	private String nome;
	private String cpf;
	private String siape;
	private LocalDate dataNascimento;
	@Enumerated(EnumType.STRING)
	private TipoPerfil tipoPerfil;
	
	public PessoaFilter(String nome, String cpf, String siape, LocalDate dataNascimento, TipoPerfil tipoPerfil) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.siape = siape;
		this.dataNascimento = dataNascimento;
		this.tipoPerfil = tipoPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public TipoPerfil getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(TipoPerfil tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}
	
}
