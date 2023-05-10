package reserva_api.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import reserva_api.models.enums.TipoPerfil;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//Gera os ids automaticamente de forma sequencial
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	//@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@Column(nullable = false, unique = true, length = 255)
	//@CPF(message = "CPF inválido")
	private String cpf;

	@Column(nullable = false, unique = true, length = 255)
	private String siape;

	@Column(nullable = false, length = 255)
	//@Past(message = "Data nascimento inválida")
	private LocalDate dataNascimento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 255)
	private TipoPerfil tipoPerfil;

	@Embedded
	private TelefoneModel telefone;

	@ManyToOne
	@JoinColumn(name = "setor_id")
	private SetorModel setor;

	@OneToOne(mappedBy = "pessoa")
	private UsuarioModel usuario;

	@JsonIgnore
	@OneToMany(mappedBy = "solicitante")
	private Set<Solicitacao> solicitacoes = new HashSet<>();

	public PessoaModel() {

	}

	public PessoaModel(Long id) {
		this.id = id;
	}

	public PessoaModel(Long id, String nome, String cpf, String siape, LocalDate dataNascimento, SetorModel setor,
					   TipoPerfil tipoPerfil, TelefoneModel telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.siape = siape;
		this.dataNascimento = dataNascimento;
		this.setor = setor;
		this.tipoPerfil = tipoPerfil;
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SetorModel getSetor() {
		return setor;
	}

	public void setSetor(SetorModel setor) {
		this.setor = setor;
	}

	public Set<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public TipoPerfil getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(TipoPerfil tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

	public void setSolicitacoes(Set<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public TelefoneModel getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneModel telefone) {
		this.telefone = telefone;
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
		PessoaModel other = (PessoaModel) obj;
		return Objects.equals(id, other.id);
	}

}