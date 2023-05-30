package reserva_api.models;

import jakarta.persistence.*;
import lombok.Data;
import reserva_api.models.enums.StatusEmail;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="email")
public class EmailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assunto;
    @Column(columnDefinition = "TEXT")
    protected String mensagem;
    private LocalDateTime dataEmail;
    private StatusEmail statusEmail;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaModel pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataEmail() {
        return dataEmail;
    }

    public void setDataEmail(LocalDateTime dataEmail) {
        this.dataEmail = dataEmail;
    }

    public StatusEmail getStatusEmail() {
        return statusEmail;
    }

    public void setStatusEmail(StatusEmail statusEmail) {
        this.statusEmail = statusEmail;
    }

    public PessoaModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }
}
