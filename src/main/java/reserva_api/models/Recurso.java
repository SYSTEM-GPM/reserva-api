package reserva_api.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "recurso")
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE recurso SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class Recurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Descrição é obrigatória")
	private String descricao;

	@Column(columnDefinition = "tinyint(1) default 0")
	@Builder.Default
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean deleted = false;

	@JsonIgnore
	@ManyToMany
	private Set<Solicitacao> solicitacoes = new HashSet<>();

	public Recurso() {
		super();
	}

	public Recurso(Long id) {
		super();
		this.id = id;
	}

	public Recurso(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Set<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
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
		Recurso other = (Recurso) obj;
		return Objects.equals(id, other.id);
	}

}
