package guilda.loja.controle;

import guilda.loja.dominio.Produto;
import guilda.loja.servico.ProdutoBuscaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoBuscaController {

    private final ProdutoBuscaService buscaService;

    public ProdutoBuscaController(ProdutoBuscaService buscaService) {
        this.buscaService = buscaService;
    }

    // --- PARTE A: Buscas Textuais ---

    @GetMapping("/busca/nome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(buscaService.buscarPorNome(termo));
    }

    @GetMapping("/busca/descricao")
    public ResponseEntity<List<Produto>> buscarPorDescricao(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(buscaService.buscarPorDescricao(termo));
    }

    @GetMapping("/busca/frase")
    public ResponseEntity<List<Produto>> buscarPorFrase(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(buscaService.buscarPorFrase(termo));
    }

    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<Produto>> buscarFuzzy(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(buscaService.buscarFuzzy(termo));
    }

    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<Produto>> buscarMultiCampos(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(buscaService.buscarMultiCampos(termo));
    }

    // --- PARTE B: Filtros ---

    @GetMapping("/busca/com-filtro")
    public ResponseEntity<List<Produto>> buscarComFiltroCategoria(
            @RequestParam String termo,
            @RequestParam String categoria) throws IOException {
        return ResponseEntity.ok(buscaService.buscarComFiltroCategoria(termo, categoria));
    }

    @GetMapping("/busca/faixa-preco")
    public ResponseEntity<List<Produto>> buscarFaixaPreco(
            @RequestParam Double min,
            @RequestParam Double max) throws IOException {
        return ResponseEntity.ok(buscaService.buscarFaixaPreco(min, max));
    }

    @GetMapping("/busca/avancada")
    public ResponseEntity<List<Produto>> buscaAvancada(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Double min,
            @RequestParam Double max) throws IOException {
        return ResponseEntity.ok(buscaService.buscaAvancada(categoria, raridade, min, max));
    }

    // --- PARTE C: Agregações ---

    @GetMapping("/agregacoes/por-categoria")
    public ResponseEntity<Map<String, Long>> quantidadePorCategoria() throws IOException {
        return ResponseEntity.ok(buscaService.quantidadePorCategoria());
    }

    @GetMapping("/agregacoes/por-raridade")
    public ResponseEntity<Map<String, Long>> quantidadePorRaridade() throws IOException {
        return ResponseEntity.ok(buscaService.quantidadePorRaridade());
    }

    @GetMapping("/agregacoes/preco-medio")
    public ResponseEntity<Double> precoMedio() throws IOException {
        return ResponseEntity.ok(buscaService.precoMedio());
    }

    @GetMapping("/agregacoes/faixas-preco")
    public ResponseEntity<Map<String, Long>> faixasPreco() throws IOException {
        return ResponseEntity.ok(buscaService.faixasPreco());
    }
}
