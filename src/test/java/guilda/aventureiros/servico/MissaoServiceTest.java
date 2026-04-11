package guilda.aventureiros.servico;

import guilda.aventureiros.dominio.*;
import guilda.aventureiros.dto.MissaoDetalheDTO;
import guilda.aventureiros.repositorio.MissaoRepository;
import guilda.aventureiros.excecoes.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissaoServiceTest {

    @Mock
    private MissaoRepository missaoRepository;

    @InjectMocks
    private MissaoService missaoService;

    private Missao missao;
    private Aventureiro aventureiro;
    private ParticipacaoMissao participacao;

    @BeforeEach
    void setUp() {
        missao = new Missao();
        missao.setId(1L);
        missao.setTitulo("Resgatar o Gato");
        missao.setStatus(StatusMissao.EM_ANDAMENTO);
        missao.setNivelPerigo(NivelPerigo.MEDIO);
        missao.setDataInicio(OffsetDateTime.now().minusDays(1));
        missao.setParticipacoes(new ArrayList<>());

        aventureiro = new Aventureiro();
        aventureiro.setId(1L);
        aventureiro.setNome("Heroi 1");

        participacao = new ParticipacaoMissao();
        participacao.setId(1L);
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapel(PapelMissao.LIDER);
        participacao.setRecompensaOuro(new BigDecimal("100.00"));
        participacao.setDestaqueMvp(true);
    }

    @Test
    void detalharMissao_ComParticipantes_RetornaSucesso() {
        missao.getParticipacoes().add(participacao);
        when(missaoRepository.findByIdComParticipantesEAventureiros(1L)).thenReturn(Optional.of(missao));

        MissaoDetalheDTO resultado = missaoService.detalharMissao(1L);

        assertNotNull(resultado);
        assertEquals("Resgatar o Gato", resultado.getTitulo());
        assertEquals(1, resultado.getParticipantes().size());
        assertEquals("Heroi 1", resultado.getParticipantes().get(0).getNomeAventureiro());
        assertTrue(resultado.getParticipantes().get(0).getDestaqueMvp());
    }

    @Test
    void detalharMissao_SemParticipantes_RetornaListaVaziaSemErro() {
        when(missaoRepository.findByIdComParticipantesEAventureiros(1L)).thenReturn(Optional.of(missao));

        MissaoDetalheDTO resultado = missaoService.detalharMissao(1L);

        assertNotNull(resultado);
        assertEquals("Resgatar o Gato", resultado.getTitulo());
        assertTrue(resultado.getParticipantes().isEmpty(), "Deve retornar lista vazia de participantes");
    }

    @Test
    void detalharMissao_MissaoInexistente_LancaExcecao() {
        when(missaoRepository.findByIdComParticipantesEAventureiros(99L)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> missaoService.detalharMissao(99L));
    }
}
