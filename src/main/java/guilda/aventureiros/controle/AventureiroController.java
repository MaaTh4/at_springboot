package guilda.aventureiros.controle;

import guilda.aventureiros.dominio.Classe;
import guilda.aventureiros.dto.AventureiroDetalheDTO;
import guilda.aventureiros.dto.AventureiroRequestDTO;
import guilda.aventureiros.dto.AventureiroResumoDTO;
import guilda.aventureiros.dto.CompanheiroRequestDTO;
import guilda.aventureiros.servico.AventureiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
@Validated
public class AventureiroController {

    private final AventureiroService service;

    @Autowired
    public AventureiroController(AventureiroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AventureiroResumoDTO> registrar(@Valid @RequestBody AventureiroRequestDTO dto) {
        AventureiroResumoDTO salvo = service.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Classe classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer minNivel,
            Pageable pageable) {
        
        Page<AventureiroResumoDTO> page = service.buscarFiltrado(nome, classe, ativo, minNivel, pageable);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        headers.add("X-Page", String.valueOf(page.getNumber()));
        headers.add("X-Size", String.valueOf(page.getSize()));
        headers.add("X-Total-Pages", String.valueOf(page.getTotalPages()));
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroDetalheDTO> buscar(@PathVariable Long id) {
        AventureiroDetalheDTO dto = service.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroResumoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AventureiroRequestDTO dto) {
        AventureiroResumoDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<Void> recrutar(@PathVariable Long id) {
        service.recrutar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroDetalheDTO> definirCompanheiro(@PathVariable Long id, @Valid @RequestBody CompanheiroRequestDTO dto) {
        AventureiroDetalheDTO atualizado = service.definirCompanheiro(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        service.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}
