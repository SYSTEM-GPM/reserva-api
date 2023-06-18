package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;

public class EnviaEmailsAutoCadastroDto {
    @NotBlank(message = "O campo E-mail é obrigatório")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
