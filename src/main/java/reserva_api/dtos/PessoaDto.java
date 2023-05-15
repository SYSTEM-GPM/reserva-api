package reserva_api.dtos;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import reserva_api.models.enums.TipoPerfil;

public class PessoaDto {

	//Validações da tabela pessoa abaixo

	@NotBlank(message = "O campo Nome é obrigatório")
	@Size(max = 100)
	private String nome;

	@NotBlank(message = "O campo CPF é obrigatório")
	@Size(min = 14, max = 14) //14 com mascara e 11 sem mascara
	@CPF(message = "CPF inválido")
	private String cpf;

	@NotBlank(message = "O campo SIAPE é obrigatório")
	@Size(min = 7, max = 11)
	private String siape;

	//(message = "O campo Data de Nascimento é obrigatório")
	@Past(message = "Data de Nascimento inválida")
	private LocalDate dataNascimento;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "O campo Tipo de Perfil é obrigatório")
	private TipoPerfil tipoPerfil;

	@NotBlank(message = "O campo Telefone é obrigatório")
	@Size(min = 15, max = 15) //15 com mascara e 11 sem mascara
	private String telefone;

	@NotNull(message = "O campo Setor é obrigatório")
	private Long setor;

	//Validações da tabela usuario abaixo

	@NotBlank(message = "O campo E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	//@NotBlank(message = "O campo Senha é obrigatório")
	//@Size(min = 6)
	//private String senha;

	//Não é obrigatório, mas não permite enviar vazio
	//@Size(min = 11, max = 11) //11 caracteres sendo EX: AM000000000
	//@NotNull(message = "O campo CNH não pode ser enviado vazio!")
	private String numeroCnh;

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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getSetor() {
		return setor;
	}

	public void setSetor(Long setor) {
		this.setor = setor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

/*
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
 */

	public String getNumeroCnh() {
		return numeroCnh;
	}

	public void setNumeroCnh(String numeroCnh) {
		this.numeroCnh = numeroCnh;
	}
}