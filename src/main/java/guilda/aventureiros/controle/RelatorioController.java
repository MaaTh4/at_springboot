package guilda.aventureiros.controle;

import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.MissaoMetricasDTO;
import guilda.aventureiros.dto.RankingAventureiroDTO;
import guilda.aventureiros.servico.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/ranking-aventureiros")
    public ResponseEntity<List<RankingAventureiroDTO>> gerarRankingAventureiros(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataTermino,
            @RequestParam(required = false) StatusMissao statusMissao) {
        return ResponseEntity.ok(relatorioService.gerarRankingAventureiros(dataInicio, dataTermino, statusMissao));
    }

    @GetMapping("/missoes-metricas")
    public ResponseEntity<List<MissaoMetricasDTO>> gerarMetricasMissoes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataTermino) {
        return ResponseEntity.ok(relatorioService.gerarMetricasMissoes(dataInicio, dataTermino));
    }
}
