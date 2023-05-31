package reserva_api.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import reserva_api.models.enums.TipoPerfil;

import java.time.LocalDate;

public class CadastroUsuarioDto {
    @NotBlank(message = "O campo Nome é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotBlank(message = "O campo CPF é obrigatório")
    @Size(min = 11, max = 14)
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O campo SIAPE é obrigatório")
    @Size(min = 7, max = 11)
    private String siape;

    @Past(message = "Data de Nascimento inválida")
    private LocalDate dataNascimento;

    @NotBlank(message = "O campo Telefone é obrigatório")
    @Size(min = 11, max = 15)
    private String telefone;

    @NotNull(message = "O campo Setor é obrigatório")
    private Long setor;

    @NotBlank(message = "O campo E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "O campo Senha é obrigatório")
    @Size(min = 8, max = 50)
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getSetor() {
        return setor;
    }

    public void setSetor(Long setor) {
        this.setor = setor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
