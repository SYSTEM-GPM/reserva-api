package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ValidaCodigoAtivacaoDto {

    @NotBlank(message = "O campo Codigo de ativação é obrigatório")
    @Size(min = 0, max = 6)
    private String codigo;


    @NotBlank(message = "O campo E-mail é obrigatório")
    private String email;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
