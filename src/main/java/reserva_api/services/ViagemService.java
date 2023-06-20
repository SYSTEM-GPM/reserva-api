package reserva_api.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reserva_api.models.*;
import reserva_api.repositories.*;

import java.util.*;

@Service
public class ViagemService {

	@Autowired
	private ViagemRepository viagemRepository;
	@Autowired
	private MotoristaRepository motoristaRepository;
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	@Autowired
	private TransporteRepository transporteRepository;
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PassageirosRepository passageirosRepository;

	public Page<Viagem> buscarTodas(Pageable pageable) {
		return viagemRepository.findAll(pageable);
	}

	public Viagem buscarPorId(Long id) {
		return viagemRepository.findById(id).orElseThrow();
	}

	public Viagem buscarPorSolicitacao(Long id) {
		Solicitacao solicitacao = solicitacaoRepository.findById(id).orElseThrow();
		return viagemRepository.findBySolicitacao(solicitacao).orElseThrow();
	}

	public Page<Viagem> buscarPorPessoas(Long id, Pageable pageable) {
		//Verifica se pessoa existe
		PessoaModel pessoa = pessoaRepository.findById(id).orElseThrow();
		//resgata todas as solicitacoes da pessoa
		List<Solicitacao> solicitacoes = solicitacaoRepository.findBySolicitante(pessoa).orElseThrow();

		List<Viagem> solicitacoesViagem = new ArrayList<>();

		// Buscar as solicitações contidas na tabela de viagem
		for (Solicitacao solicitacao : solicitacoes) {
			Optional<Viagem> solicitacaoViagem = viagemRepository.findBySolicitacao(solicitacao);
			solicitacaoViagem.ifPresent(solicitacoesViagem::add);
		}

		Collections.sort(solicitacoesViagem, new Comparator<Viagem>() {
			@Override
			public int compare(Viagem v1, Viagem v2) {
				return v2.getSolicitacao().getDataSolicitacao().compareTo(v1.getSolicitacao().getDataSolicitacao());
			}
		});

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Viagem> pageViagens;

		if (solicitacoesViagem.size() < startItem) {
			pageViagens = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, solicitacoesViagem.size());
			pageViagens = solicitacoesViagem.subList(startItem, toIndex);
		}

		Page<Viagem> page = new PageImpl<>(pageViagens, pageable, solicitacoesViagem.size());


		return page;
	}

	public Viagem salvar(Viagem viagem) {
		validarViagem(viagem);
		return viagemRepository.save(viagem);
	}

	public void excluirPorId(Long id) {
		viagemRepository.deleteById(id);
	}

	public Viagem atualizar(Long id, Viagem viagem) {
		validarViagem(viagem);
		Viagem viagemSalva = viagemRepository.findById(id).orElseThrow();
		BeanUtils.copyProperties(viagem, viagemSalva, "id");
		return viagemRepository.save(viagemSalva);
	}

	private void validarViagem(Viagem viagem) {
		transporteRepository.findById(viagem.getTransporte().getId()).orElseThrow();
		motoristaRepository.findById(viagem.getMotorista().getId()).orElseThrow();
		solicitacaoRepository.findById(viagem.getSolicitacao().getId()).orElseThrow();
		//viagem.getPassageiros().stream().forEach(x -> pessoaRepository.findById(x.getId()).orElseThrow());

	}

	public PassageirosModel salvarPassageiros(PassageirosModel passageiro) {
		return passageirosRepository.save(passageiro);
	}

	public boolean existeMotorista(MotoristaModel motorista) { return viagemRepository.existsByMotorista(motorista); }

}
