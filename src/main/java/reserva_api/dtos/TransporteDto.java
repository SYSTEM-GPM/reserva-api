package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;

public class TransporteDto {

    @NotBlank(message = "O campo Descrição é obrigatório")
    private String descricao;

    @NotBlank(message = "O campo Placa é obrigatório")
    private String placa;

    private Integer totalDeAssentos;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getTotalDeAssentos() {
        return totalDeAssentos;
    }

    public void setTotalDeAssentos(Integer totalDeAssentos) {
        this.totalDeAssentos = totalDeAssentos;
    }
}
