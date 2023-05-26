package reserva_api.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SolicitacaoLocalDto {

    //Interno
    private Long idPessoa; // Pegar nome, celular e setor

    @NotNull(message = "Data inicio é obrigatório")
    @FutureOrPresent(message = "Data início deve ser uma data futura")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data final é obrigatório")
    @FutureOrPresent(message = "Data final deve ser uma data futura")
    private LocalDateTime dataFinal;

    @NotBlank(message = "O campo Descrição de Atividade é obrigatório")
    private String justificativa; // Descrição da Atividade

    @NotNull(message = "O campo Local é obrigatório")
    private Long idLocal; // Cadastra o id do Local mas da para mudar para pegar nome ou os 2

    private String externo; // Nome ou Descrição da Entidade Externa

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    public String getExterno() {
        return externo;
    }

    public void setExterno(String externo) {
        this.externo = externo;
    }
}
