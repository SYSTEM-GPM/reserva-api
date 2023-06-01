package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComunicacaoInternaDto {
    @NotBlank(message = "O campo Pessoa Id é obrigatório")
    private Long pessoaId;

    @NotBlank(message = "O campo Assunto é obrigatório")
    private String assunto;

    @NotBlank(message = "O campo Mensagem é obrigatório")
    private String mensagem;


    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
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
}