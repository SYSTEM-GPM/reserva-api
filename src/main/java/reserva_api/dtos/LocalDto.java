package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;

public class LocalDto {

    @NotBlank(message = "O campo Descrição é obrigatório")
    private String descricao;

    @NotBlank(message = "O campo Identificação é obrigatório")
    private String identificacao;

    private Integer totalDeAssento;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Integer getTotalDeAssento() {
        return totalDeAssento;
    }

    public void setTotalDeAssento(Integer totalDeAssento) {
        this.totalDeAssento = totalDeAssento;
    }
}
