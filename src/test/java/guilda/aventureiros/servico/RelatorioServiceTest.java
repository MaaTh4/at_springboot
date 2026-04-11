package guilda.aventureiros.servico;

import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.MissaoMetricasDTO;
import guilda.aventureiros.dto.RankingAventureiroDTO;
import guilda.aventureiros.repositorio.MissaoRepository;
import guilda.aventureiros.repositorio.ParticipacaoMissaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @Mock
    private MissaoRepository missaoRepository;

    @Mock
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    @InjectMocks
    private RelatorioService relatorioService;

    @Test
    void gerarRankingAventureiros_RetornaDadosAgregadosBaseadosNosRepositorios() {
        RankingAventureiroDTO mockDto = new RankingAventureiroDTO(1L, "Heroi", 5L, new BigDecimal("500.00"), 2L);
        when(participacaoMissaoRepository.obterRankingAventureiros(any(), any(), eq(StatusMissao.CONCLUIDA)))
                .thenReturn(List.of(mockDto));

        List<RankingAventureiroDTO> resultado = relatorioService.gerarRankingAventureiros(null, null, StatusMissao.CONCLUIDA);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.get(0).getTotalParticipacoes());
        assertEquals(new BigDecimal("500.00"), resultado.get(0).getTotalRecompensas());
    }

    @Test
    void gerarMetricasMissoes_RetornaDadosConsistentesSemDuplicidadeAgregados() {
        MissaoMetricasDTO mockMetric = new MissaoMetricasDTO("Super Missao", StatusMissao.EM_ANDAMENTO,
                NivelPerigo.ALTO, 3L, new BigDecimal("1000.00"));

        when(missaoRepository.obterMetricas(any(), any())).thenReturn(List.of(mockMetric));

        List<MissaoMetricasDTO> resultado = relatorioService.gerarMetricasMissoes(null, null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(3L, resultado.get(0).getQuantidadeParticipantes());
        assertEquals(new BigDecimal("1000.00"), resultado.get(0).getTotalRecompensasDistribuidas());
    }
}
