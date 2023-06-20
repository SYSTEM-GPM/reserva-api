package reserva_api.utils;

import org.springframework.beans.factory.annotation.Value;
import reserva_api.models.ComunicacaoInternaModel;
import reserva_api.models.PessoaModel;

import java.text.MessageFormat;

public class  MensagemEmailUtil {
    public static String ativacaoUsuario(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Seja bem-vindo (a), {0}!</h2><p>Sua conta foi registrada com sucesso nos <strong>Sistema GPMM</strong> pelo adminstrador!</p><p>Para ter acesso ao sistema, por favor acesse ao <a href=''{1}/primeiro-acesso/?email={2}''>site</a> e infome o código abaixo:</p></br><h3><strong>{3}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), Constantes.urlFront, pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    public static String envioCodigoUsuario(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>O código para ativação da sua conta foi gerado.</p><p>Para ter acesso ao <strong>Sistema GPMM</strong>, por favor acesse ao <a href=''{1}/primeiro-acesso/?email={2}''>site</a> e infome o código abaixo:</p></br><h3><strong>{3}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), Constantes.urlFront, pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    public static String usuarioAutoCadastrado(PessoaModel solicitante) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>Uma solicitação para liberação de acesso ao <strong>Sistema GPMM</strong> foi enviada ao administrador do sistema.</p><p>Em caso de atualizações, entraremos em contato.</p><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        solicitante.getNome());
    }

    public static String pedidoAutoCadastro(PessoaModel solicitante) {
        return
                MessageFormat.format("<h2>Ola, Admin!</h2><p>Uma nova solicitação de cadastro para o <strong>Sistema GPMM</strong> foi enviada pelo seginte usuário:</p><p><strong>Nome: </strong>{0}<br><strong>Siape: </strong>{1}<br><strong>E-mail: </strong>{2}</p><p>Para efetivar a ativação do mesmo, por favor, entre no sistema.</p><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        solicitante.getNome(), solicitante.getSiape(), solicitante.getEmail());
    }

    public static String recuperacaoConta(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>Um novo código para recuperação da sua conta foi gerado.</p><p>Para ter acesso ao <strong>Sistema GPMM</strong>, por favor acesse ao <a href=''{1}/definir-rota/?email={2}''>site</a> e infome o código abaixo:</p></br><h3><strong>{3}</strong></h3><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), Constantes.urlFront, pessoa.getEmail(), pessoa.getCodigoAtivacao());
    }

    public static String envioUpdateStatus(PessoaModel pessoa) {
        return
                MessageFormat.format("<h2>Ola, {0}!</h2><p>O status da sua conta no <strong>Sistema GPMM</strong> foi mudado para <strong>{1}</strong> pelo administrador do sistema.</p><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>",
                        pessoa.getNome(), pessoa.getStatus());
    }

    public static String solicitacaoComunicacaoInterna(ComunicacaoInternaModel comunicacoInterna) {
        return
                "<h2>Ola, Admin!</h2><p>Você tem uma nova solicitação via Comunicação Interna no <strong>Sistema GPMM</strong>:</p><p>Nome do usuário: "+comunicacoInterna.getPessoa().getNome()+"</p><p>Mensagem:</p><h3>"+comunicacoInterna.getMensagem()+"</h3></br><p>Para responde-lo, clique no e-mail a seguir: <strong><a href='mailto:"+comunicacoInterna.getPessoa().getEmail()+"?subject=RE: "+comunicacoInterna.getAssunto()+"'>"+comunicacoInterna.getPessoa().getEmail()+"</a></strong></p><p>Atenciosamente,</p><p><strong>Equipe do Sistema GPMM</strong></p>";
    }
}
