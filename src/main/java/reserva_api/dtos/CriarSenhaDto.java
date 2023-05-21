package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CriarSenhaDto {

    @NotBlank(message = "O campo senha é obrigatório")
    @Size(min = 8, max = 50)
    private String senha;

    @NotBlank(message = "O campo Codigo de ativação é obrigatório")
    @Size(min = 0, max = 6)
    private String codigo;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
}
