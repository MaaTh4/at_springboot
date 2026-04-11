package guilda.aventureiros.controle;

import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.MissaoDetalheDTO;
import guilda.aventureiros.dto.MissaoResumoDTO;
import guilda.aventureiros.servico.MissaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

import guilda.aventureiros.dominio.PainelTaticoMissao;
import guilda.aventureiros.servico.PainelTaticoMissaoService;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;
    private final PainelTaticoMissaoService painelTaticoMissaoService;

    public MissaoController(MissaoService missaoService, PainelTaticoMissaoService painelTaticoMissaoService) {
        this.missaoService = missaoService;
        this.painelTaticoMissaoService = painelTaticoMissaoService;
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> top15dias() {
        return ResponseEntity.ok(painelTaticoMissaoService.buscarTop15Dias());
    }

    @GetMapping
    public ResponseEntity<Page<MissaoResumoDTO>> listarMissoes(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo nivelPerigo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataTermino,
            Pageable pageable) {
        return ResponseEntity.ok(missaoService.listarMissoes(status, nivelPerigo, dataInicio, dataTermino, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalheDTO> detalharMissao(@PathVariable Long id) {
        return ResponseEntity.ok(missaoService.detalharMissao(id));
    }
}
