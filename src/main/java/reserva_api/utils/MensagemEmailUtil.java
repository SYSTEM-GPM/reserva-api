package reserva_api.utils;

import reserva_api.model.Pessoa;

public class  MensagemEmailUtil {
    public static String ativacaoUsuario(String nome, String link) {
        return
            "<h3>Ola "+nome+"!</h3>" +
            "<p>Sua conta foi registrada com sucesso nos <strong>Sistema GPMM</strong> pelo adminstrador!</p>" +
            "<p>Para ter acasso ao sistema, por favor clique no <a href='"+link+";'>link</a> e crie sua senha.</p></br>" +
            "<p>Atenciosamente,</p>" +
            "<p><strong>Equipe do Sistema GPMM</strong></p>";
    }

    // envio para o ADMIN confirmar a conta
    public static String confirmarAdmin(Pessoa pessoa) {
        return
            "<h3>Ola Admin!</h3>" +
            "<p>Uma nova conta de usuário foi registrada no <strong>Sistema GPMM</strong></p>" +
            "<p>Dados:</p>" +
            "<p>Nome: "+pessoa.getNome()+" </p>" +
            "<p>Email: "+pessoa.getNome()+" </p>" +
            "<p>Siape: "+pessoa.getSiape()+" </p>" +
            "<p>Para efetuar a confirmação e ativação da conta, por favor, entre no sistema.</p></br>" +
            "<p>Atenciosamente,</p>" +
            "<p><strong>Equipe do Sistema GPMM</strong></p>";
    }

    // fazer um para solicitação de transporte (envio para o ADMIN)

}
