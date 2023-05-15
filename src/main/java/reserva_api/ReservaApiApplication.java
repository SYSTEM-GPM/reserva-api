package reserva_api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reserva_api.models.*;
import reserva_api.models.SetorModel;
import reserva_api.models.enums.StatusSolicitacao;
import reserva_api.models.enums.TipoEquipamento;
import reserva_api.models.enums.TipoPerfil;
import reserva_api.models.enums.TipoTelefone;
import reserva_api.repositories.MotoristaRepository;
import reserva_api.repositories.PessoaRepository;
import reserva_api.repositories.RecursoRepository;
import reserva_api.repositories.SetorRepository;
import reserva_api.repositories.SolicitacaoRepository;
import reserva_api.repositories.TransporteRepository;
import reserva_api.repositories.ViagemRepository;

@SpringBootApplication
public class ReservaApiApplication implements CommandLineRunner {

	@Autowired
	SetorRepository setorRepository;

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	MotoristaRepository motoristaRepository;

	@Autowired
	RecursoRepository recursoRepository;

	@Autowired
	TransporteRepository transporteRepository;

	@Autowired
	SolicitacaoRepository solicitacaoRepository;

	@Autowired
	ViagemRepository viagemRepository;

	DateTimeFormatter ftr = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	DateTimeFormatter fdate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		SpringApplication.run(ReservaApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//TimeUnit.SECONDS.sleep(10);
		//cadastrarPessoas();
		//cadastrarRecursos();
		//cadastrarSolicitacao();
		//cadastrarViagem();
	}

	private void cadastrarViagem() {
		MotoristaModel m1 = motoristaRepository.findById(4L).orElseThrow();
		Solicitacao sol1 = solicitacaoRepository.findById(1L).orElseThrow();
		Transporte tr1 = transporteRepository.findById(1L).orElseThrow();
		PessoaModel p1 = pessoaRepository.findById(2L).orElseThrow();
		PessoaModel p2 = pessoaRepository.findById(4L).orElseThrow();

		Viagem vg1 = new Viagem(null, "Itacoatiara/Manaus/Itacoatiara", "B1547899", "/arquivos/xdrweweww2.pdf", 120879L,
				null, m1, sol1, tr1);
		vg1.getPassageiros().add(p1);
		vg1.getPassageiros().add(p2);

		viagemRepository.save(vg1);

	}

	private void cadastrarSolicitacao() {
		Solicitacao sl1 = new Solicitacao(null, LocalDateTime.parse("25-04-2023 10:00", ftr),
				LocalDateTime.parse("25-04-2023 12:00", ftr), " Viagem para tabalho de campo.", LocalDateTime.now(),
				null, null, StatusSolicitacao.SOLICITADO, new PessoaModel(1L));
		sl1.getRecursos().add(new Transporte(1L));
		sl1.getRecursos().add(new Equipamento(6L));
		sl1.getRecursos().add(new Local(3L));
		solicitacaoRepository.save(sl1);

	}

	private void cadastrarRecursos() {
		Recurso rc1 = new Transporte(null, "Pickup Nissan", "HTG-1262", 4);
		Recurso rc2 = new Transporte(null, "Van Mercedes-Benz", "IAD-2304", 16);

		Recurso rc3 = new Local(null, "Auditório geral", "A101", 200);
		Recurso rc4 = new Local(null, "Mini auditório", "A210", 30);
		Recurso rc5 = new Local(null, "Sala de reunião", "C101", 20);

		Recurso rc6 = new Equipamento(null, "Projeto de slide 01", "2001547", TipoEquipamento.PROJETOR);
		Recurso rc7 = new Equipamento(null, "Projeto de slide 02", "2001548", TipoEquipamento.PROJETOR);
		Recurso rc8 = new Equipamento(null, "Notebook Dell 01", "2001521", TipoEquipamento.NOTEBOOK);
		Recurso rc9 = new Equipamento(null, "Mesa de som Behringer", "2001530", TipoEquipamento.MESASOM);
		recursoRepository.saveAll(Arrays.asList(rc1, rc2, rc3, rc4, rc5, rc6, rc7, rc8, rc9));
	}

	private void cadastrarPessoas() {
		SetorModel st1 = new SetorModel(null, "Biblioteca");
		SetorModel st2 = new SetorModel(null, "Coordenação acadêmica");
		SetorModel st3 = new SetorModel(null, "Gerência de TI");
		SetorModel st4 = new SetorModel(null, "Direção");
		SetorModel st5 = new SetorModel(null, "Coordenação administrativa");
		setorRepository.saveAll(Arrays.asList(st1, st2, st3, st4, st5));

		PessoaModel ps1 = new PessoaModel(null, "Agatha Sueli Marcela Rezende", "424.554.048-62", "28.507.242-0",
				LocalDate.parse("04-03-1985", fdate), st1, TipoPerfil.NORMAL,new TelefoneModel(TipoTelefone.CELULAR, "(47) 99468-9837"));

		PessoaModel ps2 = new PessoaModel(null, "Emilly Bruna de Paula", "658.439.303-86", "14.151.755-4",
				LocalDate.parse("20-04-1977", fdate), st2, TipoPerfil.NORMAL,new TelefoneModel(TipoTelefone.CELULAR, "(31) 98984-6061"));

		PessoaModel ps3 = new PessoaModel(null, "Kevin Geraldo Benedito Ferreira", "607.641.416-27", "31.203.387-4",
				LocalDate.parse("24-10-1988", fdate), st3, TipoPerfil.NORMAL,new TelefoneModel(TipoTelefone.FIXO, "(62) 3896-9585"));
//		PessoaModel ps4 = new MotoristaModel(null, "Lorenzo Augusto Corte Real", "116.741.496-97", "46.063.022-2",
//				LocalDate.parse("15-06-1980", fdate), st3, TipoPerfil.NORMAL, new TelefoneModel(TipoTelefone.FIXO, "(51) 2895-1562"),
//				"60427048705");
		pessoaRepository.saveAll(Arrays.asList(ps1, ps2, ps3));
	}

}
