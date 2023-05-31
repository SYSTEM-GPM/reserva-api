package reserva_api.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import reserva_api.models.PassageirosModel;

import java.time.LocalDateTime;
import java.util.List;

public class SolicitacaoTransporteDto {

    private Long idPessoa; // Pegar nome e celular

    @NotNull(message = "O campo Motorista é obrigatório")
    private Long idMotorista; // Pegar nome

    @NotBlank(message = "O campo Descrição de Atividade é obrigatório")
    private String finalidade; // Descrição da Atividade

    //Passageiros
    private List<PassageirosDto> passageiros;

    @NotNull(message = "Data inicio é obrigatório")
    @FutureOrPresent(message = "Data da partida deve ser uma data futura")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data do retorno é obrigatório")
    @FutureOrPresent(message = "Data final deve ser uma data futura")
    private LocalDateTime dataFinal;

    @NotBlank(message = "O campo Saída é obrigatório")
    private String saida;

    @NotBlank(message = "O campo Destino é obrigatório")
    private String destino;

    @NotNull(message = "O campo Transporte é obrigatório")
    private Long idTransporte;

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Long getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(Long idTransporte) {
        this.idTransporte = idTransporte;
    }

    public List<PassageirosDto> getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(List<PassageirosDto> passageiros) {
        this.passageiros = passageiros;
    }
}
