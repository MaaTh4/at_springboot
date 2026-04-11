package guilda.aventureiros.servico;

import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.MissaoMetricasDTO;
import guilda.aventureiros.dto.RankingAventureiroDTO;
import guilda.aventureiros.repositorio.MissaoRepository;
import guilda.aventureiros.repositorio.ParticipacaoMissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelatorioService {

    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public RelatorioService(MissaoRepository missaoRepository, ParticipacaoMissaoRepository participacaoMissaoRepository) {
        this.missaoRepository = missaoRepository;
        this.participacaoMissaoRepository = participacaoMissaoRepository;
    }

    public List<RankingAventureiroDTO> gerarRankingAventureiros(OffsetDateTime dataInicio, OffsetDateTime dataTermino, StatusMissao statusMissao) {
        return participacaoMissaoRepository.obterRankingAventureiros(dataInicio, dataTermino, statusMissao);
    }

    public List<MissaoMetricasDTO> gerarMetricasMissoes(OffsetDateTime dataInicio, OffsetDateTime dataTermino) {
        return missaoRepository.obterMetricas(dataInicio, dataTermino);
    }
}
