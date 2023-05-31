package reserva_api.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import reserva_api.models.enums.StatusConta;

public class AtualizarStatusDto {
    @Enumerated(EnumType.STRING)
    private StatusConta status;

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }
}
