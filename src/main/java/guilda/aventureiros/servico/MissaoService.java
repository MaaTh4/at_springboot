package guilda.aventureiros.servico;

import guilda.aventureiros.dominio.Missao;
import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.MissaoDetalheDTO;
import guilda.aventureiros.dto.MissaoResumoDTO;
import guilda.aventureiros.dto.ParticipanteMissaoDTO;
import guilda.aventureiros.repositorio.MissaoRepository;
import guilda.aventureiros.excecoes.RegraNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MissaoService {

    private final MissaoRepository missaoRepository;

    public MissaoService(MissaoRepository missaoRepository) {
        this.missaoRepository = missaoRepository;
    }

    public Page<MissaoResumoDTO> listarMissoes(StatusMissao status, NivelPerigo nivelPerigo,
                                               OffsetDateTime dataInicio, OffsetDateTime dataTermino,
                                               Pageable pageable) {
        return missaoRepository.buscarFiltrado(status, nivelPerigo, dataInicio, dataTermino, pageable)
                .map(m -> new MissaoResumoDTO(
                        m.getId(),
                        m.getTitulo(),
                        m.getStatus(),
                        m.getNivelPerigo(),
                        m.getCreatedAt(),
                        m.getDataInicio(),
                        m.getDataTermino()
                ));
    }

    public MissaoDetalheDTO detalharMissao(Long id) {
        Missao missao = missaoRepository.findByIdComParticipantesEAventureiros(id)
                .orElseThrow(() -> new RegraNegocioException("Missão não encontrada."));

        MissaoDetalheDTO dto = new MissaoDetalheDTO();
        dto.setId(missao.getId());
        dto.setTitulo(missao.getTitulo());
        dto.setStatus(missao.getStatus());
        dto.setNivelPerigo(missao.getNivelPerigo());
        dto.setDataInicio(missao.getDataInicio());
        dto.setDataTermino(missao.getDataTermino());

        dto.setParticipantes(missao.getParticipacoes().stream().map(p -> 
            new ParticipanteMissaoDTO(
                    p.getAventureiro().getNome(),
                    p.getPapel(),
                    p.getRecompensaOuro(),
                    p.getDestaqueMvp()
            )
        ).collect(Collectors.toList()));

        return dto;
    }
}
