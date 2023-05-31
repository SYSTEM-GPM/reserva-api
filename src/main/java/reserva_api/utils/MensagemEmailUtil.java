package reserva_api.utils;

import reserva_api.models.PessoaModel;

import java.text.MessageFormat;

public class  MensagemEmailUtil {
    // enviado pelo admin ao usuario, apois o cadastro
    public static String ativacaoUsuario(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Seja bem-vindo (a), {0}!</h2><p>Sua conta foi registrada com sucesso nos <strong>Sistema GPMM</strong> pelo adminstrador!</p><p>Para ter acesso ao sistema, por favor acesse ao <a href=''http://localhost:3000/primeiro-acesso/?email={1}''>site</a> e infome o código abaixo:</p></br><h3><strong>{2}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    public static String envioCodigoUsuario(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>O código para ativação da sua conta foi gerado.</p><p>Para ter acesso ao <strong>Sistema GPMM</strong>, por favor acesse ao <a href=''http://localhost:3000/primeiro-acesso/?email={1}''>site</a> e infome o código abaixo:</p></br><h3><strong>{2}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    public static String recuperacaoConta(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>Um novo código para recuperação da sua conta foi gerado.</p><p>Para ter acesso ao <strong>Sistema GPMM</strong>, por favor acesse ao <a href=''http://localhost:3000/definir-rota/?email={1}''>site</a> e infome o código abaixo:</p></br><h3><strong>{2}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    // fazer um para solicitação de transporte (envio para o ADMIN)

}
