package reserva_api.utils;

import reserva_api.models.PessoaModel;

public class LoginResposta {
    private String token;
    private PessoaModel usuario;

    public LoginResposta(PessoaModel pessoa, String token) {
        this.usuario = pessoa;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PessoaModel getUsuario() {
        return usuario;
    }

    public void setUsuario(PessoaModel usuario) {
        this.usuario = usuario;
    }
}
