package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MotoristaDto {

    @NotBlank(message = "O campo Nome é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotBlank(message = "O campo CNH é obrigatório")
    private String numeroCnh;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroCnh() {
        return numeroCnh;
    }

    public void setNumeroCnh(String numeroCnh) {
        this.numeroCnh = numeroCnh;
    }
}
