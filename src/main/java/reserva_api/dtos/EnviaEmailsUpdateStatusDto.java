package reserva_api.dtos;

import jakarta.validation.constraints.NotBlank;

public class EnviaEmailsUpdateStatusDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
