package reserva_api.utils;

import reserva_api.models.PessoaModel;

import java.text.MessageFormat;

public class  MensagemEmailUtil {
    public static String ativacaoUsuario(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>Sua conta foi registrada com sucesso nos <strong>Sistema GPMM</strong> pelo adminstrador!</p><p>Para ter acesso ao sistema, por favor acesse ao <a href=''http://localhost:3000/difinir-rota/?email={1}''>site</a> e infome o código abaixo:</p></br><h3><strong>{2}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    // envio para o ADMIN confirmar a conta
    public static String confirmarAdmin(PessoaModel pessoaModel) {
        return
            "<h3>Ola Admin!</h3>" +
            "<p>Uma nova conta de usuário foi registrada no <strong>Sistema GPMM</strong></p>" +
            "<p>Dados:</p>" +
            "<p>Nome: "+ pessoaModel.getNome()+" </p>" +
            "<p>Email: "+ pessoaModel.getNome()+" </p>" +
            "<p>Siape: "+ pessoaModel.getSiape()+" </p>" +
            "<p>Para efetuar a confirmação e ativação da conta, por favor, entre no sistema.</p></br>" +
            "<p>Atenciosamente,</p>" +
            "<p><strong>Equipe do Sistema GPMM</strong></p>";
    }

    // fazer um para solicitação de transporte (envio para o ADMIN)

}
