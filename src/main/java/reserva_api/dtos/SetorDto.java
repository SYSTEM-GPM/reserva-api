package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;

public class SetorDto {

    @NotBlank(message = "O campo Nome é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
