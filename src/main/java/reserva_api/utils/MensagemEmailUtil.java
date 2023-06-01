package reserva_api.utils;

import reserva_api.models.ComunicacaoInternaModel;
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

    public static String solicitacaoComunicacaoInterna(ComunicacaoInternaModel comunicacoInterna) {
        return
                "<h2>Ola, Admin!</h2><p>Você tem uma nova solicitação via Comunicação Interna no <strong>Sistema GPMM</strong>:</p><p>Nome do usuário: "+comunicacoInterna.getPessoa().getNome()+"</p><p>Mensagem:</p><h3>"+comunicacoInterna.getMensagem()+"</h3></br><p>Para responde-lo, clique no e-mail a seguir: <strong><a href='mailto:"+comunicacoInterna.getPessoa().getEmail()+"?subject=RE: "+comunicacoInterna.getAssunto()+"'>"+comunicacoInterna.getPessoa().getEmail()+"</a></strong></p><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>";
    }
}
