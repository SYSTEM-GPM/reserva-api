package reserva_api.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import reserva_api.models.enums.StatusSolicitacao;

public class SolicitacaoTransporteAtualizarDto {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Campo obrigatório")
    private StatusSolicitacao autorizacao;

    private String justificativa;

    public StatusSolicitacao getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(StatusSolicitacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}
