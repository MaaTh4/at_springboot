package guilda.aventureiros.servico;

import guilda.aventureiros.dominio.PainelTaticoMissao;
import guilda.aventureiros.repositorio.PainelTaticoMissaoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
public class PainelTaticoMissaoService {

    private final PainelTaticoMissaoRepository repository;

    public PainelTaticoMissaoService(PainelTaticoMissaoRepository repository) {
        this.repository = repository;
    }

    public List<PainelTaticoMissao> buscarTop15Dias() {
        OffsetDateTime dataLimite = OffsetDateTime.now().minusDays(15);
        return repository.findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(dataLimite);
    }
}
